package dressshop.service.order;

import dressshop.domain.order.dto.OrderDto;

import java.util.List;

public interface OrderService {

    void toOrder(OrderDto orderDto, Long itemId, int count);
    List<OrderDto> findOrders();
    OrderDto findOneOrder(Long orderId);
    void orderCancel(Long orderId);
}
