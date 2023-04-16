package graph;

import graph.domain.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class GraphMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("graph");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            String qlString = "select m from Member m where m.username like '%kim%'";
            List<Member> resultList = em.createQuery(qlString, Member.class)
                    .getResultList();

            String nativeString = "select MEMBER_ID, city, zipcode from MEMBER";
            em.createNativeQuery(nativeString).getResultList();

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
