package erabilgarriak;


/**
 * Generated from IDL interface "DownloadFile".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 30-may-2011 18:05:40
 */

public final class DownloadFileHelper
{
	public static void insert (final org.omg.CORBA.Any any, final erabilgarriak.DownloadFile s)
	{
			any.insert_Object(s);
	}
	public static erabilgarriak.DownloadFile extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:erabilgarriak/DownloadFile:1.0", "DownloadFile");
	}
	public static String id()
	{
		return "IDL:erabilgarriak/DownloadFile:1.0";
	}
	public static DownloadFile read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(erabilgarriak._DownloadFileStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final erabilgarriak.DownloadFile s)
	{
		_out.write_Object(s);
	}
	public static erabilgarriak.DownloadFile narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof erabilgarriak.DownloadFile)
		{
			return (erabilgarriak.DownloadFile)obj;
		}
		else if (obj._is_a("IDL:erabilgarriak/DownloadFile:1.0"))
		{
			erabilgarriak._DownloadFileStub stub;
			stub = new erabilgarriak._DownloadFileStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static erabilgarriak.DownloadFile unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof erabilgarriak.DownloadFile)
		{
			return (erabilgarriak.DownloadFile)obj;
		}
		else
		{
			erabilgarriak._DownloadFileStub stub;
			stub = new erabilgarriak._DownloadFileStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
