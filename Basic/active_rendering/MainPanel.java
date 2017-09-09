import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable {
  public static final int WIDTH = 640;
  public static final int HEIGHT = 480;

  private Ball ball;

  // ダブルバッファリング(db)用
  private Graphics dbg;
  private Image dbImage = null;

  // ゲームループ
  private Thread gameLoop;

  public MainPanel() {
    setPreferredSize(new Dimension(WIDTH, HEIGHT));

    ball = new Ball(320, 240, 9, 7);

    // ゲームループ開始
    gameLoop = new Thread(this);
    gameLoop.start();
  }

  // ゲームループ
  public void run() {
    while (true) {
      // ゲーム状態を更新
      gameUpdate();
      // バッファにレンダリング
      gameRender();
      // バッファを画面に描画
      paintScreen();
      // Active Renderingではrepaint()を用いない

      // 休止
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  // ゲーム状態を更新
  private void gameUpdate() {
    // ボールの移動
    ball.move();
  }

  // バッファにレンダリング
  private void gameRender() {
    // 初回の呼び出し時にダブルバッファリング用オブジェクトを作成
    if (dbImage == null) {
      dbImage = createImage(WIDTH, HEIGHT);
      if (dbImage == null) {
        System.out.println("dbImage is null");
        return;
      } else {
        // バッファイメージの描画オブジェクト
        dbg = dbImage.getGraphics();
      }
    }

    // バッファをクリアする
    dbg.setColor(Color.WHITE);
    dbg.fillRect(0, 0, WIDTH, HEIGHT);

    // ボールをバッファへ描画する
    ball.draw(dbg);
  }

  // バッファを画面に描画
  private void paintScreen() {
    try {
      // グラフィックオブジェクトを取得
      Graphics g = getGraphics();
      if (g != null && dbImage != null) {
        // バッファイメージを画面に描画
        g.drawImage(dbImage, 0, 0, null);
      }
      Toolkit.getDefaultToolkit().sync();
      if (g != null) {
        // グラフィックオブジェクトを破棄
        g.dispose();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
