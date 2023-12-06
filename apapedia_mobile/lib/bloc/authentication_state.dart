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


// enum AuthenticationStatus { authenticated, unauthenticated }

// class AuthenticationState extends Equatable {
//   const AuthenticationState._({
//     this.status = AuthenticationStatus.unauthenticated,
//   });

//   const AuthenticationState.uninitialized() : this._();

//   const AuthenticationState.authenticated()
//       : this._(status: AuthenticationStatus.authenticated);

//   const AuthenticationState.unauthenticated()
//       : this._(status: AuthenticationStatus.unauthenticated);

//   final AuthenticationStatus status;

//   @override
//   List<Object> get props => [status];
// }