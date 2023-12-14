// import 'dart:js';

import 'package:flutter/widgets.dart';
import 'package:apapedia_mobile/screens/pages.dart';

final Map<String, WidgetBuilder> routes = {
  LoginPage.routeName: (context) => LoginPage(),
  SignUpPage.routeName: (context) => SignUpPage(),
  ProfilePage.routeName: (context) => ProfilePage(),
  EditProfilePage.routeName: (context) => EditProfilePage(),
  TopUpPage.routeName: (context) => TopUpPage(),
  ConfirmOrderPage.routeName: (context) => ConfirmOrderPage(),
  OrderHistoryPage.routeName: (context) => OrderHistoryPage(),
};
