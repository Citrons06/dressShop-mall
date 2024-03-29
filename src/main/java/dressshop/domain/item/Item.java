package dressshop.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dressshop.domain.order.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private Integer price;

    private Integer quantity;

    private Integer itemSell;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "item", fetch = LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
