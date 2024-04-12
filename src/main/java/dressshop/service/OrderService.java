package dressshop.service;

import dressshop.domain.delivery.Delivery;
import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.item.Item;
import dressshop.domain.item.dto.ItemDto;
import dressshop.domain.member.Member;
import dressshop.domain.member.dto.MemberDto;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderItem;
import dressshop.domain.order.OrderStatus;
import dressshop.domain.order.dto.OrderDto;
import dressshop.domain.order.dto.OrderItemDto;
import dressshop.exception.customException.ItemNotStockException;
import dressshop.exception.customException.NotFoundException;
import dressshop.exception.customException.OrderOverException;
import dressshop.repository.item.ItemRepository;
import dressshop.repository.member.MemberRepository;
import dressshop.repository.order.OrderItemRepository;
import dressshop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static dressshop.domain.delivery.DeliveryStatus.READY;
import static dressshop.domain.order.OrderStatus.ORDER;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    //상품 주문
    public void toOrder(OrderDto orderDto, Long itemId, int count) {
        Member member = memberRepository.findById(orderDto.getMember().getId())
                .orElseThrow(NotFoundException::new);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(NotFoundException::new);
        Delivery delivery = Delivery.createDelivery(member);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, count);
        orderItems.add(orderItem);

        Order order = Order.createOrder(member, delivery, orderItems);

        orderRepository.save(order);
    }

    //조회: 주문 내역 조회
    @Transactional(readOnly = true)
    public List<OrderDto> findOrder(OrderDto orderDto) {
        return orderRepository.findAll()
                .stream().map(OrderDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto findOneOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);

        return OrderDto.builder()
                .member(order.getMember())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .address(order.getMember().getAddress())
                .delivery(order.getDelivery())
                .build();
    }

    //주문 취소
    public void orderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        order.cancel();
    }
}