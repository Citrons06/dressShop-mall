package dressshop.repository.cartItem;

import dressshop.domain.cart.dto.CartItemDto;

import java.util.List;

public interface CartItemRepositoryCustom {
    List<CartItemDto> findCartItemList(Long cartId);
}
