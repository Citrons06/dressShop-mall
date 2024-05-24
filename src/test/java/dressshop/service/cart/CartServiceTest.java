package dressshop.service.cart;

import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.member.MemberAuth;
import dressshop.repository.cart.CartRepository;
import dressshop.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CartServiceTest {

    @Autowired CartRepository cartRepository;
    @Autowired CartService cartService;
    @Autowired MemberRepository memberRepository;


    @Test
    @DisplayName("장바구니에 상품 등록")
    void addCartItem() {
        //given
        Member member = new Member(1L, MemberAuth.ROLE_USER, "username", "1234", "nickname", "email", "010-1234-5678", new Address("city", "street", "zipcode"), "provider", "providerId");

        Member saveMember = memberRepository.save(member);

        //when
        cartService.addCart(1L, 1, saveMember.getEmail());
        cartService.addCart(2L, 2, saveMember.getEmail());

        //then
        assertNotNull(cartRepository.findByMember(saveMember));
        assertEquals(2, cartRepository.findByMember(saveMember).getCartItems().size());

    }
}