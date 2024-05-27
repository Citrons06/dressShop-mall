package dressshop.domain.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import dressshop.domain.item.Item;
import dressshop.domain.item.ItemSellStatus;
import dressshop.domain.order.OrderItem;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class ItemDto {

    private Long id;

    @NotNull(message = "카테고리를 선택해 주세요.")
    private Long categoryId;

    @NotBlank(message = "상품 이름을 입력하세요.")
    private String itemName;

    @NotNull(message = "가격을 입력하세요.")
    @Min(value = 3000, message = "최소 가격은 3000원 입니다.")
    private int price;

    @NotNull(message = "재고 수량을 입력하세요.")
    @Min(value = 1, message = "최소 수량은 1개 입니다.")
    private int quantity;

    @Max(value = 100, message = "최대 100개까지 주문할 수 있습니다.")
    private int count;

    private CategoryDto categoryDto;
    private String categoryName;

    private List<OrderItem> orderItems;
    private String imgName;

    //기본값 = 판매 중으로 설정
    private ItemSellStatus itemSellStatus = ItemSellStatus.SELL;

    private List<MultipartFile> itemImgs = new ArrayList<>();

    @NotNull(message = "이미지 파일을 선택해 주세요.")
    private MultipartFile itemImg;

    private List<Long> itemImgIds = new ArrayList<>();

    @Builder
    @QueryProjection
    public ItemDto(Long id,
                   Long categoryId,
                   String itemName,
                   int price,
                   int quantity,
                   CategoryDto categoryDto,
                   String categoryName,
                   ItemSellStatus itemSellStatus,
                   MultipartFile itemImgDto,
                   List<MultipartFile> itemImgDtoList,
                   List<Long> itemImgIds) {
        this.id = id;
        this.categoryId = categoryId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.categoryDto = categoryDto;
        this.categoryName = categoryName;
        this.itemSellStatus = itemSellStatus;
        this.itemImg = itemImgDto;
        this.itemImgs = itemImgDtoList;
        this.itemImgIds = itemImgIds;
    }

    public ItemDto(Long id, String itemName, List<OrderItem> orderItems, int price, String imgName) {
        this.id = id;
        this.itemName = itemName;
        this.orderItems = orderItems;
        this.price = price;
        this.imgName = imgName;
    }

    public Item toEntity() {
        return Item.builder()
                .id(id)
                .itemName(itemName)
                .price(price)
                .quantity(quantity)
                .itemSellStatus(itemSellStatus)
                .build();
    }
}
