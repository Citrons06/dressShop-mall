package dressshop.domain.item;

import dressshop.domain.BaseEntity;
import dressshop.domain.item.dto.CategoryDto;
import dressshop.domain.item.dto.ItemDto;
import dressshop.domain.order.OrderItem;
import dressshop.exception.customException.ItemNotStockException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private Integer price;

    private Integer quantity;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @Setter
    private Category category;

    private String categoryName;

    @OneToMany(mappedBy = "item", fetch = LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Item(Long id,
                String itemName,
                Integer price,
                Integer quantity,
                Category category,
                String categoryName) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.categoryName = categoryName;
    }

    public ItemDto toDto() {
        return ItemDto.builder()
                .id(id)
                .itemName(itemName)
                .price(price)
                .quantity(quantity)
                .categoryDto(category.toDto())
                .categoryName(categoryName)
                .build();
    }

    public ItemDto.ItemDtoBuilder toEditor() {
        return ItemDto.builder()
                .id(id)
                .itemName(itemName)
                .price(price)
                .quantity(quantity)
                .categoryDto(category.toDto())
                .categoryName(categoryName);
    }

    public void itemEdit(ItemDto itemDto) {
        itemName = itemDto.getItemName();
        price = itemDto.getPrice();
        quantity = itemDto.getQuantity();
        categoryName = itemDto.getCategoryName();
    }

    public void decreaseStock(int orderCount) {
        int restStock = this.quantity - orderCount;
        if (restStock < 0) {
            throw new ItemNotStockException();
        }
        this.quantity = restStock;
    }

    public void increaseStock(int orderCount) {
        this.quantity += orderCount;
    }
}
