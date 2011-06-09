package erabilgarriak;


/**
 * Generated from IDL interface "DownloadFile".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public interface DownloadFileOperations
{
	/* constants */
	/* operations  */
	erabilgarriak.FileData getFileData();
	int getPartCount();
	int getPart(int numPart, erabilgarriak.DownloadFilePackage.PartHolder part);
}
