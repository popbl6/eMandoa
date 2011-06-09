package erabilgarriak;

/**
 * Generated from IDL struct "FileData".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public final class FileDataHolder
	implements org.omg.CORBA.portable.Streamable
{
	public erabilgarriak.FileData value;

	public FileDataHolder ()
	{
	}
	public FileDataHolder(final erabilgarriak.FileData initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return erabilgarriak.FileDataHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = erabilgarriak.FileDataHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		erabilgarriak.FileDataHelper.write(_out, value);
	}
}
