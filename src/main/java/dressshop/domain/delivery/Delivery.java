package dressshop.domain.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dressshop.domain.BaseTimeEntity;
import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static dressshop.domain.delivery.DeliveryStatus.READY;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Delivery extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @Setter
    private Member member;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY, cascade = CascadeType.ALL)
    private Order order;

    @Embedded
    @Setter
    private Address address;

    @Setter
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private String tel;

    @Setter
    private String deliveryMessage;

    private String username;

    @Builder
    public Delivery(Long id,
                    Member member,
                    Order order,
                    Address address,
                    DeliveryStatus deliveryStatus,
                    String tel,
                    String deliveryMessage,
                    String username) {
        this.id = id;
        this.member = member;
        this.order = order;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
        this.tel = tel;
        this.deliveryMessage = deliveryMessage;
        this.username = username;
    }

    public static Delivery createDelivery(DeliveryDto deliveryDto, Member member) {
        return Delivery.builder()
                .username(deliveryDto.getUsername())
                .tel(member.getTel())
                .member(member)
                .deliveryStatus(READY)
                .build();
    }
}
