package jpql;

import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(30);
            member.setType(MemberType.ADMIN);

            member.changeTeam(team);
            em.persist(member);


            em.flush();
            em.clear();

//            String query = "select m.username, 'HELLO', TRUE FROM Member m "
//                    + "where m.type = jpql.domain.MemberType.USER";
            String query = "select m.username, 'HELLO', TRUE FROM Member m "
                    + "where m.type = :userType";
            List<Object[]> resultList = em.createQuery(query)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
