package zerbitzari;

import erabilgarriak.DownloadFile;
import erabilgarriak.FileData;
import erabilgarriak.ServerPOA;
import erabilgarriak.ServerPackage.DownloadFileArrayHolder;
import erabilgarriak.ServerPackage.FileDataArrayHolder;

public class Zerbitzari extends ServerPOA{

	@Override
	public boolean deregister(DownloadFile file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getFile(FileData data, DownloadFileArrayHolder files) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getLista(FileDataArrayHolder files) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(DownloadFile file) {
		// TODO Auto-generated method stub
		return false;
	}

}
