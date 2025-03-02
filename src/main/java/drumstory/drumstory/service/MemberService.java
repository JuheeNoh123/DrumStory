package drumstory.drumstory.service;

import drumstory.drumstory.DTO.MemberDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.exception.ReservateException;
import drumstory.drumstory.exception.MemberLoginException;
import drumstory.drumstory.repository.MemberInterface;
import drumstory.drumstory.repository.ReservationInterface;
import drumstory.drumstory.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberInterface memberInterface;
    private final ReservationInterface reservationInterface;
    private final JwtUtility jwtUtility;

    public Member tokenToMember(HttpServletRequest request) {
        Member member = findByMemberNum(jwtUtility.getMemberNum(jwtUtility.resolveToken(request)));
        if (member != null) {
            return member;
        } else {
            throw new MemberLoginException("회원만 이용 가능합니다.\n회원 번호를 확인해주세요.", HttpStatus.UNAUTHORIZED);
        }
    }

    public Member findByMemberNum(String memberNum) {
        return memberInterface.findByMemberNum(memberNum);
    }

    public MemberDTO.ResponseLogin login(MemberDTO.MemberInfo request){
        Member member = findByMemberNum(request.getMemberNum());
        if (member == null) {
            throw new MemberLoginException("등록되지 않은 ID 입니다.", HttpStatus.BAD_REQUEST);
        }
        return new MemberDTO.ResponseLogin(jwtUtility.generateToken(member.getMemberNum()), member.getRole());
    }

    @Transactional
    public Reservation reservation(Member member, List<String> times, String date){
        LocalDate resDate = LocalDate.parse(date);
        if (times.size() > 2) {
            throw new ReservateException("최대 두 개의 시간만 선택할 수 있습니다.",HttpStatus.BAD_REQUEST);
        }

        // 오전/오후를 분리하여 처리
        String time1 = times.get(0).trim(); // 앞뒤 공백 제거
        String time2 = times.get(1).trim();     // 앞뒤 공백 제거

        time1 = convertTo24HourTime(time1); // 12시간제를 24시간제로 변환
        time2 = convertTo24HourTime(time2);     // 12시간제를 24시간제로 변환

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime sTime = LocalTime.parse(time1, formatter);
        LocalTime eTime = LocalTime.parse(time2, formatter);

        // 첫타임,두번째 타임 차이가 30이 아닐때 (연속안됐을떄)
        if (Duration.between(sTime, eTime).toMinutes() > 30) {
            throw new ReservateException("연속된 시간으로만 예약 가능합니다.",HttpStatus.BAD_REQUEST);
        }


        Reservation reservation = new Reservation(resDate,sTime,eTime,member);
        return reservationInterface.saveReservation(reservation);

    }


    private String convertTo24HourTime(String time) {
        // 오전/오후 구분
        boolean isPM = time.contains("오후");
        boolean isAM = time.contains("오전");

        // 오전/오후를 제거한 시간만 남긴다.
        time = time.replace("오전", "").replace("오후", "").trim();

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
}
