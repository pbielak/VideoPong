package pl.pwr.game;

import java.awt.*;

import static pl.pwr.game.Settings.*;

public class Ball {
  private boolean isMovingUp;
  private boolean isMovingRight;

  private Point center;

  public Ball(Point center) {
    setPosition(center);
    isMovingUp = false;
    isMovingRight = false;
  }

  public void move() {
    center.translate((isMovingRight ? 1 : -1) * BALL_SPEED_X, (isMovingUp ? -1 : 1) * BALL_SPEED_Y);
  }

  public void setPosition(Point position) {
    center = position;
  }

  public void changeXDirection() {
    isMovingRight = !isMovingRight;
  }

  public void changeYDirection() {
    isMovingUp = !isMovingUp;
  }

  public int getMinX() {
    return center.x - BALL_RADIUS;
  }

  public int getMaxX() {
    return center.x + BALL_RADIUS;
  }

  public int getMinY() {
    return center.y - BALL_RADIUS;
  }

  public int getMaxY() {
    return center.y + BALL_RADIUS;
  }

  void draw(Graphics g) {
    g.fillOval(center.x - BALL_RADIUS, center.y - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);
  }
}
