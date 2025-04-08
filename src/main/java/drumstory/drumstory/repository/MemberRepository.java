package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.RoleType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository implements MemberInterface{
    private final EntityManager em;
    @Override
    public Member findById(long Id) {
        try {
            return em.createQuery("SELECT m FROM Member m WHERE m.id = :id", Member.class)
                    .setParameter("id", Id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // 결과가 없으면 null 반환
        }
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

    @Override
    public List<Member> findAll() {
        RoleType roleMember = RoleType.ROLE_MEMBER;
        return em.createQuery("SELECT m FROM Member m where m.role=:roleMember", Member.class)
                .setParameter("roleMember", roleMember)
                .getResultList();
    }

    @Override
    public Member findAdmin() {
        RoleType roleAdmin = RoleType.ROLE_ADMIN;
        try{
            return em.createQuery("SELECT m FROM Member m where m.role=:roleAdmin", Member.class)
                    .setParameter("roleAdmin", roleAdmin)
                    .getSingleResult();
        }
         catch (NoResultException e) {
            return null; // 결과가 없으면 null 반환
        }
    }

    @Override
    public void delete(Member member) {
        em.remove(member);
    }

    @Override
    public String getMemberName(String memberNum) {
        try {
            Member member = em.createQuery("SELECT m FROM Member m WHERE m.memberNum = :memberNum", Member.class)
                    .setParameter("memberNum", memberNum)
                    .getSingleResult();
            return member.getMemberNum();
        } catch (NoResultException e) {
            return null; // 결과가 없으면 null 반환
        }
    }

}
