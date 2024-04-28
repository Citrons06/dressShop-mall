package dressshop.service;

import dressshop.domain.item.Category;
import dressshop.domain.item.Item;
import dressshop.domain.item.dto.CategoryDto;
import dressshop.domain.item.dto.ItemDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.category.CategoryRepository;
import dressshop.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    //상품 등록
    public void save(ItemDto itemDto, CategoryDto categoryDto) {
        Item item = itemDto.toEntity();
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(NotFoundException::new);
        log.info("categoryId={}", category.getId());
        item.setCategory(category);

        itemRepository.save(item);
        log.info("상품이 등록되었습니다. 등록된 상품={} 가격={} 재고수량={} 카테고리={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName());
    }

    //상품 단건 조회
    @Transactional(readOnly = true)
    public ItemDto findOne(Long itemId) {
        Item item = itemRepository.findOne(itemId);

        return item.toDto();
    }

    //상품 리스트 조회
    @Transactional(readOnly = true)
    public List<ItemDto> findList() {
        return itemRepository.findList()
                .stream()
                .map(Item::toDto)
                .collect(toList());
    }

    //상품 수정
    public void editItem(Long itemId, ItemDto itemDto, CategoryDto categoryDto) {
        Item item = itemRepository.findOne(itemId);
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(NotFoundException::new);
        log.info("상품 정보가 수정됩니다. 수정될 상품={} 가격={} 재고수량={} 카테고리={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName());

        /*ItemDto.ItemDtoBuilder itemEditor = item.toEditor();
        ItemDto itemEdit = itemEditor
                .id(itemId)
                .itemName(itemDto.getItemName())
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
                .categoryName(itemDto.getCategoryName())
                .build();*/
        item.itemEdit(itemDto);
        item.setCategory(category);

        itemRepository.save(item);
        log.info("상품 정보가 변경되었습니다. 수정 결과={} 가격={} 재고수량={} 카테고리={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName());
    }

    //상품 삭제
    public void delete(Long itemId) {
        Item item = itemRepository.findOne(itemId);

        itemRepository.delete(item);
        log.info("상품 정보가 삭제되었습니다. 삭제된 상품={} 가격={} 재고수량={} 카테고리={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName());
    }
}