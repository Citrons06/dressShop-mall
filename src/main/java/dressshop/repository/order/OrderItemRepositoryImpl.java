package dressshop.repository.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dressshop.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dressshop.domain.order.QOrderItem.orderItem;

@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return query.selectFrom(orderItem)
                .where(orderItem.order.id.eq(orderId))
                .fetch();
    }
}
