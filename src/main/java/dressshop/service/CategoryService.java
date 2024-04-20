package dressshop.service;

import dressshop.domain.item.Category;
import dressshop.domain.item.dto.CategoryDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //카테고리 등록
    public void save(CategoryDto categoryDto) {
        Category category = categoryDto.toEntity();
        category.setCategoryName(categoryDto.getCategoryName());

        categoryRepository.save(category);
    }

    //카테고리 찾기
    public CategoryDto findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);

        return category.toDto();
    }

    public List<CategoryDto> findList() {
        return categoryRepository.findAll().stream()
                .map(Category::toDto)
                .collect(Collectors.toList());
    }

    //카테고리 수정
    public void editCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);

        CategoryDto categoryEdit = category.toEditor()
                .categoryName(categoryDto.getCategoryName())
                .build();

        category.categoryEdit(categoryEdit);
    }

    //카테고리 삭제
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);

        categoryRepository.delete(category);
    }
}
