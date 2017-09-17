import java.awt.Color;
import java.awt.Graphics;

public class Player {
  // 幅
  public static final int WIDTH = 32;
  // 高さ
  public static final int HEIGHT = 32;
  // スピード
  public static final int SPEED = 6;

  // 位置
  private double x;
  private double y;

  // 速度
  private double vx;
  private double vy;

  public Player(double x, double y) {
    this.x = x;
    this.y = y;
    vx = 0;
    vy = 0;
  }

  // 停止
  public void stop() {
    vx = 0;
  }

  // 左に加速
  public void accelerateLeft() {
    vx = -SPEED;
  }

  // 右に加速
  public void accelerateRight() {
    vx = SPEED;
  }

  // プレイヤーの状態を更新
  public void update() {
    x += vx;
    y += vy;
  }

  // プレイヤーを描画
  public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
  }
}
