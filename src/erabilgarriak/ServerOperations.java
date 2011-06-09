package erabilgarriak;


/**
 * Generated from IDL interface "Server".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public interface ServerOperations
{
	/* constants */
	/* operations  */
	boolean register(erabilgarriak.DownloadFile file);
	boolean deregister(erabilgarriak.DownloadFile file);
	boolean getLista(erabilgarriak.ServerPackage.FileDataArrayHolder files);
	boolean getFile(erabilgarriak.FileData data, erabilgarriak.ServerPackage.DownloadFileArrayHolder files);
}
