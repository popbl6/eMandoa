package jaso;


import java.util.Arrays;

import utiles.FilePart;
import utiles.FilePartPackage.partHolder;

public class Jasotzailea extends Thread{
	FilePart fp;
	Deskarga des;
	
	public Jasotzailea(FilePart fp, Deskarga des){
		this.fp = fp;
		this.des = des;
	}
	
	public void run(){
		int numPart;
		while((numPart=des.getNextPart()) != -1){
			partHolder holder = new partHolder();
			holder.value = new byte[1];
			int bytes = fp.getPart(numPart, holder);
			des.putPart(new Part(numPart, Arrays.copyOf(holder.value, bytes)));
		}
	}

}
