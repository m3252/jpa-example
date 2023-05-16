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
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); // 1차 캐시
        assertThat(findMember).isSameAs(member);
    }

    @Test
    void curd() {
        Member member1 = new Member("membe1");
        Member member2 = new Member("membe2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // 단건 조회
        Member find1 = memberJpaRepository.findById(member1.getId()).get();
        Member find2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(find1).isEqualTo(member1);
        assertThat(find2).isEqualTo(member2);

        // 리스트 조회
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isSameAs(2);

        long count = memberJpaRepository.count();
        assertThat(count).isSameAs(2L);

        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        assertThat(memberJpaRepository.count()).isSameAs(0L);

    }

    @Test
    void findByUsernameAndAgeGreaterThen() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void namedQuery() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsername("AAA");

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(10);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void paging() {
        memberJpaRepository.save(new Member("AAA", 10));
        memberJpaRepository.save(new Member("AAA", 10));
        memberJpaRepository.save(new Member("AAA", 10));
        memberJpaRepository.save(new Member("AAA", 10));
        memberJpaRepository.save(new Member("AAA", 10));
        memberJpaRepository.save(new Member("AAA", 20));
        memberJpaRepository.save(new Member("AAA", 30));
        memberJpaRepository.save(new Member("AAA", 40));

        int age = 10;
        int offset = 1;
        int limit = 3;

        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);
    }

    @Test
    void bulkAgePlus() {
        memberJpaRepository.save(new Member("AAA", 10));
        memberJpaRepository.save(new Member("AAA", 19));
        memberJpaRepository.save(new Member("AAA", 20));
        memberJpaRepository.save(new Member("AAA", 21));
        memberJpaRepository.save(new Member("AAA", 40));

        int i = memberJpaRepository.bulkAgePlus(20);

        assertThat(i).isEqualTo(3);
    }

}
