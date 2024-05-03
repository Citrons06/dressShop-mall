package dressshop.domain.item;

import dressshop.domain.item.dto.ItemImgDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class ItemImg {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_img_id")
    private Long id;

    private String imgName;  //이미지 파일명
    private String oriImgName;  //원본 이미지 파일명
    private String imgUrl;  //이미지 조회 경로

    @Setter
    private String repImgYn;  //대표 이미지 여부

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    @Setter
    private Item item;

    @Builder
    public ItemImg(Long id,
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

    public ItemImgDto toDto() {
        return ItemImgDto.builder()
                .id(id)
                .imgName(imgName)
                .oriImgName(oriImgName)
                .imgUrl(imgUrl)
                .repImgYn(repImgYn)
                .item(item)
                .build();
    }

    //상품 이미지 수정 로직
    public void updateImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
