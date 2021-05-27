package App;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.jersey.servlet.ServletContainer;

public class GestionS extends HttpServlet {
	// DOGET
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_OK);
			}
	
	public static void main(String[] args) throws Exception {
		
//	KNX.GestionKNX();
	
	Server server = new Server(8080);
	ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
	
	handler.setContextPath("/");
	server.setHandler(handler);
	
	ServletHolder serHol = handler.addServlet(ServletContainer.class, "/rest/*");
	serHol.setInitOrder(1);
	serHol.setInitParameter("jersey.config.server.provider.packages", "res");
	
	try {
		server.start();
		server.join();
	} catch (Exception e) {
		System.out.println(e);
	}
	finally {
		server.destroy();
	}
	}
}
