package dressshop.domain.item.dto;

import dressshop.domain.item.Item;
import dressshop.domain.item.ItemImg;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemImgDto {

    private Long id;

    private String imgName;  //이미지 파일명
    private String oriImgName;  //원본 이미지 파일명
    private String imgUrl;  //이미지 조회 경로
    private String repImgYn;  //대표 이미지 여부
    private Item item;

    @Builder
    public ItemImgDto(Long id,
                      String imgName,
                      String oriImgName,
                      String imgUrl,
                      String repImgYn,
                      Item item) {
        this.id = id;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImgYn = repImgYn;
        this.item = item;
    }

    public ItemImg toEntity() {
        return ItemImg.builder()
                .id(id)
                .imgName(imgName)
                .oriImgName(oriImgName)
                .imgUrl(imgUrl)
                .repImgYn(repImgYn)
                .item(item)
                .build();
    }
}
