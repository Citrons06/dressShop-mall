package dressshop.domain.item.dto;

import dressshop.domain.item.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank(message = "카테고리명을 입력해주세요.")
    private String categoryName;

    @Builder
    public CategoryDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryDto(Category category) {
    }

    public CategoryDto.CategoryDtoBuilder toEditor() {
        return CategoryDto.builder()
                .categoryName(categoryName);
    }


    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }
}
