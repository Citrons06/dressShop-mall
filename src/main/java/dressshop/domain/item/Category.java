package dressshop.domain.item;

import dressshop.domain.item.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @OneToMany(mappedBy = "categories", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @Builder
    public Category(String categoryName, List<Item> items) {
        this.categoryName = categoryName;
        this.items = items;
    }

    public CategoryDto.CategoryDtoBuilder toEditor() {
        return CategoryDto.builder()
                .categoryName(categoryName);
    }

    public void categoryEdit(CategoryDto categoryEdit) {
        this.categoryName = categoryEdit.getCategoryName();
    }

    public CategoryDto toDto() {
        return CategoryDto.builder()
                .categoryName(categoryName)
                .build();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}