package erabilgarriak;


/**
 * Generated from IDL interface "Server".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 30-may-2011 18:05:40
 */

public abstract class ServerPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, erabilgarriak.ServerOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "register", new java.lang.Integer(0));
		m_opsHash.put ( "getFile", new java.lang.Integer(1));
		m_opsHash.put ( "deregister", new java.lang.Integer(2));
		m_opsHash.put ( "getLista", new java.lang.Integer(3));
	}
	private String[] ids = {"IDL:erabilgarriak/Server:1.0"};
	public erabilgarriak.Server _this()
	{
		return erabilgarriak.ServerHelper.narrow(_this_object());
	}
	public erabilgarriak.Server _this(org.omg.CORBA.ORB orb)
	{
		return erabilgarriak.ServerHelper.narrow(_this_object(orb));
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // register
			{
				erabilgarriak.DownloadFile _arg0=erabilgarriak.DownloadFileHelper.read(_input);
				_out = handler.createReply();
				_out.write_boolean(register(_arg0));
				break;
			}
			case 1: // getFile
			{
				erabilgarriak.FileData _arg0=erabilgarriak.FileDataHelper.read(_input);
				erabilgarriak.ServerPackage.DownloadFileArrayHolder _arg1= new erabilgarriak.ServerPackage.DownloadFileArrayHolder();
				_arg1._read (_input);
				_out = handler.createReply();
				_out.write_boolean(getFile(_arg0,_arg1));
				erabilgarriak.ServerPackage.DownloadFileArrayHelper.write(_out,_arg1.value);
				break;
			}
			case 2: // deregister
			{
				erabilgarriak.DownloadFile _arg0=erabilgarriak.DownloadFileHelper.read(_input);
				_out = handler.createReply();
				_out.write_boolean(deregister(_arg0));
				break;
			}
			case 3: // getLista
			{
				erabilgarriak.ServerPackage.FileDataArrayHolder _arg0= new erabilgarriak.ServerPackage.FileDataArrayHolder();
				_arg0._read (_input);
				_out = handler.createReply();
				_out.write_boolean(getLista(_arg0));
				erabilgarriak.ServerPackage.FileDataArrayHelper.write(_out,_arg0.value);
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
