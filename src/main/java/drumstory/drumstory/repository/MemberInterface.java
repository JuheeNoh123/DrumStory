package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Member;

public interface MemberInterface {
    Member findByMemberNum(String memberNum);

    Member save(Member member);
}
