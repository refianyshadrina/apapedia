
import 'package:equatable/equatable.dart';

abstract class AuthenticationEvent extends Equatable {
  AuthenticationEvent();

  @override
  List<Object> get props => [];
}

class AppStarted extends AuthenticationEvent {
  AppStarted();
}

class SignedIn extends AuthenticationEvent {
  final String token;

  SignedIn({required this.token});

  @override
  List<Object> get props => [token];

  @override
  String toString() => 'LoggedIn { token: $token }';
}

class SignedOut extends AuthenticationEvent {
  @override
  String toString() => 'LoggedOut';
}
