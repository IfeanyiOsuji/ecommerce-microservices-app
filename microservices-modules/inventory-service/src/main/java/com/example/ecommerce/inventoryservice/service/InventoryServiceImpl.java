package com.example.ecommerce.inventoryservice.service;


import com.example.ecommerce.inventoryservice.dto.InventoryResponse;
import com.example.ecommerce.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    @Override
    @Transactional
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return   inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .isInStock(inventory.getQuantity()>0)
                            .skuCode(inventory.getSkuCode())
                            .build()
                ).toList();
    }
}
