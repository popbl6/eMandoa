package jaso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;

import utiles.FilePart;

public class Deskarga {
	public static final int PART_SIZE = 1024;
	
	private ArrayList<Integer> parteak;
	private ArrayList<Integer> deskargatzen;
	private ArrayBlockingQueue<Part> gordetzeko;
	private Gordetzailea g;
	
	public Deskarga(FilePart fp, File file){
		g = new Gordetzailea(file);
		parteak = new ArrayList<Integer>();
		int part = fp.getPartCount();
		for(int i=0; i<part; i++){
			parteak.add(i);
		}
		Collections.shuffle(parteak);
		gordetzeko = new ArrayBlockingQueue<Part>(100);
		g.start();
		//Jasotzaileak hasieratu eta start
		Jasotzailea[] jasotzaileak = new Jasotzailea[3];
		for(int i=0; i<3; i++){
			jasotzaileak[i] = new Jasotzailea(fp, this);
			jasotzaileak[i].start();
		}
		//Jasotzaileak join
		for(int i=0; i<3; i++){
			try {
				jasotzaileak[i].join();
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
		
		public Gordetzailea(File file){
			try {
				ra = new RandomAccessFile(file, "rw");
			} catch (FileNotFoundException e) {
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
						System.out.println("Punteroaren posizioa: "+part.getNumPart()*Deskarga.PART_SIZE);
						ra.seek(part.getNumPart()*Deskarga.PART_SIZE);
						System.out.println("Gordetzen zatia: "+part.getNumPart());
						ra.write(part.getPart());
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
