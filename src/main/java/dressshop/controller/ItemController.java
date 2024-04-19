package dressshop.controller;

import dressshop.domain.item.dto.ItemDto;
import dressshop.exception.customException.SaveException;
import dressshop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    //상품 등록 폼 불러오기
    @GetMapping("/admin/items/save")
    public String saveItem(Model model) {
        model.addAttribute("itemForm", new ItemDto());
        return "admin/items/saveForm";
    }

    //상품 등록
    @PostMapping("/admin/items/save")
    public String save(@Valid @ModelAttribute("itemForm") ItemDto itemDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/items/saveForm";
        }

        try {
            itemService.save(itemDto);
        } catch (SaveException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/items/itemList";
    }

    //상품 단건 조회
    @GetMapping("/items/{itemId}")
    public String findById(@PathVariable Long itemId, Model model) {
        ItemDto item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "items/{itemId}";
    }

    //상품 전체 조회
    @GetMapping("/items/itemList")
    public String findList(Model model) {
        List<ItemDto> items = itemService.findList();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    //상품 수정 폼 불러오기
    @GetMapping("/admin/items/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, Model model) {
        ItemDto item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "admin/items/editForm";
    }

    //상품 수정
    @PostMapping("/admin/items/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute("item") @Valid ItemDto itemDto) {
        itemService.editItem(itemId, itemDto);
        return "redirect:/items/itemList";
    }

    //상품 삭제 폼 불러오기
    @GetMapping("/admin/items/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId, Model model) {
        ItemDto itemDto = itemService.findById(itemId);
        model.addAttribute("itemDto", itemDto);
        return "admin/items/deleteForm";
    }

    //상품 삭제
    @PostMapping("/admin/items/{itemId}/delete")
    public String delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return "redirect:/itemList";
    }
}