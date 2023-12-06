import 'dart:convert';
import 'dart:convert';
// import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:apapedia_mobile/bloc/authentication_state.dart';
import 'package:flutter/scheduler.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
// import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:intl/intl.dart';
import 'package:apapedia_mobile/bloc/authentication_bloc.dart';
import 'package:apapedia_mobile/bloc/authentication_event.dart';
import 'package:apapedia_mobile/api/api.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:apapedia_mobile/constants.dart';
import 'package:apapedia_mobile/domain/domain.dart';
import 'package:apapedia_mobile/repository/user_repository.dart';
import 'package:apapedia_mobile/style.dart';
import 'package:intl/intl.dart';
import 'package:http/http.dart';
import 'package:flutterflow_ui/flutterflow_ui.dart';



import 'dart:async';
import 'dart:convert';

part 'sign_in_page.dart';
part 'sign_up_page.dart';
part '../model/login_model.dart';