
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';



import 'api/api.dart'; // Import your API class
import 'package:apapedia_mobile/bloc/authentication_bloc.dart';
import 'package:apapedia_mobile/bloc/authentication_event.dart';
import 'package:apapedia_mobile/bloc/authentication_state.dart';
import 'package:apapedia_mobile/repository/user_repository.dart'; // Import your UserRepository class


void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
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
          builder: (context, child) {
            return BlocListener<AuthenticationBloc, AuthenticationState>(
              key: Key("appBlocListener"),
              listener: (context, state) {
                
                print(state.status == AuthenticationStatus.unauthenticated);
                if (state.status == AuthenticationStatus.unauthenticated) {
                  Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => LoginPage()));
                }
              },
              child: child,
            );
          },
        ),
      ),
    );
  }
}

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  Future<void> _login() async {
    final String username = usernameController.text.trim();
    final String password = passwordController.text.trim();

    try {
      // Call the signIn method from your API class
      Map response = await Api.signIn(username, password);

      if (response.containsKey('token')) {
        // Token retrieved successfully, store it in secure storage
        // FlutterSecureStorage secureStorage = FlutterSecureStorage();
        // UserRepository userRepository = UserRepository(storage: secureStorage);
        // await userRepository.persistToken(response['token']);

        // Navigate to the next screen or perform any other actions
        // For example, you can fetch user data and store it in UserRepository
        // based on your response structure
        // final customer = Customer.fromJson(Map<String, dynamic>.from(userResponse));


        if (response['token'] != "Failed") {
          RepositoryProvider.of<UserRepository>(context)
              .persistToken(response['token']);
          RepositoryProvider.of<UserRepository>(context)
              .setUsername(response['username']);

          BlocProvider.of<AuthenticationBloc>(context)
              .add(SignedIn(token: response['token']));
        } else {
          print("login failed");
          print(response['token']);
        }

       
        // Add more fields as needed

        // TODO: Navigate to the next screen or perform other actions
      } else {
        // Handle unsuccessful login (show error message, etc.)
        print('Login failed');
      }
    } catch (e) {
      // Handle errors
      print('Error: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Login Page'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              controller: usernameController,
              decoration: InputDecoration(labelText: 'Username'),
            ),
            TextField(
              controller: passwordController,
              obscureText: true,
              decoration: InputDecoration(labelText: 'Password'),
            ),
            SizedBox(height: 20),
            MaterialButton(
              onPressed: _login,
              child: Text('Login'),
              color: Colors.blue,
              textColor: Colors.white,
            ),
          ],
        ),
      ),
    );
  }
}
