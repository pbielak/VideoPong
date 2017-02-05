package pl.pwr.opencv;

import org.opencv.core.Mat;
import pl.pwr.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VideoJFrame extends JFrame implements KeyListener {

  private VideoPanel videoPanel;
  private boolean isEnterKeyPressed;
  private boolean isResetPressed;

  public VideoJFrame(String title) {
    setTitle(title);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    videoPanel = new VideoPanel();
    videoPanel.setPreferredSize(new Dimension(640, 360));
    videoPanel.setMinimumSize(new Dimension(640, 360));
    add(videoPanel);
    addKeyListener(this);

    pack();
    setResizable(false);
    setVisible(true);
  }

  public void updateImage(Mat mat) {
    videoPanel.setImage(Utils.toBufferedImage(mat));
    videoPanel.repaint();
  }

  public boolean isResetPressed() {
    return isResetPressed;
  }

  public boolean isEnterKeyPressed() {
    return isEnterKeyPressed;
  }

  public void consumeEnterKeyPressed() {
    isEnterKeyPressed = false;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      isEnterKeyPressed = true;
    }
    else if (e.getKeyCode() == KeyEvent.VK_R) {
      isResetPressed = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      isEnterKeyPressed = false;
    }
    else if (e.getKeyCode() == KeyEvent.VK_R) {
      isResetPressed = false;
    }
  }
}
