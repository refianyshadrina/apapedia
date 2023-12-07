import 'package:equatable/equatable.dart';

abstract class AuthenticationState extends Equatable {
  @override
  List<Object> get props => [];
}

class AuthenticationInitialState extends AuthenticationState {}

class AuthenticationAuthenticatedState extends AuthenticationState {
  final String jwtToken;

  AuthenticationAuthenticatedState({required this.jwtToken});

  @override
  List<Object> get props => [jwtToken];
}

class AuthenticationUnauthenticatedState extends AuthenticationState {}