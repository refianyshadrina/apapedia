// import 'dart:async';

// import 'package:apapedia_mobile/repository/user_repository.dart';
// import 'package:bloc/bloc.dart';
// import 'authentication_event.dart';
// import 'authentication_state.dart';


// class AuthenticationBloc
//     extends Bloc<AuthenticationEvent, AuthenticationState> {
//   final UserRepository userRepository;

//   AuthenticationBloc({required this.userRepository}) : super(const AuthenticationState.unauthenticated());

//   @override
//   Stream<AuthenticationState> mapEventToState(
//     AuthenticationEvent event,
//   ) async* {
//     if (event is AppStarted) {
//       final bool hasToken = await userRepository.hasToken();

//       if (hasToken) {
//         yield AuthenticationState.authenticated();
//       } else {
//         yield AuthenticationState.unauthenticated();
//       }
//     }

//     if (event is SignedIn) {
//       yield AuthenticationState.authenticated();
//     }

//     if (event is SignedOut) {
//       yield AuthenticationState.unauthenticated();
//     }
//   }
// }

import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:apapedia_mobile/api/api.dart'; // Import your API class
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

  @override
  Stream<AuthenticationState> mapEventToState(
    AuthenticationEvent event,
  ) async* {
    if (event is AuthenticationSignInEvent) {
      try {
        final response = await Api.signIn(event.username, event.password);

        if (response.containsKey('token')) {

          if (response['token'] != "Failed") {
            final jwtToken = response['token'];

            await secureStorage.write(key: 'jwtToken', value: jwtToken);

            // RepositoryProvider.of<UserRepository>(event.context)
            //     .setEmail(response['email']); // Replace with actual key for email in the response
            // RepositoryProvider.of<UserRepository>(event.context)
            //     .setUsername(response['username']);
            yield AuthenticationAuthenticatedState(jwtToken: jwtToken);

          } else {
            yield AuthenticationUnauthenticatedState();
          }

        } else {
          yield AuthenticationUnauthenticatedState();
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
        // final storedToken = await RepositoryProvider.of<UserRepository>(event.context)
        //     .getToken();
        if (storedToken != null) {
          // Token exists, consider the user as authenticated
          yield AuthenticationAuthenticatedState(jwtToken: storedToken);
        } else {
          // Token does not exist, user is not authenticated
          yield AuthenticationUnauthenticatedState();
        }
      } catch (error) {
        // Handle error, for example, token reading failed
        yield AuthenticationUnauthenticatedState();
      }
    }
  }
}
