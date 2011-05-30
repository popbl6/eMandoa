package erabilgarriak;


/**
 * Generated from IDL interface "DownloadFile".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at 30-may-2011 18:05:40
 */

public abstract class DownloadFilePOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, erabilgarriak.DownloadFileOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getFileData", new java.lang.Integer(0));
		m_opsHash.put ( "getPart", new java.lang.Integer(1));
		m_opsHash.put ( "getPartCount", new java.lang.Integer(2));
	}
	private String[] ids = {"IDL:erabilgarriak/DownloadFile:1.0"};
	public erabilgarriak.DownloadFile _this()
	{
		return erabilgarriak.DownloadFileHelper.narrow(_this_object());
	}
	public erabilgarriak.DownloadFile _this(org.omg.CORBA.ORB orb)
	{
		return erabilgarriak.DownloadFileHelper.narrow(_this_object(orb));
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
			case 0: // getFileData
			{
				_out = handler.createReply();
				erabilgarriak.FileDataHelper.write(_out,getFileData());
				break;
			}
			case 1: // getPart
			{
				int _arg0=_input.read_long();
				erabilgarriak.DownloadFilePackage.PartHolder _arg1= new erabilgarriak.DownloadFilePackage.PartHolder();
				_arg1._read (_input);
				_out = handler.createReply();
				_out.write_long(getPart(_arg0,_arg1));
				erabilgarriak.DownloadFilePackage.PartHelper.write(_out,_arg1.value);
				break;
			}
			case 2: // getPartCount
			{
				_out = handler.createReply();
				_out.write_long(getPartCount());
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
