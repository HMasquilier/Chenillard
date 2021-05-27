import 'package:chenillard_app/main.dart';
import 'package:chenillard_app/service/send_data.dart';
import 'package:flutter/material.dart';

class MonPattern {
  String nom;
  List<List<bool>> etapes;
  bool play; // pour la visualisation des patterns

  MonPattern({this.nom = "Patern", this.play = false, @required this.etapes});
}

class AffichagePattern extends StatefulWidget {
  final int indexPattern;
  final Function update;

  const AffichagePattern({@required this.indexPattern, @required this.update});

  @override
  _AffichagePatternState createState() => _AffichagePatternState();
}

class _AffichagePatternState extends State<AffichagePattern> {
  int etape = 0;

  @override
  Widget build(BuildContext context) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    return Container(
      padding: EdgeInsets.all(h * 0.01),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(5),
        border: Border.all(color: MyApp.selectedPattern == widget.indexPattern ? Colors.green : Colors.black, width: 2),
      ),
      child: Column(
        children: [
          // Nom pattern
          Text(
            MyApp.patterns[widget.indexPattern].nom,
            style: TextStyle(fontWeight: FontWeight.bold),
          ),
          // Affichage des leds
          Container(
            margin: EdgeInsets.symmetric(vertical: h * 0.02),
            width: w * 0.25,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                ledPattern(context, 0),
                ledPattern(context, 1),
                ledPattern(context, 2),
                ledPattern(context, 3),
              ],
            ),
          ),
          // Bouton play / Stop de visualisation
          Row(
            children: [
              IconButton(
                icon: Icon(MyApp.patterns[widget.indexPattern].play ? Icons.stop_sharp : Icons.play_arrow),
                onPressed: () {
                  MyApp.patterns[widget.indexPattern].play = !MyApp.patterns[widget.indexPattern].play;
                  if (MyApp.patterns[widget.indexPattern].play) launchPattern();
                  widget.update();
                },
              ),
              IconButton(
                  icon: Icon(
                    Icons.check,
                    color: MyApp.selectedPattern == widget.indexPattern ? Colors.green : Colors.black,
                  ),
                  onPressed: () {
                    MyApp.monChenillard.changePattern(widget.indexPattern);
                    MyApp.selectedPattern = widget.indexPattern;
                    widget.update();
                  }),
              IconButton(
                icon: Icon(Icons.clear, color: Colors.red),
                onPressed: () => setState(() {
                  if (widget.indexPattern > 3) {
                    showDialog(
                        context: context,
                        builder: (context) {
                          return AlertDialog(
                            content: Text("Voulez-vous supprimer ce pattern '" + MyApp.patterns[widget.indexPattern].nom + "' ?"),
                            actions: [
                              TextButton(
                                onPressed: () => Navigator.pop(context, true),
                                child: Text(
                                  "Oui",
                                  style: TextStyle(color: Colors.green),
                                ),
                              ),
                              TextButton(
                                onPressed: () => Navigator.pop(context, false),
                                child: Text(
                                  "Non",
                                  style: TextStyle(color: Colors.red),
                                ),
                              ),
                            ],
                          );
                        }).then((value) {
                      if (value) {
                        MyApp.patterns.removeAt(widget.indexPattern);
                        SendData.sendData({"numPattern": widget.indexPattern}, "/deletepattern/${widget.indexPattern.toString()}");
                        widget.update();
                      }
                    });
                  } else {
                    showDialog(
                      context: context,
                      builder: (context) {
                        return AlertDialog(
                          content: Text("Vous ne pouvez pas supprimer un des patterns de base !"),
                          actions: [TextButton(onPressed: () => Navigator.pop(context), child: Text("D'accord"))],
                        );
                      },
                    );
                  }
                }),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget ledPattern(BuildContext context, int number) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    return Container(
      width: w * 0.03,
      height: w * 0.03,
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        color: MyApp.patterns[widget.indexPattern].play && MyApp.patterns[widget.indexPattern].etapes[etape][number] ? Colors.orange : Colors.grey,
      ),
    );
  }

  void launchPattern() {
    setState(() {
      Future.doWhile(() async {
        await Future.delayed(Duration(milliseconds: 800));
        etape++;
        if (etape >= MyApp.patterns[widget.indexPattern].etapes.length) {
          etape = 0;
        }
        setState(() {});
        return MyApp.patterns[widget.indexPattern].play;
      });
    });
  }
}
