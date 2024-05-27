package dressshop.repository.order;

import dressshop.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {
}