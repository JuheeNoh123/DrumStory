package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository implements MemberInterface {
    private final EntityManager em;

    @Override
    public Member findByMemberNum(String memberNum) {
        List<Member> resultList = em.createQuery("select m from Member m where m.memberNum = :memberNum", Member.class)
                .setParameter("memberNum", memberNum)
                .getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }


}
