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
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setHomeAddress(new Address("str1", "str2", "homeCity", "1000"));

            member1.getAddressHistory().add(new AddressEntity("old1", "old1", "old1", "old1"));
            member1.getAddressHistory().add(new AddressEntity("old2", "old2", "old2", "old2"));
            em.persist(member1);

            em.flush();
            em.clear();

            System.out.println("===========================");
            Member member = em.find(Member.class, member1.getId());

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
