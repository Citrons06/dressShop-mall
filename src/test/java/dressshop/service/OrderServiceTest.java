package dressshop.service;

import dressshop.domain.delivery.Delivery;
import dressshop.domain.item.Item;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import dressshop.domain.order.dto.OrderDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.item.ItemRepository;
import dressshop.repository.member.MemberRepository;
import dressshop.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static dressshop.domain.member.MemberAuth.ROLE_USER;
import static dressshop.domain.order.OrderStatus.ORDER;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;

    @Test
    @DisplayName("주문 성공")
    void test() {
        Member member = Member.builder()
                .nickname("주문자A")
                .username("아요니")
                .address(new Address("서울", "도로명", "22222"))
                .email("이거슨 이메일")
                .tel("010-0000-0000")
                .memberAuth(ROLE_USER)
                .build();

        memberRepository.save(member);

        Item itemA = Item.builder()
                .itemName("스프링")
                .price(50000)
                .quantity(1000)
                .build();
        Item itemB = Item.builder()
                .itemName("JPA")
                .price(55000)
                .quantity(1000)
                .build();
        Item itemC = Item.builder()
                .itemName("QueryDsl")
                .price(30000)
                .quantity(1000)
                .build();

        itemRepository.save(itemA);
        itemRepository.save(itemB);
        itemRepository.save(itemC);

        OrderDto orderDto = OrderDto.builder()
                .member(member)
                .orderDate(LocalDateTime.now())
                .orderStatus(ORDER)
                .city("서울")
                .street("도로명")
                .zipcode("12345")
                .delivery(Delivery.createDelivery(member))
                .build();

        orderService.toOrder(orderDto, itemA.getId(), 10);
        Order order = orderRepository.findById(itemA.getId())
                .orElseThrow(NotFoundException::new);
        assertThat(order.getMember().getUsername()).isEqualTo("아요니");
        assertThat(itemA.getQuantity()).isEqualTo(990);
    }
}