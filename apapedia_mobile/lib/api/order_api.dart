import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:jwt_decoder/jwt_decoder.dart';

class OrderApi {
    static final String url = 'http://localhost:8083/api';
    static final dio = Dio(BaseOptions(baseUrl: url));

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

    static Future<dynamic> updateStatus(String storedToken, String id, int status) async {
    final decodedToken = JwtDecoder.decode(storedToken);
    final userId = decodedToken['userId'];

    Uri uri = Uri.parse('$url/orders/update-status');
    final response = await http.put(
        uri,
        headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer $storedToken',
        },
        body: jsonEncode(<String, dynamic>{
        "id": id,
        "status": status,
        }),
    );

    return response;
    }

    static Future<http.Response> getOrderHistory(String storedToken) async {
        final decodedToken = JwtDecoder.decode(storedToken);
        final userId = decodedToken['userId'];

        Uri uri = Uri.parse('${url}/orders/historyCustomer/${userId}');
        _setupInterceptors(storedToken);

        Map<String, String> header = new Map();
        header['Access-Control-Allow-Origin'] = '*';
        header["content-type"] =  "application/x-www-form-urlencoded";
        header['Content-Type'] = 'application/json';
        header['Authorization'] =  'Bearer $storedToken';
        header['Accept'] =  '*/*';

        final response = await http.get(uri, headers: header);
        
        return response;
    } 
}