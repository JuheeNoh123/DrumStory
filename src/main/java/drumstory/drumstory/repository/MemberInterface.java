package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Member;

import java.util.List;

public interface MemberInterface {
    public Member findById(long Id);

    public Member findByMemberNum(String memberNum);

    public void save(Member member);

    public List<Member> findAll();

    public Member findAdmin();

    public void delete(Member member);

    public String getMemberName(String memberNum);
}
