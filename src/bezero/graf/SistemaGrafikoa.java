package bezero.graf;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.TableRowSorter;

import erabilgarriak.DownloadFileHelper;
import erabilgarriak.FileData;
import erabilgarriak.ServerPackage.FileDataArrayHolder;

import bezeroa.Bezero;
import bezeroa.Bidaltzailea;
import bezeroa.Deskarga;
import bezeroa.Globalak;




public class SistemaGrafikoa extends JFrame implements ActionListener{
	
	JList fitxZerrenda;
	JTable table;
	
	public SistemaGrafikoa(){
		this.setSize(800, 500);
		this.setResizable(true);
		this.setLocation(100, 100);
		this.setContentPane(panelOrokorraSortu());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private Container panelOrokorraSortu() {
		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, deskargaZerrenda(), fitxategiZerrenda());
		panel.setDividerLocation(540);
		return panel;
	}

	private Component deskargaZerrenda() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel lDeskargak = new JLabel("Deskargak");
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		TableModel tableModel = new TableModel();
		for(Deskarga d : Bezero.berrabiarazteko){
			JProgressBar bar = new JProgressBar();
			d.setProgressBar(bar);
			DeskargaData data = new DeskargaData(d.getFileData().name, bar, tamainaKalkulatu(d.getFileData().size));
			tableModel.add(data);
		}
		table = new JTable(tableModel);//TableModel nik egindako klasea da
		table.setDefaultRenderer(JProgressBar.class, new CellRenderer());//Honek zutabeko klasea JProgress bar motakoa bada 
																		 //CellRenderer(nik egindakoa) klasea erabiltzekoesaten dio
		//table.setAutoCreateRowSorter(true);//JProgressBar-arendako ez dau sortzen
		
		table.getColumnModel().getColumn(1).setPreferredWidth((int) (this.getWidth()*0.3));
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
        sorter.setComparator(1, new Comparator<JProgressBar>(){

                @Override
                public int compare(final JProgressBar o1, final JProgressBar o2) {
                        return o1.getValue()-o2.getValue();
                }
                
        });
        sorter.setComparator(2, new Comparator<String>(){
        	@Override
        	public int compare(String s1, String s2) {
            	String[] s1a = s1.split(" ");
            	String[] s2a = s2.split(" ");
            	if(s1a[1].equals(s2a[1]))
                	return Double.parseDouble(s1a[0])<Double.parseDouble(s2a[0])?(int) Math.floor(Double.parseDouble(s1a[0])-Double.parseDouble(s2a[0])):(int) Math.ceil(Double.parseDouble(s1a[0])-Double.parseDouble(s2a[0]));
                else{
                	if(s1a[1].equals("B"))
                    	return -1;
                	if(s1a[1].equals("KB") && !s2a[1].equals("B"))
                        return -1;
                    if(s1a[1].equals("MB") && s2a[1].equals("GB"))
                        return -1;
                    return 1;
                }
            }
        });
        table.setRowSorter(sorter);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		panel.add(lDeskargak,BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		return panel;
	}
	
	
	//TODO Bukatzerakoan datakSortu funtzioa ezabatu
	private ArrayList<DeskargaData> datakSortu() {
		ArrayList<DeskargaData> data = new ArrayList<DeskargaData>();
		for(int i=0; i<30; i++){
			DeskargaData buff = new DeskargaData("Deskarga"+i, new JProgressBar(), ((long)((1000*Math.random())*100)/100.0)+"MB");
			buff.jpb.setMaximum(100);
			buff.jpb.setValue((int)(100*Math.random()));
			buff.jpb.setStringPainted(true);
			data.add(buff);
		}
		return data;
	}

	private Component fitxategiZerrenda() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel bPanel = new JPanel(new GridLayout(1,2));
		JButton deskargatu = new JButton("Deskargatu"),
				aktualizatu = new JButton("Aktualizatu");
		JLabel lFitxategi = new JLabel("Partekatutako Fitxategiak");
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		deskargatu.setActionCommand("deskargatu");
		deskargatu.addActionListener(this);
		aktualizatu.setActionCommand("aktualizatu");
		aktualizatu.addActionListener(this);
		
		bPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		bPanel.add(deskargatu);
		bPanel.add(aktualizatu);
		
		fitxZerrenda = new JList();
		
		
		//TODO Zerbitzarian dauden fitxategien zerrenda modeloa sortu eta zerrendari erantzi
		
		panel.add(lFitxategi,BorderLayout.NORTH);
		panel.add(fitxZerrenda, BorderLayout.CENTER);
		panel.add(bPanel,BorderLayout.SOUTH);
		return panel;
	}
	
	public DefaultListModel fitxategiZerrendaModeloaSortu(){
		FileData[] buff = new FileData[1];
		FileDataArrayHolder holder = new FileDataArrayHolder(buff);
		Globalak.eMandoa.getServer().getLista(holder);
		
		DefaultListModel modelo= new DefaultListModel();
		for(FileData data : holder.value){
			modelo.addElement(data.toString());
		}
		return modelo;
	}
	
	
	public static void main(String[] args) throws Exception{
		Bezero.fileDatakSortu();
		for (int i=0;Bezero.files.size()>i;i++){
		    try{
		    	FileData filedata=Bezero.files.get(i);
		    	Bidaltzailea bidal = new Bidaltzailea(filedata);
		        Globalak.eMandoa.getServer().register(DownloadFileHelper.narrow(Globalak.ORBGlobal.getRootPOA().servant_to_reference(bidal)));
		    }catch (Exception e){
		      e.printStackTrace();
		    }
		}
		Bezero.datakIrakurri();
		
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e1) {
			} 
		}
		new SistemaGrafikoa();
	}

	

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("deskargatu")){
			//TODO TableModel.add();
			
		}
		if(e.getActionCommand().equals("aktualizatu")){
			fitxZerrenda.setModel(fitxategiZerrendaModeloaSortu());
			
		}
		
	}
	
	private String tamainaKalkulatu(long l) {
        String unitatea = " B";
        Double buff = (double) l;
        if(buff < 1024)
            return buff+unitatea;
        else{
            unitatea = " KB";
            buff = buff/1024;
            if(buff < 1024)
                return truncate(buff) + unitatea;
            else{
                unitatea = " MB";
                buff = buff/1024;
                if(buff < 1024)
                    return truncate(buff) + unitatea;
                else{
                    unitatea = " GB";
                    buff = buff /1024;
                    return truncate(buff) + unitatea;
                }
            }
        }
    }
   
    public double truncate(Double d){
        return ((long)(d*100))/100.0;
    }

}
