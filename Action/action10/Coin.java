import java.applet.Applet;
import java.applet.AudioClip;

public class Coin extends Sprite {
  // コインを取った時の音
  private AudioClip sound;

  public Coin(double x, double y, String filename, Map map) {
    super(x, y, filename, map);

    // サウンドをロード
    sound = Applet.newAudioClip(getClass().getResource("se/coin03.wav"));
  }

  public void update() {

  }

  // サウンドを再生
  public void play() {
    sound.play();
  }
}
