package dressshop.repository.item;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dressshop.domain.item.Item;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dressshop.domain.item.QCategory.category;
import static dressshop.domain.item.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Item> findList() {
        return query.selectFrom(item)
                .leftJoin(item.category, category).fetchJoin()
                .fetch();
    }

    @Override
    public Item findOne(Long itemId) {
        return query.selectFrom(item)
                .join(item.category, category).fetchJoin()
                .where(item.id.eq(itemId))
                .fetchOne();
    }
}
