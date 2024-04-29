package dressshop.controller;

import dressshop.domain.item.dto.CategoryDto;
import dressshop.exception.customException.SaveException;
import dressshop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //카테고리 등록 폼 불러오기
    @GetMapping("/save")
    public String saveForm(@ModelAttribute("categoryForm") CategoryDto categoryDto) {
        return "admin/category/saveForm";
    }

    //카테고리 등록
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("categoryForm") CategoryDto categoryDto,
                       BindingResult bindingResult,
                       Model model) {

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
    @GetMapping("/edit/{categoryId}")
    public String editForm(@PathVariable Long categoryId, Model model) {
        CategoryDto categoryDto = categoryService.findById(categoryId);
        model.addAttribute("editForm", categoryDto);

        return "admin/category/editForm";
    }

    //카테고리 수정
    @PutMapping("/edit/{categoryId}")
    public String edit(@PathVariable Long categoryId,
                       @Valid @ModelAttribute("editForm") CategoryDto categoryDto,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/category/editForm";
        }

        try {
            categoryService.edit(categoryId, categoryDto);
        } catch (SaveException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/category/editForm";
        }

        return "redirect:/admin/category/categoryList";
    }

    //카테고리 삭제 폼 불러오기
    @GetMapping("/delete/{categoryId}")
    public String deleteForm(@PathVariable Long categoryId, Model model) {
        CategoryDto categoryDto = categoryService.findById(categoryId);
        model.addAttribute("deleteForm", categoryDto);

        return "admin/category/deleteForm";
    }

    //카테고리 삭제
    @PostMapping("/delete/{categoryId}")
    public String delete(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);

        return "redirect:/admin/category/categoryList";
    }

    //카테고리 전체 조회
    @GetMapping("/categoryList")
    public String findList(Model model) {
        List<CategoryDto> categories = categoryService.findList();
        model.addAttribute("categories", categories);
        return "admin/category/categoryList";
    }
}
