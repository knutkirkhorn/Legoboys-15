import lejos.hardware.motor.*;

// Controls the conveyor belt
public class Belt {
	// The amount of degrees the motor has to turn to complete a belt cycle
	private final int START_POSITION = 3457;

	// Motor used to move belt
	private NXTRegulatedMotor motor;

	public Belt(NXTRegulatedMotor motor, int speed) {
		this.motor = motor;
		motor.setSpeed(speed);
	}

	// Resets the rotation counter
	// Call this after completing cycle
	public void reset() {
		motor.resetTachoCount();
		motor.flt(); // Lets us move the motor manually
	}

	// Moves the motor back to the start position
	public void moveToStart() {
		motor.rotateTo(START_POSITION);
	}

	// Moves the belt to the selected dispenser
	public void moveToDispenser(Dispenser dispenser) {
		motor.rotateTo(dispenser.getPosition());
	}
}