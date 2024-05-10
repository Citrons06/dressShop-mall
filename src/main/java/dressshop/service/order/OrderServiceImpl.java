package dressshop.service.order;

import dressshop.domain.delivery.Delivery;
import dressshop.domain.item.Item;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderItem;
import dressshop.domain.order.dto.OrderDto;
import dressshop.exception.customException.ItemNotFoundException;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    //상품 주문
    @Override
    public void toOrder(OrderDto orderDto, Long itemId, int count) {
        Member member = memberRepository.findById(orderDto.getMember().getId())
                .orElseThrow(NotFoundException::new);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
        Delivery delivery = Delivery.createDelivery(member);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, count);
        orderItems.add(orderItem);

        Order order = Order.createOrder(member, delivery, orderItems, orderDto);

        orderRepository.save(order);
    }

    //조회: 주문 내역 조회
    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findOrders() {
        return orderRepository.findAll().stream()
                .map(Order::toDto)
                .collect(Collectors.toList());
    }

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