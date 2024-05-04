package dressshop.service.item;

import dressshop.domain.item.dto.ItemImgDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemImgService {

    void save(List<ItemImgDto> itemImgDto, List<MultipartFile> multipartFile) throws IOException;
}
