import javax.swing.JFrame;

public class FPSTest extends JFrame {
  public FPSTest() {
    setTitle("FPSの計算");
    MainPanel panel = new MainPanel();
    getContentPane().add(panel);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) {
    new FPSTest();
  }
}
