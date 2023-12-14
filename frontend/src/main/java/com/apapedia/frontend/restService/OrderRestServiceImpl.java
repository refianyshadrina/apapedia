package com.apapedia.frontend.restService;

import com.apapedia.frontend.payloads.TotalDTO;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import com.apapedia.frontend.payloads.OrderDTO;
import com.apapedia.frontend.payloads.UpdateOrderRequestDTO;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderRestServiceImpl implements OrderRestService {
    
    private final WebClient webClient;

    public OrderRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://web-order:8083")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public List<TotalDTO> getTop5(String sellerId, String jwtToken) {
        List<TotalDTO> totalDtoList = webClient
                .get()
                .uri("api/orders/top5/{sellerid}", sellerId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TotalDTO>>() {})
                .block();

        return totalDtoList;
    }

    @Override
    public List<OrderDTO> getOrderBySeller(UUID seller_id, String jwtToken) {
        try {
            Flux<OrderDTO> listOrder = webClient
                    .get()
                    .uri("/api/seller/{seller_id}", seller_id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(OrderDTO.class);

            return listOrder.collectList().block();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching order history", e);
        }
    }

    @Override
    public List<OrderDTO> getOrderHistory(UUID seller_id, String jwtToken) {
        try {
            Flux<OrderDTO> listOrder = webClient
                    .get()
                    .uri("/api/orders/history/{seller_id}", seller_id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(OrderDTO.class);

            return listOrder.collectList().block();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching order history", e);
        }
    }

    @Override
    public void updateStatus(UUID id, int status, String jwtToken) {
        try {
            UpdateOrderRequestDTO request = new UpdateOrderRequestDTO(id, status);
            String response = webClient
                    .put()
                    .uri("/api/orders/update-status")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(request), UpdateOrderRequestDTO.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
                
        } catch (Exception e) {
            throw new RuntimeException("Error updating order status", e);
        }
    }
}
