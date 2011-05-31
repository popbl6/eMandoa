package erabilgarriak;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "Server".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 30-may-2011 18:05:40
 */

public class ServerPOATie
	extends ServerPOA
{
	private ServerOperations _delegate;

	private POA _poa;
	public ServerPOATie(ServerOperations delegate)
	{
		_delegate = delegate;
	}
	public ServerPOATie(ServerOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public erabilgarriak.Server _this()
	{
		return erabilgarriak.ServerHelper.narrow(_this_object());
	}
	public erabilgarriak.Server _this(org.omg.CORBA.ORB orb)
	{
		return erabilgarriak.ServerHelper.narrow(_this_object(orb));
	}
	public ServerOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ServerOperations delegate)
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
	public boolean register(erabilgarriak.DownloadFile file)
	{
		return _delegate.register(file);
	}

	public boolean getFile(erabilgarriak.FileData data, erabilgarriak.ServerPackage.DownloadFileArrayHolder files)
	{
		return _delegate.getFile(data,files);
	}

	public boolean deregister(erabilgarriak.DownloadFile file)
	{
		return _delegate.deregister(file);
	}

	public boolean getLista(erabilgarriak.ServerPackage.FileDataArrayHolder files)
	{
		return _delegate.getLista(files);
	}

}
