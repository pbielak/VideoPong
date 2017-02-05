package pl.pwr.opencv;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Moments;

import static org.opencv.core.Core.inRange;
import static org.opencv.imgproc.Imgproc.*;

public class VideoProcessor {

  private final Scalar COLOR_LOW;
  private final Scalar COLOR_HIGH;

  private Mat imgThresholded;
  private Moments _moments;

  public VideoProcessor(Scalar colorLow, Scalar colorHigh) {
    COLOR_LOW = colorLow;
    COLOR_HIGH = colorHigh;
    imgThresholded = new Mat();
  }

  public void processImage(Mat frame) {
    Mat imgHSV = new Mat();
    cvtColor(frame, imgHSV, COLOR_BGR2HSV);

    imgThresholded = new Mat();
    inRange(imgHSV, COLOR_LOW, COLOR_HIGH, imgThresholded);

    erode(imgThresholded, imgThresholded, getStructuringElement(MORPH_ELLIPSE, new Size(5, 5)));
    dilate(imgThresholded, imgThresholded, getStructuringElement(MORPH_ELLIPSE, new Size(5, 5)));

    dilate(imgThresholded, imgThresholded, getStructuringElement(MORPH_ELLIPSE, new Size(5, 5)));
    erode(imgThresholded, imgThresholded, getStructuringElement(MORPH_ELLIPSE, new Size(5, 5)));

    _moments = moments(imgThresholded);
  }

  public boolean wasFilteringSuccessfull() {
    return _moments.get_m00() > 10000;
  }

  public Point getObjectPosition() {
    int posX = (int) (_moments.get_m10() / _moments.get_m00());
    int posY = (int) (_moments.get_m01() / _moments.get_m00());

    return new Point(posX, posY);
  }

}
