package dressshop.domain.delivery.dto;

import dressshop.domain.delivery.Delivery;
import dressshop.domain.delivery.DeliveryStatus;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static dressshop.domain.delivery.DeliveryStatus.READY;

@Data
@NoArgsConstructor
public class DeliveryDto {

    private Long id;
    private Member member;
    private Order order;
    private String username;

    @NotNull(message = "도시를 입력하세요.")
    private String city;
    @NotNull(message = "주소를 입력하세요.")
    private String street;
    @NotNull(message = "우편번호를 입력하세요.")
    private String zipcode;

    @NotBlank(message = "연락처를 입력하세요.")
    private String tel;

    @NotNull(message = "배송 메시지를 입력하세요.")
    private String deliveryMessage;

    private DeliveryStatus deliveryStatus;

    @Builder
    public DeliveryDto(Long id,
                       Member member,
                       Order order,
                       String username,
                       String city, String street, String zipcode,
                       DeliveryStatus deliveryStatus,
                       String tel,
                       String deliveryMessage) {
        this.id = id;
        this.member = member;
        this.order = order;
        this.username = username;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.deliveryStatus = deliveryStatus;
        this.tel = tel;
        this.deliveryMessage = deliveryMessage;
    }

    public Delivery toEntity(DeliveryDto deliveryDto, Member member) {
        return Delivery.builder()
                .member(member)
                .username(deliveryDto.getUsername())
                .address(new Address(deliveryDto.getCity(), deliveryDto.getStreet(), deliveryDto.getZipcode()))
                .tel(deliveryDto.getTel())
                .deliveryMessage(deliveryDto.getDeliveryMessage())
                .deliveryStatus(READY)
                .build();
    }
}
