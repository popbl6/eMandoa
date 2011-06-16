package bezeroa;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.apache.commons.codec.digest.DigestUtils;

import erabilgarriak.*;

public class Bezero {
	public static ArrayList<FileData> files=new ArrayList<FileData>();
	public static ArrayList<Deskarga> berrabiarazteko=new ArrayList<Deskarga>();
	public static final String ONCOMING_PATH="ongoing";//Konpartitzeko fitxategiak egongo diren helbidea
	private static final String INCOMING_PATH = "incoming";

	public static void fileDatakSortu(JProgressBar pb, JLabel label){
		File fichero=new File(ONCOMING_PATH);
		if(!fichero.exists())
			fichero.mkdir();
		File [] lista;
		lista=fichero.listFiles();
		if(lista == null)
			return;
		int pbBase = pb.getValue();
		Double dPb = 60.0/lista.length;
		Double addPbBase = 0.0;
		for(int i=0;i<lista.length;i++){
			if(lista[i].isFile()){
				FileData fd = new FileData();
				label.setText("Irakurtzen: "+lista[i].getName()+"...");
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
				addPbBase += dPb;
				pb.setValue((int)(pbBase+addPbBase));
			}
		}
	}
	
	/*public static void main(String args[]) throws ServantNotActive, WrongPolicy{
		Globalak.ORBGlobal.setArgs(args);
		fileDatakSortu();
		FileData filedata=new FileData();
		Globalak.ORBGlobal.getORBThread().start();
		for (int i=0;files.size()>i;i++){
		    try{
		    	filedata=files.get(i);
		    	Bidaltzailea bidal = new Bidaltzailea(filedata);
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
		//try {
			//datakIrakurri();
		//} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		//}
		
		
		Globalak.ORBGlobal.getORB().shutdown(true);
		try {
			Globalak.ORBGlobal.getORBThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/
	
	//Deskarga berrabiarazi


	public static void datakIrakurri(JProgressBar pb, JLabel label) throws Exception{
		File fichero=new File(INCOMING_PATH);
		if(!fichero.exists())
			fichero.mkdir();
		File [] lista;
		lista=fichero.listFiles(new Filter(".data"));
		if(lista == null)
			return;
		int pbBase = pb.getValue();
		Double dPb = 25.0/lista.length;
		Double addPbBase = 0.0;
		for (int i=0;i<lista.length;i++){
			int partKop;
			ArrayList <Integer> parteak=new ArrayList<Integer>();
			ArrayList <Integer> faltan=new ArrayList<Integer>();
			System.out.println("Fitxategia irakurtzen:"+lista[i]);
		    BufferedReader br = new BufferedReader(new FileReader(lista[i]));
		    String name=br.readLine();
		    label.setText("Berrabiarazten:"+name+"...");
		    Long size=Long.parseLong(br.readLine());
		    String hash=br.readLine();
		    FileData fd=new FileData();
		    fd.name=name;
		    fd.size=size;
		    fd.hash=hash;
		    partKop=Integer.parseInt(br.readLine());
		    String numPart;
		    while((numPart = br.readLine()) != null)
		    	parteak.add(Integer.parseInt(numPart));
		    for(int j=0;j<partKop;j++){
		    	if(!parteak.contains(j)){
		    		faltan.add(j);
		    	}
		    }
		    System.out.println("Deskarga berrabiarazten");
		    Deskarga berrabiarazi=new Deskarga(fd,faltan);
		    berrabiarazteko.add(berrabiarazi);
		    addPbBase += dPb;
			pb.setValue((int)(pbBase+addPbBase));
		}
		
	}

	public static ArrayList<FileData> getFitxBerriak() {
		File fichero=new File(ONCOMING_PATH);
		ArrayList<FileData> fitx = new ArrayList<FileData>();
		if(!fichero.exists())
			fichero.mkdir();
		File [] lista;
		lista=fichero.listFiles();
		if(lista == null)
			return null;
		for(int i=0;i<lista.length;i++){
			if(lista[i].isFile()){
				boolean found = false;
				for(FileData fd : files){
					if(fd.name.equals(lista[i].getName())){
						found = true;
						break;
					}
				}
				if(!found){
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
					fitx.add(fd);
					files.add(fd);
				}
			}
		}
		return fitx;
	}
	
	
}
