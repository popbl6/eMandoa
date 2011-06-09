package erabilgarriak;

/**
 * Generated from IDL interface "DownloadFile".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public final class DownloadFileHolder	implements org.omg.CORBA.portable.Streamable{
	 public DownloadFile value;
	public DownloadFileHolder()
	{
	}
	public DownloadFileHolder (final DownloadFile initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DownloadFileHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DownloadFileHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DownloadFileHelper.write (_out,value);
	}
}
