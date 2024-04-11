package dressshop.controller;

import dressshop.domain.order.dto.OrderDto;
import dressshop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //상품 주문 페이지 이동
    @GetMapping("/order")
    public String orderForm() {
        return "orders";
    }

    //상품 주문
    @PostMapping("/order")
    public String order(@Valid OrderDto orderDto,
                        @RequestParam Long itemId,
                        @RequestParam Integer count
                        ) {
        orderService.toOrder(orderDto, itemId, count);
        return "redirect:/";
    }

    //주문 내역 확인
    @GetMapping("/orderList")
    public String orderList(OrderDto orderDto) {
        orderService.findOrder(orderDto);
        return "orderList";
    }

    //주문 취소
    @PostMapping("/orderList/{orderId}/cancel")
    public String orderCancel(@PathVariable("orderId") Long orderId) {
        orderService.orderCancel(orderId);
        return "redirect:/orderList";
    }

}
