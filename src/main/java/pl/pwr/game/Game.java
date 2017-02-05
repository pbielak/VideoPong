package pl.pwr.game;

import java.awt.*;

import static pl.pwr.game.GameState.*;
import static pl.pwr.game.Settings.*;

public class Game {
  private GameState gameState;
  private Ball ball;
  private Player playerOne, playerTwo;

  public Game() {
    init();
  }

  public void restart() {
    init();
  }

  private void init() {
    gameState = START_SCREEN;
    ball = new Ball(new Point(DEFAULT_BALL_POSITION));
    playerOne = new Player(DEFAULT_LEFT_PADDLE_POSITION);
    playerTwo = new Player(DEFAULT_RIGHT_PADDLE_POSITION);
  }

  public void update() {
    if (gameState == PLAYING) {
      ball.move();

      if (hasBallHitWall()) {
        ball.changeYDirection();
      }

      if (ball.getMinX() < playerOne.getPaddle().getMaxX()) {
        checkCollisionWithPaddle(playerOne.getPaddle(), ball, playerTwo);
      }

      if (ball.getMaxX() > playerTwo.getPaddle().getMinX()) {
        checkCollisionWithPaddle(playerTwo.getPaddle(), ball, playerOne);
      }

      if (hasGameEnded()) {
        gameState = GAME_OVER;
      }
    }
  }

  public void movePlayer(Player player, boolean isUpKeyPressed, boolean isDownKeyPressed) {
    if (isUpKeyPressed) {
      if (player.getPaddle().getMinY() - PADDLE_SPEED > 0) {
        player.getPaddle().moveUp();
      }
    }
    if (isDownKeyPressed) {
      if (player.getPaddle().getMaxY() + PADDLE_SPEED < GAME_HEIGHT) {
        player.getPaddle().moveDown();
      }
    }
  }

  public void movePlayer(Player player, int yCoordinate) {
    if(yCoordinate > 0 && yCoordinate < GAME_HEIGHT) {
      player.getPaddle().moveToPosition(yCoordinate);
    }
  }

  public Player getPlayerOne() {
    return playerOne;
  }

  public Player getPlayerTwo() {
    return playerTwo;
  }

  public Ball getBall() {
    return ball;
  }

  public GameState getGameState() {
    return gameState;
  }

  public void setGameState(GameState state) {
    gameState = state;
  }

  private boolean hasGameEnded() {
    return playerOne.getScore() == 3 || playerTwo.getScore() == 3;
  }

  private boolean hasBallHitWall() {
    return ball.getMinY() < 0 || ball.getMaxY() > GAME_HEIGHT;
  }

  private void checkCollisionWithPaddle(Paddle playerPaddle, Ball ball, Player scoreToUpdate) {
    if (hasMissedPaddle(playerPaddle, ball)) {
      scoreToUpdate.incrementScore();
      ball.setPosition(new Point(DEFAULT_BALL_POSITION));
    } else {
      ball.changeXDirection();
    }
  }

  private boolean hasMissedPaddle(Paddle paddle, Ball ball) {
    return ball.getMinY() > paddle.getMaxY() || ball.getMaxY() < paddle.getMinY();
  }
}
