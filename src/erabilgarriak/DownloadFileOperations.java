package erabilgarriak;


/**
 * Generated from IDL interface "DownloadFile".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 30-may-2011 18:05:40
 */

public interface DownloadFileOperations
{
	/* constants */
	/* operations  */
	erabilgarriak.FileData getFileData();
	int getPartCount();
	int getPart(int numPart, erabilgarriak.DownloadFilePackage.PartHolder part);
}
