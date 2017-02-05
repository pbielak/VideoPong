package pl.pwr.game;

import java.awt.*;

public class Settings {
  public static final boolean DEBUG_MODE = true;

  public static final int GAME_WIDTH = 700;
  public static final int GAME_HEIGHT = 500;

  public static final int CAMERA_WIDTH = 640;
  public static final int CAMERA_HEIGHT = 360;

  public static final int FPS = 60;

  public static final Point DEFAULT_BALL_POSITION = new Point(GAME_WIDTH / 2, GAME_HEIGHT / 2);
  public static final int BALL_RADIUS = 10;
  public static final int BALL_SPEED_X = 4;
  public static final int BALL_SPEED_Y = 4;

  public static final int PADDLE_WIDTH = 10;
  public static final int PADDLE_HEIGHT = 100;
  public static final int PADDLE_SPEED = 5;

  public static final Point DEFAULT_LEFT_PADDLE_POSITION = new Point(25, GAME_HEIGHT / 2);
  public static final Point DEFAULT_RIGHT_PADDLE_POSITION = new Point(GAME_WIDTH - 25 - PADDLE_WIDTH, GAME_HEIGHT / 2);

  public static final Font BIG_FONT = new Font(Font.DIALOG, Font.BOLD, 36);
  public static final Font SMALL_FONT = new Font(Font.DIALOG, Font.BOLD, 18);
}
