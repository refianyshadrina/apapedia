import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:apapedia_mobile/api/user_api.dart';
import '../repository/user_repository.dart';
import 'authentication_event.dart';
import 'authentication_state.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class AuthenticationBloc
    extends Bloc<AuthenticationEvent, AuthenticationState> {
  final Api api;
  final FlutterSecureStorage secureStorage;

  AuthenticationBloc({required this.api, required this.secureStorage})
      : super(AuthenticationInitialState());

  Future<String?> getStoredToken() async {
    try {
      return await secureStorage.read(key: 'jwtToken');
    } catch (error) {
      return null;
    }
  }

  @override
  Stream<AuthenticationState> mapEventToState(
    AuthenticationEvent event,
  ) async* {
    if (event is AuthenticationSignInEvent) {
      try {

        if (event.isLogin == true) {
            final response = await Api.signIn(event.username, event.password);

            if (response.containsKey('token')) {

              if (response['token'] != "Failed") {
                final jwtToken = response['token'];

                await secureStorage.write(key: 'jwtToken', value: jwtToken);

                yield AuthenticationAuthenticatedState(jwtToken: jwtToken);

              } else {
                yield AuthenticationUnauthenticatedState();
              }

            } else {
              yield AuthenticationUnauthenticatedState();
            }
        } else {
          final String jwtToken = await Api.generateNewToken(event.username, event.password);

          await secureStorage.write(key: 'jwtToken', value: jwtToken);
          yield AuthenticationAuthenticatedState(jwtToken: jwtToken);
        }
      } catch (error) {
        yield AuthenticationUnauthenticatedState();
      }
    } else if (event is AuthenticationSignOutEvent) {
      await secureStorage.delete(key: 'jwtToken');
      yield AuthenticationUnauthenticatedState();
    } else if (event is AuthenticationCheckEvent) {
       try {
        final storedToken = await secureStorage.read(key: 'jwtToken');

        if (storedToken != null) {
        
          yield AuthenticationAuthenticatedState(jwtToken: storedToken);
        } else {
          
          yield AuthenticationUnauthenticatedState();
        }
      } catch (error) {
        
        yield AuthenticationUnauthenticatedState();
      }
    }
  }
}
