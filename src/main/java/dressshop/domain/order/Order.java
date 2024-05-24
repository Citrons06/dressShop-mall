package dressshop.domain.order;

import dressshop.domain.BaseEntity;
import dressshop.domain.delivery.Delivery;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.dto.OrderDto;
import dressshop.exception.customException.DeliveryIrrevocableException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

import static dressshop.domain.delivery.DeliveryStatus.COMP;
import static dressshop.domain.order.OrderStatus.CANCEL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Table(name = "orders")
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", fetch = LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Setter
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Embedded
    private Address address;

    @Setter
    private int totalPrice;

    @Builder
    public Order(Long id,
                 Member member,
                 List<OrderItem> orderItems,
                 OrderStatus orderStatus,
                 Delivery delivery,
                 Address address,
                 int totalPrice) {
        this.id = id;
        this.member = member;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.delivery = delivery;
        this.address = address;
        this.totalPrice = totalPrice;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getOrderList().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void cancel() {
        if (delivery.getDeliveryStatus() == COMP) {
            throw new DeliveryIrrevocableException();
        }

        this.setOrderStatus(CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public OrderDto toDto() {
        return OrderDto.builder()
                .id(id)
                .member(member)
                .orderStatus(orderStatus)
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .delivery(delivery)
                .orderItems(orderItems.stream()
                        .map(OrderItem::toDto)
                        .toList())
                .build();
    }

    public void addOrderItems(List<OrderItem> orderItemList) {
        orderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            orderItems.add(orderItem);
            orderItem.setOrder(this);
        }
    }
}