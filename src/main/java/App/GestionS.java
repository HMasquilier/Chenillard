package App;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.eclipse.jetty.servlets.CrossOriginFilter;




public class GestionS extends HttpServlet {
	// DOGET
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	public static void main(String[] args) throws Exception {

		KNX.GestionKNX();

		Server server = new Server(8080);
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

		handler.setContextPath("/");
		server.setHandler(handler);
		
		ServletHolder serHol = handler.addServlet(ServletContainer.class, "/rest/*");
		FilterHolder cors = handler.addFilter(CrossOriginFilter.class,"/*",EnumSet.of(DispatcherType.REQUEST));
		cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD,PUT");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "*");
		
		serHol.setInitOrder(1);
		serHol.setInitParameter("jersey.config.server.provider.packages", "res");

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			server.destroy();
		}
	}
}
