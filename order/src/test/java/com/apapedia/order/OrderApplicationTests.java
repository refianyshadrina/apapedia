package com.apapedia.order;
import com.apapedia.order.dto.OrderMapper;
import com.apapedia.order.model.Order;
import com.apapedia.order.restcontroller.OrderRestController;
import com.apapedia.order.restservice.OrderRestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderRestController.class)
@ContextConfiguration(classes = { OrderApplicationTests.class, OrderMapper.class })
@AutoConfigureMockMvc(addFilters = false)
public class OrderApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRestService orderRestService;

    @Autowired
    private ObjectMapper objectMapper;

	

    @Test
    public void checkPostOrderTest() throws Exception {
        Order orders = new Order();
        orders.setId(UUID.randomUUID());
        orders.setCreatedAt(new Date());
        orders.setStatus(0);
        orders.setTotalPrice(1500);
        orders.setCustomerId(UUID.randomUUID());
        orders.setSellerId(UUID.randomUUID());

        given(orderRestService.createOrders(any(Order.class))).willReturn(orders);

        ResultActions response = mockMvc.perform(post("/api/orders/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orders)));
		
				

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.status", is(orders.getStatus())))
                .andExpect(jsonPath("$.totalPrice", is(orders.getTotalPrice())))
                .andExpect(jsonPath("$.customerId", is(orders.getCustomerId().toString())))
                .andExpect(jsonPath("$.sellerId", is(orders.getSellerId().toString())));
    }

	@Test
	public void testGetOrdersByCustomer() throws Exception {
		String customerId = "1d1eea95-3ad4-4f4c-adf9-d129fb6f0f26";
		List<Order> orders = new ArrayList<>();
		Order order1 = new Order();
		order1.setId(UUID.randomUUID());
		order1.setCustomerId(UUID.fromString(customerId));
		orders.add(order1);

		given(orderRestService.getOrdersByCustomerId(UUID.fromString(customerId))).willReturn(orders);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{customer_id}", UUID.fromString(customerId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].id").exists());
	}

	@Test
	public void testGetOrdersBySeller() throws Exception {
		String sellerId = "7c9e6679-7425-40de-944b-e07fc1f90ae8";
		List<Order> orders = new ArrayList<>();
		Order order1 = new Order();
		order1.setId(UUID.randomUUID());
		order1.setSellerId(UUID.fromString(sellerId));
		orders.add(order1);


		given(orderRestService.getOrdersBySellerId(UUID.fromString(sellerId))).willReturn(orders);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/seller/{seller_id}", UUID.fromString(sellerId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}




}
