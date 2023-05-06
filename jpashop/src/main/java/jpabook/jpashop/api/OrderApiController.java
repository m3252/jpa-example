package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderFlatResponse;
import jpabook.jpashop.repository.order.query.OrderItemQueryResponse;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.repository.order.query.OrderQueryResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("api/v1/orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("api/v2/orders")
    public List<OrderResponse> orderV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderResponse> collect = orders.stream()
                .map(o -> new OrderResponse(o))
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("api/v3/orders")
    public List<OrderResponse> orderV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderResponse> collect = orders.stream()
                .map(o -> new OrderResponse(o))
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("api/v3.1/orders")
    public List<OrderResponse> orderV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        List<Order> orders = orderRepository.findAllWithMemberAndDelivery(offset, limit);
        List<OrderResponse> collect = orders.stream()
                .map(o -> new OrderResponse(o))
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("api/v4/orders")
    public List<OrderQueryResponse> orderV4() {
        return orderQueryRepository.findOrderQuery();
    }

    @GetMapping("api/v5/orders")
    public List<OrderQueryResponse> orderV5() {
        return orderQueryRepository.findAllByResponse();
    }

    @GetMapping("api/v6/orders")
    public List<OrderQueryResponse> orderV6() {
        List<OrderFlatResponse> flats = orderQueryRepository.findAllByResponse_flat();
        return flats.stream()
                .collect(Collectors.groupingBy(o -> new OrderQueryResponse(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        Collectors.mapping(o -> new OrderItemQueryResponse(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), Collectors.toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryResponse(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(Collectors.toList());
        // 지지고 볶으면 api 명세를 맞출 수 있다.

    }


    @Data
    static class OrderResponse {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemResponse> orderItems;

        public OrderResponse(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();

            order.getOrderItems().forEach(o-> o.getItem().getName());
            this.orderItems = order.getOrderItems().stream()
                    .map(OrderItemResponse::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemResponse {
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemResponse(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
