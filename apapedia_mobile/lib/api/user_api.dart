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

  static Future<http.Response> updateUser(String storedToken, String email, String password, String username, String nama,  String address) async {
    final decodedToken = JwtDecoder.decode(storedToken);
    final userId = decodedToken['userId'];

    Uri uri = Uri.parse('${url}/update');

    Map<String, String> header = new Map();
    header['Access-Control-Allow-Origin'] = '*';
    header["content-type"] =  "application/x-www-form-urlencoded";
    header['Content-Type'] = 'application/json; charset=UTF-8';
    header['Authorization'] =  'Bearer $storedToken';
    header['Accept'] =  '*/*';

    final response = await http.put(
      uri,
      headers: header,
      body: jsonEncode(<String, String>{
        "id": userId,
        "nama": nama,
        "email": email,
        "username": username,
        "password": password,
        "address": address
      }),
    );

    return response;
  }

 static Future<String> generateNewToken(String username, String passwordDTO) async {
    Uri uri = Uri.parse('${url}/generate-new-token');
    final response = await http.post(
      uri,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(
          <String, String>{'username': username, 'password': passwordDTO}),
    );

    final Map parsedResponse = json.decode(response.body);
    final String token = parsedResponse['token'];

    return token;
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

  static Future<dynamic> topUp(String storedToken, String balance) async {
    final decodedToken = JwtDecoder.decode(storedToken);
    final userId = decodedToken['userId'];
    Uri uri = Uri.parse('${url}/self-update-balance');
    final response = await http.put(
      uri,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer $storedToken'
      },
      body: jsonEncode(<String, String>{
        "id": userId,
        "balance": balance
      }),
    );

    return response;
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

  static Future<int> deleteUserPage(String storedToken) async {
      final decodedToken = JwtDecoder.decode(storedToken);
      final userId = decodedToken['userId'];

      final response = await http.delete(
      Uri.parse('${url}/delete/${userId}'),
      headers:{
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $storedToken',
      },);

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
