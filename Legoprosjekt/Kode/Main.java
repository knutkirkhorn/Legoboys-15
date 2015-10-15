import lejos.hardware.motor.*;
import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;

public class Main
{
	// The EV3 brick
	private static Brick brick;
	
	// Controls the conveyor belt
	private static NXTRegulatedMotor belt;
	
	// Contains alle the dispensers
	private static Dispenser[] dispensers;
	
	//Entry point and main method of application
	public static void main(String[] args)
	{
		brick = BrickFinder.getDefault();
		belt = Motor.A; // Conveyor belt connected to port A
		dispensers = new Dispenser[1]; // 1 dispenser for testing
		
		InitializeDispensers();
	}
	
	// Creates the Dispenser instances
	private static void InitializeDispensers()
	{
		dispensers[0] = new Dispenser(Motor.B); // First dispenser uses motor B
	}
}