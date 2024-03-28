package dressshop.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dressshop.domain.order.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @NotEmpty
    private String itemName;

    @NotEmpty
    private Integer price;

    @NotEmpty
    private Integer quantity;

    @NotEmpty
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
}
