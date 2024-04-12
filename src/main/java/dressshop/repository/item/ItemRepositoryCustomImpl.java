package dressshop.repository.item;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dressshop.domain.item.Item;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dressshop.domain.item.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Item> findList() {
        return query.selectFrom(item)
                .orderBy(item.id.desc())
                .offset(0)
                .limit(20)
                .fetch();
    }
}
