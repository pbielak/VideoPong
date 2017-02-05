package pl.pwr.game;

import pl.pwr.utils.Utils;

import javax.swing.*;
import java.awt.*;

import static pl.pwr.game.GameState.*;
import static pl.pwr.game.Settings.*;

public class PongPanel extends JPanel {

  private Game game;

  public PongPanel(Game game) {
    this.game = game;
    initPanel();
    initTimer();
  }

  private void initPanel() {
    setBackground(Color.BLACK);
    setFocusable(true);
  }

  private void initTimer() {
    Timer timer = new Timer(1000 / Settings.FPS, event -> update());
    timer.start();
  }

  private void update() {
    if (game.getGameState() == PLAYING) {
      game.update();
    }

    repaint();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.WHITE);

    if (game.getGameState() == START_SCREEN) {
      drawStartScreen(g);
    } else if (game.getGameState() == PLAYING) {
      drawBoard(g);
    } else if (game.getGameState() == GAME_OVER) {
      drawGameOverScreen(g);
    }
  }

  private void drawGameOverScreen(Graphics g) {
    String msg = (game.getPlayerOne().getScore() > game.getPlayerTwo().getScore()) ? "Player 1 Wins!" : "Player 2 Wins!";
    Utils.drawCenteredString(g, msg, getBounds(), BIG_FONT, new Point(0, 0));
    Utils.drawCenteredString(g, "Press space to restart.", getBounds(), SMALL_FONT, new Point(0, 100));
  }

  private void drawBoard(Graphics g) {
    drawMiddleLine(g);
    drawGoalLines(g);

    Utils.drawCenteredString(g, String.valueOf(game.getPlayerOne().getScore()), getBounds(), BIG_FONT, new Point(-100, -200));
    Utils.drawCenteredString(g, String.valueOf(game.getPlayerTwo().getScore()), getBounds(), BIG_FONT, new Point(100, -200));

    game.getBall().draw(g);
    game.getPlayerOne().getPaddle().draw(g);
    game.getPlayerTwo().getPaddle().draw(g);
  }

  private void drawGoalLines(Graphics g) {
    final int leftLinePosition = DEFAULT_LEFT_PADDLE_POSITION.x + PADDLE_WIDTH / 2;
    final int rightLinePosition = DEFAULT_RIGHT_PADDLE_POSITION.x - PADDLE_WIDTH / 2;
    g.drawLine(leftLinePosition, 0, leftLinePosition, GAME_HEIGHT);
    g.drawLine(rightLinePosition, 0, rightLinePosition, GAME_HEIGHT);
  }

  private void drawMiddleLine(Graphics g) {
    for (int lineY = 0; lineY < GAME_HEIGHT; lineY += 50) {
      g.drawLine(GAME_WIDTH / 2, lineY, GAME_WIDTH / 2, lineY + 25);
    }
  }

  private void drawStartScreen(Graphics g) {
    Utils.drawCenteredString(g, "Pong", getBounds(), BIG_FONT, new Point(0, 0));
    Utils.drawCenteredString(g, "Press 'P' to play.", getBounds(), SMALL_FONT, new Point(0, 100));
  }
}