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

import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;
import tuwien.auto.calimero.link.medium.TPSettings;
import tuwien.auto.calimero.process.ProcessCommunicator;
import tuwien.auto.calimero.process.ProcessCommunicatorImpl;

public class ServerB {
	// Données
	public static Boolean allume = true;
	public static Boolean restart = false;
	public static Boolean B1 = false;
	public static Boolean B2 = false;
	public static Boolean B3 = false;
	public static Boolean B4 = false;
	public static long vitesse = 1000;
	private static long minimumDelay = 500;
	public static int index = 0;
	public static Boolean[][] patternCroissant = {{true,false,false,false},{false,true,false,false},{false,false,true,false},{false,false,false,true}};
	public static Boolean[][] patternDecroissant = {{false,false,false,true},{false,false,true,false},{false,true,false,false},{true,false,false,false}};
	public static Boolean[][][] pattern = {patternCroissant,patternDecroissant};
	public static int counterMax = 4;
	public static int counter = 0;

	public static class Serveur extends HttpServlet {
		// DOGET
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

		}


		// DONNEES KNX
		public static String servAdr = "192.168.1.201";
		/**
		 * Local endpoint, The local socket address is important for multi-homed clients
		 * (several network interfaces), or if the default route is not useful.
		 */
		private static final InetSocketAddress local = new InetSocketAddress(0);
		private static final InetSocketAddress serverKNX = new InetSocketAddress(servAdr,
				KNXnetIPConnection.DEFAULT_PORT);

		public static void main(String[] args) throws Exception {
			
			//CONNEXION SERVEUR
//			Server server = new Server(8080);
//			ServletHandler handler = new ServletHandler();
//			
//			
//			handler.addServletWithMapping(Serveur.class, "/onoff");
//			handler.addServletWithMapping(Serveur.class, "/restart");
//			handler.addServletWithMapping(Serveur.class, "/accelerer");
//			handler.addServletWithMapping(Serveur.class, "/ralentir");
//			
//			server.setHandler(handler);
//			
//			server.start();
//			server.join();
			
			
			//CONNEXION KNX	

			//System.out.println("Establish a tunneling connection to the KNXnet/IP server " + server);
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
							pc.write(new GroupAddress("0/0/1"), pattern[index][counter][0]);
							pc.write(new GroupAddress("0/0/2"), pattern[index][counter][1]);
							pc.write(new GroupAddress("0/0/3"), pattern[index][counter][2]);
							pc.write(new GroupAddress("0/0/4"), pattern[index][counter][3]);
							counter ++;
							if (counter == counterMax) counter = 0;
							}
						else {
							pc.write(new GroupAddress("0/0/1"), pattern[index][0][0]);
							pc.write(new GroupAddress("0/0/2"), pattern[index][0][0]);
							pc.write(new GroupAddress("0/0/3"), pattern[index][0][0]);
							pc.write(new GroupAddress("0/0/4"), pattern[index][0][0]);
							counter = 0;
							restart = false;
						}
					}else {
						pc.write(new GroupAddress("0/0/1"), false);
						pc.write(new GroupAddress("0/0/2"), false);
						pc.write(new GroupAddress("0/0/3"), false);
						pc.write(new GroupAddress("0/0/4"), false);
					}

					TimeUnit.MILLISECONDS.sleep(vitesse);

				}

				pc.close();
				knxLink.close();

			}catch(KNXException|

		InterruptedException e)
		{
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
	public static void patternChange (int newIndex) {
		if (pattern.length >= newIndex+1) {
			index = newIndex;
			counterMax = pattern[index].length;
		}
	}

}