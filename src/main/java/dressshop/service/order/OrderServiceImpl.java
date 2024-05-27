package dressshop.service.order;

import dressshop.domain.cart.dto.CartItemDto;
import dressshop.domain.delivery.Delivery;
import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.item.Item;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderItem;
import dressshop.domain.order.dto.OrderDto;
import dressshop.domain.order.dto.OrderItemDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.cart.CartRepository;
import dressshop.repository.item.ItemRepository;
import dressshop.repository.member.MemberRepository;
import dressshop.repository.order.OrderItemRepository;
import dressshop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dressshop.domain.order.OrderStatus.ORDER;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    //상품 주문
    @Override
    public Long order(List<CartItemDto> cartItemList, DeliveryDto deliveryDto, Principal principal) {
        Member member = memberRepository.findByEmail(principal.getName());
        Delivery delivery = deliveryDto.toEntity(deliveryDto, member);

        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .address(new Address(deliveryDto.getCity(), deliveryDto.getStreet(), deliveryDto.getZipcode()))
                .orderStatus(ORDER)
                .totalPrice(cartItemList.stream()
                        .mapToInt(CartItemDto::getTotalPrice)
                        .sum())
                .build();

        orderRepository.save(order);

        //주문 상품 저장
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItemDto cartItemDto : cartItemList) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .item(cartItemDto.getItem())
                    .orderPrice(cartItemDto.getItem().getPrice())
                    .count(cartItemDto.getCount())
                    .build();

            orderItemList.add(orderItem);
            Item item = orderItem.getItem();
            item.decreaseStock(cartItemDto.getCount());
            itemRepository.save(item);
        }

        order.addOrderItems(orderItemList);

        //주문 완료 후 장바구니 제거
        cartRepository.deleteByMember(member);

        log.info("장바구니가 제거되었습니다.");
        log.info("주문이 완료되었습니다. 주문번호={} 주문자={} 이메일={} 전화번호={} 주소={} {} {}",
                order.getId(), member.getUsername(), member.getEmail(), deliveryDto.getTel(), deliveryDto.getStreet(), deliveryDto.getCity(), deliveryDto.getZipcode());

        return order.getId();
    }

    //조회: 주문 내역 조회
    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findOrders() {
        return orderRepository.findAllOrders().stream()
                .map(Order::toDto2)
                .collect(toList());
    }

    //조회: 주문 단건 조회
    @Override
    public List<OrderItemDto> findOneOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId).stream()
                .map(OrderItem::toDto)
                .collect(toList());
    }

    @Override
    public OrderDto findOneOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(Order::toDto)
                .orElseThrow(NotFoundException::new);
    }

    //주문 취소
    @Override
    public void orderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        order.cancel();
    }
}