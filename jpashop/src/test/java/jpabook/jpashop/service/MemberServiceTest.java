package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepositoryOld memberRepositoryOld;

    @Autowired
    EntityManager em;

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("moon");

        Long joinId = memberService.join(member);
        em.flush();

        assertEquals(member, memberRepositoryOld.findOne(joinId));
    }

    @Test
    void 중복회원예외() {
        Member member1 = new Member();
        member1.setName("moon");

        Member member2 = new Member();
        member2.setName("moon");

        memberService.join(member1);
        assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalStateException.class);
    }
}