package res;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import App.Boucle;

@Path("vitesse/{vitesse}")
public class Vitesse {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public void getVitesse(@PathParam("vitesse") String vitesse) {
    	System.out.println(vitesse);
    	Boucle.vitesse = Integer.parseInt(vitesse);
    }
}
