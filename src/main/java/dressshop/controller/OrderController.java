package dressshop.controller;

import dressshop.domain.cart.dto.CartItemDto;
import dressshop.domain.delivery.dto.DeliveryDto;
import dressshop.domain.order.dto.OrderDto;
import dressshop.service.cart.CartServiceImpl;
import dressshop.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartServiceImpl cartService;

    //상품 주문 폼 불러오기
    @GetMapping
    public String orderForm(@ModelAttribute("orderForm") OrderDto orderDto,
                            @ModelAttribute("deliveryForm") DeliveryDto deliveryDto,
                            Principal principal, Model model) {
        List<CartItemDto> cartList = cartService.getCartItemList(principal.getName());
        int totalPrice = cartList.stream()
                .mapToInt(CartItemDto::getTotalPrice).sum();

        log.info("totalPrice: {}", totalPrice);

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartList", cartList);

        return "orders/order";
    }

    //상품 주문
    @PostMapping
    public String order(@Valid @ModelAttribute("orderForm") OrderDto orderDto,
                        @Valid @ModelAttribute("deliveryForm") DeliveryDto deliveryDto,
                        BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "orders/order";
        }

        List<CartItemDto> cartItemList = cartService.getCartItemList(principal.getName());
        Long orderId = orderService.order(cartItemList, deliveryDto, principal);

        return "redirect:/order/orderComplete/" + orderId;
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