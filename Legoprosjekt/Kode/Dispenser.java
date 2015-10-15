import lejos.hardware.motor.NXTRegulatedMotor;

// Controls a drink dispenser (logic)
public class Dispenser
{
	// Motor used to open and close bottle
	private NXTRegulatedMotor motor;
	
	public Dispenser(NXTRegulatedMotor motor)
	{
		this.motor = motor;
	}
}