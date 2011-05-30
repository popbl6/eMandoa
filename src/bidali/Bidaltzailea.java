package bidali;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import utiles.FilePartPOA;
import utiles.FilePartPackage.partHolder;

public class Bidaltzailea extends FilePartPOA{
	String filename="grafikak.rar";

	@Override
	public int getPartCount() {
		System.out.println(new File(filename).length()/1024.0);
		return (int) Math.ceil(new File(filename).length()/1024.0);
	}

	@Override
	public int getPart(int numPart, partHolder zatia) {
		try {
			File file = new File(filename);
			//FileInputStream in = new FileInputStream(file);
			RandomAccessFile ra = new RandomAccessFile(file, "r");
			byte[] buff = new byte[1024];
			long length = file.length()-numPart*1024;
			length = (length>1024)?1024:length;
			System.out.println("luzeera:" + (int)length);
			System.out.println("offset: "+numPart*1024);
			ra.seek(numPart*1024);
			length = ra.read(buff);
			/*for(int i=0; i<numPart; i++){
				in.read(buff);
			}
			length = in.read(buff);*/
			
			System.out.println("Irakurrita");
			zatia.value = buff;
			return (int) length;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return 0;
	}

}
