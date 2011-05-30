package utiles;


/**
 * Generated from IDL interface "FilePart".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 25-may-2011 12:07:19
 */

public final class FilePartHelper
{
	public static void insert (final org.omg.CORBA.Any any, final utiles.FilePart s)
	{
			any.insert_Object(s);
	}
	public static utiles.FilePart extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:utiles/FilePart:1.0", "FilePart");
	}
	public static String id()
	{
		return "IDL:utiles/FilePart:1.0";
	}
	public static FilePart read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(utiles._FilePartStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final utiles.FilePart s)
	{
		_out.write_Object(s);
	}
	public static utiles.FilePart narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof utiles.FilePart)
		{
			return (utiles.FilePart)obj;
		}
		else if (obj._is_a("IDL:utiles/FilePart:1.0"))
		{
			utiles._FilePartStub stub;
			stub = new utiles._FilePartStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static utiles.FilePart unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof utiles.FilePart)
		{
			return (utiles.FilePart)obj;
		}
		else
		{
			utiles._FilePartStub stub;
			stub = new utiles._FilePartStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
