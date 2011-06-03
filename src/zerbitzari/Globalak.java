package zerbitzari;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Globalak {
	
	public static class ORBGlobal {
		private static ORB orb;
		private static POA rootPoa;
		private static NamingContextExt nc;
		private static String[] args;
		private static Thread ORBThread;
		
		public static synchronized ORB getORB(){
			if(orb == null) orb = ORB.init(args, null);
			return orb;
		}
		
		public static synchronized void setArgs(String[] args){ORBGlobal.args = args;}
		
		public static synchronized POA getRootPOA(){
			if(rootPoa == null){
				org.omg.CORBA.Object obj;
				try{
					obj = getORB().resolve_initial_references("RootPOA");
					rootPoa = POAHelper.narrow(obj);
					rootPoa.the_POAManager().activate();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return rootPoa;
		}
		
		public static synchronized NamingContextExt getNC(){
			if(nc == null){
				org.omg.CORBA.Object obj;
				try{
					obj = getORB().resolve_initial_references("NameService");
					nc = NamingContextExtHelper.narrow(obj);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return nc;
		}
		
		public static synchronized void rebind(String name, org.omg.CORBA.Object obj){
			try {
				NameComponent path[] = getNC().to_name(name);
				getNC().rebind(path, obj);
			} catch (InvalidName e) {
				e.printStackTrace();
			} catch (NotFound e) {
				e.printStackTrace();
			} catch (CannotProceed e) {
				e.printStackTrace();
			}
		}
		
		public static Thread getORBThread(){
			if(ORBThread == null) {
				ORBThread = new Thread(){
					public void run(){
						getORB().run();
					}
				};
			}
			return ORBThread;
		}

	}

}
