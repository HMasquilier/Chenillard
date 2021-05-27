package res;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import App.Boucle;

@Path("onoff")
public class Onoff {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public void getOnOff() {
    	Boucle.allume = !Boucle.allume;
		if (Boucle.allume == true) {
			System.out.println("Allumage du chenillard");
			Boucle.restart = true;
		} else
			System.out.println("Extinction du chenillard");
    }
}
