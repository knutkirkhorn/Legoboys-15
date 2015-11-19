import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;

// Controls a drink dispenser (logic)
public class Dispenser {
	private int id;
	
	// Motor used to open and close bottle
	private NXTRegulatedMotor motor;

	// The dispenser position in motor degrees from start
	private int position;
	
	// The time in ms to wait after dispensings (to counter spill)
	private int spillDelay;
	
	public int getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

	public Dispenser(int id, NXTRegulatedMotor motor, int position, int spillDelay) {
		this.id = id;
		this.motor = motor;
		this.position = position;
		this.spillDelay = spillDelay;
	}

	public void dispenseLiquid(int msDuration) {
		motor.setSpeed(Integer.MAX_VALUE);
		motor.forward();
		Delay.msDelay(msDuration);
		motor.stop();
		
		// Prevent spill
		Delay.msDelay(spillDelay);
	}
}