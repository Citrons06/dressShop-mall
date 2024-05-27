package dressshop.domain.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.item.dto.ItemDto;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderItemDto {

    private Long id;
    private Long itemId;

    private ItemDto itemDto;

    private String itemName;

    private Order order;

    private int orderPrice;

    private int price;

    @NotNull(message = "주문 수량을 입력하세요.")
    @Range(min = 1, max = 100)
    private int count;

    private String imgName;

    @Builder
    @QueryProjection
    public OrderItemDto(Long id, Long itemId, ItemDto itemDto, String itemName, Order order, int orderPrice, int price, int count, String imgName) {
        this.id = id;
        this.itemId = itemId;
        this.itemDto = itemDto;
        this.itemName = itemName;
        this.order = order;
        this.orderPrice = orderPrice;
        this.price = price;
        this.count = count;
        this.imgName = imgName;
    }
}
