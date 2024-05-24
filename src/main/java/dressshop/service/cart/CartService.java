package dressshop.service.cart;

import dressshop.domain.cart.dto.CartItemDto;

import java.util.List;

public interface CartService {


    List<CartItemDto> getCartList(String email);
    void addCart(Long itemId, int count, String email);
    void updateCart(CartItemDto cartItemDto, String email);
    void deleteCartItem(Long cartItemId, String email);
    List<CartItemDto> getCartItemList(String email);
}
