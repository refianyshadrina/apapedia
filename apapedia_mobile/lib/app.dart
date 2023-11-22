import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:apapedia_mobile/bloc/authentication_bloc.dart';
import 'package:apapedia_mobile/bloc/authentication_event.dart';
import 'package:apapedia_mobile/bloc/authentication_state.dart';
import 'package:apapedia_mobile/constants.dart';
import 'package:apapedia_mobile/repository/user_repository.dart';
import 'package:apapedia_mobile/routes.dart';
import 'package:apapedia_mobile/screens/pages.dart';

class MyApp extends StatelessWidget {
  final _navigatorKey = GlobalKey<NavigatorState>();

  NavigatorState? get _navigator => _navigatorKey.currentState;
  final secureStorage = FlutterSecureStorage();

  @override
  Widget build(BuildContext context) {
    final userRepository = UserRepository(storage: secureStorage);
    return BlocProvider<AuthenticationBloc>(
      create: (context) {
        return AuthenticationBloc(userRepository: userRepository)..add(AppStarted());
      },
      child: RepositoryProvider<UserRepository>(
        create: (context) => userRepository,
        child: MaterialApp(
          title: 'APAPEDIA Mobile',
          debugShowCheckedModeBanner: false,
          navigatorKey: _navigatorKey,
          onGenerateRoute: (_) => SignUpPage.route(),
          builder: (context, child) {
            return BlocListener<AuthenticationBloc, AuthenticationState>(
              key: Key("blocListener"),
              listener: (context, state) {
                if (state.status == AuthenticationStatus.unauthenticated) {
                  _navigator!.pushAndRemoveUntil<void>(
                      MaterialPageRoute<void>(builder: (_) => SignInPage()),
                          (route) => false);
                }
                // if (state.status == AuthenticationStatus.authenticated) {
                //   _navigator!.pushAndRemoveUntil<void>(
                //       MaterialPageRoute<void>(
                // }
              },
              child: child,
            );
          },
        ),
      ),
    );
  }
}

