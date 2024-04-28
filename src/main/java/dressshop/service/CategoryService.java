package dressshop.service;

import dressshop.domain.item.Category;
import dressshop.domain.item.dto.CategoryDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //카테고리 등록
    public void save(CategoryDto categoryDto) {
        Category category = categoryDto.toEntity();

        categoryRepository.save(category);
    }

    //카테고리 찾기
    @Transactional(readOnly = true)
    public CategoryDto findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);

        return category.toDto();
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findList() {
        return categoryRepository.findAll().stream()
                .map(Category::toDto)
                .collect(Collectors.toList());
    }

    //카테고리 수정
    public void edit(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);
        log.info("카테고리가 수정됩니다. 수정 전 카테고리={}", category.getCategoryName());

        CategoryDto categoryEdit = category.toEditor()
                .categoryName(categoryDto.getCategoryName())
                .build();

        category.categoryEdit(categoryEdit);
        log.info("카테고리가 수정되었습니다. 수정 후 카테고리={}", category.getCategoryName());
    }

    //카테고리 삭제
    public void delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);

        categoryRepository.delete(category);
        log.info("카테고리가 삭제되었습니다. 삭제된 카테고리={}", category.getCategoryName());
    }
}
