import lejos.hardware.motor.*;

// Controls the conveyor belt
public class Belt
{
	// Motor used to move belt
	private NXTRegulatedMotor motor;
	
	public Belt(NXTRegulatedMotor motor, int speed)
	{
		this.motor = motor;
		motor.setSpeed(speed);
	}
	
	// Resets the rotation counter
	// Call this after completing cycle
	public void reset()
	{
		motor.resetTachoCount();
		motor.flt(); // Lets us move the motor manually
	}
	
	// Moves the motor back to the start position
	public void moveToStart()
	{
		motor.rotateTo(3457);
	}
	
	// Moves the belt to the selected dispenser
	public void moveToDispenser(Dispenser dispenser)
	{
		motor.rotateTo(dispenser.getPosition());
	}
}