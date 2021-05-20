package r.Chenillard;

public class Boucle implements Runnable {
	private long sleepT;
	
	public Boucle(long time) {
		sleepT = time;
	}
	@Override
	public void run() {
	          try {
	        	  while (!Thread.currentThread().isInterrupted()){
	        		  
	        		  if (Main.B1==true) {
	        			  Main.allume = !Main.allume;
	        			  if (Main.allume == true) {
	        				  System.out.println("Allumage du chenillard");
	        				  Main.restart = true;
	        			  }
	        			  else System.out.println("Extinction du chenillard");
	        			  Main.B1 = false;
	        		  }
	        		  if (Main.B2==true) {
	        			  Main.restart = true;
	        			  Main.B2 = false;
	        		  }
	        		  if (Main.B3==true) {
	        			  if (Main.vitesse>=500) {
	        				  System.out.println("Augmentation de la vitesse");
	        				  Main.vitesse = Main.vitesse - 100;
	        			  }
	        			  else {
	        				  System.out.println("On ne peut pas plus augmenter la vitesse");
	        			  }
	        			  Main.B3 = false;
	        		  }
	        		  if (Main.B4==true) {
	        			  if (Main.vitesse<=2000) {
	        				  System.out.println("Réduction de la vitesse");
	        				  Main.vitesse = Main.vitesse + 100;
	        			  }
	        			  else {
	        				  System.out.println("On ne peut pas plus réduire la vitesse");
	        			  }
	        			  Main.B4 = false;
	        		  }
	        		  Thread.sleep(sleepT);
	        	  }
	        	  
	         }  catch (InterruptedException e) {
	         
	             System.out.println("Erreur : " + e);
	             // nous avons été interrompu
	             // on remet interrupted à false par l'appel à cette méthode
	            Thread.currentThread().interrupted() ;
	         }
		
	}
	
	public  void cancel() {
	      
	       // interruption du thread courant, c'est-à-dire le nôtre
	      Thread.currentThread().interrupt() ;
	   }

}
