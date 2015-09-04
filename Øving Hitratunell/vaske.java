import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;

public class vaske{
	 public static void main (String[] args)  throws Exception{


		 Brick brick = BrickFinder.getDefault();
		 Port s3 = brick.getPort("S3");
		 Port s2 = brick.getPort("S2");

		 EV3ColorSensor fargesensor = new EV3ColorSensor(s3); // ev3-fargesensor
		 SensorMode mode = fargesensor.getColorIDMode();
		 int colorId = fargesensor.getColorID();
	 }
 }
