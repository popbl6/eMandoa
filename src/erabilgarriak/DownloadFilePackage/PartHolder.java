package erabilgarriak.DownloadFilePackage;

/**
 * Generated from IDL alias "Part".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 30-may-2011 18:05:40
 */

public final class PartHolder
	implements org.omg.CORBA.portable.Streamable
{
	public byte[] value;

	public PartHolder ()
	{
	}
	public PartHolder (final byte[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return PartHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PartHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		PartHelper.write (out,value);
	}
}
