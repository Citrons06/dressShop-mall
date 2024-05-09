package dressshop.repository.cart;

import dressshop.domain.cart.Cart;
import dressshop.domain.member.Member;

public interface CartRepositoryCustom {
    Cart findByMember(Member member);
}
