package dressshop.service.item;

import dressshop.domain.item.dto.CategoryDto;
import dressshop.domain.item.dto.ItemDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    Long save(ItemDto itemDto, CategoryDto categoryDto, List<MultipartFile> itemImg) throws IOException;

    ItemDto findOne(Long itemId);

    List<ItemDto> findList();

    void editItem(Long itemId, ItemDto itemDto, Long categoryId);

    void delete(Long itemId);
}
