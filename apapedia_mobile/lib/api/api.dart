import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:jwt_decoder/jwt_decoder.dart';

class Api {
  static final String url = 'http://localhost:8080/api/user';
  static final dio = Dio(BaseOptions(baseUrl: url));

  static Future<int> signUp(String email, String password, String username, String nama,  String address) async {
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
        "address": address
      }),
    );

    return response.statusCode;
  }

  static Future<Map> signIn(String username, String password) async {
    Uri uri = Uri.parse('${url}/v1/login');
    final response = await http.post(
      uri,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(
          <String, String>{'username': username, 'password': password}),
    );
    if (response.statusCode == 201) {
      final Map parsedResponse = json.decode(response.body);
      final String token = parsedResponse['token'];
      bool isTokenExpired = JwtDecoder.isExpired(token);
      if (!isTokenExpired) {
        return parsedResponse;
      }
    }
    return {"token": "Failed"};
  }
}
