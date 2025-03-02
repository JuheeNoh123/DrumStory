package drumstory.drumstory.service;

import drumstory.drumstory.domain.Member;
import drumstory.drumstory.exception.MemberAddException;
import drumstory.drumstory.exception.MemberLoginException;
import drumstory.drumstory.repository.MemberInterface;
import drumstory.drumstory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {
    private final MemberInterface memberRepository;

    @Transactional
    public Member add(String name, String phoneNumber, String memberNum){
        if(memberRepository.findByMemberNum(memberNum)!=null){
            throw new MemberAddException("회원 ID가 이미 존재합니다.", HttpStatus.BAD_REQUEST);
        }
        Member member = new Member(name, phoneNumber, memberNum);
        memberRepository.save(member);

        return member;
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }


}
