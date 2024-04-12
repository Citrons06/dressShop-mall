package dressshop.controller;

import dressshop.domain.item.dto.ItemDto;
import dressshop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    //상품 등록 폼 불러오기
    @GetMapping("/items/save")
    public String saveItem(Model model) {
        model.addAttribute("item", new ItemDto());
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
    public String findById(@PathVariable Long itemId, Model model) {
        ItemDto item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "items/{itemId}";
    }

    //상품 전체 조회
    @GetMapping("/items")
    public String findList(Model model) {
        List<ItemDto> items = itemService.findList();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    //상품 수정 폼 불러오기
    @GetMapping("/items/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, Model model) {
        ItemDto item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "/items/editItemForm";
    }

    //상품 수정
    @PostMapping("/items/{itemId}/edit")
    public String edit(@PathVariable Long itemId, ItemDto itemDto) {
        itemService.editItem(itemId, itemDto);
        return "redirect:/items/{itemId}";
    }


    //상품 삭제 폼 불러오기
    @GetMapping("/items/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId, Model model) {
        ItemDto itemDto = itemService.findById(itemId);
        model.addAttribute("itemDto", itemDto);
        return "/items/deleteForm";
    }

    //상품 삭제
    @PostMapping("/items/{itemId}/delete")
    public String delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return "redirect:/items";
    }
}