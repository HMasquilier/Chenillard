package r.Chenillard;

import java.util.concurrent.TimeUnit;

import tuwien.auto.calimero.CloseEvent;
import tuwien.auto.calimero.FrameEvent;
import tuwien.auto.calimero.cemi.CEMILData;
import tuwien.auto.calimero.link.NetworkLinkListener;

public class Listener implements NetworkLinkListener {
	private boolean B1;
	private boolean B2;
	private boolean B3;
	private boolean B4;
	
	
public Listener(boolean b1,boolean b2, boolean b3, boolean b4) {
	B1 = b1;
	B2 = b2;
	B3 = b3;
	B4 = b4;
}

@Override
public void indication(FrameEvent e) {
	//System.out.println("srcadress " + e.getSource());
	System.out.println("targetadress " + ((CEMILData)e.getFrame()).getDestination());
	if (((CEMILData)e.getFrame()).getDestination().toString().equals("1/0/1")) {
		System.out.println("Bouton 1");
		B1=true;
		try {
			TimeUnit.MILLISECONDS.sleep(10);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	if (((CEMILData)e.getFrame()).getDestination().toString().equals("1/0/2")) {
		System.out.println("Bouton 2");
	}
	if (((CEMILData)e.getFrame()).getDestination().toString().equals("1/0/3")) {
		System.out.println("Bouton 3");
	}
	if (((CEMILData)e.getFrame()).getDestination().toString().equals("1/0/4")) {
		System.out.println("Bouton 4");
	}
}

@Override
public void linkClosed(CloseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void confirmation(FrameEvent e) {
	// TODO Auto-generated method stub
	
}
}