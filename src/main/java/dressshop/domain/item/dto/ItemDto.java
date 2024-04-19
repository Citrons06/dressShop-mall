package dressshop.domain.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.item.Category;
import dressshop.domain.item.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class ItemDto {

    @NotBlank(message = "상품 이름을 입력하세요.")
    private String itemName;

    @NotBlank(message = "가격을 입력하세요.")
    private Integer price;

    @NotBlank(message = "재고 수량을 입력하세요.")
    private Integer quantity;

    private Category category;

    @Builder
    @QueryProjection
    public ItemDto(String itemName, Integer price, Integer quantity, Category category) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Item toEntity() {
        return Item.builder()
                .itemName(itemName)
                .price(price)
                .quantity(quantity)
                .category(category)
                .build();
    }

    public ItemDto(Item item) {
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
        this.category = item.getCategory();
    }
}
