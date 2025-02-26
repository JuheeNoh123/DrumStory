package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository implements MemberInterface{
    private final EntityManager em;
    @Override
    public Member findById(Long Id) {
        return em.find(Member.class, Id);
    }

    @Override
    public Member findByMemberNum(String memberNum) {
        try {
            return em.createQuery("SELECT m FROM Member m WHERE m.memberNum = :memberNum", Member.class)
                    .setParameter("memberNum", memberNum)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // 결과가 없으면 null 반환
        }
    }

    @Override
    public void save(Member member) {
        em.persist(member);
    }

}
