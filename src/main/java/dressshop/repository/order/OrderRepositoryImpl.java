package dressshop.repository.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dressshop.domain.delivery.QDelivery;
import dressshop.domain.order.Order;
import dressshop.domain.order.dto.OrderDto;
import dressshop.domain.order.dto.QOrderDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dressshop.domain.item.QItem.item;
import static dressshop.domain.order.QOrder.order;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Order> findAllOrders() {
        QDelivery qDelivery = QDelivery.delivery;

        return query
                .select(order)
                .distinct()
                .from(order)
                .leftJoin(order.delivery, qDelivery).fetchJoin()
                .leftJoin(order.orderItems).fetchJoin()
                .leftJoin(item.itemImgs).fetchJoin()
                .fetch();
    }
}
