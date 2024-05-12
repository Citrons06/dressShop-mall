package dressshop.service.order;

import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.order.dto.OrderDto;
import dressshop.domain.order.dto.OrderItemDto;

import java.security.Principal;
import java.util.List;

public interface OrderService {

    void toOrder(OrderDto orderDto, OrderItemDto orderItemDto, DeliveryDto deliveryDto, Principal principal);
    List<OrderDto> findOrders();
    OrderDto findOneOrder(Long orderId);
    void orderCancel(Long orderId);
}
