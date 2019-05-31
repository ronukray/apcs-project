package game;

import game.game_objects.*;
import game.game_objects.blocks.*;
import testing.Game;
import utilities.Constants;

import org.lwjgl.opengl.Display;

public class ParkourLevel extends Level {
	
	private int playerSpawnX, playerSpawnY;
	
	public ParkourLevel() { parkourLevelSetup(); }
	
	protected void parkourLevelSetup() {
		generalSetup();
		
		playerSpawnX = 0;
		playerSpawnY = Game.HEIGHT / 2 - player.height - 64 + 5;
		
		player.x = playerSpawnX;
		player.y = playerSpawnY;
		
		for(int i = 0; i < Constants.GAME_WIDTH; i += Constants.UNITSIZE) {
			BlockLava floor = new BlockLava(i, Game.HEIGHT - Constants.UNITSIZE);
			imgs.add(floor);
			blocks.add(floor);
			collidables.add(floor);
		}
		BlockPortal portal = new BlockPortal(0, Constants.GAME_HEIGHT / 2 - 128 - 64);
		imgs.add(portal);
		blocks.add(portal);
		//collidables.add(portal);
		
		double[] obstacleXYCoords = new double[]{0, 0, 1, 0, 2, 1, 4, 1, 5, -1, 7, -1, 8, 0, 10, 1, 12.5, -1, 13.5, -1, 14.5, 0.28,
				15.5, -0.71, 16.5, -0.71, 17.5, -0.71, 18.5, -0.71};
		int baseLevel = Game.HEIGHT / 2 - 64;
		for(int i = 0; i < obstacleXYCoords.length; i += 2) {
			BlockNether b = new BlockNether((int) (obstacleXYCoords[i] * 64), (int) (obstacleXYCoords[i + 1] * -64 + baseLevel));
			imgs.add(b);
			blocks.add(b);
			collidables.add(b);
		}
		// player.x = (int) (13.5 * 64);
		// player.y = (int) (-1 * -64 + baseLevel - player.height);
		BlockEndPortal endportal = new BlockEndPortal(Constants.GAME_WIDTH - 64, (int) (-0.71 * -64 + baseLevel - 128));
		imgs.add(endportal);
		blocks.add(endportal);
		imgs.add(player);
		
	}
	
	public void run() {
               
        while (!Display.isCloseRequested()) {        	
        	update();
        	render();
        	
            Display.update();
            Display.sync(Constants.SCREENFPS);
        }
 
        Display.destroy();
        System.exit(0);
	}

	protected void update() {
		playerMoveHandling();
    	player.yVelocity += Constants.GRAVITY;
    	player.collideStop(collidables);
    	
    	for(Block b : blocks) {
    		if(b instanceof BlockLava && player.isColliding(b)) {
    			player.x = playerSpawnX;
    			player.y = playerSpawnY;
    			break;
    		}
    		if(b instanceof BlockEndPortal && player.isColliding(b)) {
    			winGame = true;	// TODO switch to win screen
    		}
    	}
    	
    	for(VisibleObject o : imgs) { o.update(); }
	}

	protected void render() {
		Textures.render(Textures.nether_background);
		
		for(VisibleObject o : imgs) { o.render(); }
		
		if(winGame) {
    		Textures.render(Textures.sky); // TODO switch to win screen
    	}
	}
}