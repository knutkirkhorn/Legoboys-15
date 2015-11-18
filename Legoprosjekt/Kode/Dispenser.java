import lejos.hardware.motor.NXTRegulatedMotor;

// Controls a drink dispenser (logic)
public class Dispenser
{
	// Motor used to open and close bottle
	private NXTRegulatedMotor motor;
	
	// The dispenser position in motor degrees from start
	private int position;
	
	public int getPosition()
	{
		return position;
	}
	
	public Dispenser(NXTRegulatedMotor motor, int position)
	{
		this.motor = motor;
		this.position = position;
	}
}