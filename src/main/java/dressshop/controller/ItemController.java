package dressshop.controller;

import dressshop.domain.item.Item;
import dressshop.domain.item.dto.ItemDto;
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

    //상품 등록 폼 불러오기
    @GetMapping("/items/save")
    public String saveItem(@ModelAttribute("item") ItemDto itemDto) {
        return "items/saveForm";
    }

    //상품 등록
    @PostMapping("/items/save")
    public String save(@Valid ItemDto itemDto) {
        itemService.save(itemDto);
        return "redirect:/";
    }

    //상품 단건 조회
    @GetMapping("/items/{itemId}")
    public String findById(@PathVariable Long itemId) {
        itemService.findById(itemId);
        return "items/{itemId}";
    }

    //상품 전체 조회
    @GetMapping("/items")
    public String findList(@ModelAttribute("item") ItemDto itemDto) {
        itemService.findList();
        return "items/itemList";
    }

    //상품 수정
    @GetMapping("/items/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @Valid ItemDto itemDto) {
        itemService.editItem(itemId, itemDto);
        return "redirect:/items";
    }

    //상품 삭제
    @GetMapping("/items/{itemId}/delete")
    public String delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return "redirect:/items";
    }
}