package erabilgarriak;


/**
 * Generated from IDL interface "Server".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 30-may-2011 18:05:40
 */

public final class ServerHelper
{
	public static void insert (final org.omg.CORBA.Any any, final erabilgarriak.Server s)
	{
			any.insert_Object(s);
	}
	public static erabilgarriak.Server extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:erabilgarriak/Server:1.0", "Server");
	}
	public static String id()
	{
		return "IDL:erabilgarriak/Server:1.0";
	}
	public static Server read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(erabilgarriak._ServerStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final erabilgarriak.Server s)
	{
		_out.write_Object(s);
	}
	public static erabilgarriak.Server narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof erabilgarriak.Server)
		{
			return (erabilgarriak.Server)obj;
		}
		else if (obj._is_a("IDL:erabilgarriak/Server:1.0"))
		{
			erabilgarriak._ServerStub stub;
			stub = new erabilgarriak._ServerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static erabilgarriak.Server unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof erabilgarriak.Server)
		{
			return (erabilgarriak.Server)obj;
		}
		else
		{
			erabilgarriak._ServerStub stub;
			stub = new erabilgarriak._ServerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
