package dressshop.controller;

import dressshop.domain.cart.dto.CartItemDto;
import dressshop.service.cart.CartServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;

    //장바구니에 상품 추가
    @PostMapping("/add")
    public String addCart(@RequestParam Long itemId,
                          @RequestParam int count,
                          Principal principal,
                          RedirectAttributes redirectAttributes) {
        cartService.addCart(itemId, count, principal.getName());
        redirectAttributes.addFlashAttribute("message", "장바구니에 상품이 추가되었습니다.");

        return "redirect:/cart";
    }

    //장바구니 페이지
    @GetMapping
    public String cartList(Principal principal, Model model) {
        List<CartItemDto> cartList = cartService.getCartList(principal.getName());
        int totalPrice = cartList.stream()
                .mapToInt(CartItemDto::getTotalPrice).sum();

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartList", cartList);

        return "cart/cartList";
    }

    //장바구니 상품 수량 변경
    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody @Valid CartItemDto cartItemDto, Principal principal) {
        cartService.updateCart(cartItemDto, principal.getName());
        return ResponseEntity.ok().build();
    }

    //장바구니 상품 삭제
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId, Principal principal) {
            cartService.deleteCartItem(cartItemId, principal.getName());
            return ResponseEntity.ok().build();
    }
}