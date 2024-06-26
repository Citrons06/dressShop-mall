package dressshop.repository.cartItem;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dressshop.domain.cart.dto.CartItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static dressshop.domain.cart.QCart.cart;
import static dressshop.domain.cart.QCartItem.cartItem;
import static dressshop.domain.item.QItem.item;
import static dressshop.domain.item.QItemImg.itemImg;

@Slf4j
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<CartItemDto> findCartList(Long cartId) {
        //장바구니에 담긴 상품 목록과 상품의 대표 이미지 조회
        return query
                .select(Projections.bean(CartItemDto.class,
                        cartItem.id,
                        cartItem.cart.id.as("cartId"),
                        cartItem.item.id.as("itemId"),
                        cartItem.item,
                        cartItem.count,
                        cartItem.item.quantity.as("quantity"),
                        itemImg.imgName))
                .from(cartItem)
                .leftJoin(cartItem.cart, cart)
                .leftJoin(cartItem.item, item)
                .leftJoin(item.itemImgs, itemImg)
                .where(cartItem.cart.id.eq(cartId)
                        .and(itemImg.repImgYn.eq("Y")))
                .fetch();
    }

    @Override
    public List<CartItemDto> findCartItemList(Long cartId) {
        return query
                .select(Projections.bean(CartItemDto.class,
                        cartItem.id,
                        cartItem.cart.id.as("cartId"),
                        cartItem.item.id.as("itemId"),
                        cartItem.item,
                        cartItem.count,
                        cartItem.item.quantity.as("quantity")))
                .from(cartItem)
                .leftJoin(cartItem.cart, cart)
                .leftJoin(cartItem.item, item)
                .where(cartItem.cart.id.eq(cartId))
                .fetch();
    }
}
