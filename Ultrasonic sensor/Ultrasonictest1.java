import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;
import lejos.hardware.port.Port;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.NXTUltrasonicSensor;

public class Ultrasonictest1
{
	 public static void main (String[] args) throws Exception
	 {
		 Brick brick = BrickFinder.getDefault();
		 //Port s4 = brick.getPort("S4"); // ultrasonic sensor
		 Port s1 = brick.getPort("S1"); // NXT ultrasonic sensor

		 RaveUltrasonicSensorNXT ultraNXT = new RaveUltrasonicSensorNXT (s1);
		 //RaveUltrasonicSensor ultra = new RaveUltrasonicSensor(s4);

		 while (!Button.ESCAPE.isDown())
		 {
		 		 //System.out.println(ultra.getDistance());
		 		 System.out.println("NXT" + ultraNXT.getDistance());
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
class RaveUltrasonicSensorNXT
{
	private NXTUltrasonicSensor sensor;
	private SampleProvider sampler;
	private float[] sample;

	public RaveUltrasonicSensorNXT(Port port)
	{
		sensor = new NXTUltrasonicSensor(port);
		sampler = sensor.getDistanceMode();
		sample = new float[sampler.sampleSize()]; // LYD
	}

	public float getDistance()
	{
		sampler.fetchSample(sample, 0);
		return sample[0];
	}
}
