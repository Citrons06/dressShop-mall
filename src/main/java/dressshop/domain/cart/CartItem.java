package dressshop.domain.cart;

import dressshop.domain.cart.dto.CartItemDto;
import dressshop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class CartItem {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_id")
    @Setter
    private Cart cart;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    @Setter
    private Item item;

    private int count;

    @Builder
    public CartItem(Long id, Cart cart, Item item, int count) {
        this.id = id;
        this.cart = cart;
        this.item = item;
        this.count = count;
    }

    public void addCartCount(int count) {
        this.count += count;
    }

    public void updateCartCount(int count) {
        this.count = count;
    }

    public int getTotalPrice() {
        return item.getPrice() * count;
    }
}
