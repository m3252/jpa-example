package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OrderRepository {
    private final EntityManager em;

    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    public void saveOrder(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

//    public List<Order> findAll(OrderSearch orderSearch) {}
}
