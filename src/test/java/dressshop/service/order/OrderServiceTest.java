package dressshop.service.order;

import dressshop.domain.cart.dto.CartItemDto;
import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.item.Category;
import dressshop.domain.item.Item;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.dto.OrderDto;
import dressshop.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;

import static dressshop.domain.item.ItemSellStatus.SELL;
import static dressshop.domain.member.MemberAuth.ROLE_USER;
import static dressshop.domain.order.OrderStatus.CANCEL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;

    @Test
    @DisplayName("주문하기")
    void order() {
        //given
        Member member = new Member(1L, ROLE_USER, "test", "1234", "test", "email@email.com", "010-1234-5678", new Address("city", "street", "zipcode"), "provider", "providerId");
        Item item = new Item(1L, "item", 1000, 10, new Category(1L, "category"), SELL);
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setCity("city");
        deliveryDto.setStreet("street");
        deliveryDto.setZipcode("zipcode");
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setItem(item);
        cartItemDto.setCount(1);
        cartItemDto.setTotalPrice(1000);
        Principal principal = mock(Principal.class);

        //when
        Long orderId = orderService.order(Collections.singletonList(cartItemDto), deliveryDto, principal);

        //then
        assertNotNull(orderId);
        assertTrue(orderRepository.findById(orderId).isPresent());
    }

    @Test
    @DisplayName("주문 취소")
    void orderCancel() {
        //given
        Member member = new Member(1L, ROLE_USER, "test", "1234", "test", "email@email.com", "010-1234-5678", new Address("city", "street", "zipcode"), "provider", "providerId");
        Item item = new Item(1L, "item", 1000, 10, new Category(1L, "category"), SELL);

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setCity("city");
        deliveryDto.setStreet("street");
        deliveryDto.setZipcode("zipcode");

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setItem(item);
        cartItemDto.setCount(1);
        cartItemDto.setTotalPrice(1000);

        Principal principal = mock(Principal.class);

        //when
        Long orderId = orderService.order(Collections.singletonList(cartItemDto), deliveryDto, principal);
        orderService.orderCancel(orderId);

        //then
        OrderDto order = orderService.findOneOrder(orderId);
        assertEquals(CANCEL, order.getOrderStatus());
    }
}