package study.datajpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

//    @Query(name = "Member.findByUsername") 관례를 통해 NamedQuery를 사용할 수 있음
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age") // 런타임 시점에 쿼리 검증이 가능하다. (쿼리가 잘못되었을 경우 오류 발생)
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m") // new operation을 통해서 DTO로 바로 조회 가능
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t") // DTO로 바로 조회 가능
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names") // in 절을 통해 여러 개의 파라미터를 받을 수 있음
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); // 컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

//    @Query(value = "select m from Member m left join m.team t",
//            countQuery = "select count(m.username) from Member m") // 페이징 최적화
    Page<Member> findByAge(int age, Pageable pageable); // 페이징

    @Modifying(clearAutomatically = true) // 벌크 연산 후 영속성 컨텍스트 초기화
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age") // 벌크 연산
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) // JPA 표준 스펙
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

//    @EntityGraph(attributePaths = {"team"})
    @EntityGraph(value = "Member.all")
    List<Member> findMemberEntityGraphByUsername(@Param("username") String username);

    @QueryHints(
            @QueryHint(name = "org.hibernate.readOnly", value = "true")
    )// JPA 표준 스펙이 아닌 하이버네이트 전용 힌트
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // JPA 표준 스펙
    List<Member> findLockByUsername(String username);
}
