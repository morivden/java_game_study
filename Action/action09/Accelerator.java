import java.applet.Applet;
import java.applet.AudioClip;

// 加速アイテム
public class Accelerator extends Sprite {
  // アイテムを取った時の音
  private AudioClip sound;

  public Accelerator(double x, double y, String filename, Map map) {
    super(x, y, filename, map);

    // サウンドをロード
    sound = Applet.newAudioClip(getClass().getResource("se/chari13_c.wav"));
  }

  public void update() {

  }

  // サウンドを再生
  public void play() {
    sound.play();
  }

  // アイテムを使う
  public void use(Player player) {
    // プレイヤーのスピードアップ
    player.setSpeed(player.getSpeed() * 2);
  }
}
