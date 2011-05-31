package erabilgarriak;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "DownloadFile".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 30-may-2011 18:05:40
 */

public class DownloadFilePOATie
	extends DownloadFilePOA
{
	private DownloadFileOperations _delegate;

	private POA _poa;
	public DownloadFilePOATie(DownloadFileOperations delegate)
	{
		_delegate = delegate;
	}
	public DownloadFilePOATie(DownloadFileOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public erabilgarriak.DownloadFile _this()
	{
		return erabilgarriak.DownloadFileHelper.narrow(_this_object());
	}
	public erabilgarriak.DownloadFile _this(org.omg.CORBA.ORB orb)
	{
		return erabilgarriak.DownloadFileHelper.narrow(_this_object(orb));
	}
	public DownloadFileOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(DownloadFileOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		return super._default_POA();
	}
	public erabilgarriak.FileData getFileData()
	{
		return _delegate.getFileData();
	}

	public int getPart(int numPart, erabilgarriak.DownloadFilePackage.PartHolder part)
	{
		return _delegate.getPart(numPart,part);
	}

	public int getPartCount()
	{
		return _delegate.getPartCount();
	}

}
