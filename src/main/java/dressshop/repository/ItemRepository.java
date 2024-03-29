package dressshop.repository;

import dressshop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByItemName(String itemName);
}
