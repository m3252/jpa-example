package jpabasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member findMember1 = em.find(Member.class, 100L);
            Member findMember2 = em.find(Member.class, 100L);
            // 엔티티는 식별자를 가진 객체이다. 식별자가 같으므로 같은 객체이다.
            // JPA는 1차 캐시로 반복 가능한 읽기(repeatable read) 등급의 트랜잭션 격리 수준을 애플리케이션 차원에서 제공한다.
            System.out.println(findMember1 == findMember2);

            // 비영속 시작
            Member member = new Member();
            member.setId(101L);
            member.setName("101번");
            // 비영속 끝


            System.out.println("====== 시작 ======");
            em.persist(member); //영속하다
//            em.detach(member); //영속에서 분리하다
            System.out.println("====== 끝 ======");

            Member member101 = em.find(Member.class, 101L);
            System.out.println("member101.getId() = " + member101.getId());
            System.out.println("member101.getName() = " + member101.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}