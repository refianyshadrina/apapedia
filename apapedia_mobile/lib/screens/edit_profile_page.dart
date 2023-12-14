part of 'pages.dart';

class EditProfilePage extends StatefulWidget {
  const EditProfilePage({Key? key}) : super(key: key);
  static String routeName = "/EditProfilePage";

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => EditProfilePage());
  }
  @override
  _EditProfilePageState createState() => _EditProfilePageState();
}

class _EditProfilePageState extends State<EditProfilePage> {
  late EditProfilePageModel _model;

  final scaffoldKey = GlobalKey<ScaffoldState>();

  Customer? customer;

  @override
  void initState() {
    super.initState();
    _model = createModel(context, () => EditProfilePageModel());

    _model.nameController ??= TextEditingController();
    _model.nameFocusNode ??= FocusNode();
    _model.nameFocusNode?.addListener(_onFocusChange);

    _model.usernameController ??= TextEditingController();
    _model.usernameFocusNode ??= FocusNode();
    _model.usernameFocusNode?.addListener(_onFocusChange);

    _model.emailController ??= TextEditingController();
    _model.emailFocusNode ??= FocusNode();
    _model.emailFocusNode?.addListener(_onFocusChange);

    _model.addressController ??= TextEditingController();
    _model.addressFocusNode ??= FocusNode();
    _model.addressFocusNode?.addListener(_onFocusChange);

    _model.passwordController ??= TextEditingController();
    _model.passwordFocusNode ??= FocusNode();

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


  void _onFocusChange() {
    // Trigger a rebuild when focus changes
    setState(() {});
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
      backgroundColor: FlutterFlowTheme.of(context).secondaryBackground,
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
            Navigator.of(context).push(ProfilePage.route());
          },
        ),
        title: Text(
          'Profile',
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
                        child: Padding(
                          padding: EdgeInsetsDirectional.fromSTEB(16.0, 24.0, 16.0, 32.0),
                          child: Row(
                            mainAxisSize: MainAxisSize.max,
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Expanded(
                                child: Container(
                                  width: 300,
                                  height: 160, // Increased height to accommodate the button
                                  decoration: BoxDecoration(
                                    color: Colors.white,
                                    borderRadius: BorderRadius.circular(16),
                                  ),
                                  child: Padding(
                                    padding: const EdgeInsets.all(16.0),
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: [
                                        Text(
                                          'APAPAY Balance',
                                          style: FlutterFlowTheme.of(context)
                                                    .bodyMedium
                                                    .override(
                                                      fontFamily: 'Readex Pro',
                                                      color: Color(0xFFEF7039),
                                                    ),
                                        ),
                                        SizedBox(height: 8),
                                        Row(
                                          children: [
                                            Text(
                                              'Rp ',
                                              style: FlutterFlowTheme.of(context)
                                                    .bodyMedium
                                                    .override(
                                                      fontFamily: 'Readex Pro',
                                                      color: Color(0xFFEF7039),
                                                    ),
                                            ),
                                            Text(
                                              '${customer!.balance}',
                                              style: FlutterFlowTheme.of(context).headlineLarge.override(
                                                fontFamily: 'Outfit',
                                                color: Color(0xFFEF7039),
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
                            ],
                          ),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
              Padding(
                padding: EdgeInsetsDirectional.fromSTEB(16.0, 24.0, 16.0, 0.0),
                child: Row(
                  mainAxisSize: MainAxisSize.max,
                  children: [
                    Text(
                      'Edit Profile',
                      style: FlutterFlowTheme.of(context).headlineMedium,
                    ),
                  ],
                ),
              ),
              Padding(
                padding: EdgeInsetsDirectional.fromSTEB(16.0, 0.0, 16.0, 16.0),
                child: Column(
                  mainAxisSize: MainAxisSize.max,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      mainAxisSize: MainAxisSize.max,
                      children: [
                        Expanded(
                          child: Padding(
                            padding: EdgeInsetsDirectional.fromSTEB(
                                0.0, 16.0, 0.0, 0.0),
                            child: TextFormField(
                              controller: _model.nameController,
                              focusNode: _model.nameFocusNode,
                              obscureText: false,
                              decoration: InputDecoration(
                                labelText: _model.nameFocusNode!.hasFocus ? 'Name' : 'Name: ${customer!.nama}',
                                labelStyle:
                                    FlutterFlowTheme.of(context).labelMedium,
                                hintText: '${customer!.nama}',
                                hintStyle:
                                    FlutterFlowTheme.of(context).labelMedium,
                                enabledBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color:
                                        FlutterFlowTheme.of(context).alternate,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                focusedBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).primary,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                errorBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).error,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                focusedErrorBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).error,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                filled: true,
                                fillColor: FlutterFlowTheme.of(context)
                                    .secondaryBackground,
                              ),
                              style: FlutterFlowTheme.of(context).bodyMedium,
                              validator: _model.usernameControllerValidator
                                  .asValidator(context),
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
                              controller: _model.usernameController,
                              focusNode: _model.usernameFocusNode,
                              obscureText: false,
                              decoration: InputDecoration(
                                labelText: _model.usernameFocusNode!.hasFocus ? 'Username' : 'Username: ${customer!.username}',
                                labelStyle:
                                    FlutterFlowTheme.of(context).labelMedium,
                                hintText: '${customer!.username}',
                                hintStyle:
                                    FlutterFlowTheme.of(context).labelMedium,
                                enabledBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color:
                                        FlutterFlowTheme.of(context).alternate,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                focusedBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).primary,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                errorBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).error,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                focusedErrorBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).error,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                filled: true,
                                fillColor: FlutterFlowTheme.of(context)
                                    .secondaryBackground,
                              ),
                              style: FlutterFlowTheme.of(context).bodyMedium,
                              validator: _model.usernameControllerValidator
                                  .asValidator(context),
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
                              controller: _model.emailController,
                              focusNode: _model.emailFocusNode,
                              obscureText: false,
                              decoration: InputDecoration(
                                labelText: _model.emailFocusNode!.hasFocus ? 'Email' : 'Email: ${customer!.email}',
                                labelStyle:
                                    FlutterFlowTheme.of(context).labelMedium,
                                hintText: '${customer!.email}',
                                hintStyle:
                                    FlutterFlowTheme.of(context).labelMedium,
                                enabledBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color:
                                        FlutterFlowTheme.of(context).alternate,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                focusedBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).primary,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                errorBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).error,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                focusedErrorBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).error,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                filled: true,
                                fillColor: FlutterFlowTheme.of(context)
                                    .secondaryBackground,
                              ),
                              style: FlutterFlowTheme.of(context).bodyMedium,
                              validator: _model.usernameControllerValidator
                                  .asValidator(context),
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
                              controller: _model.addressController,
                              focusNode: _model.addressFocusNode,
                              obscureText: false,
                              decoration: InputDecoration(
                                labelText: _model.addressFocusNode!.hasFocus ? 'Address' : 'Address: ${customer!.address}',
                                labelStyle:
                                    FlutterFlowTheme.of(context).labelMedium,
                                hintText: '${customer!.address}',
                                hintStyle:
                                    FlutterFlowTheme.of(context).labelMedium,
                                enabledBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color:
                                        FlutterFlowTheme.of(context).alternate,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                focusedBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).primary,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                errorBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).error,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                focusedErrorBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                    color: FlutterFlowTheme.of(context).error,
                                    width: 2.0,
                                  ),
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                filled: true,
                                fillColor: FlutterFlowTheme.of(context)
                                    .secondaryBackground,
                              ),
                              style: FlutterFlowTheme.of(context).bodyMedium,
                              validator: _model.usernameControllerValidator
                                  .asValidator(context),
                            ),
                          ),
                        ),
                      ],
                    ),
                    Row(
                      mainAxisSize: MainAxisSize.max,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Expanded(
                          child: Padding(
                            padding: EdgeInsetsDirectional.fromSTEB(
                                0.0, 16.0, 0.0, 0.0),
                            child: Container(
                              width: 370.0,
                              child: TextFormField(
                                controller: _model.passwordController,
                                focusNode: _model.passwordFocusNode,
                                autofocus: true,
                                autofillHints: [AutofillHints.password],
                                obscureText: !_model.passwordVisibility,
                                decoration: InputDecoration(
                                  labelText: 'Change Password',
                                  labelStyle:
                                      FlutterFlowTheme.of(context).labelMedium,
                                  enabledBorder: OutlineInputBorder(
                                    borderSide: BorderSide(
                                      color: FlutterFlowTheme.of(context)
                                          .primaryBackground,
                                      width: 2.0,
                                    ),
                                    borderRadius: BorderRadius.circular(8.0),
                                  ),
                                  focusedBorder: OutlineInputBorder(
                                    borderSide: BorderSide(
                                      color:
                                          FlutterFlowTheme.of(context).primary,
                                      width: 2.0,
                                    ),
                                    borderRadius: BorderRadius.circular(8.0),
                                  ),
                                  errorBorder: OutlineInputBorder(
                                    borderSide: BorderSide(
                                      color: _model.passwordBorderColor ?? FlutterFlowTheme.of(context).primary,
                                      width: 2.0,
                                    ),
                                    borderRadius: BorderRadius.circular(8.0),
                                  ),
                                  focusedErrorBorder: OutlineInputBorder(
                                    borderSide: BorderSide(
                                      color: _model.passwordBorderColor ?? FlutterFlowTheme.of(context).primary,
                                      width: 2.0,
                                    ),
                                    borderRadius: BorderRadius.circular(8.0),
                                  ),
                                  
                                  filled: true,
                                  fillColor: FlutterFlowTheme.of(context)
                                      .primaryBackground,
                                  suffixIcon: InkWell(
                                    onTap: () => setState(
                                      () => _model.passwordVisibility =
                                          !_model.passwordVisibility,
                                    ),
                                    focusNode: FocusNode(skipTraversal: true),
                                    child: Icon(
                                      _model.passwordVisibility
                                          ? Icons.visibility_outlined
                                          : Icons.visibility_off_outlined,
                                      color: FlutterFlowTheme.of(context)
                                          .secondaryText,
                                      size: 24.0,
                                    ),
                                  ),
                                  errorText: _model.passwordErrorMessage,
                                ),
                                style: FlutterFlowTheme.of(context).bodyMedium,
                                validator: _model.passwordControllerValidator
                                    .asValidator(context),
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
              FFButtonWidget(
                onPressed: () async {
                  String name = _model.nameController.text;
                  if (name == "") {
                    name = '${customer!.nama}';
                  }
                  String username = _model.usernameController.text;
                  if (username == "") {
                    username = '${customer!.username}';
                  }
                  String emailAddress = _model.emailController.text;
                  if (emailAddress == "") {
                    emailAddress = '${customer!.email}';
                  }
                  String password = _model.passwordController.text;
                  if (password == "") {
                    setState(() {
                      _model.passwordBorderColor = Colors.red; 
                      _model.passwordErrorMessage = 'Please enter your password'; 
                    });
                    return;
                  }
                  
                  String address = _model.addressController.text;
                  if (address == "") {
                    address = '${customer!.address}';
                  }

                  String? storedToken = await BlocProvider.of<AuthenticationBloc>(context).getStoredToken();

                  if (storedToken != null) {
                      var response = await Api.updateUser(storedToken!, 
                                            emailAddress, password, username,
                                            name, address);
                      
                      if (response.statusCode == 201) {
                        final authenticationBloc =
                          context.read<AuthenticationBloc>();
                          authenticationBloc.add(AuthenticationSignInEvent(
                            username: _model.usernameController.text,
                            password: _model.passwordController.text,
                            isLogin: false,
                            context: context
                          ));

                          await Future.delayed(const Duration(milliseconds: 500));

                          final state = authenticationBloc.state;
                          // if (state is AuthenticationUnauthenticatedState) {
                          //   // Show error dialog
                          //   Navigator.of(context).push(LoginPage.route());
                          // } else {
                            Navigator.of(context).push(ProfilePage.route());
                          // }
                      }
                  }
                },
                text: 'Save Changes',
                options: FFButtonOptions(
                  width: 270.0,
                  height: 50.0,
                  padding: EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 0.0, 0.0),
                  iconPadding:
                      EdgeInsetsDirectional.fromSTEB(0.0, 0.0, 0.0, 0.0),
                  color: Color(0xFFEF7039),
                  textStyle: FlutterFlowTheme.of(context).titleSmall.override(
                        fontFamily: 'Readex Pro',
                        color: Colors.white,
                      ),
                  elevation: 3.0,
                  borderSide: BorderSide(
                    color: Colors.transparent,
                    width: 1.0,
                  ),
                  borderRadius: BorderRadius.circular(12.0),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
