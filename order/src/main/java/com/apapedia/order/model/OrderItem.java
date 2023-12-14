
package com.apapedia.order.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.apapedia.order.dto.CatalogDTO;
import com.apapedia.order.dto.OrderItemDTO;
import com.apapedia.order.model.CartItem;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @NotNull
    @Column(name = "order_item_id", nullable = false)
    private UUID orderId = UUID.randomUUID();
    
    @NotNull
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;
 
    @NotNull
    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orders", referencedColumnName = "order_id")
    private Order orders;



    // public void fromCatalogDetail(CatalogDTO catalog) {
    //     CatalogDTO catalogDTO = new CatalogDTO();
    //     productId = catalogDTO.getId();
    //     productName = catalogDTO.getProductName();
    //     productPrice = catalogDTO.getPrice();
    // }

    
    // public void fromCartItem(CartItem catalog) {
    //     CartItem cartItem = new CartItem();
    //     quantity = cartItem.getQuantity();        
    // }

}

