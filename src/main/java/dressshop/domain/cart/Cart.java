package dressshop.domain.cart;

import dressshop.domain.BaseTimeEntity;
import dressshop.domain.cart.dto.CartDto;
import dressshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart", fetch = LAZY, cascade = CascadeType.ALL)
    @Setter
    private List<CartItem> cartItems = new ArrayList<>();

    private int totalPrice;

    @Builder
    public Cart(Long id, Member member, List<CartItem> cartItems, int totalPrice) {
        this.id = id;
        this.member = member;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    public void setTotalPrice() {
        if (cartItems != null) {
            this.totalPrice = cartItems.stream()
                    .mapToInt(CartItem::getTotalPrice)
                    .sum();
        } else {
            this.totalPrice = 0;
        }
    }
}