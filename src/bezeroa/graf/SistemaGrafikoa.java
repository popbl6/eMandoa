package bezeroa.graf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Comparator;
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

import erabilgarriak.DownloadFile;
import erabilgarriak.DownloadFileHelper;
import erabilgarriak.FileData;
import erabilgarriak.ServerPackage.FileDataArrayHolder;

import bezeroa.Bezero;
import bezeroa.Bidaltzailea;
import bezeroa.Deskarga;
import bezeroa.Globalak;




public class SistemaGrafikoa extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JList fitxZerrenda;
	JTable table;
	private TableModel tableModel;
	private ArrayList<DownloadFile> bidaltzaileak=new ArrayList<DownloadFile>();
	
	/**
	 * Hari pare batera atera fitxategiak kargatzeko funtzioak, horrela ez da itxaron behar, join-a kontruktore bukaeran
	 * 
	 * @throws Exception
	 */
	public SistemaGrafikoa() throws Exception{
		Splash splash = new Splash();
		System.out.println("Kargatzen");
		this.setSize(800, 500);
		this.setTitle("eMandoa");
		this.setResizable(true);
		this.setLocation(100, 100);
		splash.getProgressBar().setValue(5);
		this.setContentPane(panelOrokorraSortu());
		Thread a = new Thread(){
			public void run(){
				
				try {
					while(true){
						//table.updateUI();
						
						table.setVisible(false);
						table.setVisible(true);
						
						sleep(20);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		};
		a.start();
		splash.getProgressBar().setValue(10);
		//%60a dago hemen
		Bezero.fileDatakSortu(splash.getProgressBar(), splash.getLabel());
		for (int i=0;Bezero.files.size()>i;i++){
		    try{
		    	FileData filedata=Bezero.files.get(i);
		    	Bidaltzailea bidal = new Bidaltzailea(filedata);
		    	DownloadFile df = DownloadFileHelper.narrow(Globalak.ORBGlobal.getRootPOA().servant_to_reference(bidal));
		    	bidaltzaileak.add(df);
		        Globalak.eMandoa.getServer().register(df);
		    }catch (Exception e){
		      e.printStackTrace();
		    }
		}
		splash.getProgressBar().setValue(70);
		//%25a dago hemen
		Bezero.datakIrakurri(splash.getProgressBar(), splash.getLabel());
		for(Deskarga d : Bezero.berrabiarazteko){
			JProgressBar bar = new JProgressBar();
			bar.setStringPainted(true);
			d.setProgressBar(bar);
			DeskargaData data = new DeskargaData(d.getFileData().name, bar, tamainaKalkulatu(d.getFileData().size));
			tableModel.add(data);
			d.start();
		}
		splash.getProgressBar().setValue(95);
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				for(DownloadFile df : bidaltzaileak)
					Globalak.eMandoa.getServer().deregister(df);
				System.exit(0);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		splash.getProgressBar().setValue(100);
		fitxZerrenda.setModel(fitxategiZerrendaModeloaSortu());
		this.setVisible(true);
		splash.setVisible(false);
		splash.dispose();
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
		
		tableModel = new TableModel();
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
        //table.setRowSorter(sorter);
        //table.setVisible(true);
        
        //table.setMinimumSize(new Dimension((int)(this.getWidth()*0.5), 350));
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		scrollPane.setMinimumSize(new Dimension((int)(this.getWidth()*0.5), 350));
		scrollPane.setBackground(Color.white);
		
		panel.add(lDeskargak,BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		return panel;
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
		panel.add(new JScrollPane(fitxZerrenda), BorderLayout.CENTER);
		panel.add(bPanel,BorderLayout.SOUTH);
		return panel;
	}
	
	public DefaultListModel fitxategiZerrendaModeloaSortu(){
		FileData[] buff = new FileData[1];
		buff[0] = new FileData();
		FileDataArrayHolder holder = new FileDataArrayHolder(buff);
		Globalak.eMandoa.getServer().getLista(holder);
		
		DefaultListModel modelo= new DefaultListModel();
		for(FileData data : holder.value){
			modelo.addElement(data);
		}
		return modelo;
	}
	
	
	public static void main(String[] args) throws Exception{
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
			Deskarga d;
			try {
				d = new Deskarga((FileData) fitxZerrenda.getSelectedValue());
				JProgressBar bar = new JProgressBar();
				bar.setStringPainted(true);
				d.setProgressBar(bar);
				DeskargaData data = new DeskargaData(d.getFileData().name, bar, tamainaKalkulatu(d.getFileData().size));
				tableModel.add(data);
				d.start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
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
