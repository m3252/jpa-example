package jpql;

import jpql.domain.Member;
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

            member.changeTeam(team);
            em.persist(member);


            em.flush();
            em.clear();

//            List<Member> resultList = em.createQuery("select m from Member m left join m.team t", Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
            String qlString = "select m from Member m, Team t where m.username = t.name";
            List<Member> crossJoin = em.createQuery(qlString, Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("crossJoin.size() = " + crossJoin.size());

            // 조인 대상 필터링
            // String query = "select m, t from Member m left join m.team t on t.name = 'teamA'";

            // 실제 SQL
            // select m.*, t.* from Member m
            // left join Team t on m.TEAM_ID = t.id and t.name = 'teamA'


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
