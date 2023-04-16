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

            member1.getFavoriteFoods().add("치킨");
            member1.getFavoriteFoods().add("족발");
            member1.getFavoriteFoods().add("피자");

            member1.getAddressHistory().add(new Address("old1", "old1", "old1", "old1"));
            member1.getAddressHistory().add(new Address("old2", "old2", "old2", "old2"));
            em.persist(member1);

            em.flush();
            em.clear();

            System.out.println("===========================");
            Member member = em.find(Member.class, member1.getId());

            List<Address> addressHistory = member.getAddressHistory();
            addressHistory.forEach(e-> System.out.println(e.getCity()));

            Set<String> favoriteFoods = member.getFavoriteFoods();
            favoriteFoods.forEach(System.out::println);

            // homeCity > newCity
            member.getAddressHistory().add(member.getHomeAddress());
            member.setHomeAddress(new Address("new1", "new2", "newCity", "1000"));

            // 치킨 > 한식
            member.getFavoriteFoods().remove("치킨");
            member.getFavoriteFoods().add("한식");

            //old1 > old3
            member.getAddressHistory().remove(new Address("old1", "old1", "old1", "old1"));
            member.getAddressHistory().add(new Address("old3", "old3", "old3", "old3"));

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
