package dressshop.repository.item;

import dressshop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCuscom {
    Item findByItemName(String itemName);
}
