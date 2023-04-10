package com.example.ecommerce.orderservice.service;

import com.example.ecommerce.orderservice.dto.InventoryResponse;
import com.example.ecommerce.orderservice.dto.OrderLineItemsDto;
import com.example.ecommerce.orderservice.dto.OrderRequest;
import com.example.ecommerce.orderservice.model.Order;
import com.example.ecommerce.orderservice.model.OrderLineItems;
import com.example.ecommerce.orderservice.repository.OrderLineItemsRepository;
import com.example.ecommerce.orderservice.repository.Orderrepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class Orderserviceimpl implements OrderService{

    private final Orderrepository orderrepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToDto).toList();
        order.setOrderLineItemsList(orderLineItems);

        /*Get All the skuCodes from the order and pass it as queryparam to the uri*/
        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode).toList();


        /* call inventory service to check if the product is stock */
          InventoryResponse[] responses =   webClientBuilder.build().get()
                            .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                            .retrieve()
                            .bodyToMono(InventoryResponse[].class)
                            .block();
        assert responses != null;
        boolean allProductsInStock = Arrays.stream(responses).allMatch(InventoryResponse::isInStock);
          if(allProductsInStock)
              orderrepository.save(order);
          else
              throw new IllegalArgumentException("Product is currently out of stock please try again later");
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
