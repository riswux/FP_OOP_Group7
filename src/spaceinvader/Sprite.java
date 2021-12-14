package spaceinvader;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class Sprite {
	
	protected int x ;	 		// X coordinate
	protected int y ;			// Y coordinate
	protected int width ;		// width object
	protected int height ;		// height object 
	private boolean visible ;	// visible object
	private Image img ;			// image object
	
	// Abstract method
	public abstract void move() ;
	
	// Constructor
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		this.visible = true;
	}
	
	//Get dimension of image object
	protected void getImageDimension() {
		width = img.getWidth(null);
        height = img.getHeight(null);
	}
	
	// load image from file
	protected void loadImage(String filename) {
		ImageIcon image = new ImageIcon(filename) ;
		img = image.getImage() ;
	}
	
	// ---Getter dan Setter---//
	public Image getImage() {
		return img;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	
	// Method to get bounds of image
	public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}