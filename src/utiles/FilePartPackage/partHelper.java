package utiles.FilePartPackage;

/**
 * Generated from IDL alias "part".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 25-may-2011 12:07:19
 */

public final class partHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, byte[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static byte[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(utiles.FilePartPackage.partHelper.id(), "part",org.omg.CORBA.ORB.init().create_sequence_tc(1024, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(10))));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:utiles/FilePart/part:1.0";
	}
	public static byte[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		byte[] _result;
		int _l_result0 = _in.read_long();
		if (_l_result0 > 1024)
			throw new org.omg.CORBA.MARSHAL("Sequence length incorrect!");
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result0 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new byte[_l_result0];
		_in.read_octet_array(_result,0,_l_result0);
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, byte[] _s)
	{
				if (_s.length > 1024)
			throw new org.omg.CORBA.MARSHAL("Incorrect sequence length");
		_out.write_long(_s.length);
		_out.write_octet_array(_s,0,_s.length);
	}
}
