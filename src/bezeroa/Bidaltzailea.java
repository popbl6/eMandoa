package bezeroa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import erabilgarriak.DownloadFilePOA;
import erabilgarriak.FileData;
import erabilgarriak.DownloadFilePackage.PartHolder;

public class Bidaltzailea extends DownloadFilePOA{
	FileData data;
	
	public Bidaltzailea(FileData data){
		this.data = data;
	}

	@Override
	public int getPartCount() {
		System.out.println(((double) data.size)/Globalak.eMandoa.PART_SIZE);
		return (int) Math.ceil(((double) data.size)/Globalak.eMandoa.PART_SIZE);
	}

	@Override
	public int getPart(int numPart, PartHolder zatia) {
		try {
			File file = new File("ongoing/"+data.name);
			RandomAccessFile ra = new RandomAccessFile(file, "r");
			byte[] buff = new byte[Globalak.eMandoa.PART_SIZE];
			long length = file.length()-numPart*Globalak.eMandoa.PART_SIZE;
			length = (length>Globalak.eMandoa.PART_SIZE)?Globalak.eMandoa.PART_SIZE:length;
			System.out.println("luzeera:" + (int)length);
			System.out.println("offset: "+numPart*Globalak.eMandoa.PART_SIZE);
			ra.seek(numPart*Globalak.eMandoa.PART_SIZE);
			length = ra.read(buff);
			System.out.println("Irakurrita");
			zatia.value = buff;
			ra.close();
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

	@Override
	public FileData getFileData() {
		return data;
	}

}
