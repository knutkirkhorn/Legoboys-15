import lejos.hardware.motor.*;
import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import lejos.hardware.Button;

public class Main {
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
	private static String[] menuTitles	= 	new String[] { "Fifty-Fifty",	"Drink 1",		     "Drink 2",		"3/4th-1/4th" 	};
	private static String[] menuIcons	= 	new String[] { Icons.TEST,		Icons.JAGER,		Icons.BACARDI,	Icons.TOOLS	};

	// Indicates if the program should keep running
	private static boolean keepRunning = true;

	//Entry point and main method of application
	public static void main(String[] args) {
		//Start music
		MusicPlayer mPlayer = new MusicPlayer(100, true);//MusicPlayer("broilerjul.wav", 100);
		mPlayer.playMusic();

		// Set instances of EV3 components
		brick = BrickFinder.getDefault();
		lcd = LocalEV3.get().getTextLCD();

		// Initialize our components
		belt = new Belt(Motor.A, 320); // Conveyor belt connected to port A
		initializeDispensers();

		// Initialize and start menu
		menu = new GraphicMenu(menuTitles, menuIcons, 1, "Drinkmikser", 0);
		startMenu();
	}

	// Creates the Dispenser instances
	private static void initializeDispensers() {
		dispensers = new Dispenser[3]; // 1 dispenser for testing
		dispensers[0] = new Dispenser(0, Motor.B, 445, 2000); // First dispenser uses motor B
		dispensers[1] = new Dispenser(1, Motor.C, 760, 2000); // Second dispenser uses motor C
		dispensers[2] = new Dispenser(2, Motor.D, 1032, 2000); // Third dispenser uses motor D
	}

	// Displays the menu and gets input
	private static void startMenu() {
		while (keepRunning) {
			lcd.clear();
			int selection = menu.select(0, 0);
			handleMenuAction(selection);
		}
	}

	// Processes the menu input
	private static void handleMenuAction(int action) {
		switch (action) {
			case -1: // Exit
				keepRunning = false;
				break;

			case 0: // Recipe 1 - Fifty-Fifty from both dispensers
				belt.reset();
				belt.moveToDispenser(dispensers[0]);
				dispensers[0].dispenseLiquid(5000);
				belt.moveToDispenser(dispensers[1]);
				dispensers[1].dispenseLiquid(5000);
				belt.moveToStart();
				belt.reset();
				break;

			case 1: // Recipe 2 - Only dispenser 1
				belt.reset();
				belt.moveToDispenser(dispensers[0]);
				dispensers[0].dispenseLiquid(10000);
				belt.moveToStart();
				belt.reset();
				break;

			case 2: // Recipe 3 - Only dispenser 2
				belt.reset();
				belt.moveToDispenser(dispensers[1]);
				dispensers[1].dispenseLiquid(10000);
				belt.moveToStart();
				belt.reset();
				break;

			case 3: // Recipe 4 - 3 quarters - 1 Quarters
				belt.reset();
				belt.moveToDispenser(dispensers[0]);
				dispensers[0].dispenseLiquid(7500);
				belt.moveToDispenser(dispensers[1]);
				dispensers[1].dispenseLiquid(2500);
				belt.moveToStart();
				belt.reset();
				break;
		}
	}

	// Executes the selected recipe, and resets the belt
	private static void executeRecipe(Recipe recipe) {
		for(RecipeAction action : recipe.getActions()) {
			switch(action.getActionId()) {
				case RecipeAction.MOVE_TO: // Moves the cup to selected dispenser
					belt.moveToDispenser(dispensers[action.getValue()]);
					break;

				case RecipeAction.DISPENSE: // Dispenses liquid from the current dispenser
					if(belt.getCurrentDispenser() != -1) { // Do not dispense if at start
						dispensers[belt.getCurrentDispenser()].dispenseLiquid(action.getValue());
					}
					break;
			}
		}
		belt.moveToStart();
		belt.reset();
	}
}