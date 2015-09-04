import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;


public class TouchExample {

  public static void main(String[] args) {
    Brick brick = BrickFinder.getDefault();
    Port s2 = brick.getPort("S2");
    EV3TouchSensor sensor = new EV3TouchSensor(s2);

    SimpleTouch touch=new SimpleTouch(sensor);


    while (touch.isPressed()) {
	  System.out.println("Bilen er fortsatt på banen!");
      Delay.msDelay(500);
    }
    System.out.println("Bilen er utenfor banen!");

    sensor.close();

  }
}

 class SimpleTouch extends AbstractFilter {
  private float[] sample;

  public SimpleTouch(SampleProvider source) {
    super(source);
    sample = new float[sampleSize];
  }

  public boolean isPressed() {
    super.fetchSample(sample, 0);
    if (sample[0] == 0)
      return false;
    return true;
  }

}