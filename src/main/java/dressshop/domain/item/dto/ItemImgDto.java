package dressshop.domain.item.dto;

import dressshop.domain.item.ItemImg;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemImgDto {

    private Long id;

    private String imgName;  //이미지 파일명
    private String oriImgName;  //원본 이미지 파일명
    private String imgUrl;  //이미지 조회 경로
    private String repImgYn;  //대표 이미지 여부

    @Builder
    public ItemImgDto(Long id,
                      String imgName,
                      String oriImgName,
                      String imgUrl,
                      String repImgYn) {
        this.id = id;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImgYn = repImgYn;
    }

    public ItemImg toEntity() {
        return ItemImg.builder()
                .id(id)
                .imgName(imgName)
                .oriImgName(oriImgName)
                .imgUrl(imgUrl)
                .repImgYn(repImgYn)
                .build();
    }
}
