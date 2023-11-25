package com.apapedia.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

// import com.apapedia.order.model.OrderItem;
import com.apapedia.order.model.SellerDummy;
import com.apapedia.order.model.UserDummy;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateOrderRequestDTO {

    private Date createdAt;
    private Date updatedAt;
    private Integer status;
    private SellerDummy sellerId;
    private UserDummy userId;
    // private List<OrderItem> listOrderItem;


}
