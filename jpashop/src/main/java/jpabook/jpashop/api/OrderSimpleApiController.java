package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne (ManyToOne, OneToOne)
 * Order
 * Order --> Member
 * Order --> Delivery
 */
@RestController
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    public OrderSimpleApiController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * 문제점
     * - 엔티티에 ignore 적용
     * - 하이버네이트5 빈 등록
     * - 레이지 로딩 직접 호출 (iter)
     */

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getId();
        }
        return all;
    }
}
