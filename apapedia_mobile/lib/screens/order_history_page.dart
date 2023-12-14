part of 'pages.dart';

class OrderHistoryPage extends StatefulWidget {
  const OrderHistoryPage({Key? key}) : super(key: key);

  static String routeName = "/OrderHistoryPage";

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => OrderHistoryPage());
  }

  @override
  _OrderHistoryPageState createState() => _OrderHistoryPageState();
}

class _OrderHistoryPageState extends State<OrderHistoryPage> {
  late OrderModel _model;

  List<Order>? order;

  final scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();
    _model = createModel(context, () => OrderModel());
    loadOrderHistoryData();
  }

  loadOrderHistoryData() async {
    try {
      String? storedToken =
          await BlocProvider.of<AuthenticationBloc>(context).getStoredToken();

      if (storedToken != null) {
        final response = await OrderApi.getOrderHistory(storedToken!);
        final List<dynamic> orderData = json.decode(response.body);

        setState(() {
          order = orderData
              .map((orderJson) => Order.fromJson(orderJson))
              .toList();
        });
      }
    } catch (error) {
      // Handle the error
      print('Error loading order history data: $error');
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
            Navigator.pop(context);
          },
        ),
        title: Text(
          'Order History',
          style: FlutterFlowTheme.of(context).titleSmall,
        ),
        actions: [],
        centerTitle: false,
        elevation: 0.0,
      ),
      body: SingleChildScrollView(
        child: Column(
          mainAxisSize: MainAxisSize.max,
          children: [
            Padding(
              padding: EdgeInsetsDirectional.fromSTEB(0.0, 16.0, 0.0, 12.0),
              child: Text(
                'Order History',
                textAlign: TextAlign.center,
                style: FlutterFlowTheme.of(context).headlineSmall.override(
                      fontFamily: 'Outfit',
                      color: FlutterFlowTheme.of(context).info,
                    ),
              ),
            ),
            if (order == null || order!.isEmpty)
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
                            width: MediaQuery.of(context).size.width * 0.9,
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
                                            FontAwesomeIcons.truck,
                                            color: FlutterFlowTheme.of(context).info,
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
                                                  'No orders in history',
                                                  style:
                                                      FlutterFlowTheme.of(context)
                                                          .headlineSmall,
                                                ),
                                              ),
                                            ]
                                          )
                                        )
                                      )
                                    ]
                                  )
                                ]
                              )
                            )
                          )
                        )
                      )
                    )
                  ]
                )
              )
            else
              for (int i = 0; i < order!.length; i++)
                Padding(
                  padding: EdgeInsets.symmetric(vertical: 16.0),
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
                            padding: EdgeInsets.all(16.0),
                            child: Container(
                              width: MediaQuery.of(context).size.width * 0.9,
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
                                padding: EdgeInsets.all(12.0),
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
                                              FontAwesomeIcons.truck,
                                              color: FlutterFlowTheme.of(context).info,
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
                                                    'Order # : ${i + 1}',
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
                                                    'Order Id : ${order![i].id}',
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
                                                Padding(
                                                  padding: EdgeInsetsDirectional.fromSTEB(8.0, 4.0, 12.0, 0.0),
                                                  child: Text(
                                                    'Order Status : ${getStatusText(order![i].status)}',
                                                    style: FlutterFlowTheme.of(context).bodySmall.override(
                                                      fontFamily: 'Readex Pro',
                                                      fontSize: 12.0,
                                                    ),
                                                  ),
                                                ),
                                                for (int j = 0; j < order![i].orderItems!.length; j++)
                                                  Padding(
                                                    padding: EdgeInsetsDirectional
                                                        .fromSTEB(
                                                            8.0, 4.0, 12.0, 0.0),
                                                    child: Text(
                                                      'Product Name : ${order![i].orderItems![j].productName}',
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
                                                Padding(
                                                  padding: EdgeInsetsDirectional.fromSTEB(8.0, 4.0, 12.0, 0.0),
                                                  child: Text(
                                                    'Total Price : ${order![i].totalPrice}',
                                                    style: FlutterFlowTheme.of(context).bodySmall.override(
                                                      fontFamily: 'Readex Pro',
                                                      fontSize: 16.0,
                                                      fontWeight: FontWeight.bold,
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
                                        // Check the order status and take appropriate action
                                        if (order![i].status == 0) {
                                          // Status 0: Waiting for Seller Confirmation (Button disabled)
                                          return null;
                                        } else if (order![i].status == 1) {
                                          // Status 1: Seller Arranging Delivery (Button disabled)
                                          return null;
                                        } else if (order![i].status == 2) {
                                          // Status 2: Order on its Way (Button disabled)
                                          return null;
                                        } else if (order![i].status == 3) {
                                          // Status 3: Received Order
                                          showConfirmationDialog(context, "Received Order", "Are you sure you have received your order?", order![i].id!, order![i].status!);
                                        } else if (order![i].status == 4) {
                                          // Status 4: Order Received (Button disabled)
                                          return null;
                                        } else if (order![i].status == 5) {
                                          // Status 5: Order Finished (Button disabled)
                                          return null;
                                        }
                                      },
                                      style: ElevatedButton.styleFrom(
                                        primary: order![i].status == 3 ? Color(0xFFEF7039) : Colors.grey,
                                        shape: RoundedRectangleBorder(
                                          borderRadius: BorderRadius.circular(12),
                                        ),
                                      ),
                                      child: Text(
                                        order![i].status == 0
                                            ? 'Waiting for Seller Confirmation'
                                            : order![i].status == 1
                                                ? 'Seller Arranging Delivery'
                                                : order![i].status == 2
                                                    ? 'Order on its Way'
                                                    : order![i].status == 3
                                                        ? 'Received Order'
                                                        : order![i].status == 4
                                                            ? 'Order Received'
                                                            : order![i].status == 5
                                                                ? 'Order Finished!'
                                                                : '',
                                        style: FlutterFlowTheme.of(context)
                                            .bodyMedium
                                            .override(
                                              fontFamily: 'Readex Pro',
                                              color: order![i].status == 3 ? Colors.white : Colors.white,
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
          ],
        ),
      ),
    ),
  );
}

String getStatusText(int status) {
  switch (status) {
    case 0:
      return 'Waiting for Seller Confirmation';
    case 1:
      return 'Seller Arranging Delivery';
    case 2:
      return 'Order on its Way';
    case 3:
      return 'Order Has Arrived!';
    case 4:
      return 'Order Received';
    case 5:
      return 'Order Finished!';
    default:
      return 'Status Invalid';
  }
}

void showConfirmationDialog(BuildContext context, String action, String msg, String id, int status) {
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
                    updateStatusPage(id, status);
                    Navigator.of(dialogContext).pop();
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

void updateStatusPage(String id, int status) async {
  try {
    String? storedToken =
        await BlocProvider.of<AuthenticationBloc>(context).getStoredToken();

    if (storedToken != null) {
      final response = await OrderApi.updateStatus(storedToken, id, status);
      await loadOrderHistoryData();
        setState(() {}); // Update the UI

        // You can also show a snackbar or toast to indicate that the status was updated
        final snackBar = SnackBar(
          content: Text('Order status updated successfully.'),
          duration: Duration(seconds: 2),
        );
        ScaffoldMessenger.of(context).showSnackBar(snackBar);
    }
  } catch (error) {
    print('Error updating status: $error');
  }
}

}