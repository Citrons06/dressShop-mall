package dressshop.service.item;

import dressshop.domain.item.Category;
import dressshop.domain.item.Item;
import dressshop.domain.item.ItemImg;
import dressshop.domain.item.dto.CategoryDto;
import dressshop.domain.item.dto.ItemDto;
import dressshop.domain.item.dto.ItemImgDto;
import dressshop.exception.customException.NotFoundException;
import dressshop.repository.category.CategoryRepository;
import dressshop.repository.itemImg.ItemImgRepository;
import dressshop.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final CategoryRepository categoryRepository;
    private final ItemImgService itemImgService;

    //상품 등록
    @Override
    public Long save(ItemDto itemDto, CategoryDto categoryDto, List<MultipartFile> itemImgList) throws IOException {
        Item item = itemDto.toEntity();

        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(NotFoundException::new);

        item.setCategory(category);
        item.setCategoryName(category.getCategoryName());

        itemRepository.save(item);
        log.info("상품이 등록되었습니다. 등록된 상품={} 가격={} 재고수량={} 카테고리={} 판매여부={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName(), item.getItemSellStatus());

        //이미지 등록
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for (int i = 0; i < itemImgList.size(); i++) {
            MultipartFile itemImgFile = itemImgList.get(i);
            if (!itemImgFile.isEmpty()) {
                ItemImgDto itemImgDto = new ItemImgDto();
                itemImgDto.setOriImgName(itemImgFile.getOriginalFilename());
                itemImgDto.setItem(item);

                if (i == 0) {
                    itemImgDto.setRepImgYn("Y");
                } else {
                    itemImgDto.setRepImgYn("N");
                }

                itemImgDtoList.add(itemImgDto);
            }
        }

        itemImgService.save(itemImgDtoList, itemImgList);
        return item.getId();
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
                .toList();
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

    //상품 이미지 수정
    @Override
    public void updateImages(Long itemId, List<MultipartFile> itemImgList) throws IOException {
        Item item = itemRepository.findOne(itemId);

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for (int i = 0; i < itemImgList.size(); i++) {
            MultipartFile itemImgFile = itemImgList.get(i);
            if (!itemImgFile.isEmpty()) {
                ItemImgDto itemImgDto = new ItemImgDto();
                itemImgDto.setOriImgName(itemImgFile.getOriginalFilename());
                itemImgDto.setItem(item);

                if (i == 0) {
                    itemImgDto.setRepImgYn("Y");
                } else {
                    itemImgDto.setRepImgYn("N");
                }

                itemImgDtoList.add(itemImgDto);
            }
        }

        itemImgService.save(itemImgDtoList, itemImgList);
    }

    //상품 삭제
    @Override
    public void delete(Long itemId) {
        Item item = itemRepository.findOne(itemId);

        itemRepository.delete(item);
        log.info("상품 정보가 삭제되었습니다. 삭제된 상품={} 가격={} 재고수량={} 카테고리={} 판매여부={}",
                item.getItemName(), item.getPrice(), item.getQuantity(), item.getCategoryName(), item.getItemSellStatus());
    }

    //상품 이미지 제거
    @Override
    public void deleteImg(Long itemImgId) {
        itemImgRepository.deleteById(itemImgId);
        log.info("상품 이미지가 제거되었습니다.");
    }

    //상품 이미지 리스트 가져오기
    @Override
    @Transactional(readOnly = true)
    public List<ItemImgDto> getItemImages(Long itemId) {
        return itemImgRepository.findByItemId(itemId)
                .stream()
                .map(ItemImg::toDto)
                .collect(toList());
    }
}