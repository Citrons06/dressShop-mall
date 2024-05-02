package dressshop.service;

import dressshop.domain.item.dto.CategoryDto;
import dressshop.domain.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    void save(ItemDto itemDto, CategoryDto categoryDto);

    ItemDto findOne(Long itemId);

    List<ItemDto> findList();

    void editItem(Long itemId, ItemDto itemDto, Long categoryId);

    void delete(Long itemId);
}
