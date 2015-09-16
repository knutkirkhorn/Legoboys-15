import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;

public class RaveUltrasonicSensor
{
	private EV3UltrasonicSensor sensor;
	private SampleProvider sampler;
	private float[] sample;

	public RaveUltrasonicSensor(Port port)
	{
		sensor = new EV3UltrasonicSensor(port);
		sampler = sensor.getDistanceMode();
		sample = new float[sampler.sampleSize()];
	}

	public float getDistance()
	{
		sampler.fetchSample(sample, 0);
		return sample[0];
	}
}
