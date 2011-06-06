package zerbitzari;

import java.util.ArrayList;

import erabilgarriak.*;

public class SeedZerrenda {
	
	private FileData fitxategia;
	private ArrayList<DownloadFile> seed;
	
	public SeedZerrenda(FileData data){
		this.fitxategia = data;
		seed = new ArrayList<DownloadFile>();
	}
	
	public FileData getFitxategi(){
		return fitxategia;
	}
	
	public void setFitxategi(FileData fitx){
		fitxategia=fitx;
	}
	
	public ArrayList<DownloadFile> getSeedList(){
		return seed;
	}
	
	public void addSeed(DownloadFile hazi){
		seed.add(hazi);
	}
	

}
