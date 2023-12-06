part of 'pages.dart';

class LoginPage extends StatefulWidget {
    static String routeName = "/LoginPage";

    static Route<void> route() {
      return MaterialPageRoute<void>(builder: (_) => LoginPage());
    }

    @override
    _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Login Page'),
      ),
      body: BlocConsumer<AuthenticationBloc, AuthenticationState>(
        listener: (context, state) {
            Navigator.of(context).pushReplacement(
              MaterialPageRoute(
                builder: (context) => LoginPage(),
              ),
            );
          
        },
        builder: (context, state) {
          if (state is AuthenticationInitialState) {
            return const Center(
              child: CircularProgressIndicator(),
            );
          } else if (state is AuthenticationUnauthenticatedState) {
            return _buildLoginForm(context);
          } else {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text('Authenticated!'),
                  const SizedBox(height: 16.0),
                  ElevatedButton(
                    onPressed: () {
                      // Dispatch a logout event
                      context.read<AuthenticationBloc>().add(AuthenticationSignOutEvent());
                    },
                    child: const Text('Log Out'),
                  ),
                ],
              ),
            );
          }
        },
      ),
    );
  }

  Widget _buildLoginForm(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          TextField(
            controller: usernameController,
            decoration: const InputDecoration(labelText: 'Username'),
          ),
          const SizedBox(height: 16.0),
          TextField(
            controller: passwordController,
            obscureText: true,
            decoration: const InputDecoration(labelText: 'Password'),
          ),
          const SizedBox(height: 16.0),
          ElevatedButton(
            onPressed: () async {
              
            final authenticationBloc = context.read<AuthenticationBloc>();
            authenticationBloc.add(
              AuthenticationSignInEvent(
                username: usernameController.text,
                password: passwordController.text,
              ),
            );

            await Future.delayed(const Duration(milliseconds: 500));

            final state = authenticationBloc.state;
            if (state is AuthenticationUnauthenticatedState) {
              showDialog(
                context: context,
                builder: (BuildContext context) {
                  return AlertDialog(
                    title: const Text('Invalid Credentials'),
                    content: const Text('The username or password is incorrect.'),
                    actions: <Widget>[
                      TextButton(
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                        child: const Text('OK'),
                      ),
                    ],
                  );
                },
              );
            }
          },
            child: const Text('Sign In'),
          ),
        ],
      ),
    );
  }
}