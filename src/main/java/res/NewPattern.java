package res;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import App.Boucle;

@Path("newpattern/{etat1}/{etat2}/{etat3}/{etat4}/{etat5}/{etat6}")
public class NewPattern {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public void getNewPattern(@PathParam("etat1") String etat1,@PathParam("etat2") String etat2,@PathParam("etat3") String etat3,@PathParam("etat4") String etat4,@PathParam("etat5") String etat5,@PathParam("etat6") String etat6) {
    	Boolean[] state1 = Boucle.decode(etat1);
    	Boolean[] state2 = Boucle.decode(etat2);
    	if (!etat3.isEmpty()) {
    		Boolean[] state3 = Boucle.decode(etat3);
    		if (!etat4.isEmpty()) {
    			Boolean[] state4 = Boucle.decode(etat4);
        		if (!etat5.isEmpty()) {
        			Boolean[] state5 = Boucle.decode(etat5);
        			if (!etat6.isEmpty()) {
            			Boolean[] state6 = Boucle.decode(etat6);
            			Boolean[][] newPat = {state1,state2,state3,state4,state5,state6};
                		Boucle.newPattern(newPat);
                	}else {
                		Boolean[][] newPat = {state1,state2,state3,state4,state5};
                		Boucle.newPattern(newPat);
                	}
            	}else {
            		Boolean[][] newPat = {state1,state2,state3,state4};
            		Boucle.newPattern(newPat);
            	}
        	}else {
        		Boolean[][] newPat = {state1,state2,state3};
        		Boucle.newPattern(newPat);
        	}
    	}else {
    		Boolean[][] newPat = {state1,state2};
    		Boucle.newPattern(newPat);
    	}
    	System.out.println(Boucle.pattern);
    }
}
