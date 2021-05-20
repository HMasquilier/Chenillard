package App;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import r.Chenillard.Boucle;
import r.Chenillard.Listener;
import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;
import tuwien.auto.calimero.link.medium.TPSettings;
import tuwien.auto.calimero.process.ProcessCommunicator;
import tuwien.auto.calimero.process.ProcessCommunicatorImpl;

public class ServerB {
	public static class Serveur extends HttpServlet {
		//DOGET
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			
			
		}

		// Données
		public static Boolean allume = true;
		public static Boolean restart = false;
		public static Boolean B1 = false;
		public static Boolean B2 = false;
		public static Boolean B3 = false;
		public static Boolean B4 = false;
		public static long vitesse = 1000;
		private static long minimumDelay = 500;
		
		// DONNEES KNX
		private static String servAdr = "192.168.1.201";
		/**
		 * Local endpoint, The local socket address is important for multi-homed clients
		 * (several network interfaces), or if the default route is not useful.
		 */
		private static final InetSocketAddress local = new InetSocketAddress(0);
		private static final InetSocketAddress serverKNX = new InetSocketAddress(servAdr, KNXnetIPConnection.DEFAULT_PORT);

		public static void main(String[] args) throws Exception {
			
			//CONNEXION SERVEUR
			Server server = new Server(8080);
			ServletHandler handler = new ServletHandler();
			
			
			handler.addServletWithMapping(Serveur.class, "/onoff");
			handler.addServletWithMapping(Serveur.class, "/restart");
			handler.addServletWithMapping(Serveur.class, "/accelerer");
			handler.addServletWithMapping(Serveur.class, "/ralentir");
			
			server.setHandler(handler);
			
			server.start();
			server.join();
			
			
			//CONNEXION KNX	

			System.out.println("Establish a tunneling connection to the KNXnet/IP server " + server);
			KNXNetworkLinkIP knxLink;

			// KNXNetworkLink is the base interface of a Calimero link to a KNX network.
			// Here, we create an IP-based link,
			// which supports NAT (Network Address Translation) if required.
			// We also indicate that the KNX installation uses twisted-pair (TP1) medium.
			try {
				knxLink = KNXNetworkLinkIP.newTunnelingLink(local, serverKNX, false, new TPSettings());
				System.out.println("Connection established to server " + knxLink.getName());

				Thread t = new Thread(new Boucle(minimumDelay));
				t.start();

				// Listener
				Listener lis = new Listener();
				knxLink.addLinkListener(lis);

				// Allumer/éteindre
				ProcessCommunicator pc = new ProcessCommunicatorImpl(knxLink);

				while (!t.isInterrupted()) {
					Boolean LED1 = pc.readBool(new GroupAddress("0/1/1"));
					Boolean LED2 = pc.readBool(new GroupAddress("0/1/2"));
					Boolean LED3 = pc.readBool(new GroupAddress("0/1/3"));
					Boolean LED4 = pc.readBool(new GroupAddress("0/1/4"));

					// Gestion chenillard
					if (allume) {
						if (!restart) {
							if (LED1) {
								pc.write(new GroupAddress("0/0/1"), false);
								pc.write(new GroupAddress("0/0/2"), true);
							} else if (LED2) {
								pc.write(new GroupAddress("0/0/2"), false);
								pc.write(new GroupAddress("0/0/3"), true);
							} else if (LED3) {
								pc.write(new GroupAddress("0/0/3"), false);
								pc.write(new GroupAddress("0/0/4"), true);
							} else {
								pc.write(new GroupAddress("0/0/4"), false);
								pc.write(new GroupAddress("0/0/1"), true);
							}
						} else {
							pc.write(new GroupAddress("0/0/1"), true);
							pc.write(new GroupAddress("0/0/2"), false);
							pc.write(new GroupAddress("0/0/3"), false);
							pc.write(new GroupAddress("0/0/4"), false);
							restart = false;
						}
					} else {
						pc.write(new GroupAddress("0/0/1"), false);
						pc.write(new GroupAddress("0/0/2"), false);
						pc.write(new GroupAddress("0/0/3"), false);
						pc.write(new GroupAddress("0/0/4"), false);
					}

					TimeUnit.MILLISECONDS.sleep(vitesse);

				}

				pc.close();
				knxLink.close();

			} catch (KNXException | InterruptedException e) {
				// KNXException: all Calimero-specific checked exceptions are subtypes of
				// KNXException

				// InterruptedException: longer tasks that might block are interruptible, e.g.,
				// connection procedures. In
				// such case, an instance of InterruptedException is thrown.
				// If a task got interrupted, Calimero will clean up its internal state and
				// resources accordingly.
				// Any deviation of such behavior, e.g., where not feasible, is documented in
				// the Calimero API.

				System.out.println("Error creating KNXnet/IP tunneling link: " + e);

			}
		}
	}
}