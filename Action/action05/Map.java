import java.awt.Graphics;
import java.awt.Point;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Map {
  // タイルサイズ
  public static final int TILE_SIZE = 32;
  // 行数
  public static final int ROW = 20;
  // 列数
  public static final int COL = 30;
  // 幅
  public static final int WIDTH = TILE_SIZE * COL;
  // 高さ
  public static final int HEIGHT = TILE_SIZE * ROW;
  // 重力
  public static final double GRAVITY = 0.6;
  // マップ
  private int[][] map = {
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
    {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
  };

  // ブロックの画像
  private Image blockImage;

  public Map() {
    // イメージをロードする
    loadImage();
  }

  // マップを描画
  // X方向のオフセットとY方向のオフセットを引数とする
  public void draw(Graphics g, int offsetX, int offsetY) {
    // オフセットをもとに描画範囲を決める
    int firstTileX = pixelsToTiles(-offsetX);
    int lastTileX = firstTileX + pixelsToTiles(MainPanel.WIDTH) + 1;
    // 描画範囲がマップの大きさより大きくならないように調整
    lastTileX = Math.min(lastTileX, COL);

    int firstTileY = pixelsToTiles(-offsetY);
    int lastTileY = firstTileY + pixelsToTiles(MainPanel.HEIGHT) + 1;
    // 描画範囲がマップの大きさより大きくならないように調整
    lastTileY = Math.min(lastTileY, ROW);

    for (int i = firstTileY; i < lastTileY; i++) {
      for (int j = firstTileX; j < lastTileX; j++) {
        // mapの値に応じて画像を描画
        switch (map[i][j]) {
          case 1:  // ブロック
            g.drawImage(blockImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
            break;
        }
      }
    }
  }

  // 衝突するブロックの座標
  public Point getTileCollision(Player player, double newX, double newY) {
    // 小数点以下切り上げ
    // 浮動小数点の関係で切り上げしないと衝突していないと判定される場合がある
    newX = Math.ceil(newX);
    newY = Math.ceil(newY);

    double fromX = Math.min(player.getX(), newX);
    double fromY = Math.min(player.getY(), newY);
    double toX = Math.max(player.getX(), newX);
    double toY = Math.max(player.getY(), newY);

    int fromTileX = pixelsToTiles(fromX);
    int fromTileY = pixelsToTiles(fromY);
    int toTileX = pixelsToTiles(toX + Player.WIDTH - 1);
    int toTileY = pixelsToTiles(toY + Player.HEIGHT - 1);

    // 衝突してるか調べる
    for (int x = fromTileX; x <= toTileX; x++) {
      for (int y = fromTileY; y <= toTileY; y++) {
        // 画面外は衝突
        if (x < 0 || x >= COL) {
          return new Point(x, y);
        }
        if (y < 0 || y >= ROW) {
          return new Point(x, y);
        }
        // ブロックがあったら衝突
        if (map[y][x] == 1) {
          return new Point(x, y);
        }
      }
    }
    return null;
  }

  // ピクセル単位をタイル単位に変換
  public static int pixelsToTiles(double pixels) {
    return (int)Math.floor(pixels / TILE_SIZE);
  }

  // タイル単位をピクセル単位に変換
  public static int tilesToPixels(int tiles) {
    return tiles * TILE_SIZE;
  }

  // イメージをロード
  private void loadImage() {
    ImageIcon icon = new ImageIcon(getClass().getResource("image/block.gif"));
    blockImage = icon.getImage();
  }
}
