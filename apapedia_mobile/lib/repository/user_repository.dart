import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class UserRepository {
  final FlutterSecureStorage _storage;
  final String key = "token";
  final String emailKey = "email";
  final String namaKey = "nama";
  final String usernameKey = "username";

  final String idKey = "uuid";
  final String addressKey = "address";
  final String createdAtKey = "createdAt";
  final String updatedAtKey = "updatedAt";
  final String balanceKey = "balance";
  final String roleKey = "customer";

  UserRepository({required FlutterSecureStorage storage}) : _storage = storage;

  Future<void> clearAll() async {
    await _storage.delete(key: emailKey);
    await _storage.delete(key: namaKey);
    await _storage.delete(key: usernameKey);
    await _storage.delete(key: balanceKey);
    await _storage.delete(key: idKey);
    await _storage.delete(key: addressKey);
    await _storage.delete(key: createdAtKey);
    await _storage.delete(key: updatedAtKey);
    await _storage.delete(key: roleKey);

  }

  Future<Map> getData() async {
    Map data = {};
    data[emailKey] = await _storage.read(key: emailKey);
    data[namaKey] = await _storage.read(key: namaKey);
    data[usernameKey] = await _storage.read(key: usernameKey);
    data[balanceKey] = await _storage.read(key: balanceKey);
    data[addressKey] = await _storage.read(key: addressKey);
    data[idKey] = await _storage.read(key: idKey);
    data[createdAtKey] = await _storage.read(key: createdAtKey);
    data[updatedAtKey] = await _storage.read(key: updatedAtKey);
    data[roleKey] = await _storage.read(key: roleKey);
    data[key] = await _storage.read(key: key);
    return data;
  }

  Future<void> deleteToken() async {
    await _storage.delete(key: key);
  }

  Future<void> persistToken(String token) async {
    await _storage.write(key: key, value: token);
  }

  Future<bool> hasToken() async {
    final value = await _storage.read(key: key);
    return value != null;
  }

  Future<String?> getToken() async {
    return await _storage.read(key: key);
  }

  Future<String?> getNama() async {
    return await _storage.read(key: namaKey);
  }

  Future<void> setNama(String nama) async {
    await _storage.write(key: namaKey, value: nama);
  }

  Future<String?> getEmail() async {
    return await _storage.read(key: emailKey);
  }

  Future<void> setEmail(String email) async {
    await _storage.write(key: emailKey, value: email);
  }

  Future<String?> getUsername() async {
    return await _storage.read(key: usernameKey);
  }

  Future<void> setUsername(String username) async {
    await _storage.write(key: usernameKey, value: username);
  }
  
  Future<String?> getBalance() async {
    return await _storage.read(key: balanceKey);
  }

  Future<void> setSaldo(String balance) async {
    await _storage.write(key: balanceKey, value: balance);
  }
  
  Future<String?> getAddress() async {
    return await _storage.read(key: addressKey);
  }

  Future<void> setAddress(String address) async {
    await _storage.write(key: addressKey, value: address);
  }

  Future<String?> getCreatedAt() async {
    return await _storage.read(key: createdAtKey);
  }

  Future<void> setCreatedAt(String createdAt) async {
    await _storage.write(key: createdAtKey, value: createdAt);
  }

  Future<String?> getUpdatedAt() async {
    return await _storage.read(key: updatedAtKey);
  }

  Future<void> setUpdatedAt(String updatedAt) async {
    await _storage.write(key: updatedAtKey, value: updatedAt);
  }

  Future<String?> getUUID() async {
    return await _storage.read(key: idKey);
  }

  Future<void> setUUID(String uuid) async {
    await _storage.write(key: idKey, value: uuid);
  }
  
  Future<String?> getRoles() async {
    return await _storage.read(key: roleKey);
  }

  Future<void> setRoles(String roles) async {
    await _storage.write(key: roleKey, value: roles);
  }
}