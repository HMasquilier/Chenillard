import 'package:chenillard_app/main.dart';
import 'package:chenillard_app/models/mon_pattern.dart';
import 'package:chenillard_app/service/send_data.dart';
import 'package:flutter/material.dart';

class CreationPatterns extends StatefulWidget {
  final Function update;

  const CreationPatterns({@required this.update});

  @override
  _CreationPatternsState createState() => _CreationPatternsState();
}

class _CreationPatternsState extends State<CreationPatterns> {
  TextEditingController controller = TextEditingController();
  int stepNumber = 2;

  List<List<bool>> etapes;

  @override
  void initState() {
    super.initState();
    etapes = [
      [false, false, false, false],
      [false, false, false, false],
      [false, false, false, false],
      [false, false, false, false],
      [false, false, false, false],
      [false, false, false, false],
    ];
  }

  @override
  Widget build(BuildContext context) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    return Container(
      margin: EdgeInsets.symmetric(horizontal: w * 0.02, vertical: h * 0.02),
      width: w,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text(
            "Ici, vous allez pouvoir créer vos propres patterns",
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 17),
          ),
          SizedBox(height: h * 0.03),
          // Choix du nom du pattern
          Container(
            width: w * 0.2,
            child: TextField(
              controller: controller,
              decoration: InputDecoration(
                hintText: "Saisir le nom du pattern",
                labelText: "Nom du pattern",
                contentPadding: EdgeInsets.only(top: h * 0.002, bottom: h * 0.01),
              ),
              textAlignVertical: TextAlignVertical.center,
              maxLines: 1,
              style: TextStyle(fontSize: 15),
            ),
          ),
          SizedBox(height: h * 0.02),
          // Choix du nombre d'étapes
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("Nombre d'étapes du pattern :"),
              SizedBox(width: w * 0.02),
              IconButton(
                icon: Icon(Icons.remove),
                onPressed: stepNumber > 2
                    ? () => setState(() {
                          stepNumber--;
                          refreshEtapes();
                        })
                    : null,
              ),
              Container(
                margin: EdgeInsets.symmetric(horizontal: w * 0.02),
                child: Text(stepNumber.toString(), style: TextStyle(fontWeight: FontWeight.bold)),
              ),
              IconButton(
                icon: Icon(Icons.add),
                onPressed: stepNumber < 6 ? () => setState(() => stepNumber++) : null,
              ),
            ],
          ),
          // Création des étapes
          Expanded(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: List.generate(3, (index) => etape(context, index)),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: List.generate(3, (index) => etape(context, index + 3)),
                ),
              ],
            ),
          ),
          // Création du pattern
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextButton(
                child: Text("Effacer ce pattern", style: TextStyle(color: Colors.red)),
                onPressed: () {
                  setState(() {
                    stepNumber = 2;
                    controller.clear();
                    refreshEtapes();
                    for (int i = 0; i < 2; i++) {
                      for (int j = 0; j < 4; j++) etapes[i][j] = false;
                    }
                  });
                },
              ),
              SizedBox(width: w * 0.03),
              TextButton(
                child: Text("Valider ce pattern", style: TextStyle(color: Colors.green)),
                onPressed: () {
                  setState(() {
                    if (controller.text != null && controller.text != "") {
                      List<List<bool>> res = [];
                      String finPath = "";
                      for (int i = 0; i < stepNumber; i++) {
                        res.add(etapes[i].toList());
                        for (int j = 0; j < etapes[i].length; j++) {
                          if (j == 0) finPath += "/";
                          finPath += etapes[i][j] ? "T" : "F";
                        }
                      }
                      print(finPath);
                      MyApp.patterns.add(MonPattern(etapes: res, nom: controller.text));
                      SendData.sendData({"pattern": MyApp.patterns.length}, "/newpattern$finPath");
                      widget.update();
                    } else {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                          content: Text("Veuillez donner un nom au pattern"),
                          duration: Duration(seconds: 2),
                        ),
                      );
                    }
                  });
                },
              )
            ],
          ),
        ],
      ),
    );
  }

  Widget led(BuildContext context, int indexEtape, int indexLed) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    return GestureDetector(
      onTap: () {
        setState(() {
          if (indexEtape < stepNumber) etapes[indexEtape][indexLed] = !etapes[indexEtape][indexLed];
        });
      },
      child: Container(
        height: w * 0.05,
        width: h * 0.05,
        decoration: BoxDecoration(
          color: indexEtape < stepNumber
              ? etapes[indexEtape][indexLed]
                  ? Colors.orange
                  : Colors.grey.withOpacity(0.7)
              : Colors.red.withOpacity(0.3),
          shape: BoxShape.circle,
        ),
      ),
    );
  }

  void refreshEtapes() {
    setState(() {
      for (int i = stepNumber; i < etapes.length; i++) {
        for (int j = 0; j < etapes[i].length; j++) {
          etapes[i][j] = false;
        }
      }
    });
  }

  Widget etape(BuildContext context, int index) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    return Container(
      margin: EdgeInsets.symmetric(horizontal: w * 0.02),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text((index + 1).toString(), style: TextStyle(color: index < stepNumber ? Colors.black : Colors.red)),
          SizedBox(width: w * 0.005),
          led(context, index, 0),
          led(context, index, 1),
          led(context, index, 2),
          led(context, index, 3),
        ],
      ),
    );
  }
}
