package dressshop.service.item;

import dressshop.domain.item.dto.ItemImgDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ItemImgService {

    void save(ItemImgDto itemImgDto, MultipartFile multipartFile) throws IOException;
}
