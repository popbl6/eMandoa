package erabilgarriak;


/**
 * Generated from IDL struct "FileData".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public final class FileDataHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(erabilgarriak.FileDataHelper.id(),"FileData",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("size", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(23)), null),new org.omg.CORBA.StructMember("hash", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final erabilgarriak.FileData s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static erabilgarriak.FileData extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:erabilgarriak/FileData:1.0";
	}
	public static erabilgarriak.FileData read (final org.omg.CORBA.portable.InputStream in)
	{
		erabilgarriak.FileData result = new erabilgarriak.FileData();
		result.name=in.read_string();
		result.size=in.read_longlong();
		result.hash=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final erabilgarriak.FileData s)
	{
		out.write_string(s.name);
		out.write_longlong(s.size);
		out.write_string(s.hash);
	}
}
