package pl.pwr.utils;

import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Utils {

  public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font, Point translation) {
    FontMetrics metrics = g.getFontMetrics(font);

    int x = (rect.width - metrics.stringWidth(text)) / 2;
    int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

    g.setFont(font);
    g.drawString(text, x + translation.x, y + translation.y);
  }


  public static BufferedImage toBufferedImage(Mat m) {
    int type = BufferedImage.TYPE_BYTE_GRAY;
    if (m.channels() > 1) {
      type = BufferedImage.TYPE_3BYTE_BGR;
    }

    int bufferSize = m.channels() * m.cols() * m.rows();
    byte[] b = new byte[bufferSize];
    m.get(0, 0, b);

    BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

    System.arraycopy(b, 0, targetPixels, 0, b.length);
    return image;
  }


}
