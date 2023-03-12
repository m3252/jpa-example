package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void 상품주문() {
        Member member = createMember();
        Item book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, order.getOrderItems().size());
        assertEquals("주문한 가격은 가격 * 수량이다.", 10000 * orderCount, order.totalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
    }


    @Test
    void 상품주문_재고수량초과() {
        Member member = createMember();
        Item book = createBook("JPA", 10000, 10);

        int orderCount = 11;

        assertThatThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    void 주문취소() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}
