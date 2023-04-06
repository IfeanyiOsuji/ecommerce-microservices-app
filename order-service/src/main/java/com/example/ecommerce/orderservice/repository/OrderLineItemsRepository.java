package com.example.ecommerce.orderservice.repository;

import com.example.ecommerce.orderservice.model.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems, Long> {
}
