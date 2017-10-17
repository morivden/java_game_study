import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class Sprite {
  // 位置
  protected double x;
  protected double y;
  // 幅
  protected int width;
  // 高さ
  protected int height;
  // スプライト画像
  protected Image image;
  // アニメーションカウンタ
  protected int count;
  // マップへの参照
  protected Map map;

  public Sprite(double x, double y, String filename, Map map) {
    this.x = x;
    this.y = y;
    this.map = map;

    width = 32;
    height = 32;

    // イメージをロード
    loadImage(filename);

    count = 0;

    // アニメーション用スレッドを開始
    AnimationThread thread = new AnimationThread();
    thread.start();
  }

  // スプライトの状態を更新する
  public abstract void update();

  // スプライトを描画
  public void draw(Graphics g, int offsetX, int offsetY) {
    g.drawImage(image, (int)x + offsetX, (int)y + offsetY,
      (int)x + offsetX + width, (int)y + offsetY + height,
      count * width, 0,
      count * width + width, height, null);
  }

  // 他のスプライトと接触しているか
  public boolean isCollision(Sprite sprite) {
    Rectangle playerRect = new Rectangle((int)x, (int)y, width, height);
    Rectangle spriteRect = new Rectangle((int)sprite.getX(), (int)sprite.getY(), sprite.getWidth(), sprite.getHeight());
    // 自分の矩形と相手の矩形が重なっているか調べる
    if (playerRect.intersects(spriteRect)) {
      return true;
    }
    return false;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  // イメージをロードする
  private void loadImage(String filename) {
    ImageIcon icon = new ImageIcon(getClass().getResource("image/" + filename));
    image = icon.getImage();
  }

  // アニメーション用スレッド
  private class AnimationThread extends Thread {
    public void run() {
      while (true) {
        // countを切り替える
        if (count == 0) {
          count = 1;
        } else if (count == 1) {
          count = 0;
        }

        // 300ミリ秒休止
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
