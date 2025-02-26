package drumstory.drumstory.service;

import drumstory.drumstory.domain.Member;
import drumstory.drumstory.repository.MemberInterface;
import drumstory.drumstory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {
    private final MemberInterface memberRepository;

    @Transactional
    public Member add(String name, String phoneNumber, String memberNum){
        if(memberRepository.findByMemberNum(memberNum)!=null){
            return null;
        }
        Member member = new Member(name, phoneNumber, memberNum);
        memberRepository.save(member);

        return member;
    }
}
