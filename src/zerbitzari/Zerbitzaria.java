package zerbitzari;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;


public class Zerbitzaria {
	
	
	
	public static void main(String[] args) throws ServantNotActive, WrongPolicy {
		Globalak.ORBGlobal.setArgs(args);
		Zerbitzari b = new Zerbitzari();
		Globalak.ORBGlobal.rebind("zerbitzaria", Globalak.ORBGlobal.getRootPOA().servant_to_reference(b));
		Globalak.ORBGlobal.getORBThread().start();
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		String irten;
		try {
			do{
				System.out.println("Zerbitzaria martxan dagon itzali nahi duzu?(b/e)");
				irten=reader.readLine();
		    }while(irten.equals("e"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
