package com.apapedia.order;

import java.util.Locale;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.apapedia.order.model.SellerDummy;
import com.apapedia.order.restservice.SellerService;
import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);

	}


}
