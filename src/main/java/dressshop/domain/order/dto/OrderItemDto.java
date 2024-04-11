package dressshop.domain.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.item.Item;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderItem;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemDto {

    private Item item;

    private Order order;

    @NotEmpty
    private Integer orderPrice;

    @NotEmpty @Max(999)
    private Integer count;

    @Builder
    @QueryProjection
    public OrderItemDto(Item item, Order order, Integer orderPrice, Integer count) {
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public OrderItemDto(OrderItem orderItem) {
    }
}
