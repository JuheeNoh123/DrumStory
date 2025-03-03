package drumstory.drumstory.service;

import drumstory.drumstory.DTO.MemberDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.exception.UnregisteredMemberIdException;
import drumstory.drumstory.repository.MemberInterface;
import drumstory.drumstory.repository.ReservationInterface;
import drumstory.drumstory.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
            throw new UnregisteredMemberIdException("회원만 이용 가능합니다.\n회원 번호를 확인해주세요.", HttpStatus.UNAUTHORIZED);
        }
    }

    public Member findByMemberNum(String memberNum) {
        return memberInterface.findByMemberNum(memberNum);
    }

    public MemberDTO.ResponseLogin login(MemberDTO.MemberInfo request){
        Member member = findByMemberNum(request.getMemberNum());
        if (member == null) {
            throw new UnregisteredMemberIdException("등록되지 않은 ID 입니다.", HttpStatus.BAD_REQUEST);
        }
        return new MemberDTO.ResponseLogin(jwtUtility.generateToken(member.getMemberNum()), member.getRole());
    }

}
