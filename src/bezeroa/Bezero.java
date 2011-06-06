package bezeroa;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import erabilgarriak.*;
import erabilgarriak.ServerPackage.FileDataArrayHolder;

public class Bezero {
	static ArrayList<FileData> files=new ArrayList<FileData>();
	public static final String ONCOMING_PATH="ongoing";//Konpartitzeko fitxategiak egongo diren helbidea
	public static void fileDatakSortu(){
		File fichero=new File(ONCOMING_PATH);
		File [] lista;
		lista=fichero.listFiles();
		for(int i=0;i<lista.length;i++){
			if(lista[i].isFile()){
				FileData fd = new FileData();
				fd.name=lista[i].getName();
				fd.size=lista[i].length();
				try {
					System.out.println(lista[i].getAbsoluteFile());
					fd.hash=DigestUtils.md5Hex(new FileInputStream(lista[i].getAbsoluteFile()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				files.add(fd);
			}
		}
	}
	
	public static void main(String args[]) throws ServantNotActive, WrongPolicy{
		fileDatakSortu();
		FileData filedata=new FileData();
		Globalak.ORBGlobal.getORBThread().start();
		for (int i=0;files.size()>i;i++){
		    try{
		    	filedata=files.get(i);
		    	Bidaltzailea bidal = new Bidaltzailea(filedata);
		        Globalak.ORBGlobal.setArgs(args);
		        Globalak.eMandoa.getServer().register(DownloadFileHelper.narrow(Globalak.ORBGlobal.getRootPOA().servant_to_reference(bidal)));
		    }catch (Exception e){
		      e.printStackTrace();
		    }
		}
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		FileData[] buff = new FileData[1];
		buff[0] = new FileData();
		FileDataArrayHolder holder = new FileDataArrayHolder(buff);
		Globalak.eMandoa.getServer().getLista(holder);
		for(FileData data : holder.value){
			System.out.println(data.name);
		}
		scan.nextLine();
		
		//Globalak.eMandoa.getServer().register(DownloadFileHelper.narrow(Globalak.ORBGlobal.getRootPOA().servant_to_reference(new Bidaltzailea(files.get(2)))));
		
		try {
			Deskarga d = new Deskarga(holder.value[0]);
			d.start();
			System.out.println("Deskarga hasita");
			d.join();
			System.out.println("Deskarga bukatuta");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Globalak.ORBGlobal.getORB().shutdown(true);
		try {
			Globalak.ORBGlobal.getORBThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
