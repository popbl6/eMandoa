package erabilgarriak.ServerPackage;

/**
 * Generated from IDL alias "DownloadFileArray".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public final class DownloadFileArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public erabilgarriak.DownloadFile[] value;

	public DownloadFileArrayHolder ()
	{
	}
	public DownloadFileArrayHolder (final erabilgarriak.DownloadFile[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DownloadFileArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DownloadFileArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DownloadFileArrayHelper.write (out,value);
	}
}
