package bezeroa;


import java.util.Arrays;

import erabilgarriak.DownloadFile;
import erabilgarriak.DownloadFilePackage.PartHolder;

public class Jasotzailea extends Thread{
	private DownloadFile df;
	private Deskarga des;
	private boolean hil = false;
	
	public Jasotzailea(DownloadFile df, Deskarga des){
		this.df = df;
		this.des = des;
	}
	
	public void setSeeder(DownloadFile seeder){df = seeder;}
	public void hil(){hil = true;}
	
	public void run(){
		int numPart=-1;
		while(!hil){
			if((numPart=des.getNextPart()) == -1)
				break;
			try{
				System.out.println("Jasotzailea "+numPart+" partea hartzen");
				PartHolder holder = new PartHolder(new byte[1]);
				int bytes = df.getPart(numPart, holder);
				if(bytes == 0)
					throw new Exception();
				System.out.println("partea jasota");
				des.putPart(new Part(numPart, Arrays.copyOf(holder.value, bytes)));//Arrays.copy() ez da beharrezkoa, honek array-a 
																				   //irakurri dituen byte-n arabera mozten du, baina,
				   																   //arrayaren luzeera jada ondo egon beharko zan
			}catch(Exception e){
				des.error(numPart, df, this);
			}
		}
	}

}
