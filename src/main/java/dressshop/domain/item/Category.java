package dressshop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @OneToMany(mappedBy = "category", fetch = LAZY)
    private List<Item> items = new ArrayList<>();
}
