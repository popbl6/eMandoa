package bezero.graf;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;


public class TableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	ArrayList<DeskargaData> data;
	
	public TableModel(ArrayList<DeskargaData> data){
		this.data = data;
	}
	
	public TableModel() {
		data = new ArrayList<DeskargaData>();
	}

	public void add(DeskargaData data){
		this.data.add(data);
	}

	public Class<?> getColumnClass(int columnIndex) {//Funtzio honetan zutabe bakoitzeko klasea identifikatu behar da
		if(columnIndex == 0)
			return String.class;
		if(columnIndex == 1)
			return JProgressBar.class;
		if(columnIndex == 2)
			return JLabel.class;
		return String.class;//Zutabe zenbakia ez bada aurkitzen String bezala hartu
	}
	
    public int getColumnCount() { return 3; }//Zutabe kopurua
    
    public String getColumnName(int columnIndex) {//Zutabeen izenak, gero goian jartzeko
    	if(columnIndex == 0)
    		return "Izena";
    	if(columnIndex == 1)
    		return "Progresoa";
    	if(columnIndex == 2)
    		return "Tamaina";
    	return "null";
    }
    
    public int getRowCount() { return (data == null) ? 0 : data.size(); }//Lerro kopurua bueltatu
    																	 //data null bada 0 bueltatu, bestela datu kopurua
    /**
     * Zelda bakoitzeko balioa bueltatu
     * data.get(rowIndex) egitean lerro hortako datuak hartzen dira eta gero zutabe zenbakiaren arabera
     * elementu ezberdinak bueltatzen dira
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
    	if(data == null)
    		return null;
    	else{
    		if(columnIndex == 0)
    			return data.get(rowIndex).izena;
    		if(columnIndex == 1)
    			return data.get(rowIndex).jpb;
    		if(columnIndex == 2)
    			return data.get(rowIndex).tamaina;
    		return null;
    	}
    }
    
    public boolean isCellEditable(int columnIndex, int rowIndex) { return false; }//Zeldak ezin dira editatu

}
