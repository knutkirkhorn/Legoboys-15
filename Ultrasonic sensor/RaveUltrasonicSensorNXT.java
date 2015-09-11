import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;
import lejos.hardware.port.Port;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

// Returnerer avstanden til hindringer i meter. Maks avstand er 2.5, mer enn det blir infinity

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
