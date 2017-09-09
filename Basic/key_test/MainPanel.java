import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPanel extends JPanel implements KeyListener {
  // パネルサイズ
  public static final int WIDTH = 240;
  public static final int HEIGHT = 240;

  // 方向を表す定数
  private static final int UP = 0;
  private static final int DOWN = 1;
  private static final int LEFT = 2;
  private static final int RIGHT = 3;

  // ボールオブジェクト
  private Ball ball;

  public MainPanel() {
    // パネルの推奨サイズを設定、pack()するときに必要
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setSize(WIDTH, HEIGHT);
    // ボールを作成
    ball = new Ball(WIDTH / 2, HEIGHT / 2, 10, 10);
    // パネルがキーボードを受け付けるようにする(必須)
    this.setFocusable(true);
    // KeyListenerを登録
    addKeyListener(this);
  }

  // 描画処理
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    ball.draw(g);
  }

  // キーがタイプされた時に呼び出す
  public void keyTyped(KeyEvent e) {

  }

  // キーが押された時に呼び出す(ゲームではよくこちらが用いられる)
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    switch (key) {
      case KeyEvent.VK_UP:
        // 上キーが押されたらボールを上に移動
        ball.move(UP);
        break;
      case KeyEvent.VK_DOWN:
        // 下キーが押されたらボールを下に移動
        ball.move(DOWN);
        break;
      case KeyEvent.VK_LEFT:
        // 左キーが押されたらボールを左に移動
        ball.move(LEFT);
        break;
      case KeyEvent.VK_RIGHT:
        // 右キーが押されたらボールを右に移動
        ball.move(RIGHT);
        break;
      case KeyEvent.VK_X:
        // Xキーが押されたらボールの色を変える
        ball.changeColor();
        break;
    }
    // ボールを移動したので再描画
    repaint();
  }

  // キーが離された時に呼び出す
  public void keyReleased(KeyEvent e) {

  }
}
