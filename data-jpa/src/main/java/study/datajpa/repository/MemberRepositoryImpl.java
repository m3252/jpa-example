package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import study.datajpa.entity.Member;

import java.util.List;

/**
 * 사용자 정의 리포지토리
 * 인터페이스 이름 + Impl 필수이다. (설정 변경 가능)
 * QueryDSL, jdbcTemplate 이 필요할 때 주로 사용한다.
 */
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
