package dressshop.repository.itemImg;

import dressshop.domain.item.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>, ItemImgRepositoryCustom {

    List<ItemImg> findByItemId(Long itemId);

}
