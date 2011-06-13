package bezeroa.graf;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class CellRenderer implements TableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
	        boolean isSelected, boolean hasFocus, int row, int column) {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add((JProgressBar)value, BorderLayout.CENTER);//Funtzio honi JProgressBar denean bakarrik deituko dio
		
		if(isSelected){
		      panel.setBackground(table.getSelectionBackground());
		}else{
		      panel.setBackground(table.getSelectionForeground());
		}
		return panel;
	}

}
