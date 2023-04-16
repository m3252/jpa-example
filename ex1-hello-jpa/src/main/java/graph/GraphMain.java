package graph;

import graph.domain.*;

import javax.persistence.*;

public class GraphMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("graph");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address homeAddress = new Address("str1", "str2", "city", "zipcode");
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setHomeAddress(homeAddress);
            em.persist(member1);

//            Address copyAddress = new Address(homeAddress.getStreet1(), homeAddress.getStreet2(), homeAddress.getCity(), homeAddress.getZipcode());
//
//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setHomeAddress(copyAddress);
//            em.persist(member2);
//            member1.getHomeAddress().setCity("newCity");

            Address newHomeAddress = new Address(homeAddress.getStreet1(), homeAddress.getStreet2(), "newCity", homeAddress.getZipcode());
            member1.setHomeAddress(newHomeAddress);

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
