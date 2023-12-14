part of 'pages.dart';
 

class ConfirmOrderPage extends StatefulWidget {
  const ConfirmOrderPage({Key? key}) : super(key: key);
  static String routeName = "/ConfirmOrderPage";

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => ConfirmOrderPage());
  }
  @override
  _ConfirmOrderPageState createState() => _ConfirmOrderPageState();
}

class _ConfirmOrderPageState extends State<ConfirmOrderPage> {
  late ConfirmOrderModel _model;

  Customer? customer;
  Cart? cart;
  CartItem? cartItem;
  Catalog? catalog;

  final scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();
    _model = createModel(context, () => ConfirmOrderModel());

    _model.balanceController ??= TextEditingController();
    _model.balanceFocusNode ??= FocusNode();

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
        fetchCartItem(); 

      }
    } catch (error) {
      // Handle the error
      print('Error loading profile data: $error');
    }
  }

Future<List<CartItem>> fetchCartItem() async {
  try {
    final id = cart!.cartId;
    final response = await http.get(
      Uri.parse('https://localhost:8082/api/catalog/$id'),
    );

    if (response.statusCode == 200) {
      print('Response body: ${response.body}');
      
      List<dynamic> itemsJson = json.decode(response.body);
      
      List<CartItem> items = itemsJson.map((jsonItem) {
        CartItem item = CartItem.fromJson(jsonItem);
        print('Parsed item: ${item.toString()}');
        
        return item;
      }).toList();
      
      return items;
    } else {
      print('Request failed with status: ${response.statusCode}.');
      throw Exception('Failed to load catalogue');
    }
  } catch (e) {
    // If something goes wrong, print the error message
    print('An error occurred: $e');
    throw Exception('Failed to load catalogue');
  }
}


  @override
  void dispose() {
    _model.dispose();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
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
            Navigator.pop(context);
          },
        ),
        title: Text(
          'Confirm Order',
          style: FlutterFlowTheme.of(context).titleSmall,
        ),
        actions: [],
        centerTitle: false,
        elevation: 0.0,
      ),
      body: SafeArea(
        top: true,
        child: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.max,
            children: [
              Row(
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
                            borderRadius: BorderRadius.circular(8.0),
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
                                                '${catalog!.productName}',
                                                style:
                                                    FlutterFlowTheme.of(context)
                                                        .headlineSmall,
                                              ),
                                            ),
                                            Padding(
                                              padding: EdgeInsetsDirectional
                                                  .fromSTEB(8.0, 4.0, 12.0, 0.0),
                                              child: Text(
                                                '${catalog!.price}',
                                                style:
                                                    FlutterFlowTheme.of(context)
                                                        .headlineSmall,
                                                  ),
                                              ),Padding(
                                              padding: EdgeInsetsDirectional
                                                  .fromSTEB(8.0, 4.0, 12.0, 0.0),
                                              child: Text(
                                                '${catalog!.id}',
                                                style:
                                                    FlutterFlowTheme.of(context)
                                                        .headlineSmall,
                                                  ),
                                              ),Padding(
                                              padding: EdgeInsetsDirectional
                                                  .fromSTEB(8.0, 4.0, 12.0, 0.0),
                                              child: Text(
                                                '${catalog!.stock}',
                                                style:
                                                    FlutterFlowTheme.of(context)
                                                        .headlineSmall,
                                                  ),
                                              ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                                Row(
                                  mainAxisSize: MainAxisSize.max,
                                  children: [
                                    Expanded(
                                      child: Padding(
                                        padding: EdgeInsetsDirectional.fromSTEB(
                                            0.0, 16.0, 0.0, 0.0),
                                        child: TextFormField(
                                          controller: _model.balanceController,
                                          focusNode: _model.balanceFocusNode,
                                          obscureText: false,
                                          decoration: InputDecoration(
                                            hintText: 'Input top up amount...',
                                            hintStyle:
                                                FlutterFlowTheme.of(context)
                                                    .bodyMedium,
                                            enabledBorder: OutlineInputBorder(
                                              borderSide: BorderSide(
                                                color: Color(0xFFEF7039),
                                                width: 2.0,
                                              ),
                                              borderRadius:
                                                  BorderRadius.circular(8.0),
                                            ),
                                            focusedBorder: OutlineInputBorder(
                                              borderSide: BorderSide(
                                                color: Color(0xFFEF7039),
                                                width: 2.0,
                                              ),
                                              borderRadius:
                                                  BorderRadius.circular(8.0),
                                            ),
                                            errorBorder: OutlineInputBorder(
                                              borderSide: BorderSide(
                                                color: _model.balanceBorderColor ?? FlutterFlowTheme.of(context).primary,
                                                width: 2.0,
                                              ),
                                              borderRadius:
                                                  BorderRadius.circular(8.0),
                                            ),
                                            focusedErrorBorder:
                                                OutlineInputBorder(
                                              borderSide: BorderSide(
                                                color: _model.balanceBorderColor ?? FlutterFlowTheme.of(context).primary,
                                                width: 2.0,
                                              ),
                                              borderRadius:
                                                  BorderRadius.circular(8.0),
                                            ),
                                            errorText: _model.balanceErrorMessage,
                                            filled: true,
                                            fillColor: Color(0xFFEBB97B),
                                          ),
                                          style: FlutterFlowTheme.of(context)
                                              .bodyMedium,
                                          validator: _model
                                              .balanceControllerValidator
                                              .asValidator(context),
                                        ),
                                      ),
                                    ),
                                  ],
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
              Padding(
                padding: EdgeInsetsDirectional.fromSTEB(16.0, 0.0, 16.0, 16.0),
                child: Column(
                  mainAxisSize: MainAxisSize.max,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      mainAxisSize: MainAxisSize.max,
                      children: [],
                    ),
                  ],
                ),
              ),

              // buttonnya
              // FFButtonWidget(
              //   onPressed: () async {
              //     String balance = _model.balanceController.text;
              //     double? parsedBalance = double.tryParse(balance);

              //     if (balance != "") {
              //       if (parsedBalance != null && parsedBalance >= 0) {
              //         String? storedToken = await BlocProvider.of<AuthenticationBloc>(context).getStoredToken();

              //         if (storedToken != null) {
              //           var response = await Api.topUp(storedToken!, balance);

              //           if (response.statusCode == 200) {
              //             Navigator.of(context).push(ProfilePage.route());
              //           }
                        
              //         }
              //       } else {
              //         setState(() {
              //           _model.balanceBorderColor = Colors.red; 
              //           _model.balanceErrorMessage = 'Please enter a valid number'; 
              //         });
              //         return;
              //       }
                    
              //     } else {
              //       Navigator.of(context).push(ProfilePage.route());
              //     }
              //   },
              //   text: 'Top Up',
              //   options: FFButtonOptions(
              //     width: 270.0,
              //     height: 50.0,
              //     padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 0.0, 0.0),
              //     iconPadding:
              //         EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 0.0, 0.0),
              //     color: FlutterFlowTheme.of(context).primaryBackground,
              //     textStyle: FlutterFlowTheme.of(context).titleSmall.override(
              //           fontFamily: 'Readex Pro',
              //           color: Color(0xFFEF7039),
              //         ),
              //     elevation: 3.0,
              //     borderSide: BorderSide(
              //       color: Colors.transparent,
              //       width: 1.0,
              //     ),
              //     borderRadius: BorderRadius.circular(12.0),
              //   ),
              // ),
            ],
          ),
        ),
      ),
    );
  }
}
