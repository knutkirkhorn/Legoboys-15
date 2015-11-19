import java.util.ArrayList;

public class RecipeAction {
	// Constants defining which actions to perform
	public static final int MOVE_TO = 0;
	public static final int DISPENSE = 1;
	
	// The current action, as described above
	private int actionId;
	
	// The parameter of the current action. Dispenser ID for MOVE_TO, and amount for DISPENSE
	private int value;
	
	public int getActionId() {
		return actionId;
	}
	
	public int getValue() {
		return value;
	}
	
	public RecipeAction(int actionId, int value) {
		this.actionId = actionId;
		this.value = value;
	}
}