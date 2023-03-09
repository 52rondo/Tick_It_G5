package tw.com.tibame.main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JOptionTest {
	 	public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new JOptionTest().displayGUI();
	            }
	        });
	        
	        //永榮的 使用DIALOG 的
//	        JOptionPane optionPane = new JOptionPane("新增員工成功，請至員工列表確認",JOptionPane.WARNING_MESSAGE);
//			JDialog dialog = optionPane.createDialog("Warning");
//			dialog.setAlwaysOnTop(true); // to show top of all other application
//			dialog.setVisible(true); // to visible the dialog
	        
	    }
	
	    public void displayGUI() {
	        JOptionPane.showConfirmDialog(null, getPanel(),
	                        "Verification Status : ",
//	                        JOptionPane.OK_CANCEL_OPTION,
	                        JOptionPane.DEFAULT_OPTION,
	                        JOptionPane.PLAIN_MESSAGE);
	    }

	    protected JPanel getPanel() {
	        JPanel panel = new JPanel();
	        JLabel label = new JLabel("驗證成功，登入已啟用");
//	        ImageIcon image = null;
//	        try {
//	        	File pic = new File("/fireAlligagtor.png");
//	            image = new ImageIcon(ImageIO.read(pic));
////	            System.out.println(image);
//	        } catch(MalformedURLException mue) {
//	            mue.printStackTrace();
//	        } catch(IOException ioe) {
//	            ioe.printStackTrace();
//	        } 
//
//	        label.setIcon(image);
	        panel.add(label);

	        return panel;
	    }
	
}
