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
	
	// The menu component
	private static GraphicMenu menu;
	private static String[] menuTitles = new String[] { "Element1", "Element2", "Element3" };
	private static String[] menuIcons = new String[] { Icons.TEST, Icons.TEST, Icons.TEST };
	
	//Entry point and main method of application
	public static void main(String[] args)
	{
		brick = BrickFinder.getDefault();
		belt = Motor.A; // Conveyor belt connected to port A
		dispensers = new Dispenser[1]; // 1 dispenser for testing
		
		menu = new GraphicMenu(menuTitles, menuIcons, 1, "Drinkmikser", 0);
		
		InitializeDispensers();
	}
	
	// Creates the Dispenser instances
	private static void InitializeDispensers()
	{
		dispensers[0] = new Dispenser(Motor.B); // First dispenser uses motor B
	}
}