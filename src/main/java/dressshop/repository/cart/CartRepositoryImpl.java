package dressshop.repository.cart;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dressshop.domain.cart.Cart;
import dressshop.domain.member.Member;
import lombok.RequiredArgsConstructor;

import static dressshop.domain.cart.QCart.cart;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory query;

    public Cart findByMember(Member member) {
        return query.selectFrom(cart)
                .where(cart.member.eq(member))
                .fetchOne();
    }
}
