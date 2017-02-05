package pl.pwr.controller;

import org.opencv.core.Scalar;
import pl.pwr.opencv.VideoProcessor;

public class ControllerConfiguration {

  private final VideoProcessor processor;

  public ControllerConfiguration(Scalar low, Scalar high) {
    processor = new VideoProcessor(low, high);
  }

  public VideoProcessor getProcessor() {
    return processor;
  }
}
