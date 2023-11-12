import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:dio/dio.dart';
// import 'package:jwt_decoder/jwt_decoder.dart';

class Api {
  static final String url = 'http://localhost:8080/api';
  static final dio = Dio(BaseOptions(baseUrl: url));

  static Future<int> signUp(String email, String password, String username, String nama, String balance, String address, String cartId) async {
    Uri uri = Uri.parse('${url}/register');
    final response = await http.post(
      uri,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        "nama": nama,
        "email": email,
        "username": username,
        "password": password,
        "balance": balance,
        "address": address,
        "cartId": cartId
      }),
    );

    return response.statusCode;
  }
}
