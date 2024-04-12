package dressshop.service;

import dressshop.domain.item.Item;
import dressshop.domain.item.dto.ItemDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //상품 등록
    public void save(ItemDto itemDto) {
        Item item = Item.builder()
                .itemName(itemDto.getItemName())
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
                .build();

        itemRepository.save(item);
    }

    //상품 단건 조회
    @Transactional(readOnly = true)
    public ItemDto findById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(NotFoundException::new);

        return ItemDto.builder()
                .itemName(item.getItemName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }

    //상품 리스트 조회
    @Transactional(readOnly = true)
    public List<ItemDto> findList() {
        return itemRepository.findList().stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());
    }

    //상품 수정
    public void editItem(Long itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(NotFoundException::new);

        ItemDto itemEdit = item.toEditor()
                .itemName(itemDto.getItemName())
                .price(itemDto.getPrice())
                .quantity(item.getQuantity())
                .build();

        item.itemEdit(itemEdit);
    }

    //상품 삭제
    public void delete(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(NotFoundException::new);

        itemRepository.delete(item);
    }
}