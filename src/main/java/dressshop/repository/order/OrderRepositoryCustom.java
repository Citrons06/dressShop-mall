package dressshop.repository.order;

import dressshop.domain.order.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findAllOrders();
}
