package erabilgarriak;

/**
 * Generated from IDL interface "Server".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public final class ServerHolder	implements org.omg.CORBA.portable.Streamable{
	 public Server value;
	public ServerHolder()
	{
	}
	public ServerHolder (final Server initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ServerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ServerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ServerHelper.write (_out,value);
	}
}
