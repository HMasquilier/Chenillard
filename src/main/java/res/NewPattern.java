package res;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import App.Boucle;

@Path("newpattern")
public class NewPattern {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public void getNewPattern() {
    	Boucle.vitesse = 1000;
    }
}
