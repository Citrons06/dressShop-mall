package dressshop.domain.cart.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.item.Item;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDto {

    private Long id;
    private Long cartId;
    private Long itemId;

    @NotNull(message = "상품을 선택해 주세요.")
    private Item item;

    private String itemName;
    private int price;

    @Min(value = 1, message = "최소 1개 이상 주문해 주세요.")
    @Max(value = 100, message = "최대 100개까지 주문할 수 있습니다.")
    private int count;

    private String imgName;

    @Builder
    @QueryProjection
    public CartItemDto(Long id,
                       Long cartId,
                       Long itemId,
                       Item item,
                       String itemName,
                       int price,
                       int count,
                       String imgName) {
        this.id = id;
        this.cartId = cartId;
        this.itemId = itemId;
        this.item = item;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.imgName = imgName;
    }
}
