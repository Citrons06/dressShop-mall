package dressshop.controller;

import dressshop.domain.item.Item;
import dressshop.dto.ItemDto;
import dressshop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    //상품 등록
    @GetMapping("/item/save")
    public String saveItem(@ModelAttribute("item") ItemDto item) {
        return "item/saveForm";
    }

    @PostMapping("/item/save")
    public String save(@Valid ItemDto item) {
        itemService.save(item);
        return "redirect:/";
    }

    //상품 조회
    @GetMapping("/item/{itemId}")
    public String findById(@PathVariable("itemId") Long itemId) {
        itemService.findById(itemId);
        return "item/{itemId}";
    }

    @GetMapping("/item")
    public String findList(@PathVariable("item") Item item) {
        itemService.findList();
        return "item/itemList";
    }

    //상품 수정

    //상품 삭제
    @GetMapping("/item/{itemId}/delete")
    public String delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return "redirect:/item";
    }
}