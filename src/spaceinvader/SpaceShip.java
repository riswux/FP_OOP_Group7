package spaceinvader;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {
	
		// Delta x and y for the movement of spaceship
		private int dx ;
		private int dy ;
		
		//All the missiles fired by the spaceship are stored in the missiles list
		private List<Missile> missiles ;
		
		// Constructor
		public SpaceShip(int x, int y) {
			super(x, y);
			initCraft() ;
		}
		
		// Initialize Spaceship
		public void initCraft() {
			// make list of missiles
			missiles = new ArrayList<> () ;
			
			// get spaceship image and dimension
			super.loadImage("images/space-shuttle.png");
			super.getImageDimension();
		}
		
		
		@Override // Method for movement limitation of spaceship
		public void move() {

	        x += dx;
	        y += dy;
	        
	        if (x < 1) // left limit
	            x = 1 ;
	        if(x > 470)//right limit
	        	x = 470;
	        if (y < 1) // upper limit
	            y = 1 ;
	        if (y > 370) // lower limit
	        	y = 370 ;
	        
	        
	    }
		
		// Getter Missile
		public List<Missile> getMissiles() {
	        return missiles;
	    }
		
		// Method to retrieve the key pressed on the keyboard
		public void keyPressed(KeyEvent e) {

	        int key = e.getKeyCode();
	        // space to fire missile
	        if (key == KeyEvent.VK_SPACE)
	            fire();
	        // left arrow or A to move left
	        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) 
	            dx = -3;
	        // right arrow or D to move right
	        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) 
	            dx = 3;
	        // up arrow or W to move upward
	        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
	        	dy = -3;
	        //down arrow or S to move downward
	        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) 
	            dy = 3;
	    }
		
		// When we fire a missile, a new Missile object is added to the missiles list.
		public void fire() {
		    missiles.add(new Missile(x + width, y + height / 2));
		}
		
		// Method to return dx dy state when keyboard key is released
		public void keyReleased(KeyEvent e) {

	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A ) 
	            dx = 0;

	        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) 
	            dx = 0;

	        if (key == KeyEvent.VK_UP|| key == KeyEvent.VK_W) 
	            dy = 0;

	        if (key == KeyEvent.VK_DOWN|| key == KeyEvent.VK_S) 
	            dy = 0;
	  }
}


