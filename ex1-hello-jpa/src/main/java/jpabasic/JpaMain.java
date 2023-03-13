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
            MemberTest memberTest1 = new MemberTest();
            memberTest1.setUsername("A");

            MemberTest memberTest2 = new MemberTest();
            memberTest2.setUsername("B");

            MemberTest memberTest3 = new MemberTest();
            memberTest3.setUsername("C");

            System.out.println("==================");

            // DB SEQ = 1  | 1
            // DB SEQ = 51 | 2
            // DB SEQ = 51 | 3

            em.persist(memberTest1);
            em.persist(memberTest2);
            em.persist(memberTest3);

            System.out.println("member1.getId() = " + memberTest1.getId());
            System.out.println("member2.getId() = " + memberTest2.getId());
            System.out.println("member3.getId() = " + memberTest3.getId());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}