part of '../screens/pages.dart';

class OrderModel extends FlutterFlowModel<OrderHistoryPage> {
    final unfocusNode = FocusNode();

    void initState(BuildContext context) {}

    void dispose() {
        unfocusNode.dispose();
    }
}