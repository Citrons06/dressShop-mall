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
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ItemImgService itemImgService;

    //상품 등록
    @Override
    public void save(ItemDto itemDto, CategoryDto categoryDto) {
        Item item = itemDto.toEntity();

        //카테고리 엔티티를 찾아 와서 카테고리 이름 설정
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(NotFoundException::new);

        item.setCategory(category);
        item.setCategoryName(category.getCategoryName());

        itemRepository.save(item);
        log.info("상품이 등록되었습니다. 등록된 상품={} 가격={} 재고수량={} 카테고리={} 판매여부={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName(), item.getItemSellStatus());
    }

    //상품 단건 조회
    @Override
    @Transactional(readOnly = true)
    public ItemDto findOne(Long itemId) {
        Item item = itemRepository.findOne(itemId);

        return item.toDto();
    }

    //상품 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> findList() {
        return itemRepository.findList()
                .stream()
                .map(Item::toDto)
                .collect(toList());
    }

    //상품 수정
    @Override
    public void editItem(Long itemId, ItemDto itemDto, Long categoryId) {
        Item item = itemRepository.findOne(itemId);
        log.info("상품이 수정됩니다. 수정 전 상품={} 가격={} 재고수량={} 카테고리={} 판매여부={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName(), item.getItemSellStatus());

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);

        item.setCategory(category);
        item.setCategoryName(category.getCategoryName());

        item.itemEdit(itemDto);

        itemRepository.save(item);
        log.info("상품이 수정되었습니다. 수정 후 상품={} 가격={} 재고수량={} 카테고리={} 판매여부={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName(), item.getItemSellStatus());
    }

    //상품 삭제
    @Override
    public void delete(Long itemId) {
        Item item = itemRepository.findOne(itemId);

        itemRepository.delete(item);
        log.info("상품 정보가 삭제되었습니다. 삭제된 상품={} 가격={} 재고수량={} 카테고리={} 판매여부={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName(), item.getItemSellStatus());
    }
}