package bezeroa;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;

import erabilgarriak.*;

public class Bezero {
	static ArrayList<FileData> files=new ArrayList<FileData>();
	public static final String ONCOMING_PATH=".";//Konpartitzeko fitxategiak egongo diren helbidea
	public static void fileDatakSortu(){
		File fichero=new File(ONCOMING_PATH);
		File [] lista;
		FileData fd = new FileData();
		lista=fichero.listFiles();
		for(int i=0;i<lista.length;i++){
			if(lista[i].isFile()){
			fd.name=lista[i].getName();
			fd.size=lista[i].length();
			try {
				fd.hash=DigestUtils.md5Hex(new FileInputStream(lista[i].getName()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			files.add(fd);
			}
			}
	}
	
	public static void main(String args[])
	  {
		fileDatakSortu();
		FileData filedata=new FileData();
		for (int i=0;files.size()>i;i++){
	    try
	    {
	    	filedata=files.get(i);
	    	Bidaltzailea bidal = new Bidaltzailea(filedata);
	        Globalak.ORBGlobal.setArgs(args);
			Globalak.ORBGlobal.rebind("bidaltzailea", Globalak.ORBGlobal.getRootPOA().servant_to_reference(bidal));
			Globalak.ORBGlobal.getORBThread().start();
	    }
	    catch (Exception e)
	    {
	      System.out.println("ERROR : " + e) ;
	      e.printStackTrace(System.out);
	    }
		}
	  }
	
	
}
