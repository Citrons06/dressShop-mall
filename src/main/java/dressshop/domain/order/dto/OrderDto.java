package dressshop.domain.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.delivery.Delivery;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderDto {

    private Member member;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private Delivery delivery;

    @Builder
    @QueryProjection
    public OrderDto(Member member,
                    LocalDateTime orderDate,
                    OrderStatus orderStatus,
                    Address address,
                    Delivery delivery) {
        this.member = member;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.delivery = delivery;
    }

    public OrderDto(Order order) {
        member = order.getMember();
        orderDate = order.getOrderDate();
        orderStatus = order.getOrderStatus();
        address = order.getMember().getAddress();
        delivery = order.getDelivery();
    }
}
