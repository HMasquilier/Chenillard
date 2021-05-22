package App;

import tuwien.auto.calimero.CloseEvent;
import tuwien.auto.calimero.FrameEvent;
import tuwien.auto.calimero.cemi.CEMILData;
import tuwien.auto.calimero.link.NetworkLinkListener;

public class Listener implements NetworkLinkListener {
	
	
public Listener() {
}

@Override
public void indication(FrameEvent e) {
	//System.out.println("srcadress " + e.getSource());
	System.out.println("targetadress " + ((CEMILData)e.getFrame()).getDestination());
	if (((CEMILData)e.getFrame()).getDestination().toString().equals("1/0/1")) {
		System.out.println("Bouton 1");
		KNX.B1=true;
	}
	if (((CEMILData)e.getFrame()).getDestination().toString().equals("1/0/2")) {
		System.out.println("Bouton 2");
		KNX.B2=true;
	}
	if (((CEMILData)e.getFrame()).getDestination().toString().equals("1/0/3")) {
		System.out.println("Bouton 3");
		KNX.B3=true;
	}
	if (((CEMILData)e.getFrame()).getDestination().toString().equals("1/0/4")) {
		System.out.println("Bouton 4");
		KNX.B4=true;
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