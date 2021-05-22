package App;

public class Boucle implements Runnable {
	private long sleepT;
	
	public Boucle(long time) {
		sleepT = time;
	}
	@Override
	public void run() {
	          try {
	        	  while (!Thread.currentThread().isInterrupted()){
	        		  
	        		  if (KNX.B1==true) {
	        			  KNX.allume = !KNX.allume;
	        			  if (KNX.allume == true) {
	        				  System.out.println("Allumage du chenillard");
	        				  KNX.restart = true;
	        			  }
	        			  else System.out.println("Extinction du chenillard");
	        			  KNX.B1 = false;
	        		  }
	        		  if (KNX.B2==true) {
	        			  KNX.restart = true;
	        			  KNX.B2 = false;
	        		  }
	        		  if (KNX.B3==true) {
	        			  if (KNX.vitesse>=500) {
	        				  System.out.println("Augmentation de la vitesse");
	        				  KNX.vitesse = KNX.vitesse - 100;
	        			  }
	        			  else {
	        				  System.out.println("On ne peut pas plus augmenter la vitesse");
	        			  }
	        			  KNX.B3 = false;
	        		  }
	        		  if (KNX.B4==true) {
	        			  if (KNX.vitesse<=2000) {
	        				  System.out.println("Réduction de la vitesse");
	        				  KNX.vitesse = KNX.vitesse + 100;
	        			  }
	        			  else {
	        				  System.out.println("On ne peut pas plus réduire la vitesse");
	        			  }
	        			  KNX.B4 = false;
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
