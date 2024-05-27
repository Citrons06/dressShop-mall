package dressshop.repository.cart;

import dressshop.domain.cart.Cart;
import dressshop.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
    Cart findByMember(Member member);
    void deleteByIdAndMemberId(Long cartId, Long id);
    void deleteByMember(Member member);
}
