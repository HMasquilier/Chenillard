import 'dart:convert' as convert;
import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

class SendData {
  static void sendData(Map<String, dynamic> msg, String path) async {
    String url = "http://localhost:8080";
    String longPath = "/rest" + path;

    try {
      final response = await http.post(
        Uri.parse(url + longPath),
        headers: <String, String>{
<<<<<<< HEAD
          'Access-Control-Allow-Origin' : '*',
=======
          'Access-Control-Allow-Origin': '*',
>>>>>>> branch 'master' of https://github.com/HMasquilier/Chenillard.git
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(msg),
      );
      Map responseJson = json.decode(response.body);
      print(responseJson);
    } catch (e) {
      print(e);
    }
  }
}
