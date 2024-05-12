package dressshop.controller;

import dressshop.domain.cart.dto.CartItemDto;
import dressshop.domain.delivery.Delivery;
import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.item.Item;
import dressshop.domain.item.dto.ItemDto;
import dressshop.domain.member.Address;
import dressshop.domain.member.dto.MemberDto;
import dressshop.domain.order.OrderItem;
import dressshop.domain.order.dto.OrderDto;
import dressshop.domain.order.dto.OrderItemDto;
import dressshop.service.cart.CartService;
import dressshop.service.item.ItemService;
import dressshop.service.member.MemberService;
import dressshop.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final CartService cartService;

    //상품 주문 폼 불러오기
    @GetMapping
    public String orderForm(@ModelAttribute("orderForm") OrderDto orderDto,
                            @ModelAttribute("deliveryForm") DeliveryDto deliveryDto,
                            Principal principal, Model model) {
        List<CartItemDto> cartList = cartService.getCartList(principal.getName());
        MemberDto member = memberService.findByEmail(principal.getName());
        int totalPrice = cartList.stream()
                .mapToInt(cartItem -> cartItem.getItem().getPrice() * cartItem.getCount())
                .sum();

        log.info("totalPrice: {}", totalPrice);

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartList", cartList);
        model.addAttribute("member", member);


        return "orders/order";
    }

    //상품 주문
    @PostMapping
    public String order(@ModelAttribute("orderForm") OrderDto orderDto,
                        @Valid @ModelAttribute("orderItem") OrderItemDto orderItemDto,
                        @Valid @ModelAttribute("deliveryForm") DeliveryDto deliveryDto,
                        BindingResult bindingResult,
                        Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return "orders/order";
        }

        try {
            List<CartItemDto> cartList = cartService.getCartList(principal.getName());
            MemberDto member = memberService.findByEmail(principal.getName());
            int totalPrice = cartList.stream()
                    .mapToInt(cartItem -> cartItem.getItem().getPrice() * cartItem.getCount())
                    .sum();

            List<OrderItem> orderItems = cartList.stream()
                    .map(cartItem -> OrderItem.createOrderItem(cartItem.getItem(), cartItem.getCount()))
                    .toList();

            orderItemDto.setItems(orderItems);

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartList", cartList);
            model.addAttribute("member", member);

            orderService.toOrder(orderDto, orderItemDto, deliveryDto, principal);

        } catch (Exception e) {
            log.info("주문 처리 중 오류가 발생하였습니다. {}", e.getMessage());
            return "orders/order";
        }

        return "redirect:/order/orderComplete/" + orderDto.getId();
    }

    //주문 완료 페이지
    @GetMapping("/orderComplete/{orderId}")
    public String orderComplete(@PathVariable Long orderId, Model model) {
        OrderDto order = orderService.findOneOrder(orderId);
        model.addAttribute("order", order);
        return "orders/orderComplete";
    }

    //주문 내역 전체 조회
    @GetMapping("/orderList")
    public String findOrders(Model model) {
        List<OrderDto> orders =  orderService.findOrders();
        model.addAttribute("orders", orders);
        return "orders/orderList";
    }

    //주문 내역 단건 조회
    @GetMapping("/orderList/{orderId}")
    public String findOneOrder(Long orderId, Model model) {
        OrderDto order = orderService.findOneOrder(orderId);
        model.addAttribute("order", order);
        return "orders/orderDetail";
    }

    //주문 취소
    @PutMapping("/orderList/cancel/{orderId}")
    public String orderCancel(@PathVariable("orderId") Long orderId) {
        orderService.orderCancel(orderId);
        return "redirect:/orderList";
    }
}