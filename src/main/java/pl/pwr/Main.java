package pl.pwr;

import pl.pwr.controller.KeyGameController;
import pl.pwr.controller.VideoGameController;
import pl.pwr.game.Game;
import pl.pwr.game.PongPanel;

import javax.swing.*;
import java.awt.*;

import static pl.pwr.game.Settings.*;

public class Main {
  public static void main(String[] args) {
    Game game = new Game();

    JFrame frame = new JFrame("Pong");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());

    Thread thread = new Thread(new VideoGameController(game));
    KeyGameController keyGameController = new KeyGameController(game);

    PongPanel pongPanel = new PongPanel(game);
    pongPanel.addKeyListener(keyGameController);
    pongPanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
    pongPanel.setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
    frame.add(pongPanel, BorderLayout.CENTER);

    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);

    thread.run();
  }

}
