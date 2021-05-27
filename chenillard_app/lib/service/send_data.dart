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
          'Access-Control-Allow-Origin': '*',
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(msg),
      );
    } catch (e) {
      print(e);
    }
  }
}
