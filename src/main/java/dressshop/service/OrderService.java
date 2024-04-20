package dressshop.service;

import dressshop.domain.delivery.Delivery;
import dressshop.domain.item.Item;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderItem;
import dressshop.domain.order.dto.OrderDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.item.ItemRepository;
import dressshop.repository.member.MemberRepository;
import dressshop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    //상품 주문
    public void toOrder(OrderDto orderDto, Long itemId, Integer count) {
        Member member = memberRepository.findById(orderDto.getMember().getId())
                .orElseThrow(NotFoundException::new);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(NotFoundException::new);
        Delivery delivery = Delivery.createDelivery(member);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, count);
        orderItems.add(orderItem);

        Order order = Order.createOrder(member, delivery, orderItems, orderDto);

        orderRepository.save(order);
    }

    //조회: 주문 내역 조회
    @Transactional(readOnly = true)
    public List<OrderDto> findOrders() {
        return orderRepository.findAll().stream()
                .map(Order::toOrderDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto findOneOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);

        return order.toOrderDto();
    }

    //주문 취소
    public void orderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        order.cancel();
    }
}