import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:jwt_decoder/jwt_decoder.dart';

class Api {
  static final String url = 'http://localhost:8080/api/user';
  static final dio = Dio(BaseOptions(baseUrl: url));

  static Future<http.Response> signUp(String email, String password, String username, String nama,  String address) async {
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

    return response;
  }

  static void _setupInterceptors(String jwtToken) {
    dio.interceptors.add(
      InterceptorsWrapper(
        onRequest: (options, handler) {
          // Set the Authorization header with Bearer + jwtToken
          options.headers['Authorization'] = 'Bearer $jwtToken';
          return handler.next(options);
        },
      ),
    );
  }

  static Future<http.Response> getUserProfile(String storedToken) async {
      final decodedToken = JwtDecoder.decode(storedToken);
      final userId = decodedToken['userId'];

      print(userId);

      Uri uri = Uri.parse('${url}/detail/${userId}');
      _setupInterceptors(storedToken);

      print(uri);

      Map<String, String> header = new Map();
      header['Access-Control-Allow-Origin'] = '*';
      header["content-type"] =  "application/x-www-form-urlencoded";
      header['Content-Type'] = 'application/json';
      header['Authorization'] =  'Bearer $storedToken';
      header['Accept'] =  '*/*';



      final response = await http.get(uri, headers: header
      );
      // await dio.get('/profile/$userId');

      return response;

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
