package jpabook.jpashop.repository.order.query;

import lombok.Data;

@Data
public class OrderItemQueryResponse {
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQueryResponse(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public OrderItemQueryResponse(String itemName, int orderPrice, int count) {
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
