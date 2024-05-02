package dressshop.domain.item;

import dressshop.domain.BaseEntity;
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

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private Integer price;

    private Integer quantity;

    @ManyToOne(fetch = LAZY, cascade = MERGE)
    @JoinColumn(name = "category_id")
    @Setter
    private Category category;

    @Setter
    private String categoryName;

    @Enumerated(EnumType.STRING)
    @Setter
    private ItemSellStatus itemSellStatus;

    @OneToMany(mappedBy = "item", fetch = LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Item(Long id,
                String itemName,
                Integer price,
                Integer quantity,
                Category category,
                ItemSellStatus itemSellStatus) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.itemSellStatus = itemSellStatus;
    }

    public ItemDto toDto() {
        return ItemDto.builder()
                .id(id)
                .itemName(itemName)
                .price(price)
                .quantity(quantity)
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }

    public void itemEdit(ItemDto itemDto) {
        this.itemName = itemDto.getItemName();
        this.price = itemDto.getPrice();
        this.quantity = itemDto.getQuantity();
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