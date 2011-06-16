package bezeroa.graf;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;


public class Splash extends JWindow{
	private static final long serialVersionUID = 1L;
	JProgressBar pb;
	JLabel label;
	
	public Splash(){
		super();
        JLabel l = new JLabel(new ImageIcon("eMandoaLogo.png"));
        pb =  new JProgressBar(0, 100);
        pb.setStringPainted(true);
        label = new JLabel("Hasieratzen...");
        JPanel panel = new JPanel(new GridLayout(2, 1));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(pb);
        panel.add(label);
        getContentPane().add(l, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.SOUTH);
        pack();
        Dimension screenSize =
          Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                    screenSize.height/2 - (labelSize.height/2));
        setVisible(true);
	}

    public Splash(String filename){
        super();
        JLabel l = new JLabel(new ImageIcon(filename));
        pb =  new JProgressBar(0, 100);
        pb.setStringPainted(true);
        getContentPane().add(l, BorderLayout.CENTER);
        getContentPane().add(pb, BorderLayout.SOUTH);
        pack();
        Dimension screenSize =
          Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                    screenSize.height/2 - (labelSize.height/2));
        setVisible(true);
    }
    
    public JProgressBar getProgressBar(){
    	return pb;
    }
    
    public JLabel getLabel(){
    	return label;
    }
}
