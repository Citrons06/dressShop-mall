package dressshop.service;

import dressshop.domain.item.Item;
import dressshop.dto.ItemDto;
import dressshop.exception.NotFoundException;
import dressshop.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //상품 등록
    public void save(ItemDto itemDto) {
        Item item = new Item(itemDto.getItemName(), itemDto.getPrice(), itemDto.getQuantity());
        itemRepository.save(item);
    }

    //상품 조회
    public Item findById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> {
            log.info("itemId[{}] not found", itemId);
            return new NotFoundException("상품 조회에 실패하였습니다.");
        });
    }

    public List<ItemDto> findList() {
        return itemRepository.findList().stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());
    }

    //상품 수정
    public void editItem(Long itemId, String itemName, Integer price, Integer quantity) {
        Item item = itemRepository.findById(itemId).orElseThrow(NotFoundException::new);
    }

    //상품 삭제
    public void delete(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
