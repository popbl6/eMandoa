package jaso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import utiles.FilePart;
import utiles.FilePartHelper;
import utiles.FilePartPackage.partHolder;

public class Jaso {
	public static String filename = "grafikak2";

	/**
	 * @param args
	 * @throws InvalidName 
	 * @throws CannotProceed 
	 * @throws NotFound 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args){
		try{
		Globalak.ORBGlobal.setArgs(args);
		Globalak.ORBGlobal.getORBThread().start();
		FilePart fp = FilePartHelper.narrow(Globalak.ORBGlobal.getNC().resolve_str("bidaltzailea"));
		/*File f = new File("file.part");
		FileOutputStream partOut = new FileOutputStream(f);
		int parts = fp.getPartCount();
		System.out.println("Parteak: "+parts);
		for(int i=0; i<parts; i++){
			partHolder holder = new partHolder();
			holder.value = new byte[1];
			int bytes = fp.getPart(i, holder);
			System.out.println("Bytak: "+bytes);
			partOut.write(Arrays.copyOf(holder.value, bytes));
		}
		f.renameTo(new File("grafikak2.rar"));*/
		
		File file = new File(filename+".part");
		Deskarga des = new Deskarga(fp, file);
		file.renameTo(new File(filename+".rar"));
		
		Globalak.ORBGlobal.getORB().shutdown(true);
		Globalak.ORBGlobal.getORBThread().join();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
