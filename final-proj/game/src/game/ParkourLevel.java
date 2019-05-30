package game;

import java.util.ArrayList;

import game.game_objects.VisibleObject;
import game.game_objects.blocks.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


public class ParkourLevel extends Level {
	
	public static void levelOneSetup() {
		player.x = 0;
		player.y = Game.HEIGHT / 2 - player.height - 64;
		for(int i = 0; i < Constants.GAME_WIDTH; i += new Block().width) {
			BlockLava floor = new BlockLava(i, Game.HEIGHT - new Block().height);
			imgs.add(floor);
			blocks.add(floor);
			collidables.add(floor);
		}
		BlockPortal portal = new BlockPortal(0, Constants.GAME_HEIGHT / 2 - 128 - 64);
		imgs.add(portal);
		blocks.add(portal);
		//collidables.add(portal);
		
		double[] obstacleXYCoords = new double[]{0, 0, 1, 0, 2, 1, 4, 1, 5, -1, 7, -1, 8, 0, 10, 1, 12.5, -1, 13.5, -1, 14.5, 0.29,
				15.5, -0.71, 16.5, -0.71, 17.5, -0.71, 18.5, -0.71};
		int baseLevel = Game.HEIGHT / 2 - 64;
		for(int i = 0; i < obstacleXYCoords.length; i += 2) {
			BlockNether b = new BlockNether((int) (obstacleXYCoords[i] * 64), (int) (obstacleXYCoords[i + 1] * -64 + baseLevel));
			imgs.add(b);
			blocks.add(b);
			collidables.add(b);
		}
//		player.x = (int) (13.5 * 64);
//		player.y = (int) (-1 * -64 + baseLevel - player.height);
		BlockEndPortal endportal = new BlockEndPortal(Constants.GAME_WIDTH - 64, (int) (-0.71 * -64 + baseLevel - 128));
		imgs.add(endportal);
		blocks.add(endportal);
		imgs.add(player);
		
	}
	
	public static void main(String[] args) {
       
		setup();
		levelOneSetup();   
        
        while (!Display.isCloseRequested()) {
        	Textures.render(Textures.nether_background);
        	
        	playerMoveHandling();
        	
        	player.yVelocity += Constants.GRAVITY;

        	player.collideStop(collidables);
        	
        	for(Block b : blocks) {
        		if(b instanceof BlockLava && player.isColliding(b)) {
        				player.x = 0;
        				player.y = Game.HEIGHT / 2 - player.height - 64;
        				break;
        		}
        		if(b instanceof BlockEndPortal && player.isColliding(b)) {
        			gameOver = true;
        		}
        	}

        	player.update();
        	
        	System.out.println(Mouse.getX() + " " + Mouse.getY());
        	
        	for(VisibleObject o : imgs) {
        		o.render();
        	}
        	
        	if(gameOver) {
        		Textures.render(Textures.sky);
        	}
            Display.update();
            Display.sync(60);
        }
 
        Display.destroy();
        System.exit(0);
	}
}