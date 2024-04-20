package dressshop.controller;

import dressshop.domain.order.dto.OrderDto;
import dressshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //상품 주문 폼 불러오기
    @GetMapping("/order")
    public String orderForm(@ModelAttribute("orderForm") OrderDto orderDto) {
        return "orders/order";
    }

    //상품 주문
    @PostMapping("/order")
    public String order(OrderDto orderDto, Long itemId, Integer count) {
        orderService.toOrder(orderDto, itemId, count);
        return "redirect:/";
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
        return "orders";
    }

    //주문 취소 폼 불러오기
    @GetMapping("/orderList/{orderId}/cancel")
    public String orderCancelForm(@PathVariable Long orderId, Model model) {
        OrderDto order = orderService.findOneOrder(orderId);
        model.addAttribute("order", order);
        return "orders/cancelForm";
    }

    //주문 취소
    @PostMapping("/orderList/{orderId}/cancel")
    public String orderCancel(@PathVariable("orderId") Long orderId) {
        orderService.orderCancel(orderId);
        return "redirect:/orderList";
    }

}
