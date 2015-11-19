import java.util.ArrayList;

public class Recipe {
	// The array containing all the actions (moving and dispensing)
	private ArrayList<RecipeAction> actions;
	
	public ArrayList<RecipeAction> getActions() {
		return actions;
	}
	
	public Recipe() {
		actions = new ArrayList<RecipeAction>();
	}
	
	// Adds a new action to the recipe
	public void addAction(int actionId, int value) {
		actions.add(new RecipeAction(actionId, value));
	}
}