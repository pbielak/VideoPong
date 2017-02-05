package pl.pwr.game;

import java.awt.*;

public class Player {

  private Paddle paddle;
  private int score;

  public Player(Point paddlePosition) {
    paddle = new Paddle(paddlePosition);
    score = 0;
  }

  public Paddle getPaddle() {
    return paddle;
  }

  public void incrementScore() {
    score = score + 1;
  }

  public int getScore() {
    return score;
  }
}
