package zerbitzari;

import java.util.ArrayList;

import erabilgarriak.DownloadFile;
import erabilgarriak.FileData;
import erabilgarriak.ServerPOA;
import erabilgarriak.ServerPackage.DownloadFileArrayHolder;
import erabilgarriak.ServerPackage.FileDataArrayHolder;

public class Zerbitzari extends ServerPOA{
	
	ArrayList<SeedZerrenda> fitxZerrenda;  
	
	public Zerbitzari(){
		fitxZerrenda = new ArrayList<SeedZerrenda>();
		
	}
	
	public boolean deregister(DownloadFile file) {
		
		for(SeedZerrenda zer : fitxZerrenda){
			if(zer.getFitxategi().equals(file.getFileData())){
				zer.getSeedList().remove(file);
				return true;
			}
		}
		return false;
	}

	public boolean getFile(FileData data, DownloadFileArrayHolder files) {
		try{
			for(SeedZerrenda zer : fitxZerrenda){
				if(zer.getFitxategi().equals(data)){
					files.value = new DownloadFile[zer.getSeedList().size()];
					for(int i=0; i<files.value.length; i++){
						files.value[i] = zer.getSeedList().get(i);
					}
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}


	public boolean getLista(FileDataArrayHolder files) {
		/*ArrayList<FileData> fitx=new ArrayList<FileData>();
		
		int kop=fitxZerrenda.size();
		for(int i=0;i<kop;i++){
			fitx.add(fitxZerrenda.get(i).getFitxategi());
		}
		try{
			files.value=(FileData[]) fitx.toArray();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;*/
		FileData[] fitx = new FileData[fitxZerrenda.size()];
		for(int i=0;i<fitxZerrenda.size();i++)
			fitx[i] = fitxZerrenda.get(i).getFitxategi();
		files.value = fitx;
		return true;
	}


	public boolean register(DownloadFile file) {
		System.out.println("Gehitzen fitxategia: "+file.getFileData().name);
		for(SeedZerrenda zer : fitxZerrenda){
			if(zer.getFitxategi().equals(file.getFileData())){
				zer.addSeed(file);
				return true;
			}
		}
		SeedZerrenda buff = new SeedZerrenda(file.getFileData());
		buff.addSeed(file);
		fitxZerrenda.add(buff);
		return true;
	}

}
