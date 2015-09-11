import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;
import lejos.hardware.port.Port;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

public class Ultrasonictest
{
	 public static void main (String[] args) throws Exception
	 {
		 Brick brick = BrickFinder.getDefault();
		 Port s1 = brick.getPort("S1"); // ultrasonic sensor

		 RaveUltrasonicSensor ultra = new RaveUltrasonicSensor(s1);

		 while (!Button.ESCAPE.isDown())
		 {
		 		 System.out.println(ultra.getDistance());
		 }
	 }
}

class RaveUltrasonicSensor
{
	private EV3UltrasonicSensor sensor;
	private SampleProvider sampler;
	private float[] sample;

	public RaveUltrasonicSensor(Port port)
	{
		sensor = new EV3UltrasonicSensor(port);
		sampler = sensor.getDistanceMode();
		sample = new float[sampler.sampleSize()]; // LYD
	}

	public float getDistance()
	{
		sampler.fetchSample(sample, 0);
		return sample[0];
	}
}
