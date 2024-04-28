package dressshop.domain.item;

import dressshop.domain.BaseEntity;
import dressshop.domain.item.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @OneToMany(mappedBy = "category", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @Builder
    public Category(Long id, String categoryName, List<Item> items) {
        this.id = id;
        this.categoryName = categoryName;
        this.items = items;
    }

    public Category(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CategoryDto.CategoryDtoBuilder toEditor() {
        return CategoryDto.builder()
                .id(id)
                .categoryName(categoryName);
    }

    public void categoryEdit(CategoryDto categoryEdit) {
        this.categoryName = categoryEdit.getCategoryName();
    }

    public CategoryDto toDto() {
        return CategoryDto.builder()
                .id(id)
                .categoryName(categoryName)
                .build();
    }
}