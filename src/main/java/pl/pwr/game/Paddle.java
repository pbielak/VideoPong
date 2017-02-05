package pl.pwr.game;

import java.awt.*;

import static pl.pwr.game.Settings.*;

public class Paddle {
  private Point center;

  public Paddle(Point position) {
    center = position;
  }

  public void moveToPosition(int yCoordinate) {
    center.move(center.x, yCoordinate);
  }

  public void moveUp() {
    center.translate(0, -PADDLE_SPEED);
  }

  public void moveDown() {
    center.translate(0, PADDLE_SPEED);
  }

  public int getMinX() {
    return center.x - PADDLE_WIDTH / 2;
  }

  public int getMaxX() {
    return center.x + PADDLE_WIDTH / 2;
  }

  public int getMinY() {
    return center.y - PADDLE_HEIGHT / 2;
  }

  public int getMaxY() {
    return center.y + PADDLE_HEIGHT / 2;
  }

  public void draw(Graphics g) {
    g.fillRect(center.x - PADDLE_WIDTH / 2, center.y - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
  }
}
