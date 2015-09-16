import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;

public class RaveUltrasonicSensorNXT
{
	private NXTUltrasonicSensor sensor;
	private SampleProvider sampler;
	private float[] sample;

	public RaveUltrasonicSensorNXT(Port port)
	{
		sensor = new NXTUltrasonicSensor(port);
		//sensor.enable();
		sampler = sensor.getPingMode();
		sample = new float[sampler.sampleSize()];
	}

	public float getDistance()
	{
		sampler.fetchSample(sample, 0);
		return sample[0];
	}
}