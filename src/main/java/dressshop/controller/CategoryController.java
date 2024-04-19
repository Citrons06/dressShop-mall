package dressshop.controller;

import dressshop.domain.item.dto.CategoryDto;
import dressshop.exception.customException.SaveException;
import dressshop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //카테고리 등록 폼 불러오기
    @GetMapping("/save")
    public String saveCategory(Model model) {
        model.addAttribute("categoryForm", new CategoryDto());
        return "admin/category/saveForm";
    }

    //카테고리 등록
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("categoryForm") CategoryDto categoryDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/category/saveForm";
        }

        try {
            categoryService.save(categoryDto);
        } catch (SaveException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/category/categoryList";
    }

    //카테고리 수정 폼 불러오기
    @GetMapping("/{categoryId}/update")
    public String updateCategory(Long categoryId, Model model) {
        CategoryDto categoryDto = categoryService.findById(categoryId);
        model.addAttribute("categoryForm", categoryDto);

        return "admin/category/updateForm";
    }

    //카테고리 수정
    @PostMapping("/{categoryId}/update")
    public String update(Long categoryId, @Valid CategoryDto categoryDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/category/updateForm";
        }

        try {
            categoryService.editCategory(categoryId, categoryDto);
        } catch (SaveException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/category/updateForm";
        }

        return "redirect:/";
    }

    //카테고리 삭제
    @PostMapping("/{categoryId}/delete")
    public String delete(Long categoryId) {
        categoryService.deleteCategory(categoryId);

        return "redirect:/";
    }

    //카테고리 전체 조회
    @GetMapping("/categoryList")
    public String findList(Model model) {
        List<CategoryDto> categories = categoryService.findList();
        model.addAttribute("categories", categories);
        return "admin/category/categoryList";
    }
}