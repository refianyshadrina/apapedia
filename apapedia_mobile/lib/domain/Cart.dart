import '../domain/CartItem.dart';

class Cart {
  String cartId;
  String userId;
  int totalPrice;
  List<CartItem> listCartItem;

  Cart({
    required this.cartId,
    required this.userId,
    required this.totalPrice,
    required this.listCartItem,
  });

  factory Cart.fromJson(Map<String, dynamic> json) => Cart(
        cartId: json['cart_id'],
        userId: json['id_user'],
        totalPrice: json['total_price'],
        listCartItem: List<CartItem>.from(
          json['list_cart_item'].map((x) => CartItem.fromJson(x)),
        ),
      );

  Map<String, dynamic> toJson() => {
        'cart_id': cartId,
        'id_user': userId,
        'total_price': totalPrice,
        'list_cart_item': List<dynamic>.from(
          listCartItem.map((x) => x.toJson()),
        ),
      };
}