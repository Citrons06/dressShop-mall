package dressshop.domain.delivery.dto;

import dressshop.domain.delivery.DeliveryStatus;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryDto {

    private Member member;
    private Order order;

    @NotBlank(message = "주소를 입력하세요.")
    private Address address;

    private DeliveryStatus deliveryStatus;

    @NotBlank(message = "연락처를 입력하세요.")
    private String tel;

    @Builder
    public DeliveryDto(Member member,
                       Order order,
                       Address address,
                       DeliveryStatus deliveryStatus,
                       String tel) {
        this.member = member;
        this.order = order;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
        this.tel = tel;
    }
}