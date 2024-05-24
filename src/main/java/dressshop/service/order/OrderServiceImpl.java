package dressshop.service.order;

import dressshop.domain.cart.dto.CartItemDto;
import dressshop.domain.delivery.Delivery;
import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderItem;
import dressshop.domain.order.dto.OrderDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.cart.CartRepository;
import dressshop.repository.member.MemberRepository;
import dressshop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dressshop.domain.order.OrderStatus.ORDER;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

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
            orderItem.getItem().removeStock(cartItemDto.getCount());
        }

        order.addOrderItems(orderItemList);
        return order.getId();
    }


    //조회: 주문 내역 조회
    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findOrders() {
        return orderRepository.findAll().stream()
                .map(Order::toDto)
                .collect(Collectors.toList());
    }

    //조회: 주문 상세 내역 조회
    @Transactional(readOnly = true)
    @Override
    public OrderDto findOneOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);

        return order.toDto();
    }

    //주문 취소
    @Override
    public void orderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        order.cancel();
    }
}