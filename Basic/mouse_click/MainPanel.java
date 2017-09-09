import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements MouseListener {
  // パネルサイズ
  public static final int WIDTH = 240;
  public static final int HEIGHT = 240;
  // 点の大きさ
  public static final int SIZE = 10;

  // 点の位置を格納するリスト
  private ArrayList pointList;

  public MainPanel() {
    // パネルの推奨サイズを設定、pack()するときに必要
    setPreferredSize(new  Dimension(WIDTH, HEIGHT));

    // 10個(デフォルト)の要素を持つArrayListを作成
    pointList = new ArrayList();

    // MouseListenerを登録
    addMouseListener(this);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.RED);

    // pointListに格納されている点の場所に点を描く
    for (int i = 0; i < pointList.size(); i++) {
      Point p = (Point) pointList.get(i);
      g.fillOval(p.x - SIZE / 2, p.y - SIZE / 2, SIZE, SIZE);
    }
  }

  // マウスをクリックした時に呼び出す
  public void mouseClicked(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    // pointListに登録
    pointList.add(new Point(x, y));

    repaint();
  }

  // マウスがウィンドウ内に入った時に呼び出す
  public void mouseEntered(MouseEvent e) {

  }
  // マウスがウィンドウ外に出た時に呼び出す
  public void mouseExited(MouseEvent e) {

  }
  // マウスボタンが押された時に呼び出す
  public void mousePressed(MouseEvent e) {

  }
  // マウスボタンが離された時に呼び出す
  public void mouseReleased(MouseEvent e) {

  }
}
