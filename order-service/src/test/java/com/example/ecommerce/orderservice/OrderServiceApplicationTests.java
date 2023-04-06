package com.example.ecommerce.orderservice;

import com.example.ecommerce.orderservice.dto.OrderLineItemsDto;
import com.example.ecommerce.orderservice.dto.OrderRequest;
import com.example.ecommerce.orderservice.repository.Orderrepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

	@Container
	private static final MySQLContainer mySQLContainer = new MySQLContainer()
			.withDatabaseName("order_service")
			.withUsername("root")
			.withPassword("2208Mynumber#");
	@Autowired
	MockMvc mockMvc;

	@Autowired
	Orderrepository orderrepository;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void test() {
		assertThat(mySQLContainer.isRunning()).isTrue();
	}

	@Test
	void testMakeOrder() throws Exception {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderLineItemsDtoList(getOrderLineItemsListDto());
		String orderRequestString = objectMapper.writeValueAsString(orderRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order/place-order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(orderRequestString)
		).andExpect(status().isCreated());
		assertEquals(1, orderrepository.findAll().size());
	}

	private List<OrderLineItemsDto> getOrderLineItemsListDto() {
		OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto();
		orderLineItemsDto.setPrice(BigDecimal.valueOf(200000));
		orderLineItemsDto.setQuantity(20);
		orderLineItemsDto.setSkuCode("iphone_14");

		OrderLineItemsDto orderLineItemsDto1 = new OrderLineItemsDto();
		orderLineItemsDto1.setPrice(BigDecimal.valueOf(150000));
		orderLineItemsDto1.setQuantity(18);
		orderLineItemsDto1.setSkuCode("tecno_camon_19");

		List<OrderLineItemsDto>lineItemsDtos = new ArrayList<>();
		lineItemsDtos.add(orderLineItemsDto);
		lineItemsDtos.add(orderLineItemsDto1);
		return lineItemsDtos;

	}


}
