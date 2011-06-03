package zerbitzari;

import java.util.ArrayList;

import erabilgarriak.*;

public class FitxategiZerrenda {
	
	private FileData fitxategia;
	private ArrayList<DownloadFile> seed;
	
	public FileData getFitxategi(){
		return fitxategia;
	}
	
	public void setFitxategi(FileData fitx){
		fitxategia=fitx;
	}
	
	public ArrayList<DownloadFile> getSeedList(){
		return seed;
	}
	
	public void setSeed(DownloadFile hazi){
		seed.add(hazi);
	}
	

}
