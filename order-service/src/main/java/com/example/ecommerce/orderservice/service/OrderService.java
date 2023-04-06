package com.example.ecommerce.orderservice.service;

import com.example.ecommerce.orderservice.dto.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
