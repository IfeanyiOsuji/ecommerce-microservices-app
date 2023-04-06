package com.example.ecommerce.orderservice.service;

import com.example.ecommerce.orderservice.dto.OrderLineItemsDto;
import com.example.ecommerce.orderservice.dto.OrderRequest;
import com.example.ecommerce.orderservice.model.Order;
import com.example.ecommerce.orderservice.model.OrderLineItems;
import com.example.ecommerce.orderservice.repository.OrderLineItemsRepository;
import com.example.ecommerce.orderservice.repository.Orderrepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class Orderserviceimpl implements OrderService{

    private final Orderrepository orderrepository;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToDto).toList();
        order.setOrderLineItemsList(orderLineItems);
        log.info("order line items list -> {}", order.getOrderLineItemsList());
        orderrepository.save(order);
        log.info(" Order Saved");

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        log.info("orderLineItems ->{}", orderLineItems);

        return orderLineItems;
    }
}
