package bezeroa;


import java.util.Arrays;

import erabilgarriak.DownloadFile;
import erabilgarriak.DownloadFilePackage.PartHolder;

public class Jasotzailea extends Thread{
	DownloadFile df;
	Deskarga des;
	
	public Jasotzailea(DownloadFile df, Deskarga des){
		this.df = df;
		this.des = des;
	}
	
	public void run(){
		int numPart=-1;
		try{
			while((numPart=des.getNextPart()) != -1){
				PartHolder holder = new PartHolder(new byte[1]);
				int bytes = df.getPart(numPart, holder);
				des.putPart(new Part(numPart, Arrays.copyOf(holder.value, bytes)));//Arrays.copy() ez da beharrezkoa, honek array-a 
																				   //irakurri dituen byte-n arabera mozten du, baina,
				   																   //arrayaren luzeera jada ondo egon beharko zan
		}
		}catch(Exception e){
			des.error(numPart);
		}
	}

}
