package drumstory.drumstory.service;

import drumstory.drumstory.DTO.MemberDTO;
import drumstory.drumstory.DTO.ReservationDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.exception.ReservateException;
import drumstory.drumstory.exception.UnregisteredMemberIdException;
import drumstory.drumstory.repository.MemberInterface;
import drumstory.drumstory.repository.ReservationInterface;
import drumstory.drumstory.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberInterface memberInterface;
    private final ReservationInterface reservationInterface;
    private final JwtUtility jwtUtility;
    private final ReservationService reservationService;

    public Member tokenToMember(HttpServletRequest request) throws Exception {
        Member member = findByMemberNum(jwtUtility.getMemberNum(jwtUtility.resolveToken(request)));
        if (member != null) {
            return member;
        } else {
            throw new UnregisteredMemberIdException("회원만 이용 가능합니다.\n회원 번호를 확인해주세요.", HttpStatus.UNAUTHORIZED);
        }
    }

    public Member findByMemberNum(String memberNum) {
        return memberInterface.findByMemberNum(memberNum);
    }

    public MemberDTO.ResponseLogin login(MemberDTO.MemberInfo request) throws Exception {
        Member member = findByMemberNum(request.getMemberNum());
        if (member == null) {
            throw new UnregisteredMemberIdException("등록되지 않은 ID 입니다.", HttpStatus.BAD_REQUEST);
        }
        List<Reservation> reservationList = reservationInterface.findReservationByMember(member);
        String token = jwtUtility.generateToken(member.getMemberNum());

        if (!reservationList.isEmpty()) {
            List<Integer> resTimeIds = new ArrayList<>();
            String roomNum = reservationList.getFirst().getRoom().getRoomNum();
            for (Reservation reservation : reservationList) {
                resTimeIds.add(reservation.getTime().getId());
            }
            ReservationDTO.ReservationTimeRes reservationTimeRes  = reservationService.selectTime(member,resTimeIds, String.valueOf(reservationList.getFirst().getResDate()));
            ReservationDTO.ReservationTimeRoomRes  reservationTimeRoomRes = new ReservationDTO.ReservationTimeRoomRes(member.getName(),
                    reservationTimeRes.getStartTime(),reservationTimeRes.getEndTime(),reservationTimeRes.getResDate(),
                    reservationTimeRes.getResDay(),roomNum);
            return new MemberDTO.ResponseLogin(token,member.getRole(),reservationTimeRoomRes, member.getName());
        }
        else{
            return new MemberDTO.ResponseLogin(token,member.getRole(),null,member.getName());
        }


    }


}
