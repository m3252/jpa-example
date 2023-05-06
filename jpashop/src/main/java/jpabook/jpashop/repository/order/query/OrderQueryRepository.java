package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryResponse> findOrderQuery() {
        List<OrderQueryResponse> result = findOrders();
        result.forEach(o -> {
            List<OrderItemQueryResponse> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return result;
    }

    private List<OrderItemQueryResponse> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryResponse(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryResponse.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryResponse> findOrders() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryResponse(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryResponse.class
        ).getResultList();
    }

    public List<OrderQueryResponse> findAllByResponse() {

        List<OrderQueryResponse> result = findOrders();

        List<OrderItemQueryResponse> orderItems = toOrderIds(result);

        Map<Long, List<OrderItemQueryResponse>> orderItemMap = findOrderItemMap(orderItems);

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private List<OrderItemQueryResponse> toOrderIds(List<OrderQueryResponse> result) {
        List<Long> orderIds = result.stream()
                .map(OrderQueryResponse::getOrderId)
                .collect(Collectors.toList());

        List<OrderItemQueryResponse> orderItems = em.createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderItemQueryResponse(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id IN :orderIds", OrderItemQueryResponse.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
        return orderItems;
    }

    private static Map<Long, List<OrderItemQueryResponse>> findOrderItemMap(List<OrderItemQueryResponse> orderItems) {
        Map<Long, List<OrderItemQueryResponse>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryResponse::getOrderId));
        return orderItemMap;
    }
}
