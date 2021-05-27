import 'dart:convert' as convert;
import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

class SendData {
  void sendAllumer() async {
    String url = "http://localhost:8080";
    String path = "/rest/msg";
    final msg = jsonEncode({"title": "test"});
    
    try {
      final response = await http.post(
        Uri.parse(url + path),
        headers: <String, String>{
          'Access-Control-Allow-Origin': '*',
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: msg,
      );
      Map responseJson = json.decode(response.body);
      print(responseJson);
      print(responseJson['name']);
      // responseJson = _response(response);
      // print(responseJson);
    } catch (e) {
      print(e);
    }
  }

  // dynamic _response(http.Response response) {
  //   switch (response.statusCode) {
  //     case 200:
  //       var responseJson = json.decode(response.body.toString());
  //       return responseJson;
  //     case 400:
  //       throw BadRequestException(response.body.toString());
  //     case 401:
  //     case 403:
  //       throw UnauthorisedException(response.body.toString());
  //     case 500:
  //     default:
  //       throw FetchDataException('Error occured while Communication with Server with StatusCode: ${response.statusCode}');
  //   }
  // }
}

/*class CustomException implements Exception {
  final _message;
  final _prefix;

  CustomException([this._message, this._prefix]);

  String toString() {
    return "$_prefix$_message";
  }
}

class FetchDataException extends CustomException {
  FetchDataException([String message]) : super(message, "Error During Communication: ");
}

class BadRequestException extends CustomException {
  BadRequestException([message]) : super(message, "Invalid Request: ");
}

class UnauthorisedException extends CustomException {
  UnauthorisedException([message]) : super(message, "Unauthorised: ");
}

class InvalidInputException extends CustomException {
  InvalidInputException([String message]) : super(message, "Invalid Input: ");
}
*/
