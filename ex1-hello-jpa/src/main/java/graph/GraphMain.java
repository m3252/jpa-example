package graph;

import graph.domain.Member;
import graph.domain.Team;

import javax.persistence.*;
import java.util.List;

public class GraphMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("graph");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("팀스카이");

            em.persist(team);

            Team team2 = new Team();
            team2.setName("팀AX");

            em.persist(team2);

            Member member = new Member();
            member.setUsername("후롬");
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("발롱");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
//            System.out.println("findMember.getTeam().getClass() = " + findMember.getTeam().getClass());
//            System.out.println("==================");
//            System.out.println(findMember.getTeam().getName());
//            System.out.println("==================");

            List<Member> selectMFromMemberM = em.createQuery("Select m from Member m join fetch m.team", Member.class)
                    .getResultList();


            selectMFromMemberM.forEach(System.out::println);



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
