package M5.M5A1;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayImages extends JPanel{
public static void main(String[] args){
    JFrame frame = new JFrame("Image Example");
    JPanel pane = new JPanel(new GridLayout(2, 2, 10, 10)); 
    pane.add(new JLabel(new ImageIcon("C:\\school\\M5\\M5A1\\images\\porsche1.jpg")));
    pane.add(new JLabel(new ImageIcon("C:\\school\\M5\\M5A1\\images\\porsche2.jpg")));
    pane.add(new JLabel(new ImageIcon("C:\\school\\M5\\M5A1\\images\\porsche3.jpg")));
    pane.add(new JLabel(new ImageIcon("C:\\school\\M5\\M5A1\\images\\porsche4.jpg")));
        frame.add(pane);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}