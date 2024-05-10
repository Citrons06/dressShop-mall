package dressshop.controller;

import dressshop.domain.order.dto.OrderDto;
import dressshop.service.order.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    //상품 주문 폼 불러오기
    @GetMapping("/order")
    public String orderForm(@ModelAttribute("orderForm") OrderDto orderDto) {
        return "orders/order";
    }

    //상품 주문
    @PostMapping("/order")
    public String order(@Valid @ModelAttribute("orderForm") OrderDto orderDto,
                        BindingResult bindingResult,
                        Long itemId,
                        int count) {
        if (bindingResult.hasErrors()) {
            return "orders/order";
        }

        try {
            orderService.toOrder(orderDto, itemId, count);
        } catch (Exception e) {
            return "orders/order";
        }

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
        return "orders/orderDetail";
    }

    //주문 취소
    @PutMapping("/orderList/cancel/{orderId}")
    public String orderCancel(@PathVariable("orderId") Long orderId) {
        orderService.orderCancel(orderId);
        return "redirect:/orderList";
    }
}