package bezeroa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

import erabilgarriak.DownloadFile;
import erabilgarriak.FileData;
import erabilgarriak.ServerPackage.DownloadFileArrayHolder;

public class Deskarga extends Thread {
	public static final int JASOTZAILE_MAX = 5;
	
	private ArrayList<DownloadFile> seeds;
	private ArrayList<DownloadFile> seedsErabiltzen;
	private ArrayList<Integer> parteak;
	//Array hau igoal ez da beharrezkoa, erroreren bat badago berriro ere parteen array-ra sartuko direlako eta 
	//programa ixten bada ez da fitxategian gordeta egongo deskargatua izan dela
	//private ArrayList<Integer> deskargatzen;
	private ArrayList<Jasotzailea> jasotzaileak;
	private ArrayBlockingQueue<Part> gordetzeko;
	private Gordetzailea g;
	private int partCount = -1;
	private int downloadedParts = 0;
	private Semaphore jasoEM;
	private Semaphore seederEM;
	private Semaphore amaituta;
	private FileData file;
	private SeedChecker checker;
	private int jasotzaileAktibo = 0;
	
	
	/**
	 * Ez pasatu seed-ak, file data bat pasatu, run egitean seedCheker klasea martxan jarri eta seed bat egon arte
	 * itxaroten jarri, horrela deskarga bat berrabiaraztean ere ongi doa
	 * 
	 * partCount aldagaia bertan kalkulatu behar da, tamainaren arabera eta ez kanpotik eskatu
	 * 
	 * 
	 * @param seeds
	 * @throws Exception
	 */
	public Deskarga(FileData file) throws Exception{
		this.file = file;
		parteak = new ArrayList<Integer>();
		seeds = new ArrayList<DownloadFile>();
		seedsErabiltzen = new ArrayList<DownloadFile>();
		partCount = (int) Math.ceil(((double)file.size)/Globalak.eMandoa.PART_SIZE);
		g = new Gordetzailea(file, false);
		checker = new SeedChecker(file, this);
		//Deskargatzeko dauden parteen array-a bete
		for(int i=0; i<partCount; i++){
			parteak.add(i);
		}
		gordetzeko = new ArrayBlockingQueue<Part>(100);
		jasotzaileak = new ArrayList<Jasotzailea>();
		jasoEM = new Semaphore(1);
		seederEM = new Semaphore(1);
		amaituta = new Semaphore(0);
	}
	
	public Deskarga(FileData file, ArrayList<Integer> parteak) throws Exception{
		this.file = file;
		this.parteak = parteak;
		seeds = new ArrayList<DownloadFile>();
		seedsErabiltzen = new ArrayList<DownloadFile>();
		partCount = (int) Math.ceil(((double)file.size)/Globalak.eMandoa.PART_SIZE);
		downloadedParts = partCount - parteak.size();
		g = new Gordetzailea(file, true);
		checker = new SeedChecker(file, this);
		gordetzeko = new ArrayBlockingQueue<Part>(100);
		jasotzaileak = new ArrayList<Jasotzailea>();
		jasoEM = new Semaphore(0);
		seederEM = new Semaphore(1);
	}
	
	private void jasotzaileaGehitu(){jasoEM.acquireUninterruptibly(); jasotzaileAktibo++; jasoEM.release();}
	private void jasotzaileaKendu(){jasoEM.acquireUninterruptibly(); jasotzaileAktibo--; jasoEM.release();}
	
	public void run(){
		g.start();
		checker.start();
		//SeedChecker enkargatuko da jasotzaileak hasieratzeaz
		
		//Amaitu arte itxaron
		amaituta.acquireUninterruptibly();
		checker.interrupt();
		
		//Jasotzaileak join (Honetan kontrolatu behar da ea denekin egiten duen join-a edo hasieran daudenekin bakarrik,
		//iteratzaileak hasierako balioen kopia bat egiten du edo dinamikoa da?)
		for(Jasotzailea jaso : jasotzaileak){
			try {
				jaso.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Gordetzailea gelditu
		//Exekuzioa bukatu behar da
		g.interrupt();
		try {
			g.join();
			checker.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized int getNextPart(){
		if(parteak.size() != 0){
			int part = parteak.get(0);
			parteak.remove(0);
			return part;
		}
		return -1;
	}
	
	public void putPart(Part part){
		try {
			gordetzeko.put(part);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Hari bakar bat egon daiteke seede-etan ibiltzen aldi bakoitzean, bestela arazoak egon daitezke
	 * 
	 * semaforoan release eta
	 * Seeder-en bat libre badago jasotzaileari ematen zaio seed hori, bestela seedCheker funtzioak sortuko du
	 * seeder berriak daudenean
	 * 
	 * 
	 * @param numPart
	 */
	public void error(int numPart, DownloadFile df, Jasotzailea jaso){
		boolean hasita=false;
		parteak.add(numPart);
		seeds.remove(df);
		seedsErabiltzen.remove(df);
		//Uste dut hau egina dagoela, konprobatu!!!
		try {
			if(seeds.size() > seedsErabiltzen.size()){
				//Hari bakarra egon daiteke seeder bat bilatzen
				seederEM.acquire();
				for(DownloadFile seed : seeds){
					if(!seedsErabiltzen.contains(seed)){
						seedsErabiltzen.add(seed);
						jaso.setSeeder(seed);
						hasita = true;
					}
				}
				seederEM.release();
			}
			//Ezin izan bada hasi jasotzailea hilko da
			if(!hasita){
				jasotzaileaKendu();
				jaso.hil();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class Gordetzailea extends Thread{
		private boolean stopped;
		private RandomAccessFile ra;
		private BufferedWriter bw;
		
		public Gordetzailea(FileData file, boolean isRestart){
			try {
				//Deskargatzeko fitxategia sortu
				ra = new RandomAccessFile(new File("./incoming/"+file.name), "rw");
				//Deskargaren progresoa gordetzen duen fitxategia sortu
				bw = new BufferedWriter(new FileWriter("./incoming/"+file.name+".data", isRestart));
				if(!isRestart){
					bw.write(file.name);
					bw.newLine();
					bw.write(file.size+"");
					bw.newLine();
					bw.write(file.hash);
					bw.newLine();
					bw.write(partCount+"");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void interrupt(){
			stopped = true;
			super.interrupt();
		}
		
		public void run(){
			while(!stopped){
				try {
					Part part = gordetzeko.take();
					try {
						//System.out.println("Punteroaren posizioa: "+part.getNumPart()*Deskarga.PART_SIZE);
						ra.seek(part.getNumPart()*Globalak.eMandoa.PART_SIZE);
						//System.out.println("Gordetzen zatia: "+part.getNumPart());
						ra.write(part.getPart());
						//Datu fitxategian partea deskargatu dela gorde
						bw.write(part.getNumPart()+"");
						bw.newLine();
						downloadedParts++;
						if(downloadedParts == partCount){
							stopped = true;
							amaituta.release();
							new File("./incoming/"+file.name+".data").delete();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (InterruptedException e) {
					System.out.println("Gordetzailea gelditzen");
				}
			}
		}
		
	}
	
	private class SeedChecker extends Thread{
		private FileData data;
		private boolean stopped;
		private Deskarga des;
		
		public SeedChecker(FileData file, Deskarga des){
			this.data = file;
			stopped = false;
			this.des = des;
		}
		
		public void interrupt(){
			stopped = true;
			super.interrupt();
		}
		
		public void run(){
			while(!stopped){
				try {
					//zerbitzariari seed gehiago eskatu
					DownloadFileArrayHolder files = new DownloadFileArrayHolder(new DownloadFile[1]);
					if(Globalak.eMandoa.getServer().getFile(data, files)){
						//List<DownloadFile> lista = Arrays.asList(files.value);
						ArrayList<DownloadFile> lista = new ArrayList<DownloadFile>();
						for(DownloadFile df : files.value){
							lista.add(df);
						}
						System.out.println("Seed kopurua: "+files.value.length);
						//seeder-ak bueltatzen baditu jada seeder arraian ez daudela konprobatu
						seederEM.acquire();
						ArrayList<DownloadFile> bufferRemove = new ArrayList<DownloadFile>();
						for(DownloadFile df : lista){
							//Seeder-en konexioa konprobatu
							try{
								if(df._non_existent()){
									bufferRemove.add(df);
								}else{
									for(DownloadFile seed : seeds){
										if(seed._is_equivalent(df))
											bufferRemove.add(df);
									}
								}
							}catch(Exception e){
								bufferRemove.add(df);
							}
						}
						for(DownloadFile df : bufferRemove){
							lista.remove(df);
						}
						//listan oraindik elementuren bat badago eta jasotzaile kopurua 5 baino txikiagoa bada jasotzaile berriak sortu
						for(DownloadFile df : lista){
							if(jasotzaileAktibo < Deskarga.JASOTZAILE_MAX){
								Jasotzailea buff = new Jasotzailea(df, des);
								seedsErabiltzen.add(df);
								seeds.add(df);
								buff.start();
								jasotzaileaGehitu();
								jasotzaileak.add(buff);
							}
						}
						seederEM.release();
					}
					sleep(1*60*1000);
				} catch (InterruptedException e) {}
			}
		}
	}
}
