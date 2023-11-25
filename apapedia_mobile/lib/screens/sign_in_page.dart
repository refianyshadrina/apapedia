part of 'pages.dart';

class SignInPage extends StatefulWidget {
  static String routeName = "/SignInPage";

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => SignInPage());
  }

  @override
  _SignInPageState createState() => _SignInPageState();
}

class _SignInPageState extends State<SignInPage> {
  final _formKey = GlobalKey<FormState>();

  String errorEmail = "";
  String errorPassword = "";
  // String url = "https://localhost:8080/login";
  bool _isHidden = true;
  bool isLoading = false;
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
       // cpde here
    );

  }
  void _togglePasswordView() {
    setState(() {
      _isHidden = !_isHidden;
    });
  }

  
}
