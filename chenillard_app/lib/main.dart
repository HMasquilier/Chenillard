import 'package:chenillard_app/creation_patterns.dart';
import 'package:chenillard_app/gestion_bases.dart';
import 'package:chenillard_app/gestion_patterns.dart';
import 'package:chenillard_app/models/chenillard.dart';
import 'package:chenillard_app/models/mon_pattern.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static Chenillard monChenillard = Chenillard(allume: false, pattern: 0, speed: 50, etat: [false, false, false, false]);
  static List<MonPattern> patterns = [
    MonPattern(
      nom: "Droite à gauche",
      etapes: [
        [true, false, false, false],
        [false, true, false, false],
        [false, false, true, false],
        [false, false, false, true],
      ],
    ),
    MonPattern(
      nom: "Gauche à droite",
      etapes: [
        [false, false, false, true],
        [false, false, true, false],
        [false, true, false, false],
        [true, false, false, false],
      ],
    ),
    MonPattern(
      nom: "Inter-exter",
      etapes: [
        [true, false, false, true],
        [false, true, true, false],
      ],
    ),
    MonPattern(
      nom: "1 - 3 - 2 - 4",
      etapes: [
        [true, false, false, false],
        [false, false, true, false],
        [false, true, false, false],
        [false, false, false, true],
      ],
    ),
  ];
  static int selectedPattern = 0;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Notre chenillard',
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List<Widget> pages;

  @override
  void initState() {
    super.initState();
    pages = [GestionBases(update: update), GestionPatterns(), CreationPatterns(update: update)];
  }

  int selectedIndex = 0;

  void update() {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    bool web = w > h;

    return Scaffold(
      appBar: AppBar(title: Center(child: Text("Chenillard François & Hugo"))),
      body: Column(
        children: [
          Container(
            margin: EdgeInsets.all(h * 0.02),
            child: Wrap(
              alignment: WrapAlignment.spaceAround,
              spacing: w * 0.05,
              runSpacing: h * 0.01,
              children: [bouton(context, 0), bouton(context, 1), bouton(context, 2)],
            ),
          ),
          Expanded(child: IndexedStack(index: selectedIndex, children: pages)),
          AffichageChenillard()
        ],
      ),
    );
  }

  Widget bouton(BuildContext context, int index) {
    double w = MediaQuery.of(context).size.width;
    double h = MediaQuery.of(context).size.height;
    bool web = w > h;
    return GestureDetector(
      child: Container(
        decoration: BoxDecoration(
          color: selectedIndex == index ? Colors.lightBlue : Color.fromRGBO(180, 180, 180, 0.8),
          borderRadius: BorderRadius.circular(50),
          boxShadow: [BoxShadow(blurRadius: 4.0, spreadRadius: 1, color: Colors.blueGrey, offset: Offset(3, 1))],
        ),
        height: web ? h * 0.06 : h * 0.06,
        width: web ? w * 0.22 : w * 0.35,
        child: Center(
          child: Text(
            index == 0
                ? "Gestion des bases"
                : index == 1
                    ? "Gestion des patterns"
                    : "Création de patterns",
            style: TextStyle(fontWeight: FontWeight.bold),
          ),
        ),
      ),
      onTap: () {
        setState(() => selectedIndex = index);
      },
    );
  }
}
