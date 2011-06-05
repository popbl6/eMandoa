package bezeroa;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;

import erabilgarriak.*;

public class Bezero {
	static ArrayList<FileData> files=new ArrayList<FileData>();
	public static final String ONCOMING_PATH=".";//Konpartitzeko fitxategiak egongo diren helbidea
	private static final String INCOMING_PATH = null;
	
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
	
	//Deskarga berrabiarazi


	public static void partakIrakurri() throws Exception{
		File fichero=new File(INCOMING_PATH);
		int partKop;
		ArrayList <Integer> parteak=new ArrayList<Integer>();
		ArrayList <Integer> faltan=new ArrayList<Integer>();
		int partea;
		File [] lista;
		lista=fichero.listFiles(new Filter(".data"));
		for (int i=0;i<lista.length;i++){
		FileInputStream data=new FileInputStream(lista[i]);
	    DataInputStream in = new DataInputStream(data);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String name=br.readLine();
	    Long size=(long)br.read();
	    String hash=br.readLine();
	    FileData fd=new FileData();
	    fd.name=name;
	    fd.size=size;
	    fd.hash=hash;
	    partKop=(int)br.read();
	    for(int i1=0; i1<partKop; i1++){
	    	partea=(int)br.read();
			parteak.add(partea);
		}
	    for(int i1=0;i1<partKop;i1++){
	    	if(!parteak.contains(i1)){
	    		faltan.add(i1);
	    	}
	    }
	    Deskarga berrabiarazi=new Deskarga(fd,faltan);	    
		}
		
	}
	
	
}
