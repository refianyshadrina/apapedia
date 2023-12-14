package com.apapedia.frontend.restService;

import com.apapedia.frontend.payloads.TotalDTO;
import com.apapedia.frontend.payloads.OrderDTO;
import java.util.List;
import java.util.UUID;

public interface OrderRestService {
    List<OrderDTO> getOrderBySeller(UUID seller_id, String jwtToken);
    List<OrderDTO> getOrderHistory(UUID seller_id, String jwtToken);
    void updateStatus(UUID id, int status, String jwtToken);
    List<TotalDTO> getTop5(String sellerId, String jwtToken);
}
