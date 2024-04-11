package dressshop.domain.delivery.dto;

import dressshop.domain.delivery.DeliveryStatus;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryDto {

    private Member member;
    private Order order;
    private Address address;
    private DeliveryStatus deliveryStatus;
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
