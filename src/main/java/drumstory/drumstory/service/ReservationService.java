package drumstory.drumstory.service;

import drumstory.drumstory.DTO.ReservationDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.domain.TimeTable;
import drumstory.drumstory.exception.ReservateException;
import drumstory.drumstory.repository.ReservationInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationInterface reservationInterface;
    @Transactional
    public ReservationDTO.ReservateTimeRes reservationTime(Member member, List<String> times, String date){
        LocalDate resDate = LocalDate.parse(date);
        if (times.size() > 2) {
            throw new ReservateException("최대 두 개의 시간만 선택할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }
        if (times.isEmpty()) {
            throw new ReservateException("예약 시간을 선택해주세요",HttpStatus.BAD_REQUEST);
        }
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
        String day = resDate.getDayOfWeek().toString();
        return new ReservationDTO.ReservateTimeRes(member.getName(),times,time1, endTime,resDate,day);

    }

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

    private String separateAMPM(String time){
        // 오전/오후를 제거한 시간만 남긴다.
        time = time.replace("오전", "").replace("오후", "").trim();
        return time;
    }

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


    public Reservation saveReservationTimeRoom(Member member, List<String> times, String date, int roomNum) {
        LocalDate resDate = LocalDate.parse(date);
        String time1;
        String time2;





//        Reservation reservation = new Reservation(resDate, )
//        reservationInterface.saveReservation()

        return null;
    }




    public List<Reservation> findAll() {
        return reservationInterface.findAll();
    }
}
