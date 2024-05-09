package dressshop.repository.itemImg;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dressshop.domain.item.ItemImg;
import lombok.RequiredArgsConstructor;

import static dressshop.domain.item.QItemImg.itemImg;

@RequiredArgsConstructor
public class ItemImgRepositoryImpl implements ItemImgRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public ItemImg findRepImgByItemId(Long itemId) {
        return query.selectFrom(itemImg)
                .where(itemImg.item.id.eq(itemId)
                        .and(itemImg.repImgYn.eq("Y")))
                .fetchOne();
    }
}
