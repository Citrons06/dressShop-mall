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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    //장바구니 상품 조회
    @Override
    public List<CartItemDto> getCartList(String email) {
        List<CartItemDto> cartItemList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMember(member);

        //장바구니가 없을 경우 새로 생성
        if (cart == null) {
            cart = Cart.builder()
                    .member(member)
                    .build();

            cart = cartRepository.save(cart);
            log.info("새로운 장바구니가 생성되었습니다.");
        }

        cartItemList = cartItemRepository.findCartList(cart.getId());

        //장바구니에 담긴 상품의 총 가격 계산
        totalPriceCalculation(cartItemList, cart);

        return cartItemList;
    }



    //Cart에 상품 추가
    @Override
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

        //장바구니에 담긴 상품의 총 가격 계산
        List<CartItemDto> cartItemList = cartItemRepository.findCartList(cart.getId());

        totalPriceCalculation(cartItemList, cart);
    }

    private static void totalPriceCalculation(List<CartItemDto> cartItemList, Cart cart) {
        cartItemList.forEach(ci -> {
            ci.setTotalPrice(ci.getItem().getPrice() * ci.getCount());
        });
        cart.setTotalPrice();
    }

    //Cart 수량 수정
    @Override
    public void updateCart(CartItemDto cartItemDto, String email) {
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMember(member);

        CartItem cartItem = cartItemRepository.findById(cartItemDto.getId())
                .orElseThrow(NotFoundException::new);

        cartItem.updateCount(cartItemDto.getCount());

        //총 주문 금액 업데이트
        cart.setTotalPrice();

        log.info("장바구니에 담긴 상품의 수량이 수정되었습니다. cartItemId={}", cartItemDto.getId());
    }

    //Cart 상품 삭제
    @Override
    public void deleteCartItem(Long cartItemId, String email) {
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMember(member);

        //CartItem 삭제: Cart, CartItem 모두 업데이트
        cart.getCartItems().removeIf(cartItem -> cartItem.getId().equals(cartItemId));
        cartItemRepository.deleteById(cartItemId);

        //총 주문 금액 차감
        cart.setTotalPrice();

        log.info("장바구니에 담긴 상품이 삭제되었습니다. cartItemId={}", cartItemId);
    }

    @Override
    public List<CartItemDto> getCartItemList(String email) {
        List<CartItemDto> cartItemList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMember(member);

        cartItemList = cartItemRepository.findCartItemList(cart.getId());

        //장바구니에 담긴 상품의 총 가격 계산
        totalPriceCalculation(cartItemList, cart);

        return cartItemList;
    }
}
