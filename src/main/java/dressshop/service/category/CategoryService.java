package dressshop.service.category;

import dressshop.domain.item.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    void save(CategoryDto categoryDto);
    CategoryDto findById(Long categoryId);
    List<CategoryDto> findList();
    void edit(Long categoryId, CategoryDto categoryDto);
    void delete(Long categoryId);
}
