import 'package:apapedia_mobile/api/user_api.dart';
import 'package:apapedia_mobile/screens/pages.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:apapedia_mobile/bloc/authentication_bloc.dart';
import 'package:apapedia_mobile/bloc/authentication_event.dart';
import 'package:provider/provider.dart';

class MyApp extends StatelessWidget { 
  final storage = const FlutterSecureStorage();

  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        BlocProvider(
          create: (context) => AuthenticationBloc(
            api: Api(), 
            secureStorage: storage,
          )..add(AuthenticationCheckEvent(context: context)),
        ),
      ],
      child: MaterialApp(
        title: 'Apapedia',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: LoginPage(),
      ),
    );
  }
}
