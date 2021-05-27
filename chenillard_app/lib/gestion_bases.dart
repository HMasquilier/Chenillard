import 'package:chenillard_app/main.dart';
import 'package:chenillard_app/service/send_data.dart';
import 'package:flutter/material.dart';

class GestionBases extends StatefulWidget {
  final Function update;

  const GestionBases({@required this.update});

  @override
  _GestionBasesState createState() => _GestionBasesState();
}

class _GestionBasesState extends State<GestionBases> {
  @override
  Widget build(BuildContext context) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    return Container(
      height: h,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          SizedBox(),
          // Allumer / Éteindre
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ElevatedButton(
                style: ElevatedButton.styleFrom(
                  shape: CircleBorder(),
                  primary: Color.fromRGBO(200, 200, 200, 0.8),
                  onPrimary: Color.fromRGBO(170, 170, 170, 0.8),
                  elevation: 15.0,
                  padding: EdgeInsets.all(w * 0.01),
                ),
                child: Icon(
                  Icons.power_settings_new_outlined,
                  color: MyApp.monChenillard.allume ? Colors.green : Colors.red,
                  size: w * 0.05,
                ),
                onPressed: () {
                  setState(() {
                    MyApp.monChenillard.power();
                    if (MyApp.monChenillard.allume) {
                      int i = 0;
                      int previousPattern = 0;
                      Future.doWhile(() async {
                        if (previousPattern != MyApp.selectedPattern) {
                          previousPattern = MyApp.selectedPattern;
                          i = 0;
                        }
                        MyApp.monChenillard.etat = MyApp.patterns[MyApp.monChenillard.pattern].etapes[i];
                        i++;
                        if (i >= MyApp.patterns[MyApp.monChenillard.pattern].etapes.length) i = 0;
                        widget.update();
                        await Future.delayed(Duration(milliseconds: MyApp.monChenillard.percentToMs()));
                        return MyApp.monChenillard.allume;
                      });
                    } else {
                      MyApp.monChenillard.etat = [false, false, false, false];
                      widget.update();
                    }
                  });
                },
              ),
              SizedBox(height: h * 0.02),
              Text(
                MyApp.monChenillard.allume ? "ON" : "OFF",
                style: TextStyle(color: MyApp.monChenillard.allume ? Colors.green : Colors.red),
              ),
            ],
          ),
          // Gestion Vitesse
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                "Gestion de la vitesse",
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              Slider(
                value: MyApp.monChenillard.speed,
                max: 100,
                min: 0,
                activeColor: MyApp.monChenillard.speed < 25
                    ? Colors.green[300]
                    : MyApp.monChenillard.speed < 50
                        ? Colors.yellow
                        : MyApp.monChenillard.speed < 75
                            ? Colors.orange
                            : Colors.red,
                onChanged: (value) {
                  print(value);
                  setState(() {
                    MyApp.monChenillard.changeSpeed(value);
                  });
                },
                onChangeEnd: (value) {
                  SendData.sendData({"speed": MyApp.monChenillard.percentToMs()}, "/vitesse/${MyApp.monChenillard.percentToMs().toString()}");
                },
              ),
              Text(MyApp.monChenillard.speed.toStringAsFixed(1) + "%"),
              SizedBox(height: h * 0.01),
              Text(MyApp.monChenillard.percentToMs().toString() + "ms d'intervalle")
            ],
          ),
          SizedBox()
        ],
      ),
    );
  }
}
