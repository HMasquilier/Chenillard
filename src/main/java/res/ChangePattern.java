package res;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import App.Boucle;

@Path("changepattern/{index}")
public class ChangePattern {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public void getChangePattern(@PathParam("index") String index) {
    	System.out.println(index);
    	Boucle.patternChange(Integer.parseInt(index));
    }
}