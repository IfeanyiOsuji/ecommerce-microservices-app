package com.example.ecommerce.orderservice.repository;

import com.example.ecommerce.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Orderrepository extends JpaRepository<Order, Long> {
}
