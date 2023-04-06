package com.example.ecommerce;

import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.5");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void shouldCreateTest() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productrequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/product-create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productrequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("i phone 14")
				.description(" i phone 14")
				.price(BigDecimal.valueOf(100000))
				.build();
	}

	@Test
	void shouldReturnProducts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product/getAllProducts")
				)
				.andExpect(status().isOk());
	}


}
