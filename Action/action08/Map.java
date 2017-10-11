import java.awt.Graphics;
import java.awt.Point;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import javax.swing.ImageIcon;

public class Map {
  // タイルサイズ
  public static final int TILE_SIZE = 32;
  // 重力
  public static final double GRAVITY = 0.6;
  // マップ
  private char[][] map;
  // 行数
  private int row;
  // 列数
  private int col;
  // 幅
  private int width;
  // 高さ
  private int height;

  // ブロックの画像
  private Image blockImage;
  // スプライトリスト
  private LinkedList sprites;

  public Map(String filename) {
    sprites = new LinkedList();
    // マップをロードする
    load(filename);

    width = TILE_SIZE * col;
    height = TILE_SIZE * row;

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
    lastTileX = Math.min(lastTileX, col);

    int firstTileY = pixelsToTiles(-offsetY);
    int lastTileY = firstTileY + pixelsToTiles(MainPanel.HEIGHT) + 1;
    // 描画範囲がマップの大きさより大きくならないように調整
    lastTileY = Math.min(lastTileY, row);

    for (int i = firstTileY; i < lastTileY; i++) {
      for (int j = firstTileX; j < lastTileX; j++) {
        // mapの値に応じて画像を描画
        switch (map[i][j]) {
          case 'B':  // ブロック
            g.drawImage(blockImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
            break;
        }
      }
    }
  }

  // 衝突するブロックの座標
  public Point getTileCollision(Sprite sprite, double newX, double newY) {
    // 小数点以下切り上げ
    // 浮動小数点の関係で切り上げしないと衝突していないと判定される場合がある
    newX = Math.ceil(newX);
    newY = Math.ceil(newY);

    double fromX = Math.min(sprite.getX(), newX);
    double fromY = Math.min(sprite.getY(), newY);
    double toX = Math.max(sprite.getX(), newX);
    double toY = Math.max(sprite.getY(), newY);

    int fromTileX = pixelsToTiles(fromX);
    int fromTileY = pixelsToTiles(fromY);
    int toTileX = pixelsToTiles(toX + sprite.getWidth() - 1);
    int toTileY = pixelsToTiles(toY + sprite.getHeight() - 1);

    // 衝突してるか調べる
    for (int x = fromTileX; x <= toTileX; x++) {
      for (int y = fromTileY; y <= toTileY; y++) {
        // 画面外は衝突
        if (x < 0 || x >= col) {
          return new Point(x, y);
        }
        if (y < 0 || y >= row) {
          return new Point(x, y);
        }
        // ブロックがあったら衝突
        if (map[y][x] == 'B') {
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

  // マップをロード
  private void load(String filename) {
    try {
      // ファイルを開く
      BufferedReader br = new BufferedReader(new InputStreamReader(
        getClass().getResourceAsStream("map/" + filename)));

      // 行数を読み込む
      String line = br.readLine();
      row = Integer.parseInt(line);
      // 列数を読み込む
      line = br.readLine();
      col = Integer.parseInt(line);
      // マップを作成
      map = new char[row][col];
      for (int i = 0; i < row; i++) {
        line = br.readLine();
        for (int j = 0; j < col; j++) {
          map[i][j] = line.charAt(j);
          switch (map[i][j]) {
            case 'o': // コイン
              sprites.add(new Coin(tilesToPixels(j), tilesToPixels(i), "coin.gif", this));
              break;
            case 'k': // 栗ボー
              sprites.add(new Kuribo(tilesToPixels(j), tilesToPixels(i), "kuribo.gif", this));
              break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public LinkedList getSprites() {
    return sprites;
  }
}
