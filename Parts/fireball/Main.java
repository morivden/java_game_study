import java.awt.Container;
import javax.swing.JFrame;

public class Main extends JFrame {
  public Main() {
    // タイトルを設定
    setTitle("ファイアボール");
    setResizable(false);

    // メインパネルを作成してフレームに追加
    MainPanel panel = new MainPanel();
    Container contentPane = getContentPane();
    contentPane.add(panel);

    // パネルサイズに合わせてフレームサイズを自動設定
    pack();
  }

  public static void main(String[] args) {
    Main frame = new Main();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
