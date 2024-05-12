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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static dressshop.domain.delivery.DeliveryStatus.COMP;
import static dressshop.domain.order.OrderStatus.CANCEL;
import static dressshop.domain.order.OrderStatus.ORDER;
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

    private LocalDateTime orderDate;

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
                 LocalDateTime orderDate,
                 OrderStatus orderStatus,
                 Delivery delivery,
                 Address address,
                 int totalPrice) {
        this.id = id;
        this.member = member;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
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

    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems, OrderDto orderDto) {
        Order order = Order.builder()
                .id(orderDto.getId())
                .member(member)
                .orderDate(LocalDateTime.now())
                .orderStatus(ORDER)
                .address(delivery.getAddress())
                .delivery(delivery)
                .build();

        order.setMember(member);

        return order;
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
                .orderDate(orderDate)
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
}