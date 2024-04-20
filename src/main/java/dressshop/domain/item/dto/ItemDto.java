package dressshop.domain.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.item.Category;
import dressshop.domain.item.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ItemDto {

    private Long id;

    @NotBlank(message = "상품 이름을 입력하세요.")
    private String itemName;

    @NotNull(message = "가격을 입력하세요.")
    private Integer price;

    @NotNull(message = "재고 수량을 입력하세요.")
    private Integer quantity;

    private Category categories;

    private String categoryName;

    @Builder
    @QueryProjection
    public ItemDto(Long id,
                   String itemName,
                   Integer price,
                   Integer quantity,
                   String categoryName) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.categoryName = categoryName;
    }

    public Item toEntity() {
        return Item.builder()
                .id(id)
                .itemName(itemName)
                .price(price)
                .quantity(quantity)
                .categoryName(categoryName)
                .build();
    }

    public ItemDto(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
        this.categoryName = item.getCategoryName();
    }
}
