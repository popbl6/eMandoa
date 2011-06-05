package zerbitzari;

import java.util.ArrayList;

import erabilgarriak.DownloadFile;
import erabilgarriak.FileData;
import erabilgarriak.ServerPOA;
import erabilgarriak.ServerPackage.DownloadFileArrayHolder;
import erabilgarriak.ServerPackage.FileDataArrayHolder;

public class Zerbitzari extends ServerPOA{
	
	ArrayList<FitxategiZerrenda> fitxZerrenda;  
	
	public Zerbitzari(){
		fitxZerrenda = new ArrayList<FitxategiZerrenda>();
		
	}
	
	public boolean deregister(DownloadFile file) {
		
		for(FitxategiZerrenda zer : fitxZerrenda){
			if(zer.getFitxategi().equals(file.getFileData())){
				zer.getSeedList().remove(file);
				return true;
			}
		}
		return false;
	}

	public boolean getFile(FileData data, DownloadFileArrayHolder files) {

		for(FitxategiZerrenda zer : fitxZerrenda){
			if(zer.getFitxategi().equals(data)){
				files.value= (DownloadFile[]) zer.getSeedList().toArray();
				return true;
			}
		}
		return false;
	}


	public boolean getLista(FileDataArrayHolder files) {
		ArrayList<FileData> fitx=new ArrayList<FileData>();
		
		int kop=fitxZerrenda.size();
		for(int i=0;i<kop;i++){
			fitx.add(fitxZerrenda.get(i).getFitxategi());
		}
		files.value=(FileData[]) fitx.toArray();
		return true;
	}


	public boolean register(DownloadFile file) {
		for(FitxategiZerrenda zer : fitxZerrenda){
			if(zer.getFitxategi().equals(file.getFileData())){
				zer.getSeedList().add(file);
				return true;
			}
		}
		return false;
	}

}
