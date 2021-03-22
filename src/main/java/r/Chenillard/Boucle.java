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
	        		  
	        		  if (App.B1==true) {
	        			  App.allume = !App.allume;
	        			  if (App.allume == true) System.out.println("Allumage du chenillard");
	        			  else System.out.println("Extinction du chenillard");
	        			  App.B1 = false;
	        		  }
	        		  if (App.B2==true) {
	        			  App.restart = true;
	        			  App.B2 = false;
	        		  }
	        		  if (App.B3==true) {
	        			  if (App.vitesse>=500) {
	        				  System.out.println("Augmentation de la vitesse");
	        				  App.vitesse = App.vitesse - 100;
	        			  }
	        			  else {
	        				  System.out.println("On ne peut pas plus augmenter la vitesse");
	        			  }
	        			  App.B3 = false;
	        		  }
	        		  if (App.B4==true) {
	        			  if (App.vitesse<=2000) {
	        				  System.out.println("Réduction de la vitesse");
	        				  App.vitesse = App.vitesse + 100;
	        			  }
	        			  else {
	        				  System.out.println("On ne peut pas plus réduire la vitesse");
	        			  }
	        			  App.B4 = false;
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
