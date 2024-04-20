package dressshop.domain.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.delivery.Delivery;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class OrderDto {
    private Long id;
    private Member member;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    @NotBlank(message = "도시를 입력하세요.")
    private String city;

    @NotBlank(message = "거리를 입력하세요.")
    private String street;

    @NotBlank(message = "우편번호를 입력하세요.")
    private String zipcode;

    private Delivery delivery;

    @Builder
    @QueryProjection
    public OrderDto(Long id,
                    Member member,
                    LocalDateTime orderDate,
                    OrderStatus orderStatus,
                    String city,
                    String street,
                    String zipcode,
                    Delivery delivery) {
        this.id = id;
        this.member = member;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.delivery = delivery;
    }

    public OrderDto(Order order) {
        id = order.getId();
        member = order.getMember();
        orderDate = order.getOrderDate();
        orderStatus = order.getOrderStatus();
        city = order.getAddress().getCity();
        street = order.getAddress().getStreet();
        zipcode = order.getAddress().getZipcode();
        delivery = order.getDelivery();
    }

    public Order toEntity() {
        return Order.builder()
                .id(id)
                .member(member)
                .orderDate(orderDate)
                .orderStatus(orderStatus)
                .address(new Address(city, street, zipcode))
                .delivery(delivery)
                .build();
    }
}
