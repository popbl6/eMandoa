package bezeroa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import erabilgarriak.DownloadFile;
import erabilgarriak.FileData;

public class Deskarga extends Thread {
	public static final int PART_SIZE = 1024;
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
	
	public Deskarga(ArrayList<DownloadFile> seeds) throws Exception{
		parteak = new ArrayList<Integer>();
		this.seeds = seeds;
		seedsErabiltzen = new ArrayList<DownloadFile>();
		//Parteak hartzen sahiatu, errorea ematen badu seed hori deskartatu eta hurrengoa hartu
		do{
			if(seeds.size() != 0){//Ez badago seed-ik errore bat bota
				try{
					partCount = seeds.get(0).getPartCount();
				}catch(Exception e){
					seeds.remove(0);
				}
			} else {
				throw new Exception("Ezin izan da deskarga hasieratu");
			}
		}while(partCount == -1);
		g = new Gordetzailea(seeds.get(0).getFileData(), false);
		//Deskargatzeko dauden parteen array-a bete
		for(int i=0; i<partCount; i++){
			parteak.add(i);
		}
		gordetzeko = new ArrayBlockingQueue<Part>(100);
		jasotzaileak = new ArrayList<Jasotzailea>();	
	}
	
	public void run(){
		g.start();
		//Jasotzaileak hasieratu eta start JASOTZAILE_MAX baino seed gehiago badaude JASOTZAILE_MAX 
		//jasotzaile bakarrik sortuko dira
		for(int i=0; i<seeds.size() && i<Deskarga.JASOTZAILE_MAX; i++){
			Jasotzailea buff = new Jasotzailea(seeds.get(i), this);
			jasotzaileak.add(buff);
			seedsErabiltzen.add(seeds.get(i));
			buff.start();
		}
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
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Hobekuntza bat funtzio honek zein parte hartu esateaz gain nundik hartu behar duen esatea da
	 * horrela seed-ak klase honek kontrolatzen ditu
	 * 
	 * Beste era bat jasotzaileak sortzerakoan kopuru finko bat sortzea gutxienez da, eta bakoitzari
	 * fitxategi objetu ezberdin bat eman, seed ezberdinenak dienak. Batek jasotzean errorea ematen badu 
	 * errore funtzio batekin deskarga klaseari(hau) abisatu eta klase honek beste jasotzaile bat sortu
	 * beste seed batendako, eta parte zenbakia deskargatzen bufferretik kendu eta parteak bufferrera sartu,
	 * jasotzaile berria jasotzaileen bufferrean sartu.
	 * 
	 * Erroreak 2 motakoak izan daitezke, Jasotzaileak ezberdindu beharko ditu, partea jasotzean errore sinple bat
	 * partea berriro ere irakurtzen jarri daiteke, beste errore bat egon daiteke, non seed-a deskonektatu
	 * egin den, errore honekin jasotzailea bukatu eta beste berri bat jarri beharko da.
	 * 
	 * Errore denekin jasotzailea bukatu eta berria hasi, errorearen arabera seed-a ere kendu edo bufferrean berriro
	 * ere sartu.
	 * 
	 * Ez badago seed bat ere ez libre ez da jasotzailerik sortuko.
	 * 
	 * Aurrerago oraindik bukatu ez diren deskargak ere leecher modura sartu daitezke eta zein parte dituzten bukatuta
	 * publikatu, datuak hartzerako orduan leecher hauei preferentzia emango zaie, ez badago nahi den partea deskargatzeko
	 * seeder bati eskatuko zaio. Honekin parteak emateko estruktura aldatu beharko litzateke, eta partea ematen duen
	 * funtzioak seeder edo leecher-aren objetua ere eman beharko luke, erroreen gestioa berdina izango litzateke, baina
	 * Jasotzaileak ez dira kenduko, ez daudelako seeder bati lotuta.
	 * 
	 * @return
	 */
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
	
	private class Gordetzailea extends Thread{
		private boolean stopped;
		private RandomAccessFile ra;
		private BufferedWriter bw;
		
		public Gordetzailea(FileData file, boolean isRestart){
			try {
				//Deskargatzeko fitxategia sortu
				ra = new RandomAccessFile(new File("./incoming/"+file.name), "rw");
				//Deskargaren progresoa gordetzen duen fitxategia sortu
				bw = new BufferedWriter(new FileWriter("./incoming"+file.name+".data", isRestart));
				if(!isRestart){
					bw.write(file.name);
					bw.newLine();
					bw.write(file.size+"");
					bw.newLine();
					bw.write(file.hash);
					bw.newLine();
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
						ra.seek(part.getNumPart()*Deskarga.PART_SIZE);
						//System.out.println("Gordetzen zatia: "+part.getNumPart());
						ra.write(part.getPart());
						//Datu fitxategian partea deskargatu dela gorde
						bw.write(part.getNumPart()+"");
						bw.newLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (InterruptedException e) {
					//e.printStackTrace();
					System.out.println("Gordetzailea gelditzen");
				}
			}
		}
		
	}

}
