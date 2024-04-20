package dressshop.domain.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.item.Item;
import dressshop.domain.order.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class OrderItemDto {

    private Long id;

    private Item item;

    private Order order;

    private Integer orderPrice;

    @NotBlank(message = "주문 수량을 입력하세요.")
    @Range(min = 1, max = 999)
    private Integer count;

    @Builder
    @QueryProjection
    public OrderItemDto(Long id,
                        Item item,
                        Order order,
                        Integer orderPrice,
                        Integer count) {
        this.id = id;
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
