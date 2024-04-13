package dressshop.domain.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dressshop.domain.BaseTimeEntity;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static dressshop.domain.delivery.DeliveryStatus.READY;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
public class Delivery extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Setter
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private String tel;

    @Builder
    public Delivery(Member member,
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

    public static Delivery createDelivery(Member member) {
        return Delivery.builder()
                .member(member)
                .address(member.getAddress())
                .deliveryStatus(READY)
                .tel(member.getTel())
                .build();
    }
}
