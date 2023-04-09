package graph;

import graph.domain.Child;
import graph.domain.Member;
import graph.domain.Parent;
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
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            em.persist(child1);
            em.persist(child2);

            em.flush();
            em.clear();

//            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildren().remove(0);
            Parent findParent = em.find(Parent.class, parent.getId());
            em.remove(findParent);



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
