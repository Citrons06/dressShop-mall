package dressshop.service.order;

import dressshop.domain.delivery.Delivery;
import dressshop.domain.item.Item;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderItem;
import dressshop.domain.order.dto.OrderDto;
import dressshop.exception.customException.ItemNotFoundException;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.cart.CartRepository;
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
    private final CartRepository cartRepository;

    //상품 주문
    @Override
    public void toOrder(OrderDto orderDto, Long itemId, int count) {
        Member member = memberRepository.findById(orderDto.getMember().getId())
                .orElseThrow(NotFoundException::new);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
        Delivery delivery = Delivery.createDelivery(member);

        List<OrderItem> orderItems = new ArrayList<>();

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, count);
        orderItems.add(orderItem);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItems, orderDto);

        orderRepository.save(order);

        //주문이 완료되면 장바구니에서 해당 상품 삭제(cartId, memberId)
        Long cartId = cartRepository.findByMember(member).getId();
        cartRepository.deleteByIdAndMemberId(cartId, member.getId());
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