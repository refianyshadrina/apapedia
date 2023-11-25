

String formatDateTime(String unformattedDateTime) {
  List<String> datetime = unformattedDateTime.split("T");
  String date = datetime[0];
  String time = datetime[1];
  String formattedDate =
      "${date.split("-")[2]}-${date.split("-")[1]}-${date.split("-")[0]}";
  String formattedTime = "${time.split(":")[0]}:${time.split(":")[1]}";
  return "$formattedDate $formattedTime";
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