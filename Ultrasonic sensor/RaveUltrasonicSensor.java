import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;
import lejos.hardware.port.Port;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

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

	// Returnerer avstanden til hindringer i meter. Maks avstand er 2.5, mer enn det blir infinity
	public float getDistance()
	{
		sampler.fetchSample(sample, 0);
		return sample[0];
	}
}