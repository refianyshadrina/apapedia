

class Catalog {
  final String id;
  final String sellerId;
  final int price;
  final String productName;
  final String productDescription;
  final int stock;
  final List<int> image;
  final bool isDeleted;

  Catalog({
    required this.id,
    required this.sellerId,
    required this.price,
    required this.productName,
    required this.productDescription,
    required this.stock,
    required this.image,
    required this.isDeleted,
  });

  factory Catalog.fromJson(Map<String, dynamic> json) => Catalog(
        id: json['id'],
        sellerId: json['sellerId'],
        price: json['price'],
        productName: json['productName'],
        productDescription: json['productDescription'],
        stock: json['stock'],
        image: List<int>.from(json['image']),
        isDeleted: json['isDeleted'],
      );

  Map<String, dynamic> toJson() => {
        'id': id,
        'sellerId': sellerId,
        'price': price,
        'productName': productName,
        'productDescription': productDescription,
        'stock': stock,
        'image': image,
        'isDeleted': isDeleted,
      };
}