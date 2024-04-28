package dressshop.repository.item;

import dressshop.domain.item.Item;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> findList();
    Item findOne(Long itemId);
}