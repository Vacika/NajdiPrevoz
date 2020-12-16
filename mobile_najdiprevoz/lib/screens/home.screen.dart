import 'dart:convert';
import 'dart:developer';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:mobile_najdiprevoz/services/auth.service.dart';
import 'package:mobile_najdiprevoz/services/http.service.dart';

class HomeScreen extends StatelessWidget {
  HomeScreen(this.token);

  final String token;

  factory HomeScreen.fromBase64(String token) => HomeScreen(token);

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(title: Text("Home Screen")),
        body: Center(
          child: FutureBuilder(
              future: HttpService().get('http://10.0.2.2:8080/api/notifications', context).catchError(
                      (error) => {log("VaskoERROR:$error")}),
              builder: (context, snapshot) => snapshot.hasData
                  ? Column(
                      children: <Widget>[
                        LogoutButton(),
                        Text(snapshot.data.toString(),
                            style: Theme.of(context).textTheme.display1)
                      ],
                    )
                  : snapshot.hasError
                      ? Text("An error occurred${snapshot.error}")
                      : CircularProgressIndicator()),
        ),
      );
}

class LogoutButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return FlatButton(
      child: Text("Logout"),
      onPressed: () async => AuthService().logout(context)
    );
  }
}
