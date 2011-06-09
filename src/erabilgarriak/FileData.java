package erabilgarriak;

/**
 * Generated from IDL struct "FileData".
 *
 * @author JacORB IDL compiler V 2.3.1, 27-May-2009
 * @version generated at Jun 9, 2011 3:02:02 PM
 */

public final class FileData
	implements org.omg.CORBA.portable.IDLEntity
{
	public FileData(){}
	public java.lang.String name = "";
	public long size;
	public java.lang.String hash = "";
	public FileData(java.lang.String name, long size, java.lang.String hash)
	{
		this.name = name;
		this.size = size;
		this.hash = hash;
	}
	
	public boolean equals(Object o){
		FileData fd = (FileData) o;
		return this.name.equals(fd.name) && this.size == fd.size && this.hash.equals(fd.hash);
	}
}
