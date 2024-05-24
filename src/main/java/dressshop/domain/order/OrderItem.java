package dressshop.domain.order;

import dressshop.domain.BaseEntity;
import dressshop.domain.item.Item;
import dressshop.domain.order.dto.OrderItemDto;
import dressshop.exception.customException.ItemNotStockException;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Slf4j
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    @Setter
    private String imgName;

    @Builder
    public OrderItem(Item item, Order order, int orderPrice, int count, String imgName) {
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
        this.imgName = imgName;
    }

    public void cancel() {
        item.increaseStock(count);
    }

    public OrderItemDto toDto() {
        return OrderItemDto.builder()
                .id(id)
                .itemId(item.getId())
                .itemName(item.getItemName())
                .order(order)
                .orderPrice(orderPrice)
                .count(count)
                .imgName(imgName)
                .build();
    }
}