// import 'dart:js';

import 'package:flutter/widgets.dart';
import 'package:apapedia_mobile/screens/pages.dart';

// We use name route
// All our routes will be available here
final Map<String, WidgetBuilder> routes = {
  SignInPage.routeName: (context) => SignInPage(),
  SignUpPage.routeName: (context) => SignUpPage(),
};
