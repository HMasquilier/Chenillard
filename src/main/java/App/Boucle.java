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
	        		  
	        		  if (ServerB.B1==true) {
	        			  ServerB.allume = !ServerB.allume;
	        			  if (ServerB.allume == true) {
	        				  System.out.println("Allumage du chenillard");
	        				  ServerB.restart = true;
	        			  }
	        			  else System.out.println("Extinction du chenillard");
	        			  ServerB.B1 = false;
	        		  }
	        		  if (ServerB.B2==true) {
	        			  ServerB.restart = true;
	        			  ServerB.B2 = false;
	        		  }
	        		  if (ServerB.B3==true) {
	        			  if (ServerB.vitesse>=500) {
	        				  System.out.println("Augmentation de la vitesse");
	        				  ServerB.vitesse = ServerB.vitesse - 100;
	        			  }
	        			  else {
	        				  System.out.println("On ne peut pas plus augmenter la vitesse");
	        			  }
	        			  ServerB.B3 = false;
	        		  }
	        		  if (ServerB.B4==true) {
	        			  if (ServerB.vitesse<=2000) {
	        				  System.out.println("Réduction de la vitesse");
	        				  ServerB.vitesse = ServerB.vitesse + 100;
	        			  }
	        			  else {
	        				  System.out.println("On ne peut pas plus réduire la vitesse");
	        			  }
	        			  ServerB.B4 = false;
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
