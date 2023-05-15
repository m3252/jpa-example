package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveMember() {
        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); // 1차 캐시
        assertThat(findMember).isSameAs(member);
    }

    @Test
    void curd() {
        Member member1 = new Member("membe1");
        Member member2 = new Member("membe2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회
        Member find1 = memberRepository.findById(member1.getId()).get();
        Member find2 = memberRepository.findById(member2.getId()).get();
        assertThat(find1).isEqualTo(member1);
        assertThat(find2).isEqualTo(member2);

        // 리스트 조회
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isSameAs(2);

        long count = memberRepository.count();
        assertThat(count).isSameAs(2L);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        assertThat(memberRepository.count()).isSameAs(0L);

    }

    @Test
    void findByUsernameAndAgeGreaterThen() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findHellBy() {
        List<Member> helloBy = memberRepository.findTop3HelloBy();
    }

    @Test
    void namedQuery1() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("AAA");

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(10);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void namedQuery2() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("AAA", 10);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0)).isEqualTo(m1);
    }


}