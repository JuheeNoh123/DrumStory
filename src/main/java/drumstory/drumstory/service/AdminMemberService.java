package drumstory.drumstory.service;

import drumstory.drumstory.domain.Member;
import drumstory.drumstory.exception.DuplicatedMemberIdException;
import drumstory.drumstory.exception.UnregisteredMemberIdException;
import drumstory.drumstory.repository.MemberInterface;
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
            throw new DuplicatedMemberIdException("회원 ID가 이미 존재합니다.", HttpStatus.CONFLICT); //409 conflict 에러 반환
        }
        Member member = new Member(name, phoneNumber, memberNum);
        memberRepository.save(member);

        return member;
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    // ID/닉네임/전화번호 수정
    @Transactional
    public Member update(String oldMemberNum, String name, String phoneNumber, String memberNum) {
        Member member = memberRepository.findByMemberNum(oldMemberNum);
        //회원 ID를 바꿨을 경우, 기존 아이디와 같지 않으면서 DB에 바꾼 ID가 존재하면 안됨
        if(!oldMemberNum.equals(memberNum) && memberRepository.findByMemberNum(memberNum)!=null){
            throw new DuplicatedMemberIdException("회원 ID가 이미 존재합니다.", HttpStatus.CONFLICT);
        }

        member.setMemberNum(memberNum);
        member.setName(name);
        member.setPhoneNumber(phoneNumber);
        return member;

    }

    @Transactional
    public Member delete(String memberNum){
        if(memberRepository.findByMemberNum(memberNum)==null){
            throw new UnregisteredMemberIdException("등록되지 않은 ID 입니다.", HttpStatus.CONFLICT); //409 conflict 에러 반환
        }
        Member member = memberRepository.findByMemberNum(memberNum);
        memberRepository.delete(member);

        return member;
    }


}
