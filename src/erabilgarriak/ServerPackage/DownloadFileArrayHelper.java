package erabilgarriak.ServerPackage;

/**
 * Generated from IDL alias "DownloadFileArray".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public final class DownloadFileArrayHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, erabilgarriak.DownloadFile[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static erabilgarriak.DownloadFile[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(erabilgarriak.ServerPackage.DownloadFileArrayHelper.id(), "DownloadFileArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_interface_tc("IDL:erabilgarriak/DownloadFile:1.0", "DownloadFile")));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:erabilgarriak/Server/DownloadFileArray:1.0";
	}
	public static erabilgarriak.DownloadFile[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		erabilgarriak.DownloadFile[] _result;
		int _l_result2 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result2 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result2);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new erabilgarriak.DownloadFile[_l_result2];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=erabilgarriak.DownloadFileHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, erabilgarriak.DownloadFile[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			erabilgarriak.DownloadFileHelper.write(_out,_s[i]);
		}

	}
}
