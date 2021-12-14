package spaceinvader;

public class Missile extends Sprite {
private static int MISSILE_SPEED ;
	
	// Constructor
	public Missile(int x, int y) {
		super(x, y);
		
		initMissile() ;
	}
	
	// Setter
	public static void setMissileSpeed(int speed) {
		MISSILE_SPEED = speed;
	}
	
	// Initialize missile 
	public void initMissile() {
		super.loadImage("images/bullet20.png");
		super.getImageDimension();
	}
	
	@Override // 
	public void move() {
		x += MISSILE_SPEED ;
		// They disappear after they reach the right window border
		if (x > Board.BOARD_WIDTH) 
			super.setVisible(false) ;
	}

}
