package dressshop.domain.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.delivery.Delivery;
import dressshop.domain.member.Member;
import dressshop.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Member member;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    private String city;
    private String street;
    private String zipcode;

    private Delivery delivery;

    private List<OrderItemDto> orderItems = new ArrayList<>();

    private int totalPrice;

    @Builder
    @QueryProjection
    public OrderDto(Long id,
                    Member member,
                    LocalDateTime orderDate,
                    OrderStatus orderStatus,
                    String city,
                    String street,
                    String zipcode,
                    Delivery delivery,
                    List<OrderItemDto> orderItems) {
        this.id = id;
        this.member = member;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.delivery = delivery;
        this.orderItems = orderItems;
    }
}
