import java.awt.Container;
import javax.swing.JFrame;

public class TransparentTest extends JFrame {
  public TransparentTest() {
    setTitle("半透明描画");
    MainPanel panel = new MainPanel();
    Container contentPane = getContentPane();
    contentPane.add(panel);

    pack();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) {
    new TransparentTest();
  }
}
