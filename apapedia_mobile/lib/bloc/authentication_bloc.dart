import 'dart:async';

import 'package:apapedia_mobile/repository/user_repository.dart';
import 'package:bloc/bloc.dart';
import 'authentication_event.dart';
import 'authentication_state.dart';


class AuthenticationBloc
    extends Bloc<AuthenticationEvent, AuthenticationState> {
  final UserRepository userRepository;

  AuthenticationBloc({required this.userRepository}) : super(const AuthenticationState.unauthenticated());

  @override
  Stream<AuthenticationState> mapEventToState(
    AuthenticationEvent event,
  ) async* {
    if (event is AppStarted) {
      final bool hasToken = await userRepository.hasToken();

      if (hasToken) {
        yield AuthenticationState.authenticated();
      } else {
        yield AuthenticationState.unauthenticated();
      }
    }

    if (event is SignedIn) {
      yield AuthenticationState.authenticated();
    }

    if (event is SignedOut) {
      yield AuthenticationState.unauthenticated();
    }
  }
}
