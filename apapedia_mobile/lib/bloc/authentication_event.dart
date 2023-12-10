import 'package:equatable/equatable.dart';
import 'package:flutter/cupertino.dart';

abstract class AuthenticationEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class AuthenticationSignInEvent extends AuthenticationEvent {
  final String username;
  final String password;
  final bool isLogin;
  final BuildContext context;

  AuthenticationSignInEvent({required this.username, required this.password, required this.isLogin,required this.context});

  @override
  List<Object> get props => [username, password];
}

class AuthenticationSignOutEvent extends AuthenticationEvent {}

class AuthenticationCheckEvent extends AuthenticationEvent {
  final BuildContext context;

  AuthenticationCheckEvent({required this.context});
}
