package dressshop.repository.order;

import dressshop.domain.order.OrderItem;

import java.util.List;

public interface OrderItemRepositoryCustom {
    List<OrderItem> findByOrderId(Long orderId);
}
