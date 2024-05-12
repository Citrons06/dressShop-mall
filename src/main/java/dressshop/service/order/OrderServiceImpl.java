package dressshop.service.order;

import dressshop.domain.delivery.Delivery;
import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.member.Address;
import dressshop.domain.member.Member;
import dressshop.domain.order.Order;
import dressshop.domain.order.OrderItem;
import dressshop.domain.order.dto.OrderDto;
import dressshop.domain.order.dto.OrderItemDto;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    //상품 주문
    @Override
    public void toOrder(OrderDto orderDto, OrderItemDto orderItemDto, DeliveryDto deliveryDto, Principal principal) {
        Member member = memberRepository.findByEmail(principal.getName());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(orderItemDto.getItem(), orderItemDto.getCount());

        // 대표 이미지 설정
        if (orderItemDto.getItem().getItemImgs() != null) {
            orderItemDto.getItem().getItemImgs().stream()
                    .filter(itemImg -> itemImg.getRepImgYn().equals("Y"))
                    .findFirst()
                    .ifPresent(itemImg -> orderItem.setImgName(itemImg.getImgName()));
        }

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        // 배송 생성
        Delivery delivery = Delivery.createDelivery(deliveryDto, member);
        delivery.setAddress(new Address(deliveryDto.getCity(), deliveryDto.getStreet(), deliveryDto.getZipcode()));
        delivery.setDeliveryMessage(deliveryDto.getDeliveryMessage());

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItems, orderDto);
        order.setTotalPrice(orderDto.getTotalPrice());
        delivery.setMember(member);

        orderRepository.save(order);

        // 주문 완료 후 장바구니 제거
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