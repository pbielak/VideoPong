package pl.pwr.opencv;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoPanel extends JPanel {
  private BufferedImage image;

  public VideoPanel() {
    super();
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if(image != null)
      g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
  }
}
