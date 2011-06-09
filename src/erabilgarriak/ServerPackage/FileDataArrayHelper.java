package erabilgarriak.ServerPackage;

/**
 * Generated from IDL alias "FileDataArray".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public final class FileDataArrayHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, erabilgarriak.FileData[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static erabilgarriak.FileData[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(erabilgarriak.ServerPackage.FileDataArrayHelper.id(), "FileDataArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, erabilgarriak.FileDataHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:erabilgarriak/Server/FileDataArray:1.0";
	}
	public static erabilgarriak.FileData[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		erabilgarriak.FileData[] _result;
		int _l_result1 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result1 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result1);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new erabilgarriak.FileData[_l_result1];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=erabilgarriak.FileDataHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, erabilgarriak.FileData[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			erabilgarriak.FileDataHelper.write(_out,_s[i]);
		}

	}
}
