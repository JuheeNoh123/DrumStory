package drumstory.drumstory.service;

import drumstory.drumstory.DTO.ReservationDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.domain.Room;
import drumstory.drumstory.domain.TimeTable;
import drumstory.drumstory.exception.ReservateException;
import drumstory.drumstory.repository.ReservationInterface;
import drumstory.drumstory.repository.RoomInterface;
import drumstory.drumstory.repository.TimeTableInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationInterface reservationInterface;
    private final RoomInterface roomInterface;
    private final TimeTableInterface timeTableInterface;


    public ReservationDTO.ReservationTimeRes selectTime(Member member, List<Integer> resTimeIds, String date){
//        if (!reservationInterface.findReservationByMember(member)){
//            throw new ReservateException("이미 예약한 내역이 존재합니다.", HttpStatus.BAD_REQUEST);
//        }
        LocalDate resDate = LocalDate.parse(date);
        if (resTimeIds.size() > 2) {
            throw new ReservateException("최대 두 개의 시간만 선택할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }
        if (resTimeIds.isEmpty()) {
            throw new ReservateException("예약 시간을 선택해주세요",HttpStatus.BAD_REQUEST);
        }

        List<TimeTable> timeTablesList = new ArrayList<>();
        List<String> timeNameList = new ArrayList<>();  //18:30,19:00
        for (Integer timeId : resTimeIds) {
            TimeTable timeTable = timeTableInterface.findById(timeId);
            timeNameList.add(timeTable.getTimeTable());
            timeTablesList.add(timeTable);
        }
        List<String> StartEndTimes = startEndTime(timeNameList);

        String day = resDate.getDayOfWeek().toString();
        return new ReservationDTO.ReservationTimeRes(member.getName(),timeTablesList,StartEndTimes.get(0), StartEndTimes.get(1),resDate,day);

    }

    //오전 오후 다시 붙이기 + 24시간제를 12시간제로
    private String formatToAmPm(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        String period = (hour < 12) ? "오전" : "오후";

        // 24시간제를 12시간제로 변환
        int hour12;
        if (hour > 12) {
            // 오후 시간 (13~23시)는 12를 빼서 12시간제로 변환
            hour12 = hour - 12;
        } else {
            // 나머지는 그대로 사용 (0~12시)
            hour12 = hour;
        }

        return String.format("%s %02d:%02d", period, hour12, minute);
    }


    //방정보 받아서 예약 진행
    @Transactional
    public ReservationDTO.ReservationTimeRoomRes saveReservationTimeRoom(Member member, List<Integer> resTimeIds, String date, Long roomId) {

        LocalDate resDate = LocalDate.parse(date);
        String day = resDate.getDayOfWeek().toString();
        Room room = roomInterface.findById(roomId);

        List<String> timetableString = new ArrayList<>();

        //예약
        for (int timeId : resTimeIds) {
            TimeTable timeTable = timeTableInterface.findById(timeId);
            timetableString.add(timeTable.getTimeTable());
            Reservation reservation = new Reservation(resDate, timeTable,member,room);

            reservationInterface.saveReservation(reservation);

        }

        List<String> StartEndTimes = startEndTime(timetableString); //시작 시간, 끝 시간 구하기


        return new ReservationDTO.ReservationTimeRoomRes(member.getName(), StartEndTimes.get(0), StartEndTimes.get(1), resDate, day, room.getRoomNum());
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime Time1 = LocalTime.parse(time1, formatter);
        LocalTime Time2 = LocalTime.parse(time2, formatter);

        // 첫타임,두번째 타임 차이가 30이 아닐때 (연속안됐을떄)
        if (Duration.between(Time1, Time2).toMinutes() > 30) {
            throw new ReservateException("연속된 시간으로만 예약 가능합니다.",HttpStatus.BAD_REQUEST);
        }


        // 예약 끝 시간 계산 (30분 추가)
        LocalTime endTimeObj = Time2.plusMinutes(30);

        System.out.println(endTimeObj);
        String endTime = formatToAmPm(endTimeObj);
        String startTime = formatToAmPm(Time1);
        System.out.println(endTime);

        return List.of(new String[]{startTime, endTime});
    }



    public List<ReservationDTO.ReservationListRes> findAll(LocalDate resDate) {
        List<Reservation> reservationList = reservationInterface.findAll(resDate);
        List<ReservationDTO.ReservationListRes> reservationInfo= new ArrayList<>();
        for (Reservation reservation : reservationList) {
            ReservationDTO.ReservationListRes reservationListRes = new ReservationDTO.ReservationListRes(reservation.getTime().getTimeTable(), reservation.getMember().getName(), reservation.getRoom().getRoomNum(), reservation.getId());
            reservationInfo.add(reservationListRes);
        }
        return reservationInfo;

    }

    public List<TimeTable> findAvailableTimes(LocalDate resDate) {
        LocalDate currentDate = LocalDate.now();
        if (resDate.isAfter(currentDate)) {
            return timeTableInterface.getAllTimeTables();
        }
        LocalTime currentTime = LocalTime.now();

        List<Reservation> reservations = reservationInterface.findByResDate(resDate);
        System.out.println(reservations);


        List<TimeTable> allTimeTables = timeTableInterface.getAllTimeTables();

        // 현재 시간을 30분 단위로 반올림 (예: 16:20 -> 16:00, 16:30 -> 16:30)
        LocalTime roundedCurrentTime = currentTime.withSecond(0).withNano(0).withMinute(currentTime.getMinute() / 30 * 30);

        // 예약 가능한 시간만 필터링하는 스트림
        List<TimeTable> availableTimeTables = allTimeTables.stream()
                .filter(timeTable -> {
                    LocalTime time = LocalTime.parse(timeTable.getTimeTable());
                    return time.isAfter(roundedCurrentTime) || time.equals(roundedCurrentTime);
                })
                .toList();

        // 연습실별로 예약된 시간이 없으면 그 시간은 예약 가능
        // 연습실이 모두 예약되어 있는 시간을 제외
        List<TimeTable> finalAvailableTimes = new ArrayList<>();
        for (TimeTable timeTable : availableTimeTables) {
            // 해당 시간대에 예약된 방들을 찾음
            List<Room> bookedRooms = reservations.stream()
                    .filter(reservation ->{
                                LocalTime reservationTime = LocalTime.parse(reservation.getTime().getTimeTable()); // 또는 LocalTime으로 변경
                                LocalTime timeTableTime = LocalTime.parse(timeTable.getTimeTable());
                                return reservationTime.equals(timeTableTime);
                            })  // 해당 시간대의 예약 찾기
                    .map(Reservation::getRoom)  // 예약된 방만 추출
                    .toList();

            System.out.println(timeTable.getTimeTable()); // 현재 시간대 출력
            System.out.println(bookedRooms.size()); // 예약된 방 개수 출력


            // 예약된 방이 5개 미만이면 해당 시간은 예약 가능한 시간으로 간주
            // 만약 예약된 방이 5개 미만이면 해당 시간을 예약 가능 시간으로 추가
            if (bookedRooms.size() < 5) {
                finalAvailableTimes.add(timeTable);
            }
        }

        return finalAvailableTimes;

    }

    public List<Room> findAvailableRooms(LocalDate resDate, List<Integer> reservedTimes) {
        List<TimeTable> timeTables = timeTableInterface.findAllByIds(reservedTimes);
        System.out.println("Reserved Times: " + timeTables);


        List<Reservation> reservedReservations = reservationInterface.findReservationsByDateAndTimes(resDate, timeTables);
        for (Reservation reservation : reservedReservations) {
            System.out.println(reservation.getId());
        }
        // 예약된 방들의 리스트에서 예약된 방들을 제외한 방들을 찾기
        List<Long> reservedRoomIds = reservedReservations.stream()
                .map(reservation -> reservation.getRoom().getId())
                .collect(Collectors.toList());

        System.out.println("Reserved Room IDs: " + reservedRoomIds);

        // 예약되지 않은 방들 필터링
        List<Room> allRooms = roomInterface.findAll();

        List<Room> availableRooms = allRooms.stream()
                .filter(room -> !reservedRoomIds.contains(room.getId())) // 예약된 방 제외
                .collect(Collectors.toList());

        System.out.println("Available Rooms: " + availableRooms);  // 최종 예약되지 않은 방 출력

        return availableRooms;

    }

    @Transactional
    @Scheduled(cron = "0 0/30 * * * *", zone = "Asia/Seoul") // 매 시간 0분, 30분마다 실행
    public void cleanUpExpiredReservations() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // 원하는 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // LocalTime을 String으로 변환
        String formattedTime = currentTime.format(formatter);

        // 현재 시간에 해당하는 time_table 찾기
        TimeTable currentTimeTable = timeTableInterface.findByTime(formattedTime);

        int timeTableId = currentTimeTable.getId();
        reservationInterface.deletePastReservations(currentDate, timeTableId);

    }
    @Transactional
    public void deleteReservation(Member member) {
        reservationInterface.deleteReservationByMember(member);
    }




}
