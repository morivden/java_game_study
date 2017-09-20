import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable, KeyListener {
  // パネルサイズ
  public static final int WIDTH = 640;
  public static final int HEIGHT = 480;

  // マップ
  private Map map;

  // プレイヤー
  private Player player;

  // キーの状態(押されているか、押されていないか)
  private boolean leftPressed;
  private boolean rightPressed;
  private boolean upPressed;

  // ゲーム用ループスレッド
  private Thread gameLoop;

  public MainPanel() {
    // パネルの推奨サイズを設定、pack()するときに必要
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    // パネルがキー入力を受け付けるようにする
    setFocusable(true);

    // マップを作成
    map = new Map();

    // プレイヤーを作成
    player = new Player(192, 32, map);

    // キーイベントリスナーを登録
    addKeyListener(this);

    // ゲームループ開始
    gameLoop = new Thread(this);
    gameLoop.start();
  }

  // ゲームループ
  public void run() {
    while (true) {
      if (leftPressed) {
        // 左キーが押されていれば左向きに加速
        player.accelerateLeft();
      } else if (rightPressed) {
        // 右キーが押されていれば右向きに加速
        player.accelerateRight();
      } else {
        // 何もされていない時は停止
        player.stop();
      }

      if (upPressed) {
        // ジャンプ
        player.jump();
      }

      // プレイヤーの状態を更新
      player.update();

      // 再描画
      repaint();

      // 休止
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  // 描画処理
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // 背景を黒で塗りつぶす
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());

    // マップを描画
    map.draw(g);

    // プレイヤーを描画
    player.draw(g);
  }

  // キーが押されたらキーの状態を「押された」に変える
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();

    if (key == KeyEvent.VK_LEFT) {
      leftPressed = true;
    }
    if (key == KeyEvent.VK_RIGHT) {
      rightPressed = true;
    }
    if (key == KeyEvent.VK_UP) {
      upPressed = true;
    }
  }

  // キーが離されたらキーの状態を「離された」に変える
  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();

    if (key == KeyEvent.VK_LEFT) {
      leftPressed = false;
    }
    if (key == KeyEvent.VK_RIGHT) {
      rightPressed = false;
    }
    if (key == KeyEvent.VK_UP) {
      upPressed = false;
    }
  }

  public void keyTyped(KeyEvent e) {

  }
}
