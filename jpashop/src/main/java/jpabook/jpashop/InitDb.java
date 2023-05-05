package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.doInit1();
        initService.doInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void doInit1() {
            // Create a member
            Member member = createMember("userA", "서울", "1", "1111");
            em.persist(member);

            // Create a book
            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            // Create an order item
            OrderItem orderItem1 = OrderItem.createOrderitem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderitem(book2, 10000, 2);

            // Create a delivery
            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void doInit2() {
            // Create a member
            Member member = createMember("userB", "부산", "2", "2222");
            em.persist(member);

            // Create a book
            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book2);

            // Create an order item
            OrderItem orderItem1 = OrderItem.createOrderitem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderitem(book2, 40000, 4);

            // Create a delivery
            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private static Member createMember(String username, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(username);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private static Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
