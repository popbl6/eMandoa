package erabilgarriak.ServerPackage;

/**
 * Generated from IDL alias "FileDataArray".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public final class FileDataArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public erabilgarriak.FileData[] value;

	public FileDataArrayHolder ()
	{
	}
	public FileDataArrayHolder (final erabilgarriak.FileData[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return FileDataArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FileDataArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		FileDataArrayHelper.write (out,value);
	}
}
