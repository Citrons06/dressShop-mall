package dressshop.service;

import dressshop.domain.item.Item;
import dressshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item itemDto) {
        Item item = new Item(itemDto.getItemName(), itemDto.getPrice(), itemDto.getQuantity());
        itemRepository.save(item);
    }
}
