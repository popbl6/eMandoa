package utiles;

/**
 * Generated from IDL interface "FilePart".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 25-may-2011 12:07:19
 */

public final class FilePartHolder	implements org.omg.CORBA.portable.Streamable{
	 public FilePart value;
	public FilePartHolder()
	{
	}
	public FilePartHolder (final FilePart initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FilePartHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FilePartHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FilePartHelper.write (_out,value);
	}
}
