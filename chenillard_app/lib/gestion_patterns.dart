import 'package:chenillard_app/main.dart';
import 'package:chenillard_app/models/mon_pattern.dart';
import 'package:flutter/material.dart';

class GestionPatterns extends StatefulWidget {
  @override
  _GestionPatternsState createState() => _GestionPatternsState();
}

class _GestionPatternsState extends State<GestionPatterns> {
  void update() => setState(() {});

  @override
  Widget build(BuildContext context) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    bool web = w > h;

    List<Widget> buildListPatterns() {
      double w = MediaQuery.of(context).size.width;
      List<Widget> res = [SizedBox(width: w * 0.01)];
      for (int i = 0; i < MyApp.patterns.length; i++) {
        res.add(AffichagePattern(indexPattern: i, update: update));
      }
      res.add(SizedBox(width: w * 0.01));
      return res;
    }

    return Container(
      width: w,
      child: Center(
        child: SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          child: Wrap(
            spacing: w * 0.03,
            runSpacing: w * 0.03,
            children: buildListPatterns(),
          ),
        ),
      ),
    );
  }
}
