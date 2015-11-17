import lejos.hardware.motor.*;
import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import lejos.hardware.Button;

public class Main
{
	// The EV3 brick
	private static Brick brick;
	
	// THe LCD screen controller
	private static TextLCD lcd;
	
	// Controls the conveyor belt
	private static NXTRegulatedMotor belt;
	
	// Contains all the dispensers
	private static Dispenser[] dispensers;
	
	// The menu component
	private static GraphicMenu menu;
	private static String[] menuTitles = new String[] { "Element1", "Element2", "Element3" };
	private static String[] menuIcons = new String[] { Icons.TEST, Icons.JAGER, Icons.BACARDI };
	
	// Should the program keep running?
	private static boolean keepRunning = true;
	
	//Entry point and main method of application
	public static void main(String[] args)
	{
		brick = BrickFinder.getDefault();
		lcd = LocalEV3.get().getTextLCD();
		
		belt = Motor.A; // Conveyor belt connected to port A
		dispensers = new Dispenser[1]; // 1 dispenser for testing
		
		menu = new GraphicMenu(menuTitles, menuIcons, 1, "Drinkmikser", 0);
		
		initializeDispensers();
		startMenu();
	}
	
	// Creates the Dispenser instances
	private static void initializeDispensers()
	{
		dispensers[0] = new Dispenser(Motor.B); // First dispenser uses motor B
	}
	
	// Displays the menu and gets input
	private static void startMenu()
	{
        while(keepRunning) {
            lcd.clear();
            int selection = menu.select(0, 0);
			handleMenuAction(selection);
        }
	}
	
	// Processes the menu input
	private static void handleMenuAction(int action)
	{
		switch(action)
		{
			case -1: // Exit
			keepRunning = false;
			break;
			
			case 0: // Recipe 1 here
			lcd.clear();
			belt.setSpeed(150);
			belt.forward();
			
			// Wait for the belt to go a whole round (3457 degrees)
			do
			{
				Delay.msDelay(1);
				//lcd.clear();
				//lcd.drawString("TC: " + belt.getTachoCount(), 0, 0);
			}
			while(belt.getTachoCount() < 3457); //360*9.60
			belt.stop();
			belt.resetTachoCount(); // Resets the 'degree ccounter'
			
			// Placeholder for the drink making process
			lcd.drawString("Lager drink", 0, 0);
			Delay.msDelay(1000);
			lcd.drawString("...", 0, 1);
			Delay.msDelay(2000);
			lcd.drawString("...", 0, 2);
			Delay.msDelay(2000);
			lcd.drawString("Done!", 0, 3);
			Delay.msDelay(5000);
			break;
		}
	}
}