part of '../screens/pages.dart';

class TopUpModel extends FlutterFlowModel<TopUpPage> {
  ///  State fields for stateful widgets in this page.

  // State field(s) for TextField widget.
  FocusNode? balanceFocusNode;
  TextEditingController? balanceController;
  String? Function(BuildContext, String?)? balanceControllerValidator;
  Color? balanceBorderColor;
  String? balanceErrorMessage;

  /// Initialization and disposal methods.

  void initState(BuildContext context) {
    balanceBorderColor = Colors.transparent;
    balanceErrorMessage = '';
  }

  void dispose() {
    balanceFocusNode?.dispose();
    balanceController?.dispose();
  }

  /// Action blocks are added here.

  /// Additional helper methods are added here.
}
