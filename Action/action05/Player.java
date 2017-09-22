import java.awt.Graphics;
import java.awt.Point;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Player {
  // 幅
  public static final int WIDTH = 32;
  // 高さ
  public static final int HEIGHT = 32;
  // スピード
  private static final int SPEED = 6;
  // ジャンプ力
  private static final int JUMP_SPEED = 24;

  // 方向
  private static final int RIGHT = 0;
  private static final int LEFT = 1;

  // 位置
  private double x;
  private double y;

  // 速度
  private double vx;
  private double vy;

  // 着地しているか
  private boolean onGround;

  // 向いている方向
  private int dir;
  // アニメーション用カウンタ
  private int count;

  // プレイヤー画像
  private Image image;

  // マップへの参照
  private Map map;

  public Player(double x, double y, Map map) {
    this.x = x;
    this.y = y;
    this.map = map;
    vx = 0;
    vy = 0;
    onGround = false;
    dir = RIGHT;
    count = 0;

    // イメージをロード
    loadImage();

    // アニメーション用スレッドを開始
    AnimationThread thread = new AnimationThread();
    thread.start();
  }

  // 停止
  public void stop() {
    vx = 0;
  }

  // 左に加速
  public void accelerateLeft() {
    vx = -SPEED;
    dir = LEFT;
  }

  // 右に加速
  public void accelerateRight() {
    vx = SPEED;
    dir = RIGHT;
  }

  // ジャンプ
  public void jump() {
    if (onGround) {
      // 上向きに速度を加える
      vy = -JUMP_SPEED;
      onGround = false;
    }
  }

  // プレイヤーの状態を更新
  public void update() {
    // 重力で下向きに加速度がかかる
    vy += Map.GRAVITY;

    // x方向の当たり判定
    // 移動先座標を求める
    double newX = x + vx;
    // 移動先座標で衝突するタイルの位置を取得
    // x方向だけで考え、y座標は変化しないと仮定
    Point tile = map.getTileCollision(this, newX, y);
    if (tile == null) {
      x = newX;
    } else {
      // 衝突するタイルがある場合
      if (vx > 0) {
        // 右へ移動中なので右のブロックと衝突
        // ブロックにめり込む or 隙間が無いように位置調整
        x = Map.tilesToPixels(tile.x) - WIDTH;
      } else if (vx < 0) {
        // 左へ移動中なので左のブロックと衝突
        // 位置調整
        x = Map.tilesToPixels(tile.x + 1);
      }
      vx = 0;
    }
    // y方向のあたり判定
    // 移動先座標を決める
    double newY = y + vy;
    // 移動先座標で衝突するタイルの位置を取得
    // y方向だけ考えるのでx座標は変化しないと仮定
    tile = map.getTileCollision(this, x, newY);
    if (tile == null) {
      // 衝突するタイルがなければ移動
      y = newY;
      // 衝突してないということは空中
      onGround = false;
    } else {
      // 衝突するタイルがある場合
      if (vy > 0) {
        // 下へ移動中なので下のブロックと衝突(着地)
        // 位置調整
        y = Map.tilesToPixels(tile.y) - HEIGHT;
        // 着地したのでy方向速度を0に
        vy = 0;
        // 着地
        onGround = true;
      } else if (vy < 0) {
        // 上へ移動中なので上のブロックと衝突
        // 位置調整
        y = Map.tilesToPixels(tile.y + 1);
        // 天井にぶつかったのでy方向速度を0に
        vy = 0;
      }
    }
  }

  // プレイヤーを描画
  // X方向のオフセットとY方向のオフセットを引数とする
  public void draw(Graphics g, int offsetX, int offsetY) {
    g.drawImage(image, (int)x + offsetX, (int)y + offsetY,
                (int)x + offsetX + WIDTH, (int)y + offsetY + HEIGHT,
                count * WIDTH, dir * HEIGHT,
                count * WIDTH + WIDTH, dir * HEIGHT + HEIGHT,
                null);
  }

  // xの値を取得
  public double getX() {
    return x;
  }

  // yの値を取得
  public double getY() {
    return y;
  }

  // イメージをロード
  private void loadImage() {
    ImageIcon icon = new ImageIcon(getClass().getResource(
      "image/player.gif"));
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

        // 300ミリ秒休止 = 300ミリ秒おきにプレイヤーの絵を切り替える
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
