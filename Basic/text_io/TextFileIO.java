import javax.swing.JFrame;

public class TextFileIO extends JFrame {
  public TextFileIO() {
    setTitle("テキストファイル入出力");
    MainPanel panel = new MainPanel();
    getContentPane().add(panel);

    pack();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) {
    new TextFileIO();
  }
}
