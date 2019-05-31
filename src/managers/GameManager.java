package managers;

public class GameManager {
	// Using scenes and levels interchangeable
	/*
	 * Scene 0 -> Main Menu
	 * Scene 1 to numOfLevels -> Level(s)
	 * Scene numLevels + 2 -> Win Screen 
	*/

	public static void main(String[] args) {
		System.out.println("Starting the game!");
		RunScene(0);	// eventually, we will want to start at the main menu - scene 0
	}
	
	public static void RunScene(int scene) {
		LevelManager LM = new LevelManager(scene);
		LM.playScene();
	}
}
