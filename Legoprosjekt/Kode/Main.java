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

	// The LCD screen controller
	private static TextLCD lcd;

	// Conveyor belt controller
	private static Belt belt;

	// Contains all the dispensers
	private static Dispenser[] dispensers;

	// The menu component
	private static GraphicMenu menu;
	private static String[] menuTitles = new String[] { "Testprogram", "Jagermaister", "Bacardi", "Kalibrer" };
	private static String[] menuIcons = new String[] { Icons.TEST, Icons.JAGER, Icons.BACARDI, Icons.TOOLS };

	// Indicates if the program should keep running
	private static boolean keepRunning = true;

	//Entry point and main method of application
	public static void main(String[] args)
	{
		//Start music
		MusicPlayer mPlayer = new MusicPlayer("broilerjul.wav", 100);
		mPlayer.playMusic();

		brick = BrickFinder.getDefault();
		lcd = LocalEV3.get().getTextLCD();

		belt = new Belt(Motor.A, 320); // Conveyor belt connected to port A
		dispensers = new Dispenser[3]; // 1 dispenser for testing

		menu = new GraphicMenu(menuTitles, menuIcons, 1, "Drinkmikser", 0);

		initializeDispensers();
		startMenu();
	}

	// Creates the Dispenser instances
	private static void initializeDispensers()
	{
		dispensers[0] = new Dispenser(Motor.B, 405); // First dispenser uses motor B
		dispensers[1] = new Dispenser(Motor.C, 720); // First dispenser uses motor B
		dispensers[2] = new Dispenser(Motor.D, 1032); // First dispenser uses motor B
	}

	// Displays the menu and gets input
	private static void startMenu()
	{
		while(keepRunning)
		{
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

			case 0: // Recipe 1 here (Placeholder for testing)
				lcd.clear();

				for(int i = 0; i < 5; i++)
				{
					belt.moveToDispenser(dispensers[0]);
					dispensers[0].dispenseLiquid(20000);
					// belt.moveToDispenser(dispensers[1]);
					// Delay.msDelay(250);
					// belt.moveToDispenser(dispensers[0]);
					// Delay.msDelay(250);

					// belt.moveToDispenser(dispensers[0]);
					// Delay.msDelay(250);
					// belt.moveToDispenser(dispensers[1]);
					// Delay.msDelay(250);
					// belt.moveToDispenser(dispensers[2]);
					// Delay.msDelay(250);

					belt.moveToStart();
					belt.reset();
					Delay.msDelay(500);
				}
				break;

			case 1:
				//Move to dispenser 1
				belt.moveToDispenser(dispensers[1]);
				dispensers[1].dispenseLiquid(10000);
				Delay.msDelay(1000);

				//Kan endre dette til:
				//doDispenserShit(1, 10000, 1000);

				//Move to dispenser 2
				belt.moveToDispenser(dispensers[0]);
				dispensers[0].dispenseLiquid(10000);
				Delay.msDelay(1000);


				//Reset
				belt.moveToStart();
				belt.reset();
				Delay.msDelay(500);

				break;

			case 2:
				//dispensers[0].pumpTest();
				break;

			case 3: // Calibrate the belt (lets us move it back to start position)
				belt.reset();
				break;
		}
	}

	private void doDispenserShit(int dispenserNumber, int liquidAmount, int delayAtStop){//Endre på dette? ;))
		belt.moveToDispenser(dispensers[dispenserNumber]);
		dispensers[dispenserNumber].dispenseLiquid(liquidAmount);
		Delay.msDelay(delayAtStop);
	}
}