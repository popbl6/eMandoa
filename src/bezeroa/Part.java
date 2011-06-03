package bezeroa;

public class Part {
	private int numPart;
	private byte[] part;
	
	public Part(int numPart, byte[] part){
		this.numPart = numPart;
		this.part = part;
	}
	
	public int getNumPart(){return numPart;}
	public byte[] getPart(){return part;}

}
