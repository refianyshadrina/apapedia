part of '../screens/pages.dart';

class ConfirmOrderModel extends FlutterFlowModel<TopUpPage> {
  ///  State fields for stateful widgets in this page.

  // State field(s) for TextField widget.
  FocusNode? balanceFocusNode;
  TextEditingController? balanceController;
  String? Function(BuildContext, String?)? balanceControllerValidator;
  Color? balanceBorderColor;
  String? balanceErrorMessage;


  void initState(BuildContext context) {
    balanceBorderColor = Colors.transparent;
    balanceErrorMessage = '';
  }

  void dispose() {
    balanceFocusNode?.dispose();
    balanceController?.dispose();
  }

}
