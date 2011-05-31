package bidali;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;



public class Bidali {

	/**
	 * @param args
	 * @throws WrongPolicy 
	 * @throws ServantNotActive 
	 */
	public static void main(String[] args) throws ServantNotActive, WrongPolicy {
		Globalak.ORBGlobal.setArgs(args);
		Bidaltzailea b = new Bidaltzailea();
		Globalak.ORBGlobal.rebind("bidaltzailea", Globalak.ORBGlobal.getRootPOA().servant_to_reference(b));
		System.out.println("Martxan");
		Globalak.ORBGlobal.getORBThread().start();
	}

}
