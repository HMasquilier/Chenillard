package App;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class GestionS extends HttpServlet {
	// DOGET
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	public static void main(String[] args) throws Exception {
		
		//Connexion au knx et lancement du thread
		KNX.GestionKNX();
		
		//Gestion du server
		Server server = new Server(8080);
		ServletHandler handler = new ServletHandler();

		handler.addServletWithMapping(GestionS.class, "/onoff");
		handler.addServletWithMapping(GestionS.class, "/restart");
		handler.addServletWithMapping(GestionS.class, "/accelerer");
		handler.addServletWithMapping(GestionS.class, "/ralentir");

		server.setHandler(handler);

		server.start();
		server.join();

	}
}
