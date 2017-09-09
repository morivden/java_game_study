import java.awt.*;
import javax.swing.*;

public class KeyTest extends JFrame {
  public KeyTest() {
    // タイトルを設定
    setTitle("キーボードテスト");

    // メインパネルを作成してフレームに追加
    MainPanel panel = new MainPanel();
    Container contentPane = getContentPane();
    contentPane.add(panel);

    // パネルサイズに合わせてフレームサイズを自動設定
    pack();
  }

  public static void main(String[] args) {
    KeyTest frame = new KeyTest();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
