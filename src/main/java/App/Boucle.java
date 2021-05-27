package App;

import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.KNXFormatException;
import tuwien.auto.calimero.KNXTimeoutException;
import tuwien.auto.calimero.link.KNXLinkClosedException;
import tuwien.auto.calimero.process.ProcessCommunicator;

public class Boucle implements Runnable {
	private long vitesse;
	public ProcessCommunicator KNXpc;
	public static Boolean allume = true;
	public static Boolean restart = false;
	public static Boolean B1 = false;
	public static Boolean B2 = false;
	public static Boolean B3 = false;
	public static Boolean B4 = false;
	public static int index = 0;
	public static Boolean[][] patternCroissant = {{true,false,false,false},{false,true,false,false},{false,false,true,false},{false,false,false,true}};
	public static Boolean[][] patternDecroissant = {{false,false,false,true},{false,false,true,false},{false,true,false,false},{true,false,false,false}};
	public static Boolean[][][] pattern = {patternCroissant,patternDecroissant};
	public static int counterMax = 4;
	public static int counter = 0;

	public Boucle(long time, ProcessCommunicator pc){
		vitesse = time;
		KNXpc = pc;
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Boolean LED1 = KNXpc.readBool(new GroupAddress("0/1/1"));
				Boolean LED2 = KNXpc.readBool(new GroupAddress("0/1/2"));
				Boolean LED3 = KNXpc.readBool(new GroupAddress("0/1/3"));
				Boolean LED4 = KNXpc.readBool(new GroupAddress("0/1/4"));
				boucleKNX();
				boutonsMaquette();
				Thread.sleep(vitesse);
			}

		} catch (InterruptedException | KNXException e) {

			System.out.println("Erreur : " + e);
			// nous avons été interrompu
			// on remet interrupted à false par l'appel à cette méthode
			Thread.currentThread().interrupted();
		}

	}

	public void boucleKNX() throws KNXTimeoutException, KNXLinkClosedException, KNXFormatException {

		// Gestion chenillard
		if (allume) {
			if (!restart) {
				KNXpc.write(new GroupAddress("0/0/1"), pattern[index][counter][0]);
				KNXpc.write(new GroupAddress("0/0/2"), pattern[index][counter][1]);
				KNXpc.write(new GroupAddress("0/0/3"), pattern[index][counter][2]);
				KNXpc.write(new GroupAddress("0/0/4"), pattern[index][counter][3]);
				counter++;
				if (counter == counterMax)
					counter = 0;
			} else {
				KNXpc.write(new GroupAddress("0/0/1"), pattern[index][0][0]);
				KNXpc.write(new GroupAddress("0/0/2"), pattern[index][0][0]);
				KNXpc.write(new GroupAddress("0/0/3"), pattern[index][0][0]);
				KNXpc.write(new GroupAddress("0/0/4"), pattern[index][0][0]);
				counter = 0;
				restart = false;
			}
		} else {
			KNXpc.write(new GroupAddress("0/0/1"), false);
			KNXpc.write(new GroupAddress("0/0/2"), false);
			KNXpc.write(new GroupAddress("0/0/3"), false);
			KNXpc.write(new GroupAddress("0/0/4"), false);
		}
	}

	public void boutonsMaquette() {
		if (B1 == true) {
			allume = !allume;
			if (allume == true) {
				System.out.println("Allumage du chenillard");
				restart = true;
			} else
				System.out.println("Extinction du chenillard");
			B1 = false;
		}
		if (B2 == true) {
			restart = true;
			B2 = false;
		}
		if (B3 == true) {
			if (vitesse >= 500) {
				System.out.println("Augmentation de la vitesse");
				vitesse = vitesse - 100;
			} else {
				System.out.println("On ne peut pas plus augmenter la vitesse");
			}
			B3 = false;
		}
		if (B4 == true) {
			if (vitesse <= 2000) {
				System.out.println("Réduction de la vitesse");
				vitesse = vitesse + 100;
			} else {
				System.out.println("On ne peut pas plus réduire la vitesse");
			}
			B4 = false;
		}
	}

	public static void patternChange (int newIndex) {
		if (pattern.length >= newIndex+1) {
			index = newIndex;
			counterMax = pattern[index].length;
		}
	}
	
	public void cancel() {

		// interruption du thread courant, c'est-à-dire le nôtre
		Thread.currentThread().interrupt();
	}

}
