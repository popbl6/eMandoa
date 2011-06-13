package zerbitzari;

import java.util.Scanner;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import zerbitzari.Zerbitzari.Garbitzailea;


public class Zerbitzaria {
	
	
	
	public static void main(String[] args) throws ServantNotActive, WrongPolicy {
		Globalak.ORBGlobal.setArgs(args);
		Zerbitzari zerb = new Zerbitzari();
		Globalak.ORBGlobal.rebind("server", Globalak.ORBGlobal.getRootPOA().servant_to_reference(zerb));
		Globalak.ORBGlobal.getORBThread().start();
		Garbitzailea garb = zerb.new Garbitzailea();
		garb.start();
		Scanner in = new Scanner(System.in);
		System.out.println("Zerbitzaria martxan dagon itzali nahi duzu?(b/e)");
		in.nextLine();
		Globalak.ORBGlobal.getORB().shutdown(true);
		garb.interrupt();
		try {
			Globalak.ORBGlobal.getORBThread().join();
			garb.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
