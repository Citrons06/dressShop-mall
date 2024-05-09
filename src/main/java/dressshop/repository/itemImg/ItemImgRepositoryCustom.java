package dressshop.repository.itemImg;

import dressshop.domain.item.ItemImg;

public interface ItemImgRepositoryCustom {
    ItemImg findRepImgByItemId(Long itemId);
}
