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
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgServiceImpl implements ItemImgService {

    @Value("${spring.servlet.multipart.location}")
    private String imgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;


    public void save(ItemImgDto itemImgDto, MultipartFile itemImgFile) throws IOException {
        try {
            String oriImgName = itemImgDto.getOriImgName();
            String imgName = "";
            String imgUrl = "";

            //파일 업로드
            if (StringUtils.isEmpty(oriImgName)) {
                imgName = fileService.upload(imgLocation, oriImgName, itemImgFile.getBytes());
                imgUrl = "/image_file/item/" + imgName;
            }

            //상품 이미지 정보 저장
            ItemImg itemImg = itemImgDto.toEntity();
            itemImg.updateImg(oriImgName, imgName, imgUrl);

            itemImgRepository.save(itemImg);
            log.info("이미지 파일이 저장되었습니다. 저장된 이미지 파일={}", imgName);

        } catch (IOException e) {
            log.error("이미지 파일 저장에 실패했습니다. 실패한 이미지 파일={}", itemImgDto.getOriImgName());
            throw new IOException("이미지 파일 저장에 실패했습니다.");
        }
    }
}