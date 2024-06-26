package dressshop.service.order;

import dressshop.domain.cart.dto.CartItemDto;
import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.order.dto.OrderDto;
import dressshop.domain.order.dto.OrderItemDto;

import java.security.Principal;
import java.util.List;

public interface OrderService {

    Long order(List<CartItemDto> cartItemList, DeliveryDto deliveryDto, Principal principal);
    List<OrderDto> findOrders();
    List<OrderItemDto> findOneOrderItems(Long orderId);
    void orderCancel(Long orderId);
    OrderDto findOneOrder(Long orderId);
}
