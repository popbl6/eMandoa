package erabilgarriak;


/**
 * Generated from IDL interface "Server".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 30-may-2011 18:05:40
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
