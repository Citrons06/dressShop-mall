package dressshop.service.cart;

import dressshop.domain.cart.Cart;
import dressshop.domain.cart.CartItem;
import dressshop.domain.cart.dto.CartItemDto;
import dressshop.domain.item.Item;
import dressshop.domain.member.Member;
import dressshop.exception.customException.ItemNotFoundException;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.cartItem.CartItemRepository;
import dressshop.repository.cart.CartRepository;
import dressshop.repository.item.ItemRepository;
import dressshop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    //장바구니 상품 조회
    @Transactional(readOnly = true)
    public List<CartItemDto> getCartList(String email) {
        List<CartItemDto> cartItemList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findById(member.getId())
                .orElseThrow(NotFoundException::new);

        if (cart == null) {
            return cartItemList;
        }

        cartItemList = cartItemRepository.findCartItemList(cart.getId());

        return cartItemList;
    }

    //Cart에 상품 추가
    public void addCart(Long itemId, int count, String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            log.error("회원 정보가 없습니다.");
            throw new NotFoundException();
        }

        //상품을 최초로 담을 경우 Cart 생성
        Cart cart = cartRepository.findByMember(member);

        if (cart == null) {
            cart = Cart.builder()
                    .member(member)
                    .build();
            cart = cartRepository.save(cart);
            log.info("새로운 장바구니가 생성되었습니다.");
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);

        //이미 장바구니에 담긴 상품이 있는지 확인
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());  //장바구니의 상품 리스트 초기화
        }
        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getItem().getId().equals(item.getId()))
                .findFirst();

        //이미 장바구니에 담긴 상품이 있다면 수량만 추가
        if (existingCartItem.isPresent()) {
            existingCartItem.get().addCartCount(count);
            log.info("장바구니에 담긴 상품의 수량이 추가되었습니다.");
        } else {
            //장바구니에 담겨 있지 않은 상품일 경우 새로운 CartItem을 생성하여 장바구니에 추가
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .item(item)
                    .count(count)
                    .build();

            cart.getCartItems().add(newCartItem);
            log.info("장바구니에 상품이 추가되었습니다.");
        }
    }

    //Cart 수량 수정
    public void updateCart(Long itemId, int count, String email) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findById(member.getId())
                .orElseThrow(NotFoundException::new);

        Optional<CartItem> cartItem = cart.getCartItems()
                .stream().filter(
                        ci -> ci.getItem().getId().equals(item.getId()))
                .findFirst();

        if (cartItem.isPresent()) {
            cartItem.get().updateCartCount(count);
            log.info("장바구니에 담긴 상품의 수량이 수정되었습니다.");
        } else {
            log.error("장바구니에 담긴 상품이 없습니다.");
        }
    }

    public boolean validateCartItem(Long cartItemId, String email) {
        Member member = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(NotFoundException::new);

        Member savedMember = cartItem.getCart().getMember();

        return StringUtils.equals(member.getEmail(), savedMember.getEmail());
    }

    //Cart 상품 삭제
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(NotFoundException::new);

        cartItemRepository.delete(cartItem);
        log.info("장바구니에 담긴 상품이 삭제되었습니다. 삭제된 상품={}", cartItem.getItem().getItemName());
    }
}
