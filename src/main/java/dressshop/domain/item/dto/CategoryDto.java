package dressshop.domain.item.dto;

import dressshop.domain.item.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank(message = "카테고리명을 입력해주세요.")
    private String categoryName;

    @Builder
    public CategoryDto(String categoryName, Long id) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public Category toEntity() {
        return Category.builder()
                .id(id)
                .categoryName(categoryName)
                .build();
    }
}
