import 'package:chenillard_app/main.dart';
import 'package:flutter/material.dart';

class Chenillard {
  bool allume;
  double speed; // Vitesse en pourcentage
  int pattern;
  List<bool> etat;

  Chenillard({@required this.allume, @required this.speed, @required this.pattern, @required this.etat});

  void power() {
    this.allume = !this.allume;
  }

  void changeSpeed(double newS) {
    this.speed = newS;
  }

  void changePattern(int numPattern) {
    this.pattern = numPattern;
  }

  int percentToMs() {
    // print((2000 - 1500 * (this.speed / 100)).toInt());
    return (2000 - 1500 * (this.speed / 100)).toInt();
  }
}

class AffichageChenillard extends StatefulWidget {
  @override
  _AffichageChenillardState createState() => _AffichageChenillardState();
}

class _AffichageChenillardState extends State<AffichageChenillard> {
  @override
  Widget build(BuildContext context) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    bool web = w > h;

    return Container(
      margin: EdgeInsets.symmetric(vertical: web ? h * 0.02 : h * 0.05, horizontal: web ? w * 0.25 : w * 0.06),
      decoration: BoxDecoration(
        color: Colors.grey[350],
        border: Border.all(color: Colors.black, width: 2),
        borderRadius: BorderRadius.circular(50),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [led(context, 0), led(context, 1), led(context, 2), led(context, 3)],
      ),
    );
  }

  Widget led(BuildContext context, int number) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    bool web = w > h;
    return Container(
      margin: EdgeInsets.all(h * 0.005),
      height: web ? w * 0.05 : w * 0.1,
      width: web ? w * 0.05 : w * 0.1,
      decoration: BoxDecoration(
        color: MyApp.monChenillard.etat[number] ? Colors.orange : Colors.grey,
        shape: BoxShape.circle,
      ),
    );
  }
}
