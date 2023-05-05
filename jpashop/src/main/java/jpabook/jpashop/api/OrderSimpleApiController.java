package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // N + 1 조회
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderResponse> orderV2() {

        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        return orders.stream()
                .map(SimpleOrderResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderResponse> orderV3() {

        List<Order> orders = orderRepository.findAllWithMemberAndDelivery();

        List<SimpleOrderResponse> result = orders.stream()
                .map(SimpleOrderResponse::new)
                .collect(Collectors.toList());

        return result;
    }

    @Data
    static class SimpleOrderResponse {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderResponse(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}
