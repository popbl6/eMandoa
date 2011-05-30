package utiles.FilePartPackage;

/**
 * Generated from IDL alias "part".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 25-may-2011 12:07:19
 */

public final class partHolder
	implements org.omg.CORBA.portable.Streamable
{
	public byte[] value;

	public partHolder ()
	{
	}
	public partHolder (final byte[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return partHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = partHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		partHelper.write (out,value);
	}
}
