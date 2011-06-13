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
		System.out.println("Fitxategia deregistratzen");
		for(SeedZerrenda zer : fitxZerrenda){
			if(zer.getFitxategi().equals(file.getFileData())){
				zer.getSeedList().remove(file);
				return true;
			}
		}
		return false;
	}

	public boolean getFile(FileData data, DownloadFileArrayHolder files) {
		System.out.println("Fitxategi eskaera: "+data.name);
		try{
			for(SeedZerrenda zer : fitxZerrenda){
				if(zer.getFitxategi().equals(data)){
					files.value = new DownloadFile[zer.getSeedList().size()];
					for(int i=0; i<files.value.length; i++){
						files.value[i] = zer.getSeedList().get(i);
					}
					System.out.println("Fitxategia bueltatzen "+files.value.length+" seeder");
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
	
	public class Garbitzailea extends Thread{
		private boolean stopped=false;
		
		public void interrupt(){
			stopped = true;
			super.interrupt();
		}
		
		public void run(){
			while(!stopped){
				try {
					sleep(1*60*1000);
					ArrayList<SeedZerrenda> szKentzeko = new ArrayList<SeedZerrenda>();
					for(SeedZerrenda sz : fitxZerrenda){
						ArrayList<DownloadFile> dfKentzeko = new ArrayList<DownloadFile>();
						//Kentzeko seederrak bilatu eta gorde
						for(DownloadFile df : sz.getSeedList()){
							try{
								if(df._non_existent())
									dfKentzeko.add(df);
							}catch(Exception e){
								dfKentzeko.add(df);
							}
						}
						//Kentzeko daudenak kendu
						for(DownloadFile df : dfKentzeko){
							sz.getSeedList().remove(df);
						}
						//SeedZerrenda hutzik gelditzen bada kentzeko jarri
						if(sz.getSeedList().isEmpty()){
							szKentzeko.add(sz);
						}
					}
					for(SeedZerrenda sz : szKentzeko){
						fitxZerrenda.remove(sz);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}	
	}
}
