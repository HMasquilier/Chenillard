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

public class KNX {
	// Données

	public static int test =1;
		// DONNEES KNX
		public static String servAdr = "192.168.1.201";
		private static long minimumDelay = 1000;
		/**
		 * Local endpoint, The local socket address is important for multi-homed clients
		 * (several network interfaces), or if the default route is not useful.
		 */
		private static final InetSocketAddress local = new InetSocketAddress(0);
		private static final InetSocketAddress serverKNX = new InetSocketAddress(servAdr,
				KNXnetIPConnection.DEFAULT_PORT);

		public static void GestionKNX() {
			
			//CONNEXION SERVEUR
			
			
			
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
				
				ProcessCommunicator pc = new ProcessCommunicatorImpl(knxLink);
				
				// Listener
				Listener lis = new Listener();
				knxLink.addLinkListener(lis);

				
				Thread t = new Thread(new Boucle(minimumDelay,pc));
				t.start();
				 
				//pc.close();
				//knxLink.close();

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