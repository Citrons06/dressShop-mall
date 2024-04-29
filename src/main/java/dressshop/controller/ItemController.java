package dressshop.controller;

import dressshop.domain.item.Category;
import dressshop.domain.item.dto.CategoryDto;
import dressshop.domain.item.dto.ItemDto;
import dressshop.domain.item.dto.ItemImgDto;
import dressshop.exception.customException.SaveException;
import dressshop.repository.category.CategoryRepository;
import dressshop.service.ItemImgService;
import dressshop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final CategoryRepository categoryRepository;

    @ModelAttribute
    public List<CategoryDto> categories(Model model) {
        List<CategoryDto> categories = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();

        for (Category category : categoryList) {
            categories.add(category.toDto());
            model.addAttribute("categories", categories);
        }

        return categories;
    }

    //상품 등록 폼 불러오기
    @GetMapping("/admin/items/save")
    public String saveForm(@ModelAttribute("itemForm") ItemDto itemDto) {
        return "admin/items/saveForm";
    }

    //상품 등록
    @PostMapping("/admin/items/save")
    public String save(@Valid @ModelAttribute("itemForm") ItemDto itemDto,
                       BindingResult bindingResult,
                       CategoryDto categoryDto,
                       @ModelAttribute("itemImg") List<MultipartFile> itemImgList,
                       Model model) throws IOException {
        /*if (bindingResult.hasErrors()) {
            return "admin/items/saveForm";
        }*/

        if (itemImgList.get(0).isEmpty() && itemDto.getId() == null) {
            model.addAttribute("errors", "상품 이미지를 올려 주세요.");
            return "admin/items/saveForm";
        }

        try {
            itemService.save(itemDto, categoryDto, itemImgList);
        } catch (Exception e) {
            model.addAttribute("errors", e.getMessage());
        }

        return "redirect:/";
    }

    //상품 단건 조회
    @GetMapping("/items/{itemId}")
    public String findById(@PathVariable Long itemId, Model model) {
        ItemDto item = itemService.findOne(itemId);
        model.addAttribute("itemDetail", item);

        return "items/itemDetail";
    }

    //상품 전체 조회
    @GetMapping("/items/itemList")
    public String findList(Model model) {
        List<ItemDto> items = itemService.findList();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    //상품 관리 페이지 불러오기
    @GetMapping("/admin/items")
    public String findItemList(Model model) {
        List<ItemDto> items = itemService.findList();
        model.addAttribute("items", items);

        return "admin/items/itemList";
    }

    //상품 수정 폼 불러오기
    @GetMapping("/admin/items/edit/{itemId}")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemDto item = itemService.findOne(itemId);
        model.addAttribute("editForm", item);

        return "admin/items/editForm";
    }

    //상품 수정
    @PutMapping("/admin/items/edit/{itemId}")
    public String edit(@PathVariable Long itemId,
                       @Valid @ModelAttribute("editForm") ItemDto itemDto,
                       BindingResult bindingResult,
                       @ModelAttribute("category") CategoryDto categoryDto,
                       Model model) {
        /*if (bindingResult.hasErrors()) {
            return "redirect:/admin/items/edit/{itemId}";
        }*/

        try {
            itemService.editItem(itemId, itemDto, categoryDto);
        } catch (SaveException e) {
            model.addAttribute("errors", e.getMessage());
        }

        return "redirect:/admin/items";
    }

    //상품 삭제 폼 불러오기
    @GetMapping("/admin/items/delete/{itemId}")
    public String deleteForm(@PathVariable Long itemId, Model model) {
        ItemDto itemDto = itemService.findOne(itemId);
        model.addAttribute("deleteForm", itemDto);
        return "admin/items/deleteForm";
    }

    //상품 삭제
    @PostMapping("/admin/items/delete/{itemId}")
    public String delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return "redirect:/admin/items";
    }
}