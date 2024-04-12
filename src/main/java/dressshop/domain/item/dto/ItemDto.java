package dressshop.domain.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.item.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor
public class ItemDto {

    @NotBlank(message = "상품 이름을 입력하세요.")
    private String itemName;

    @NotNull(message = "가격을 입력하세요.")
    private Integer price;

    @NotNull(message = "재고 수량을 입력하세요.")
    private Integer quantity;

    @Builder
    @QueryProjection
    public ItemDto(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public ItemDto(Item item) {
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
    }
}
