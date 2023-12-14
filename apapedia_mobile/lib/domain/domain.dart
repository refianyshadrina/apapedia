

// import 'dart:ffi';

String formatDateTime(String unformattedDateTime) {
  List<String> datetime = unformattedDateTime.split("T");
  String date = datetime[0];
  String time = datetime[1];
  String formattedDate =
      "${date.split("-")[2]}-${date.split("-")[1]}-${date.split("-")[0]}";
  String formattedTime = "${time.split(":")[0]}:${time.split(":")[1]}";
  return "$formattedDate $formattedTime";
}

String formatBalance(int balance) {
  String newBalance = "";
  String balanceString = balance.toString();
  for(int i = 0; i < balanceString.length; i++) {
    
  }
  return newBalance;
}

class Customer {
  final String username;
  final String nama;
  final String email;
  final String id;
  final String address;
  final String createdAt;
  final String updatedAt;
  late final int balance;

  Customer(
      {required this.username,
      required this.nama,
      required this.email,
      required this.id,
      required this.address,
      required this.createdAt,
      required this.updatedAt,
      required this.balance});

  factory Customer.fromJson(Map<String, dynamic> json) => Customer(
      username: json["username"],
      nama: json["nama"],
      email: json["email"],
      id: json["id"],
      address: json["address"],
      createdAt: json["createdAt"],
      updatedAt: json["updatedAt"],
      balance: json["balance"]);

  Map<dynamic, dynamic> toJson() => {
        "username": username,
        "nama": nama,
        "email": email,
        "id": id,
        "address": address,
        "createdAt": createdAt,
        "updatedAt": updatedAt,
        "balance": balance,
      };
}

class Order {
  final String id;
  final String createdAt;
  final String updatedAt;
  late final int status;
  final int totalPrice;
  final String customerId;
  final String sellerId;
  final List<OrderItem> orderItems;

  Order({
    required this.id,
    required this.createdAt,
    required this.updatedAt,
    required this.status,
    required this.totalPrice,
    required this.customerId,
    required this.sellerId,
    required this.orderItems,
  });

  factory Order.fromJson(Map<String, dynamic> json) => Order(
        id: json["id"],
        createdAt: json["createdAt"],
        updatedAt: json["updatedAt"],
        status: json["status"],
        totalPrice: json["totalPrice"],
        customerId: json['customerId'],
        sellerId: json["sellerId"],
        orderItems: List<OrderItem>.from(
          json["orderItems"].map(
            (item) => OrderItem.fromJson(item),
          ),
        ),
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "createdAt": createdAt,
        "updatedAt": updatedAt,
        "status": status,
        "totalPrice": totalPrice,
        "customerId": customerId,
        "sellerId": sellerId,
        "orderItems": List<dynamic>.from(
          orderItems.map((item) => item.toJson()),
        ),
      };
}

class OrderItem {
  final String id;
  final String productId;
  final int quantity;
  final String productName;
  final int productPrice;

  OrderItem({
    required this.id,
    required this.productId,
    required this.quantity,
    required this.productName,
    required this.productPrice,
  });

  factory OrderItem.fromJson(Map<String, dynamic> json) => OrderItem(
        id: json["id"],
        productId: json["productId"],
        quantity: json["quantity"],
        productName: json["productName"],
        productPrice: json["productPrice"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "productId": productId,
        "quantity": quantity,
        "productName": productName,
        "productPrice": productPrice,
      };
}
