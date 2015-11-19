import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;

// Controls a drink dispenser (logic)
public class Dispenser {
	// Motor used to open and close bottle
	private NXTRegulatedMotor motor;

	// The dispenser position in motor degrees from start
	private int position;

	public int getPosition() {
		return position;
	}

	public Dispenser(NXTRegulatedMotor motor, int position) {
		this.motor = motor;
		this.position = position;
	}

	public void dispenseLiquid(int msDuration) {
		motor.setSpeed(Integer.MAX_VALUE);
		motor.forward();
		Delay.msDelay(msDuration);
		motor.stop();
		motor.flt();
	}
}