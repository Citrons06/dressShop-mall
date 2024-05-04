package dressshop.service.item;

import dressshop.domain.item.ItemImg;
import dressshop.domain.item.dto.ItemImgDto;
import dressshop.repository.item.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgServiceImpl implements ItemImgService {

    @Value("${uploadPath}")
    private String imgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;


    public void save(List<ItemImgDto> itemImgDtos, List<MultipartFile> itemImgFiles) throws IOException {
        try {
            List<ItemImg> itemImgList = new ArrayList<>();

            for (int i = 0; i < itemImgFiles.size(); i++) {
                MultipartFile itemImgFile = itemImgFiles.get(i);
                if (!itemImgFile.isEmpty()) {
                    String oriImgName = itemImgFile.getOriginalFilename();
                    String imgName = fileService.upload(imgLocation, oriImgName, itemImgFile.getBytes());
                    String imgUrl = imgLocation + imgName;

                    ItemImgDto itemImgDto = itemImgDtos.get(i);
                    ItemImg itemImg = itemImgDto.toEntity();
                    itemImg.updateImg(oriImgName, imgName, imgUrl);

                    if (i == 0) {
                        itemImg.setRepImgYn("Y");
                    } else {
                        itemImg.setRepImgYn("N");
                    }

                    itemImgList.add(itemImg);
                }
            }
            itemImgRepository.saveAll(itemImgList);
            log.info("이미지 파일이 저장되었습니다.");
        } catch (IOException e) {
            log.error("이미지 파일 저장에 실패했습니다.");
            throw new IOException();
        }
    }
}