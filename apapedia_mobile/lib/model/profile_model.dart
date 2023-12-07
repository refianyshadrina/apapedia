part of '../screens/pages.dart';

class ProfileModel extends FlutterFlowModel<ProfilePage> {

  final unfocusNode = FocusNode();

  void initState(BuildContext context) {}

  void dispose() {
    unfocusNode.dispose();
  }
}