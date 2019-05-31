package game.game_objects;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import game.Textures;
import utilities.Constants;

public class EntityPlayer extends Entity {

	private Texture[] WLFrames = Textures.playerWLFrames, WRFrames = Textures.playerWRFrames, 
			JFrames = Textures.playerJFrames, FFrames = Textures.playerFFrames;
	
	public EntityPlayer(int x, int y, int animationFPS) {
		super(x, y, animationFPS);
		this.width = 64;
		this.height = 128;
		isJumping = false;
		isMoving = false;
		reverseAnim = false;
		animator.setFrames(WRFrames);	// default
	}
	
	public void update() {	
		handleAnimations();
		super.update();
	}
	
	public void render() {
		Textures.render(selectAnimationFrame(), x, y, Constants.UNITSIZE, Constants.UNITSIZE*2);
	}
	
	protected void handleAnimations() {
		super.handleAnimations();
	
		// Vertical movement animations take precedence over horizontal movement animations
		if (yVelocity < 0) {
			reverseAnim = true;
			animator.setFrames(JFrames);
		} else if (yVelocity > 0) {
			reverseAnim = true;
			animator.setFrames(FFrames);
		} else {	
			if (!(animator.getCurrentFrames() == WRFrames || animator.getCurrentFrames() == WLFrames) && !isJumping) { // if finished falling
				reverseAnim = false;
				animator.setFrames(WRFrames); // default 
			}

			if (xVelocity > 0) {
				reverseAnim = true;
				animator.setFrames(WRFrames);
			} else if (xVelocity < 0) {
				reverseAnim = true;
				animator.setFrames(WLFrames);
			} 
		}
	}

	public void collideStop(ArrayList<VisibleObject> collidables) {
		this.x += 13;
		this.width -= 26;
		this.height -= 5;
		super.collideStop(collidables);
		this.x -= 13;
		this.width += 26;
		this.height += 5;	
	}
}
