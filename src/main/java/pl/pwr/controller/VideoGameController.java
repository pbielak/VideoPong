package pl.pwr.controller;

import org.opencv.core.*;
import org.opencv.highgui.VideoCapture;
import pl.pwr.game.Game;
import pl.pwr.game.GameState;
import pl.pwr.game.Settings;
import pl.pwr.opencv.VideoJFrame;
import pl.pwr.opencv.VideoProcessor;

import java.util.Arrays;

import static org.opencv.core.Core.*;
import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2HSV;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.resize;
import static pl.pwr.game.Settings.*;

public class VideoGameController implements Runnable {

  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

//  private final Scalar ORANGE_LOW = new Scalar(0, 153, 220);
//  private final Scalar ORANGE_HIGH = new Scalar(17, 255, 255);
//  private VideoProcessor orangeFilter = new VideoProcessor(ORANGE_LOW, ORANGE_HIGH);

  private ControllerConfiguration config1;
  private ControllerConfiguration config2;

//  private final Scalar GREEN_LOW = new Scalar(62, 112, 0);
//  private final Scalar GREEN_HIGH = new Scalar(77, 255, 255);
//  private VideoProcessor greenFilter = new VideoProcessor(GREEN_LOW, GREEN_HIGH);

  private VideoJFrame imgOrgFrame = new VideoJFrame("Original");
  private Game game;

  public VideoGameController(Game game) {
    this.game = game;
    config1 = new ControllerConfiguration(new Scalar(0, 153, 220), new Scalar(17, 255, 255));
    config2 = new ControllerConfiguration(new Scalar(62, 112, 0), new Scalar(77, 255, 255));
  }

  @Override
  public void run() {
    VideoCapture cap = new VideoCapture(0);

    if (!cap.isOpened()) {
      throw new IllegalStateException("Cannot open the webcam!");
    }

    Mat imgTmp = new Mat();
    cap.read(imgTmp);

    while (true) {
      Mat imgOrg = new Mat();

      boolean readSuccessful = cap.read(imgOrg);

      if (!readSuccessful) {
        throw new IllegalStateException("Cannot read a frame from video stream");
      }

      resize(imgOrg, imgOrg, new Size(imgOrg.size().width / 2, imgOrg.size().height / 2));

      if (config1 == null) {
        config1 = calibrate(imgOrg, 1);
      } else if (config2 == null) {
        config2 = calibrate(imgOrg, 2);
      } else {
        trackControllers(imgOrg);
      }

      if(imgOrgFrame.isResetPressed()) {
        config1 = config2 = null;
      }
    }
  }

  private ControllerConfiguration calibrate(Mat imgOrg, int player) {
    ControllerConfiguration config = null;
    Mat imgCalibrate = new Mat(imgOrg.size(), CV_8UC3, new Scalar(0));

    final int rectangleSize = 20;
    putText(imgCalibrate, "Kalibracja gracza " + player, new Point(0, 50), FONT_HERSHEY_TRIPLEX, 1, new Scalar(255, 255, 255));
    rectangle(imgCalibrate, new Point(CAMERA_WIDTH / 2 - rectangleSize, CAMERA_HEIGHT / 2 - rectangleSize), new Point(CAMERA_WIDTH / 2 + rectangleSize, CAMERA_HEIGHT / 2 + rectangleSize), new Scalar(255, 255, 255));

    if (imgOrgFrame.isEnterKeyPressed()) {
      Mat imgHSV = new Mat();
      cvtColor(imgOrg, imgHSV, COLOR_BGR2HSV);

      double avgH = 0, avgS = 0, avgV = 0;
      for (int i = 0; i < 40; i++) {
        for (int j = 0; j < 40; j++) {
          double[] pixel = imgHSV.get(i + CAMERA_WIDTH / 2 - rectangleSize, j + CAMERA_HEIGHT / 2 - rectangleSize);
          avgH += pixel[0];
          avgS += pixel[1];
          avgV += pixel[2];
        }
      }

      avgH /= 40 * 40;
      avgS /= 40 * 40;
      avgV /= 40 * 40;

      System.out.println("H: " + avgH + " S: " + avgS + " V: " + avgV);
      final int delta = 20;
      config = new ControllerConfiguration(new Scalar(avgH - delta, avgS - delta, avgV - delta), new Scalar(avgH + delta, 255, 255));

      imgOrgFrame.consumeEnterKeyPressed();
    }


    add(imgOrg, imgCalibrate, imgCalibrate);
    imgOrgFrame.updateImage(imgCalibrate);
    return config;
  }

  private void trackControllers(Mat imgOrg) {
    config1.getProcessor().processImage(imgOrg);
    Point leftPlayerPosition = config1.getProcessor().getObjectPosition();

    config2.getProcessor().processImage(imgOrg);
    Point rightPlayerPosition = config2.getProcessor().getObjectPosition();

    if (Settings.DEBUG_MODE) {
      if (config1.getProcessor().wasFilteringSuccessfull()) {
        if (leftPlayerPosition.x >= 0 && leftPlayerPosition.y >= 0) {
          circle(imgOrg, new Point(leftPlayerPosition.x, leftPlayerPosition.y), 50, new Scalar(105, 105, 105));
        }
      }

      if (config2.getProcessor().wasFilteringSuccessfull()) {
        if (rightPlayerPosition.x >= 0 && rightPlayerPosition.y >= 0) {
          rectangle(imgOrg, new Point(rightPlayerPosition.x - 25, rightPlayerPosition.y - 25), new Point(rightPlayerPosition.x + 25, rightPlayerPosition.y + 25), new Scalar(105, 105, 105));
        }
      }

      putText(imgOrg, "LEFT -> X: " + leftPlayerPosition.x + " Y: " + leftPlayerPosition.y, new Point(0, imgOrg.size().height - 20), FONT_HERSHEY_PLAIN, 1, new Scalar(255, 255, 0));
      putText(imgOrg, "RIGHT -> X: " + rightPlayerPosition.x + " Y: " + rightPlayerPosition.y, new Point(0, imgOrg.size().height - 50), FONT_HERSHEY_PLAIN, 1, new Scalar(255, 255, 0));

      imgOrgFrame.updateImage(imgOrg);
    }

    if (game.getGameState() == GameState.PLAYING) {
      final double scaleFactor = Settings.GAME_HEIGHT / (double) imgOrg.height();
      game.movePlayer(game.getPlayerOne(), (int) (leftPlayerPosition.y * scaleFactor));
      game.movePlayer(game.getPlayerTwo(), (int) (rightPlayerPosition.y * scaleFactor));
    }
  }
}
