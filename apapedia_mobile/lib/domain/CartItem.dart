import '../domain/Cart.dart';

class CartItem {
  String id;
  String productId;
  Cart cart;
  int quantity;

  CartItem({
    required this.id,
    required this.productId,
    required this.cart,
    required this.quantity,
  });

  factory CartItem.fromJson(Map<String, dynamic> json) => CartItem(
        id: json['cart_item_id'],
        productId: json['product_id'],
        cart: Cart.fromJson(json['cart']),
        quantity: json['quantity'],
      );

  Map<String, dynamic> toJson() => {
        'cart_item_id': id,
        'product_id': productId,
        'cart': cart.toJson(),
        'quantity': quantity,
      };
}