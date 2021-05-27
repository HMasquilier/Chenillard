import 'dart:convert' as convert;

import 'package:http/http.dart' as http;

class SendData {
  static void sendAllumer() async {
    String url = "http://localhost:8080";
    String path = "/rest/msg";

    await http.put(Uri.parse(url + path), headers: <String, String>{
      'Content-Type': 'application/json; charset=UTF-8',
    }, body: {
      "title": "test",
    });
  }
}
