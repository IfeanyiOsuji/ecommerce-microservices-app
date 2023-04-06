package com.example.ecommerce.inventoryservice;

import com.example.ecommerce.inventoryservice.model.Inventory;
import com.example.ecommerce.inventoryservice.model.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);

	}


	@Bean
	public CommandLineRunner loadData(final InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantity(30);
			inventoryRepository.save(inventory);
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("camon_19");
			inventory1.setQuantity(25);
			inventoryRepository.save(inventory1);
		};


	}


}
