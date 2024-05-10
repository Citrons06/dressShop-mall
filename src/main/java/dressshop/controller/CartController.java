package dressshop.controller;

import dressshop.domain.cart.dto.CartItemDto;
import dressshop.service.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

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
        model.addAttribute("cartItemList", cartList);

        return "cart/cartList";
    }

    //장바구니 상품 수량 변경
    @PatchMapping("/{cartItemId}")
    public @ResponseBody ResponseEntity updateCart(@PathVariable Long cartItemId,
                                                   @RequestParam Long itemId,
                                                   @RequestParam int count,
                                                   @RequestBody @Valid CartItemDto cartItemDto,
                                                   BindingResult bindingResult,
                                                   Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        if (cartItemDto.getCount() <= 0) {
            return new ResponseEntity<String>("수량은 1개 이상이어야 합니다.", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<String>("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }

        cartService.updateCart(itemId, count, principal.getName());
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    //장바구니 상품 삭제
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId, Principal principal) {
            cartService.deleteCartItem(cartItemId, principal.getName());
            return ResponseEntity.ok().build();
    }
}