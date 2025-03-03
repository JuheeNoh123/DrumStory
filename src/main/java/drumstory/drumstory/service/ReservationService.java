package drumstory.drumstory.service;

import drumstory.drumstory.DTO.ReservationDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.domain.Room;
import drumstory.drumstory.domain.TimeTable;
import drumstory.drumstory.exception.ReservateException;
import drumstory.drumstory.repository.ReservationInterface;
import drumstory.drumstory.repository.RoomInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationInterface reservationInterface;
    private final RoomInterface roomInterface;


    public ReservationDTO.ReservationTimeRes selectTime(Member member, List<String> times, String date){
        LocalDate resDate = LocalDate.parse(date);
        if (times.size() > 2) {
            throw new ReservateException("최대 두 개의 시간만 선택할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }
        if (times.isEmpty()) {
            throw new ReservateException("예약 시간을 선택해주세요",HttpStatus.BAD_REQUEST);
        }
        List<String> StartEndTimes = startEndTime(times);

        String day = resDate.getDayOfWeek().toString();
        return new ReservationDTO.ReservationTimeRes(member.getName(),times,StartEndTimes.get(0), StartEndTimes.get(1),resDate,day);

    }

    //오전 오후 다시 붙이기
    private String formatToAmPm(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        String period = (hour < 12) ? "오전" : "오후";

        // 24시간제를 12시간제로 변환
        int hour12;
        if (hour == 0) {
            // 24시간제에서 0시는 12시(자정)로 변환
            hour12 = 12;
        } else if (hour > 12) {
            // 오후 시간 (13~23시)는 12를 빼서 12시간제로 변환
            hour12 = hour - 12;
        } else {
            // 나머지는 그대로 사용 (1~12시)
            hour12 = hour;
        }

        return String.format("%s %02d:%02d", period, hour12, minute);
    }

    //오전 오후 뜯어내기
    private String separateAMPM(String time){
        // 오전/오후를 제거한 시간만 남긴다.
        time = time.replace("오전", "").replace("오후", "").trim();
        return time;
    }

    //24시로 시간 변환
    private String convertTo24HourTime(String time) {
        time = separateAMPM(time);

        boolean isPM = time.contains("오후");
        boolean isAM = time.contains("오전");

        // 시간 문자열을 12시간제에서 24시간제로 변환
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // 오후인 경우 12를 더해준다. (12시간제 -> 24시간제)
        if (isPM && hour < 12) {
            hour += 12;
        } else if (isAM && hour == 12) {
            // 오전 12시 -> 자정으로 처리
            hour = 0;
        }

        // 24시간제로 변환된 시간을 다시 "HH:mm" 형식으로 반환
        return String.format("%02d:%02d", hour, minute);
    }

    //방정보 받아서 예약 진행
    @Transactional
    public ReservationDTO.ReservationTimeRoomRes saveReservationTimeRoom(Member member, List<String> times, String date, String roomNum) {
        LocalDate resDate = LocalDate.parse(date);
        String day = resDate.getDayOfWeek().toString();
        Room room = roomInterface.findByRoomNum(roomNum);



        //예약
        for (String time : times) {
            // time을 사용하여 원하는 로직을 처리
            String separateTime = separateAMPM(time);
            TimeTable timeTable = reservationInterface.getTimeTableByTime(separateTime);
            Reservation reservation = new Reservation(resDate, timeTable,member,room);

            reservationInterface.saveReservation(reservation);

        }

        List<String> StartEndTimes = startEndTime(times);


        return new ReservationDTO.ReservationTimeRoomRes(member.getName(), StartEndTimes.get(0), StartEndTimes.get(1), resDate, day, roomNum);
    }

    //시작 시간, 끝시간 계산
    private List<String> startEndTime(List<String> times){
        String time1;
        String time2;
        if (times.size() == 2) {
            // 오전/오후를 분리하여 처리
            time1 = times.get(0).trim(); // 앞뒤 공백 제거
            time2 = times.get(1).trim();     // 앞뒤 공백 제거
        }
        else {
            time1 = times.getFirst().trim();
            time2 = time1;
        }
        String time1_24 = convertTo24HourTime(time1);
        String time2_24 = convertTo24HourTime(time2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime Time1 = LocalTime.parse(time1_24, formatter);
        LocalTime Time2 = LocalTime.parse(time2_24, formatter);

        // 첫타임,두번째 타임 차이가 30이 아닐때 (연속안됐을떄)
        if (Duration.between(Time1, Time2).toMinutes() > 30) {
            throw new ReservateException("연속된 시간으로만 예약 가능합니다.",HttpStatus.BAD_REQUEST);
        }


        // 예약 끝 시간 계산 (30분 추가)
        LocalTime endTimeObj = Time2.plusMinutes(30);
        String endTime = formatToAmPm(endTimeObj);
        List<String> StartEndTimes = new ArrayList<>();
        StartEndTimes.add(time1);
        StartEndTimes.add(endTime);
        return StartEndTimes;
    }



    public List<Reservation> findAll() {
        return reservationInterface.findAll();
    }

    public List<TimeTable> findAvailableTimes(LocalDate resDate) {
        LocalTime currentTime = LocalTime.now();

        List<Reservation> reservations = reservationInterface.findByResDate(resDate);

        //reservation리스트에서 예약한 시간만 뽑아서 리스트로 수정
        List<TimeTable> reservedTimes = reservations.stream()
                .map(Reservation::getTime)
                .collect(Collectors.toList());

        List<TimeTable> allTimeTables = reservationInterface.getAllTimeTables();

        // 현재 시간을 30분 단위로 반올림 (예: 16:20 -> 16:00, 16:30 -> 16:30)
        LocalTime roundedCurrentTime = currentTime.withSecond(0).withNano(0).withMinute(currentTime.getMinute() / 30 * 30);

        // allTimeTables 리스트를 스트림으로 변환
        return allTimeTables.stream()
                // 시간표에서 예약 가능한 시간만 필터링
                .filter(timeTable -> {
                    // 각 TimeTable 객체에서 시간을 문자열로 가져와 LocalTime 객체로 변환
                    LocalTime time = LocalTime.parse(timeTable.getTimeTable());

                    // 현재 시간 이후이거나 현재 시간이랑 같은 시간 (정각)에 예약 가능 시간 포함
                    return time.isAfter(roundedCurrentTime) || time.equals(roundedCurrentTime);
                })
                // 이미 예약된 시간 목록에 포함되지 않은 시간만 필터링
                .filter(time -> !reservedTimes.contains(time))
                // 예약 가능한 시간들을 리스트로 수집하여 반환
                .collect(Collectors.toList());

    }

}
