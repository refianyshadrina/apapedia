// part of 'pages.dart';

// class ProfilePage extends StatefulWidget {
//   const ProfilePage({Key? key}) : super(key: key);

//   static String routeName = "/ProfilePage";

//   static Route<void> route() {
//     return MaterialPageRoute<void>(builder: (_) => ProfilePage());
//   }

//   @override
//   _ProfilePageState createState() => _ProfilePageState();
// }

// class _ProfilePageState extends State<ProfilePage> {
//   late ProfileModel _model;

//   Customer? customer;

//   final scaffoldKey = GlobalKey<ScaffoldState>();

//   @override
//   void initState() {
//     super.initState();
//     _model = createModel(context, () => ProfileModel());
//     loadProfileData();
//   }

//   loadProfileData() async {
//     try {
//       String? storedToken =
//           await BlocProvider.of<AuthenticationBloc>(context).getStoredToken();


//       if (storedToken != null) {
//         final response = await Api.getUserProfile(storedToken!);
//         final userData = json.decode(response.body);

//         setState(() {
//           customer = Customer.fromJson(userData);
//         });
//       }
//     } catch (error) {
//       // Handle the error
//       print('Error loading profile data: $error');
//     }
//   }

//   @override
//   void dispose() {
//     _model.dispose();

//     super.dispose();
//   }

//   @override
//   Widget build(BuildContext context) {

//     return GestureDetector(
//       onTap: () => _model.unfocusNode.canRequestFocus
//           ? FocusScope.of(context).requestFocus(_model.unfocusNode)
//           : FocusScope.of(context).unfocus(),
//       child: Scaffold(
//         key: scaffoldKey,
//         backgroundColor: Color(0xFFEF7039),
//         appBar: AppBar(
//         backgroundColor: Color(0xFFEF7039),
//         automaticallyImplyLeading: false,
//         leading: FlutterFlowIconButton(
//           borderColor: Colors.transparent,
//           borderRadius: 30.0,
//           buttonSize: 48.0,
//           icon: Icon(
//             Icons.arrow_back_rounded,
//             color: FlutterFlowTheme.of(context).info,
//             size: 30.0,
//           ),
//           onPressed: () {
//             // Navigator.of(context).push(LoginPage.route());
//             Navigator.pop(context);
//           },
//         ),
//         title: Text(
//           'Home',
//           style: FlutterFlowTheme.of(context).titleSmall,
//         ),
//         actions: [],
//         centerTitle: false,
//         elevation: 0.0,
//       ),
//         body: Align(
//           alignment: AlignmentDirectional(0.00, 0.00),
//           child: Column(
//             mainAxisSize: MainAxisSize.max,
//             children: [
              
//               Padding(
//                 padding: EdgeInsetsDirectional.fromSTEB(0.0, 16.0, 0.0, 12.0),
//                 child: Text(
//                   '${customer!.nama}',
//                   textAlign: TextAlign.center,
//                   style: FlutterFlowTheme.of(context).headlineSmall.override(
//                         fontFamily: 'Outfit',
//                         color: FlutterFlowTheme.of(context).info,
//                       ),
//                 ),
//               ),
//               Padding(
//                 padding: EdgeInsetsDirectional.fromSTEB(16.0, 24.0, 16.0, 32.0),
//                 child: Row(
//                   mainAxisSize: MainAxisSize.max,
//                   mainAxisAlignment: MainAxisAlignment.center,
//                   children: [
//                     Expanded(
//                       child: Container(
//                       width: double.infinity,
//                       decoration: BoxDecoration(
//                         color: Color(0xFFEF7039),
//                       ),
//                       child: Padding(
//                         padding: EdgeInsetsDirectional.fromSTEB(
//                             16.0, 24.0, 16.0, 24.0),
//                         child: Container(
//                           width: MediaQuery.sizeOf(context).width * 0.9,
//                           decoration: BoxDecoration(
//                             color: FlutterFlowTheme.of(context).accent4,
//                             boxShadow: [
//                               BoxShadow(
//                                 blurRadius: 8.0,
//                                 color: Color(0x36000000),
//                                 offset: Offset(0.0, 4.0),
//                               )
//                             ],
//                             borderRadius: BorderRadius.circular(16.0),
//                           ),
//                           child: Padding(
//                             padding: EdgeInsetsDirectional.fromSTEB(
//                                 12.0, 12.0, 12.0, 12.0),
//                             child: Column(
//                               mainAxisSize: MainAxisSize.max,
//                               children: [
//                                 Row(
//                                   mainAxisSize: MainAxisSize.max,
//                                   children: [
//                                     Card(
//                                       clipBehavior: Clip.antiAliasWithSaveLayer,
//                                       color: Color(0xFFEBB97B),
//                                       shape: RoundedRectangleBorder(
//                                         borderRadius:
//                                             BorderRadius.circular(8.0),
//                                       ),
//                                       child: Padding(
//                                         padding: EdgeInsetsDirectional.fromSTEB(
//                                             8.0, 8.0, 8.0, 8.0),
//                                         child: FaIcon(
//                                           FontAwesomeIcons.moneyCheckAlt,
//                                           color:
//                                               FlutterFlowTheme.of(context).info,
//                                           size: 24.0,
//                                         ),
//                                       ),
//                                     ),
//                                     Expanded(
//                                       child: Padding(
//                                         padding: EdgeInsetsDirectional.fromSTEB(
//                                             8.0, 0.0, 0.0, 0.0),
//                                         child: Column(
//                                           mainAxisSize: MainAxisSize.max,
//                                           crossAxisAlignment:
//                                               CrossAxisAlignment.start,
//                                           children: [
//                                             Padding(
//                                               padding: EdgeInsetsDirectional
//                                                   .fromSTEB(8.0, 0.0, 0.0, 0.0),
//                                               child: Text(
//                                                 'Rp ${customer!.balance}',
//                                                 style:
//                                                     FlutterFlowTheme.of(context)
//                                                         .headlineSmall,
//                                               ),
//                                             ),
//                                             Padding(
//                                               padding: EdgeInsetsDirectional
//                                                   .fromSTEB(
//                                                       8.0, 4.0, 12.0, 0.0),
//                                               child: Text(
//                                                 'APAPAY Balance',
//                                                 style:
//                                                     FlutterFlowTheme.of(context)
//                                                         .bodySmall
//                                                         .override(
//                                                           fontFamily:
//                                                               'Readex Pro',
//                                                           fontSize: 12.0,
//                                                         ),
//                                               ),
//                                             ),
//                                           ],
//                                         ),
//                                       ),
//                                     ),
//                                   ],
//                                 ),
//                                 SizedBox(height: 16),
//                                 ElevatedButton(
//                                   onPressed: () {
//                                     Navigator.of(context).push(TopUpPage.route());
//                                   },
//                                   style: ElevatedButton.styleFrom(
//                                     primary: Color(0xFFEF7039),
//                                     shape: RoundedRectangleBorder(
//                                       borderRadius: BorderRadius.circular(12), 
//                                     ),
//                                   ),
//                                   child: Text(
//                                     'Top Up',
//                                     style: FlutterFlowTheme.of(context)
//                                             .bodyMedium
//                                             .override(
//                                               fontFamily: 'Readex Pro',
//                                               color: Colors.white,
//                                             ),
//                                   ),
//                                 ),
//                               ],
//                             ),
//                           ),
//                         ),
//                       ),
//                     ),
//                     ),
//                   ],
//                 ),
//               ),
//               Expanded(
//                 child: Container(
//                   width: double.infinity,
//                   height: 400.0,
//                   decoration: BoxDecoration(
//                     color: FlutterFlowTheme.of(context).secondaryBackground,
//                     boxShadow: [
//                       BoxShadow(
//                         blurRadius: 3.0,
//                         color: Color(0x33000000),
//                         offset: Offset(0.0, -1.0),
//                       )
//                     ],
//                     borderRadius: BorderRadius.only(
//                       bottomLeft: Radius.circular(0.0),
//                       bottomRight: Radius.circular(0.0),
//                       topLeft: Radius.circular(16.0),
//                       topRight: Radius.circular(16.0),
//                     ),
//                   ),
//                   child: SingleChildScrollView(
//                     child: Column(
//                       mainAxisSize: MainAxisSize.max,
//                       crossAxisAlignment: CrossAxisAlignment.start,
//                       children: [
//                         Padding(
//                           padding: EdgeInsetsDirectional.fromSTEB(
//                               16.0, 16.0, 16.0, 0.0),
//                           child: Column(
//                             mainAxisSize: MainAxisSize.max,
//                             crossAxisAlignment: CrossAxisAlignment.start,
//                             children: [
//                               Padding(
//                                 padding: EdgeInsetsDirectional.fromSTEB(
//                                     0.0, 0.0, 0.0, 12.0),
//                                 child: Text(
//                                   'Profile',
//                                   style: FlutterFlowTheme.of(context)
//                                       .headlineSmall,
//                                 ),
//                               ),
//                               Padding(
//                                 padding: EdgeInsetsDirectional.fromSTEB(0.0, 16.0, 0.0, 12.0),
//                                 child: ElevatedButton(
//                                   onPressed: () {
//                                     // Navigate to the ConfirmOrderPage
//                                     Navigator.of(context).push(ConfirmOrderPage.route());
//                                   },
//                                   style: ElevatedButton.styleFrom(
//                                     primary: Color(0xFFEF7039),
//                                     shape: RoundedRectangleBorder(
//                                       borderRadius: BorderRadius.circular(12),
//                                     ),
//                                   ),
//                                   child: Text(
//                                     'Confirm Order',
//                                     style: FlutterFlowTheme.of(context).bodyMedium.override(
//                                       fontFamily: 'Readex Pro',
//                                       color: Colors.white,
//                                     ),
//                                   ),
//                                 ),
//                               ),Padding(
//                                 padding: EdgeInsetsDirectional.fromSTEB(0.0, 16.0, 0.0, 12.0),
//                                 child: ElevatedButton(
//                                   onPressed: () {
//                                     // Navigate to the ConfirmOrderPage
//                                     Navigator.of(context).push(DetailProductPage.route());
//                                   },
//                                   style: ElevatedButton.styleFrom(
//                                     primary: Color(0xFFEF7039),
//                                     shape: RoundedRectangleBorder(
//                                       borderRadius: BorderRadius.circular(12),
//                                     ),
//                                   ),
//                                   child: Text(
//                                     'Detail product',
//                                     style: FlutterFlowTheme.of(context).bodyMedium.override(
//                                       fontFamily: 'Readex Pro',
//                                       color: Colors.white,
//                                     ),
//                                   ),
//                                 ),
//                               ),
//                               Padding(
//                                 padding: EdgeInsetsDirectional.fromSTEB(
//                                     0.0, 0.0, 0.0, 8.0),
//                                 child: Row(
//                                   mainAxisSize: MainAxisSize.max,
//                                   mainAxisAlignment: MainAxisAlignment.start,
//                                   children: [
//                                     Padding(
//                                       padding: EdgeInsetsDirectional.fromSTEB(
//                                           0.0, 8.0, 16.0, 8.0),
//                                       child: Icon(
//                                         Icons.account_circle,
//                                         color: Color(0xFFEF7039),
//                                         size: 24.0,
//                                       ),
//                                     ),
//                                     Expanded(
//                                       child: Padding(
//                                         padding: EdgeInsetsDirectional.fromSTEB(
//                                             0.0, 0.0, 12.0, 0.0),
//                                         child: Text(
//                                           'Username',
//                                           textAlign: TextAlign.start,
//                                           style: FlutterFlowTheme.of(context)
//                                               .bodyMedium,
//                                         ),
//                                       ),
//                                     ),
//                                     Text(
//                                       '${customer!.username}',
//                                       textAlign: TextAlign.center,
//                                       style: FlutterFlowTheme.of(context)
//                                           .bodyMedium
//                                           .override(
//                                             fontFamily: 'Readex Pro',
//                                             color: Color(0xFFEF7039),
//                                           ),
//                                     ),
//                                   ],
//                                 ),
//                               ),
//                               Padding(
//                                 padding: EdgeInsetsDirectional.fromSTEB(
//                                     0.0, 0.0, 0.0, 8.0),
//                                 child: Row(
//                                   mainAxisSize: MainAxisSize.max,
//                                   mainAxisAlignment: MainAxisAlignment.start,
//                                   children: [
//                                     Padding(
//                                       padding: EdgeInsetsDirectional.fromSTEB(
//                                           0.0, 8.0, 16.0, 8.0),
//                                       child: Icon(
//                                         Icons.language_rounded,
//                                         color: Color(0xFFEF7039),
//                                         size: 24.0,
//                                       ),
//                                     ),
//                                     Expanded(
//                                       child: Padding(
//                                         padding: EdgeInsetsDirectional.fromSTEB(
//                                             0.0, 0.0, 12.0, 0.0),
//                                         child: Text(
//                                           'Email',
//                                           textAlign: TextAlign.start,
//                                           style: FlutterFlowTheme.of(context)
//                                               .bodyMedium,
//                                         ),
//                                       ),
//                                     ),
//                                     Text(
//                                       '${customer!.email}',
//                                       textAlign: TextAlign.center,
//                                       style: FlutterFlowTheme.of(context)
//                                           .bodyMedium
//                                           .override(
//                                             fontFamily: 'Readex Pro',
//                                             color: Color(0xFFEF7039),
//                                           ),
//                                     ),
//                                   ],
//                                 ),
//                               ),
//                               Divider(
//                                 thickness: 1.0,
//                                 color: Color(0xFF072135),
//                               ),
//                               Padding(
//                                 padding: EdgeInsetsDirectional.fromSTEB(
//                                     0.0, 0.0, 0.0, 8.0),
//                                 child: Row(
//                                   mainAxisSize: MainAxisSize.max,
//                                   mainAxisAlignment: MainAxisAlignment.start,
//                                   children: [
//                                     Padding(
//                                       padding: EdgeInsetsDirectional.fromSTEB(
//                                           0.0, 8.0, 16.0, 8.0),
//                                       child: Icon(
//                                       Icons.location_on,
//                                       color: Color(0xFFEF7039),
//                                       size: 24.0,
//                                     ),
//                                     ),
//                                     Expanded(
//                                       child: Padding(
//                                         padding: EdgeInsetsDirectional.fromSTEB(
//                                             0.0, 0.0, 12.0, 0.0),
//                                         child: Text(
//                                           '${customer!.address}',
//                                           textAlign: TextAlign.start,
//                                           style: FlutterFlowTheme.of(context)
//                                               .bodyMedium,
//                                         ),
//                                       ),
//                                     ),
                                    
//                                   ],
//                                 ),
//                               ),
//                               Divider(
//                                 thickness: 1.0,
//                                 color: Color(0xFF072135),
//                               ),
//                               GestureDetector(
//                                 onTap: () {
//                                   Navigator.of(context).push(EditProfilePage.route());
//                                 },
//                                 child: Padding(
//                                   padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 0.0, 8.0),
//                                   child: Row(
//                                     mainAxisSize: MainAxisSize.max,
//                                     mainAxisAlignment: MainAxisAlignment.start,
//                                     children: [
//                                       Padding(
//                                         padding: EdgeInsetsDirectional.fromSTEB(0.0, 8.0, 16.0, 8.0),
//                                         child: Icon(
//                                           Icons.edit,
//                                           color: Color(0xFFEF7039),
//                                           size: 24.0,
//                                         ),
//                                       ),
//                                       Expanded(
//                                         child: Padding(
//                                           padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 12.0, 0.0),
//                                           child: Text(
//                                             'Profile Settings',
//                                             textAlign: TextAlign.start,
//                                             style: FlutterFlowTheme.of(context).bodyMedium,
//                                           ),
//                                         ),
//                                       ),
//                                       Icon(
//                                         Icons.chevron_right_rounded,
//                                         color: Color(0xFF14456E),
//                                         size: 24.0,
//                                       ),
//                                     ],
//                                   ),
//                                 ),
//                               ),
//                               Padding(
//                                 padding: EdgeInsetsDirectional.fromSTEB(
//                                     0.0, 0.0, 0.0, 8.0),
//                                 child: Row(
//                                   mainAxisSize: MainAxisSize.max,
//                                   mainAxisAlignment: MainAxisAlignment.start,
//                                   children: [
//                                     Padding(
//                                       padding: EdgeInsetsDirectional.fromSTEB(
//                                           0.0, 8.0, 16.0, 8.0),
//                                       child: Icon(
//                                         Icons.login_rounded,
//                                         color: Color(0xFFEF7039),
//                                         size: 24.0,
//                                       ),
//                                     ),
//                                     Expanded(
//                                       child: Padding(
//                                         padding: EdgeInsetsDirectional.fromSTEB(
//                                             0.0, 0.0, 12.0, 0.0),
//                                         child: Text(
//                                           'Log out of account',
//                                           textAlign: TextAlign.start,
//                                           style: FlutterFlowTheme.of(context)
//                                               .bodyMedium,
//                                         ),
//                                       ),
//                                     ),
                                    
//                                     GestureDetector(
//                                       onTap: () {
//                                         showConfirmationDialog(context, "Log Out", "Are you sure you want to log out?");
//                                       },
//                                       child: Text(
//                                       'Log Out?',
//                                       textAlign: TextAlign.center,
//                                       style: FlutterFlowTheme.of(context)
//                                           .bodyMedium
//                                           .override(
//                                             fontFamily: 'Readex Pro',
//                                             color: Color(0xFFEF7039),
//                                           ),
                                      
//                                     ),
//                                     ),
//                                   ],
//                                 ),
//                               ),
//                               Divider(
//                                 thickness: 1.0,
//                                 color: Color(0xFF072135),
//                               ),
//                               Padding(
//                                 padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 0.0, 8.0),
//                                 child: Row(
//                                   mainAxisSize: MainAxisSize.max,
//                                   mainAxisAlignment: MainAxisAlignment.start,
//                                   children: [
//                                     Padding(
//                                       padding: EdgeInsetsDirectional.fromSTEB(0.0, 8.0, 16.0, 8.0),
//                                       child: Icon(
//                                         Icons.delete,
//                                         color: Color(0xFFEF7039),
//                                         size: 24.0,
//                                       ),
//                                     ),
//                                     Expanded(
//                                       child: Padding(
//                                         padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 12.0, 0.0),
//                                         child: Text(
//                                           'Delete Account',
//                                           textAlign: TextAlign.start,
//                                           style: FlutterFlowTheme.of(context).bodyMedium,
//                                         ),
//                                       ),
//                                     ),
//                                     GestureDetector(
//                                       onTap: () {
//                                         showConfirmationDialog(context, "Delete Account", "Are you sure you want to delete your account?");
//                                       },
//                                       child: Text(
//                                         'Delete',
//                                         textAlign: TextAlign.center,
//                                         style: FlutterFlowTheme.of(context).bodyMedium.override(
//                                           fontFamily: 'Readex Pro',
//                                           color: Color(0xFFEF7039),
//                                         ),
//                                       ),
//                                     ),
//                                   ],
//                                 ),
//                               ),
//                             ],
//                           ),
//                         ),
//                       ],
//                     ),
//                   ),
//                 ),
//               ),
//             ],
//           ),
//         ),
//       ),
//     );
//   }

// void showConfirmationDialog(BuildContext context, String action, String msg) {
//   showDialog(
//     context: context,
//     builder: (BuildContext dialogContext) {
//       return AlertDialog(
//         shape: RoundedRectangleBorder(
//           borderRadius: BorderRadius.all(Radius.circular(20.0)),
//         ),
//         title: Center(
//           child: Text(
//             action + " ?",
//             style: FlutterFlowTheme.of(context).titleSmall.override(
//               fontFamily: 'Readex Pro',
//               color: Color(0xFFEF7039),
//             ),
//           ),
//         ),
//         content: Text(
//           msg,
//           textAlign: TextAlign.center,
//           style: FlutterFlowTheme.of(context).labelMedium.override(
//             fontFamily: 'Readex Pro',
//           ),
//         ),
//         actions: [
//           Center(
//             child: Row(
//               mainAxisAlignment: MainAxisAlignment.center,
//               children: [
//                 TextButton(
//                   onPressed: () {
//                     Navigator.of(dialogContext).pop(); // Close the dialog
//                   },
//                   child: Text(
//                     'Cancel',
//                     style: FlutterFlowTheme.of(context).bodyMedium.override(
//                       fontFamily: 'Readex Pro',
//                       color: Color(0xFFEF7039),
//                     ),
//                   ),
//                 ),
//                 ElevatedButton(
//                   child: Text(action),
//                   onPressed: () {
//                     if (action == "Delete Account") {
//                       deletePage();
//                     }
//                     context.read<AuthenticationBloc>().add(AuthenticationSignOutEvent());
//                     Navigator.of(dialogContext).pop(); // Close the dialog
//                     Navigator.of(context).push(LoginPage.route());
//                   },
//                   style: ButtonStyle(
//                     backgroundColor: MaterialStateProperty.resolveWith<Color>(
//                       (Set<MaterialState> states) {
//                         if (states.contains(MaterialState.pressed))
//                           return kPrimaryColor;
//                         return kPrimaryColor;
//                       },
//                     ),
//                   ),
//                 ),
//               ],
//             ),
//           ),
//         ],
//       );
//     },
//   );
// }


//   void deletePage() async {
//     try {
//       String? storedToken =
//           await BlocProvider.of<AuthenticationBloc>(context).getStoredToken();

//       if (storedToken != null) {
//         final response = await Api.deleteUserPage(storedToken);
//       }
//     } catch (error) {
//       print('Error deleting user page: $error');
//     }
//   }
// }

part of 'pages.dart';

class ProfilePage extends StatefulWidget {
  const ProfilePage({Key? key}) : super(key: key);

  static String routeName = "/ProfilePage";

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => ProfilePage());
  }

  @override
  _ProfilePageState createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  late ProfileModel _model;

  Customer? customer;

  final scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();
    _model = createModel(context, () => ProfileModel());
    loadProfileData();
  }

  loadProfileData() async {
    try {
      String? storedToken =
          await BlocProvider.of<AuthenticationBloc>(context).getStoredToken();


      if (storedToken != null) {
        final response = await Api.getUserProfile(storedToken!);
        final userData = json.decode(response.body);

        setState(() {
          customer = Customer.fromJson(userData);
        });
      }
    } catch (error) {
      // Handle the error
      print('Error loading profile data: $error');
    }
  }

  @override
  void dispose() {
    _model.dispose();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {

    return GestureDetector(
      onTap: () => _model.unfocusNode.canRequestFocus
          ? FocusScope.of(context).requestFocus(_model.unfocusNode)
          : FocusScope.of(context).unfocus(),
      child: Scaffold(
        key: scaffoldKey,
        backgroundColor: Color(0xFFEF7039),
        appBar: AppBar(
        backgroundColor: Color(0xFFEF7039),
        automaticallyImplyLeading: false,
        leading: FlutterFlowIconButton(
          borderColor: Colors.transparent,
          borderRadius: 30.0,
          buttonSize: 48.0,
          icon: Icon(
            Icons.arrow_back_rounded,
            color: FlutterFlowTheme.of(context).info,
            size: 30.0,
          ),
          onPressed: () {
            // Navigator.of(context).push(LoginPage.route());
            Navigator.pop(context);
          },
        ),
        title: Text(
          'Home',
          style: FlutterFlowTheme.of(context).titleSmall,
        ),
        actions: [],
        centerTitle: false,
        elevation: 0.0,
      ),
        body: Align(
          alignment: AlignmentDirectional(0.00, 0.00),
          child: Column(
            mainAxisSize: MainAxisSize.max,
            children: [
              
              Padding(
                padding: EdgeInsetsDirectional.fromSTEB(0.0, 16.0, 0.0, 12.0),
                child: Text(
                  '${customer!.nama}',
                  textAlign: TextAlign.center,
                  style: FlutterFlowTheme.of(context).headlineSmall.override(
                        fontFamily: 'Outfit',
                        color: FlutterFlowTheme.of(context).info,
                      ),
                ),
              ),
              Padding(
                padding: EdgeInsetsDirectional.fromSTEB(16.0, 24.0, 16.0, 32.0),
                child: Row(
                  mainAxisSize: MainAxisSize.max,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Expanded(
                      child: Container(
                      width: double.infinity,
                      decoration: BoxDecoration(
                        color: Color(0xFFEF7039),
                      ),
                      child: Padding(
                        padding: EdgeInsetsDirectional.fromSTEB(
                            16.0, 24.0, 16.0, 24.0),
                        child: Container(
                          width: MediaQuery.sizeOf(context).width * 0.9,
                          decoration: BoxDecoration(
                            color: FlutterFlowTheme.of(context).accent4,
                            boxShadow: [
                              BoxShadow(
                                blurRadius: 8.0,
                                color: Color(0x36000000),
                                offset: Offset(0.0, 4.0),
                              )
                            ],
                            borderRadius: BorderRadius.circular(16.0),
                          ),
                          child: Padding(
                            padding: EdgeInsetsDirectional.fromSTEB(
                                12.0, 12.0, 12.0, 12.0),
                            child: Column(
                              mainAxisSize: MainAxisSize.max,
                              children: [
                                Row(
                                  mainAxisSize: MainAxisSize.max,
                                  children: [
                                    Card(
                                      clipBehavior: Clip.antiAliasWithSaveLayer,
                                      color: Color(0xFFEBB97B),
                                      shape: RoundedRectangleBorder(
                                        borderRadius:
                                            BorderRadius.circular(8.0),
                                      ),
                                      child: Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(
                                            8.0, 8.0, 8.0, 8.0),
                                        child: FaIcon(
                                          FontAwesomeIcons.moneyCheckAlt,
                                          color:
                                              FlutterFlowTheme.of(context).info,
                                          size: 24.0,
                                        ),
                                      ),
                                    ),
                                    Expanded(
                                      child: Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(
                                            8.0, 0.0, 0.0, 0.0),
                                        child: Column(
                                          mainAxisSize: MainAxisSize.max,
                                          crossAxisAlignment:
                                              CrossAxisAlignment.start,
                                          children: [
                                            Padding(
                                              padding: EdgeInsetsDirectional
                                                  .fromSTEB(8.0, 0.0, 0.0, 0.0),
                                              child: Text(
                                                'Rp ${customer!.balance}',
                                                style:
                                                    FlutterFlowTheme.of(context)
                                                        .headlineSmall,
                                              ),
                                            ),
                                            Padding(
                                              padding: EdgeInsetsDirectional
                                                  .fromSTEB(
                                                      8.0, 4.0, 12.0, 0.0),
                                              child: Text(
                                                'APAPAY Balance',
                                                style:
                                                    FlutterFlowTheme.of(context)
                                                        .bodySmall
                                                        .override(
                                                          fontFamily:
                                                              'Readex Pro',
                                                          fontSize: 12.0,
                                                        ),
                                              ),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                                SizedBox(height: 16),
                                ElevatedButton(
                                  onPressed: () {
                                    Navigator.of(context).push(TopUpPage.route());
                                  },
                                  style: ElevatedButton.styleFrom(
                                    primary: Color(0xFFEF7039),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(12), 
                                    ),
                                  ),
                                  child: Text(
                                    'Top Up',
                                    style: FlutterFlowTheme.of(context)
                                            .bodyMedium
                                            .override(
                                              fontFamily: 'Readex Pro',
                                              color: Colors.white,
                                            ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ),
                    ),
                    ),
                  ],
                ),
              ),
              Expanded(
                child: Container(
                  width: double.infinity,
                  height: 400.0,
                  decoration: BoxDecoration(
                    color: FlutterFlowTheme.of(context).secondaryBackground,
                    boxShadow: [
                      BoxShadow(
                        blurRadius: 3.0,
                        color: Color(0x33000000),
                        offset: Offset(0.0, -1.0),
                      )
                    ],
                    borderRadius: BorderRadius.only(
                      bottomLeft: Radius.circular(0.0),
                      bottomRight: Radius.circular(0.0),
                      topLeft: Radius.circular(16.0),
                      topRight: Radius.circular(16.0),
                    ),
                  ),
                  child: SingleChildScrollView(
                    child: Column(
                      mainAxisSize: MainAxisSize.max,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Padding(
                          padding: EdgeInsetsDirectional.fromSTEB(
                              16.0, 16.0, 16.0, 0.0),
                          child: Column(
                            mainAxisSize: MainAxisSize.max,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Padding(
                                padding: EdgeInsetsDirectional.fromSTEB(
                                    0.0, 0.0, 0.0, 12.0),
                                child: Text(
                                  'Profile',
                                  style: FlutterFlowTheme.of(context)
                                      .headlineSmall,
                                ),
                              ),
                              Padding(
                                padding: EdgeInsetsDirectional.fromSTEB(0.0, 16.0, 0.0, 12.0),
                                child: ElevatedButton(
                                  onPressed: () {
                                    // Navigate to the ConfirmOrderPage
                                    Navigator.of(context).push(DetailProductPage.route());
                                  },
                                  style: ElevatedButton.styleFrom(
                                    primary: Color(0xFFEF7039),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(12),
                                    ),
                                  ),
                                  child: Text(
                                    'Detail product',
                                    style: FlutterFlowTheme.of(context).bodyMedium.override(
                                      fontFamily: 'Readex Pro',
                                      color: Colors.white,
                                    ),
                                  ),
                                ),
                              ),
                              Padding(
                                padding: EdgeInsetsDirectional.fromSTEB(
                                    0.0, 0.0, 0.0, 8.0),
                                child: Row(
                                  mainAxisSize: MainAxisSize.max,
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Padding(
                                      padding: EdgeInsetsDirectional.fromSTEB(
                                          0.0, 8.0, 16.0, 8.0),
                                      child: Icon(
                                        Icons.account_circle,
                                        color: Color(0xFFEF7039),
                                        size: 24.0,
                                      ),
                                    ),
                                    Expanded(
                                      child: Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(
                                            0.0, 0.0, 12.0, 0.0),
                                        child: Text(
                                          'Username',
                                          textAlign: TextAlign.start,
                                          style: FlutterFlowTheme.of(context)
                                              .bodyMedium,
                                        ),
                                      ),
                                    ),
                                    Text(
                                      '${customer!.username}',
                                      textAlign: TextAlign.center,
                                      style: FlutterFlowTheme.of(context)
                                          .bodyMedium
                                          .override(
                                            fontFamily: 'Readex Pro',
                                            color: Color(0xFFEF7039),
                                          ),
                                    ),
                                  ],
                                ),
                              ),
                              Padding(
                                padding: EdgeInsetsDirectional.fromSTEB(
                                    0.0, 0.0, 0.0, 8.0),
                                child: Row(
                                  mainAxisSize: MainAxisSize.max,
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Padding(
                                      padding: EdgeInsetsDirectional.fromSTEB(
                                          0.0, 8.0, 16.0, 8.0),
                                      child: Icon(
                                        Icons.language_rounded,
                                        color: Color(0xFFEF7039),
                                        size: 24.0,
                                      ),
                                    ),
                                    Expanded(
                                      child: Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(
                                            0.0, 0.0, 12.0, 0.0),
                                        child: Text(
                                          'Email',
                                          textAlign: TextAlign.start,
                                          style: FlutterFlowTheme.of(context)
                                              .bodyMedium,
                                        ),
                                      ),
                                    ),
                                    Text(
                                      '${customer!.email}',
                                      textAlign: TextAlign.center,
                                      style: FlutterFlowTheme.of(context)
                                          .bodyMedium
                                          .override(
                                            fontFamily: 'Readex Pro',
                                            color: Color(0xFFEF7039),
                                          ),
                                    ),
                                  ],
                                ),
                              ),
                              Padding(
                                padding: EdgeInsetsDirectional.fromSTEB(
                                    0.0, 0.0, 0.0, 8.0),
                                child: Row(
                                  mainAxisSize: MainAxisSize.max,
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Padding(
                                      padding: EdgeInsetsDirectional.fromSTEB(
                                          0.0, 8.0, 16.0, 8.0),
                                      child: Icon(
                                      Icons.location_on,
                                      color: Color(0xFFEF7039),
                                      size: 24.0,
                                    ),
                                    ),
                                    Expanded(
                                      child: Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(
                                            0.0, 0.0, 12.0, 0.0),
                                        child: Text(
                                          '${customer!.address}',
                                          textAlign: TextAlign.start,
                                          style: FlutterFlowTheme.of(context)
                                              .bodyMedium,
                                        ),
                                      ),
                                    ),
                                    
                                  ],
                                ),
                              ),
                              Divider(
                                thickness: 1.0,
                                color: Color(0xFF072135),
                              ),
                              GestureDetector(
                                onTap: () {
                                  Navigator.of(context).push(OrderHistoryPage.route());
                                },
                                child: Padding(
                                  padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 0.0, 8.0),
                                  child: Row(
                                    mainAxisSize: MainAxisSize.max,
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(0.0, 8.0, 16.0, 8.0),
                                        child: Icon(
                                          Icons.star,
                                          color: Color(0xFFEF7039),
                                          size: 24.0,
                                        ),
                                      ),
                                      Expanded(
                                        child: Padding(
                                          padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 12.0, 0.0),
                                          child: Text(
                                            'Order History Page',
                                            textAlign: TextAlign.start,
                                            style: FlutterFlowTheme.of(context).bodyMedium,
                                          ),
                                        ),
                                      ),
                                      Icon(
                                        Icons.chevron_right_rounded,
                                        color: Color(0xFF14456E),
                                        size: 24.0,
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                              Divider(
                                thickness: 1.0,
                                color: Color(0xFF072135),
                              ),
                              GestureDetector(
                                onTap: () {
                                  Navigator.of(context).push(EditProfilePage.route());
                                },
                                child: Padding(
                                  padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 0.0, 8.0),
                                  child: Row(
                                    mainAxisSize: MainAxisSize.max,
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(0.0, 8.0, 16.0, 8.0),
                                        child: Icon(
                                          Icons.edit,
                                          color: Color(0xFFEF7039),
                                          size: 24.0,
                                        ),
                                      ),
                                      Expanded(
                                        child: Padding(
                                          padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 12.0, 0.0),
                                          child: Text(
                                            'Profile Settings',
                                            textAlign: TextAlign.start,
                                            style: FlutterFlowTheme.of(context).bodyMedium,
                                          ),
                                        ),
                                      ),
                                      Icon(
                                        Icons.chevron_right_rounded,
                                        color: Color(0xFF14456E),
                                        size: 24.0,
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                              Padding(
                                padding: EdgeInsetsDirectional.fromSTEB(
                                    0.0, 0.0, 0.0, 8.0),
                                child: Row(
                                  mainAxisSize: MainAxisSize.max,
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Padding(
                                      padding: EdgeInsetsDirectional.fromSTEB(
                                          0.0, 8.0, 16.0, 8.0),
                                      child: Icon(
                                        Icons.login_rounded,
                                        color: Color(0xFFEF7039),
                                        size: 24.0,
                                      ),
                                    ),
                                    Expanded(
                                      child: Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(
                                            0.0, 0.0, 12.0, 0.0),
                                        child: Text(
                                          'Log out of account',
                                          textAlign: TextAlign.start,
                                          style: FlutterFlowTheme.of(context)
                                              .bodyMedium,
                                        ),
                                      ),
                                    ),
                                    
                                    GestureDetector(
                                      onTap: () {
                                        showConfirmationDialog(context, "Log Out", "Are you sure you want to log out?");
                                      },
                                      child: Text(
                                      'Log Out?',
                                      textAlign: TextAlign.center,
                                      style: FlutterFlowTheme.of(context)
                                          .bodyMedium
                                          .override(
                                            fontFamily: 'Readex Pro',
                                            color: Color(0xFFEF7039),
                                          ),
                                      
                                    ),
                                    ),
                                  ],
                                ),
                              ),
                              Divider(
                                thickness: 1.0,
                                color: Color(0xFF072135),
                              ),
                              Padding(
                                padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 0.0, 8.0),
                                child: Row(
                                  mainAxisSize: MainAxisSize.max,
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Padding(
                                      padding: EdgeInsetsDirectional.fromSTEB(0.0, 8.0, 16.0, 8.0),
                                      child: Icon(
                                        Icons.delete,
                                        color: Color(0xFFEF7039),
                                        size: 24.0,
                                      ),
                                    ),
                                    Expanded(
                                      child: Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 12.0, 0.0),
                                        child: Text(
                                          'Delete Account',
                                          textAlign: TextAlign.start,
                                          style: FlutterFlowTheme.of(context).bodyMedium,
                                        ),
                                      ),
                                    ),
                                    GestureDetector(
                                      onTap: () {
                                        showConfirmationDialog(context, "Delete Account", "Are you sure you want to delete your account?");
                                      },
                                      child: Text(
                                        'Delete',
                                        textAlign: TextAlign.center,
                                        style: FlutterFlowTheme.of(context).bodyMedium.override(
                                          fontFamily: 'Readex Pro',
                                          color: Color(0xFFEF7039),
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

void showConfirmationDialog(BuildContext context, String action, String msg) {
  showDialog(
    context: context,
    builder: (BuildContext dialogContext) {
      return AlertDialog(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(20.0)),
        ),
        title: Center(
          child: Text(
            action + " ?",
            style: FlutterFlowTheme.of(context).titleSmall.override(
              fontFamily: 'Readex Pro',
              color: Color(0xFFEF7039),
            ),
          ),
        ),
        content: Text(
          msg,
          textAlign: TextAlign.center,
          style: FlutterFlowTheme.of(context).labelMedium.override(
            fontFamily: 'Readex Pro',
          ),
        ),
        actions: [
          Center(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                TextButton(
                  onPressed: () {
                    Navigator.of(dialogContext).pop(); // Close the dialog
                  },
                  child: Text(
                    'Cancel',
                    style: FlutterFlowTheme.of(context).bodyMedium.override(
                      fontFamily: 'Readex Pro',
                      color: Color(0xFFEF7039),
                    ),
                  ),
                ),
                ElevatedButton(
                  child: Text(action),
                  onPressed: () {
                    if (action == "Delete Account") {
                      deletePage();
                    }
                    context.read<AuthenticationBloc>().add(AuthenticationSignOutEvent());
                    Navigator.of(dialogContext).pop(); // Close the dialog
                    Navigator.of(context).push(LoginPage.route());
                  },
                  style: ButtonStyle(
                    backgroundColor: MaterialStateProperty.resolveWith<Color>(
                      (Set<MaterialState> states) {
                        if (states.contains(MaterialState.pressed))
                          return kPrimaryColor;
                        return kPrimaryColor;
                      },
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      );
    },
  );
}


  void deletePage() async {
    try {
      String? storedToken =
          await BlocProvider.of<AuthenticationBloc>(context).getStoredToken();

      if (storedToken != null) {
        final response = await Api.deleteUserPage(storedToken);
      }
    } catch (error) {
      print('Error deleting user page: $error');
    }
  }
}