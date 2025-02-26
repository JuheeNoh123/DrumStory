package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Member;

public interface MemberInterface {
    public Member findById(Long Id);

    public Member findByMemberNum(String memberNum);
}
