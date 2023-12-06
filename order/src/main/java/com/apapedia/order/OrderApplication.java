package com.apapedia.order;

import java.util.Locale;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.apapedia.order.model.SellerDummy;
import com.apapedia.order.restservice.SellerRestService;
import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);

	}

	// @Bean
	// @Transactional
	// CommandLineRunner run(SellerRestService sellerService) {
	// 	return args -> {
	// 		Faker faker = new Faker(new Locale("in-ID"));

	// 		for(int j=0;j<10;j++){
	// 			SellerDummy seller = new SellerDummy();
	// 			seller.setNama(faker.name().firstName());
	// 			sellerService.createSellerDummy(seller);
	// 		}
	// 	};
	// }

}
