package utilities;

import org.newdawn.slick.opengl.Texture;

public class Animator {
	
	private int fps;
	private int animationFrame = 0;
	private double currTime, animateTime, deltaTime;
	
	private Texture[] frames;
	
	private boolean left, right;
		
	public Animator(int fps) {
		this.fps = fps;
		deltaTime = 1000/fps;
		currTime = System.currentTimeMillis();
		animateTime = System.currentTimeMillis();
		left = false;
		right = true;
	}
	
	public Animator(int fps, Texture[] frames) {
		this.fps = fps;
		this.frames = frames;
		deltaTime = 1000/fps;
		currTime = System.currentTimeMillis();
		animateTime = System.currentTimeMillis();
		left = false;
		right = true;
	}
	
	public void update() {
		currTime = System.currentTimeMillis();
	}
	
	public Texture animFrame(boolean animate, boolean reverse) {
		if (frames.length == 0 || !animate) {
			return frames[0];
		} else {
			 if (currTime - animateTime >= deltaTime) {
				 if (reverse) {
					 if (left) {
						 if (animationFrame == 0) { 
							 right = true; 
							 left = false;
						 } else { animationFrame--; }
					 } else if (right) {
						 if (animationFrame >= Constants.ANIMFRAMELENGH-1) { 
							 left = true; 
							 right = false;
						 } else { animationFrame++; }
					 }
				 } else {
					 if (animationFrame >= Constants.ANIMFRAMELENGH-1) { animationFrame = 0; }
					 else { animationFrame++; }
				 }
				 animateTime = currTime;
			 } 
		}
		return frames[animationFrame];
	}
	
	// getters/setters
	public Texture[] getCurrentFrames() { return frames; }
	public void resetFrames() { animationFrame = 0; }
	public void setFrames(Texture[] frames) { this.frames = frames; }
}