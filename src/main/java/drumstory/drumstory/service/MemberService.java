package drumstory.drumstory.service;

import drumstory.drumstory.DTO.MemberDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.exception.MemberLoginException;
import drumstory.drumstory.repository.MemberInterface;
import drumstory.drumstory.repository.MemberRepository;
import drumstory.drumstory.security.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberInterface memberInterface;
    private final JwtUtility jwtUtility;


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
}
