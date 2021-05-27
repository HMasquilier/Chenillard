import 'dart:convert' as convert;
import 'dart:convert';

import 'package:http/http.dart' as http;

class SendData {
  static void sendAllumer() async {
    String url = "http://localhost:8080";
    String path = "/rest/msg";
    final msg = jsonEncode({"title": "test"});


    await http.post(Uri.parse(url + path), headers: <String, String>{
      'Content-Type': 'application/json; charset=UTF-8',
    }, body: msg
    );
  }
}
