package com.example.ecommerce.orderservice.controller;

import com.example.ecommerce.orderservice.dto.OrderRequest;
import com.example.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place-order")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest request){
        if(request.getOrderLineItemsDtoList() == null){
            log.info("orderLineitemsList is null -> {}", (Object) null);

        }
        orderService.placeOrder(request);
        return "Order placed successfully";
    }
}
