import java.awt.Color;
import java.awt.Graphics;

public class Ball {
  private static final int SIZE =  32;
  private int x, y;
  private int vx, vy;

  // コンストラクタ
  public Ball(int x, int y, int vx, int vy) {
    this.x = x;
    this.y = y;
    this.vx = vx;
    this.vy = vy;
  }

  public void move() {
    x += vx;
    y += vy;

    if (x < 0) {
      x = 0;
      vx = -vx;
    }

    if (x > MainPanel.WIDTH - SIZE) {
      x = MainPanel.WIDTH - SIZE;
      vx = -vx;
    }

    if (y < 0) {
      y = 0;
      vy = -vy;
    }

    if (y > MainPanel.HEIGHT - SIZE) {
      y = MainPanel.HEIGHT - SIZE;
      vy = -vy;
    }
  }

  public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.fillOval(x, y, SIZE, SIZE);
  }
}
