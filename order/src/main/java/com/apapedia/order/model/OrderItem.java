// package com.apapedia.order.model;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotNull;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// import java.util.UUID;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Entity
// @Table(name = "order_item")
// public class OrderItem {

//     @NotNull
//     @Column(name = "product_id", nullable = false)
//     private UUID productId;

//     @NotNull
//     @Column(name = "order_id", nullable = false)
//     private UUID orderId;

//     @ManyToOne(fetch = FetchType.EAGER)
//     @JoinColumn(name = "order", referencedColumnName = "order_id")
//     private Order order;

//     @NotNull
//     @Column(name = "quantity", nullable = false)
//     private Integer quantity;

//     @NotNull
//     @Column(name = "product_name", nullable = false)
//     private Integer productName;
 
//     @NotNull
//     @Column(name = "product_price", nullable = false)
//     private Integer productPrice;

// }
