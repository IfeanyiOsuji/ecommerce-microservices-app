package com.example.ecommerce.inventoryservice.service;

import com.example.ecommerce.inventoryservice.dto.InventoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    List<InventoryResponse> isInStock(List<String> skuCode);
}
