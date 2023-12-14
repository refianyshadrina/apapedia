part of 'pages.dart';

class DetailProductPage extends StatefulWidget {
  const DetailProductPage({Key? key}) : super(key: key);
  static String routeName = "/DetailProductPage";

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => DetailProductPage());
  }

  @override
  _DetailProductPageState createState() => _DetailProductPageState();
}

class _DetailProductPageState extends State<DetailProductPage> {
  late DetailProductModel _model;
  Customer? customer;
  Catalog? catalog;
  final scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();
    _model = createModel(context, () => DetailProductModel());

    _model.balanceController ??= TextEditingController();
    _model.balanceFocusNode ??= FocusNode();

    loadProfileData();
    fetchCatalogDetail();
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
        // fetchCatalogDetail();
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

Future<Catalog> fetchCatalogDetail() async {
  try {
    final catalogId = catalog!.id;
    final response = await http.get(
      Uri.parse('https://localhost:8082/api/catalog/$catalogId'),
    );

    if (response.statusCode == 200) {
      print('Response body: ${response.body}');
      
      Map<String, dynamic> catalogJson = json.decode(response.body);
      
      Catalog catalogItem = Catalog.fromJson(catalogJson);
      print('Parsed item: ${catalogItem.toString()}');
      
      return catalogItem;
    } else {
      print('Request failed with status: ${response.statusCode}.');
      throw Exception('Failed to load catalog item');
    }
  } catch (e) {
    // If something goes wrong, print the error message
    print('An error occurred: $e');
    throw Exception('Failed to load catalog item');
  }
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
        child: SingleChildScrollView(
          child: Column(
            children: [
              Container(
                width: double.infinity,
                decoration: BoxDecoration(
                  color: Colors.amber,
                  borderRadius: BorderRadius.circular(8.0),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(12.0),
                  child: Row(
                    children: [
                      Card(
                        clipBehavior: Clip.antiAliasWithSaveLayer,
                        color: Color(0xFFEBB97B),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(8.0),
                        ),
                        // Add any additional styling or child widgets for the image
                      ),
                      Expanded(
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(8.0, 0.0, 0.0, 0.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                
                                'American Style Wax',
                                style: TextStyle(
                                  fontSize: 16.0,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              Text(
                                'Price: \$100', // Dummy Price
                                style: TextStyle(
                                  fontSize: 14.0,
                                ),
                              ),
                              Text(
                                'Product ID: DummyID123',
                                style: TextStyle(
                                  fontSize: 14.0,
                                ),
                              ),
                              Text(
                                'Stock: 50', // Dummy Stock
                                style: TextStyle(
                                  fontSize: 14.0,
                                ),
                              ),
                              Text(
                                'Description: Dummy Product Description',
                                style: TextStyle(
                                  fontSize: 14.0,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),

              // Add buttons below the product details
              Padding(
                padding: EdgeInsets.all(16.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    ElevatedButton(
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => ConfirmOrderPage(),
                          ),
                        );
                      },
                      child: Text('Confirm Order'),
                    ),
                    ElevatedButton(
                      onPressed: () {
                        Navigator.pop(context);
                      },
                      child: Text('Add To Cart'),
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
}