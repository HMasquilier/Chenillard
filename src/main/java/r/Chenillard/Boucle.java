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
	        		  
	        		  
	        		  Thread.sleep(sleepT) ;
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
