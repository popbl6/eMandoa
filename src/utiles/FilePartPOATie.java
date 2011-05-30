package utiles;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "FilePart".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 25-may-2011 12:07:19
 */

public class FilePartPOATie
	extends FilePartPOA
{
	private FilePartOperations _delegate;

	private POA _poa;
	public FilePartPOATie(FilePartOperations delegate)
	{
		_delegate = delegate;
	}
	public FilePartPOATie(FilePartOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public utiles.FilePart _this()
	{
		return utiles.FilePartHelper.narrow(_this_object());
	}
	public utiles.FilePart _this(org.omg.CORBA.ORB orb)
	{
		return utiles.FilePartHelper.narrow(_this_object(orb));
	}
	public FilePartOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(FilePartOperations delegate)
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
	public int getPart(int numPart, utiles.FilePartPackage.partHolder zatia)
	{
		return _delegate.getPart(numPart,zatia);
	}

	public int getPartCount()
	{
		return _delegate.getPartCount();
	}

}
