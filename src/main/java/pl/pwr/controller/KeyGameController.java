package pl.pwr.controller;

import pl.pwr.game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import static pl.pwr.game.GameState.GAME_OVER;
import static pl.pwr.game.GameState.PLAYING;
import static pl.pwr.game.GameState.START_SCREEN;

public class KeyGameController implements KeyListener {

  private Game game;
  private Map<Integer, Boolean> keys;

  public KeyGameController(Game game) {
    this.game = game;
    initKeyMap();
  }

  private void initKeyMap() {
    keys = new HashMap<>();
    keys.put(KeyEvent.VK_P, false);
    keys.put(KeyEvent.VK_SPACE, false);
  }

  private void step() {
    if (game.getGameState() == START_SCREEN) {
      if (keys.get(KeyEvent.VK_P)) {
        game.setGameState(PLAYING);
      }
    } else if (game.getGameState() == GAME_OVER) {
      if(keys.get(KeyEvent.VK_SPACE)) {
        game.restart();
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    keys.put(e.getKeyCode(), true);
    step();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keys.put(e.getKeyCode(), false);
    step();
  }
}
