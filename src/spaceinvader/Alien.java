package spaceinvader;

public class Alien extends Sprite{
	private static int ALIEN_SPEED ;
	
	// Constructor
	public Alien(int x, int y) {
		super(x, y);
		
		initAlien() ;
	}
	
	// Setter
	public static void setAlienSpeed(int speed) {
		ALIEN_SPEED = speed;
	}
	
	// Initialize alien
	public void initAlien() {
		super.loadImage("images/ufo.png");
		super.getImageDimension();
	}

	@Override // Method to move aliens
	public void move() {
		//Aliens return to the screen on the right side after they have disappeared on the left.
		if (x < -30) { 
	        x = Board.BOARD_WIDTH;
	    }

	    x -= ALIEN_SPEED;
	}

}
