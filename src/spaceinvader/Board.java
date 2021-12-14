package spaceinvader;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
	private Timer timer;	
    private SpaceShip spaceship;	// Spaceship object 
    private List<Alien> aliens;		// List of Aliens
    protected static boolean ingame;// Status in game or not
    private final int ICRAFT_X = 0;// initial x  position of Spacehip
    private final int ICRAFT_Y = 200;// initial y  position of Spacehip
    protected static int BOARD_WIDTH = 500 ; // width of window
    protected static int BOARD_HEIGHT = 400 ;// height of window
    private final int DELAY = 15;	//delay time
    private int countAlien ;		// total of aliens
    private boolean winStatus ;		// win or lose status
    
    private int[][] pos ;			//Existing alien position array
    
    // Constructor
	public Board() {
		initBoard() ;
	}
	
	// Initialize Board Window
	public void initBoard() {
		//Choose Level
		this.chooseDifficulty();
		
		// Key listener to detect input from keyboard
		addKeyListener(new TAdapter()) ;
		setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;
        
        // Window Size
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        
        //Make Spaceship Object
        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        // initialize Aliens 
        initAliens();
        
        // make time object
        timer = new Timer(DELAY, this);
        timer.start();
	}
	
	public void chooseDifficulty() {
		int aliens = 0 ;
		// Level Option
		String[] options1 = {"Level 1", "Level 2", "Level 3"} ;
		ImageIcon icon = new ImageIcon("images/ufoIcon.png");
		
		// JOptionPane to choose difficulties
		int input1 = JOptionPane.showOptionDialog(null, 
				"Choose Level", 
				"'Space' Invader", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options1, options1[0]) ;
		
		// for each level alien and missile speed as well as total of aliens varies depends on the level
		if (input1 == 0) {
			Alien.setAlienSpeed(1) ;
			Missile.setMissileSpeed(4) ;
			aliens = 20 ;
			
		}
		else if (input1 == 1) {
			Alien.setAlienSpeed(3) ;
			Missile.setMissileSpeed(4) ;
			aliens = 25 ;
		}
		else if (input1 == 2) {
			Alien.setAlienSpeed(5) ;
			Missile.setMissileSpeed(4) ;
			aliens = 30 ;
		}
		else //if there is no input, it exits 
			System.exit(0) ;
		
		this.countAlien = aliens;
		
		// randomize Alien's Position
		this.pos = randomizeAlien() ;
	}
	
	// Method to randomize alien's positon
	public int[][] randomizeAlien() {
		int x, y ; 
		int [][] aliens = new int[this.countAlien][2] ;
		Random rand = new Random() ;
		
		for (int i = 0 ; i < this.countAlien ; i++) {
			x = 500 + rand.nextInt(2500 - 510) ;
			y = 29 + rand.nextInt(370 - 29) ;
			
			aliens[i][0] = x ;
			aliens[i][1] = y ;
		}
		
		return aliens ;
	}
	
	//The aliens take their initial positions from the pos array.
	public void initAliens() {
        
        aliens = new ArrayList<>();
        
        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }
    
	@Override // either draw game sprite or write the game over message
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //If the game is still going
        if (ingame) {
        	// draw object
            drawObjects(g);

        } else { // If game ends
        	// draw Game Over
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }
	
	private void drawObjects(Graphics g) {
		// draw SpaceShip
        if (spaceship.isVisible()) {
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                    this);
        }
        
        List<Missile> ms = spaceship.getMissiles();
        //draw missile
        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(), 
                        missile.getY(), this);
            }
        }
        // draw Alien
        // they are drawn only if they have not been previously destroyed
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }
        // draw how many aliens are left
        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
    }
	
	private void drawGameOver(Graphics g) {
		
        String msg = "Game Over, You Lost";
        if (this.winStatus) // if the status is true
        	msg = "Congratulation, You Win!" ;
        Font big = new Font("Comic Sans MS", Font.BOLD, 18);
        FontMetrics fm = getFontMetrics(big);
        
        g.setColor(Color.white);
        g.setFont(big);
        g.drawString(msg, (BOARD_WIDTH - fm.stringWidth(msg)) / 2,
                BOARD_HEIGHT / 2);
        
        // Option to play again or exit the game
        msg = "Press 'Space' to Play Again \t Press 'esc' to Exit" ;
        Font small = new Font("Comic Sans MS", Font.ITALIC, 14);
        fm = getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (BOARD_WIDTH - fm.stringWidth(msg)) / 2,
                BOARD_HEIGHT / 2 + 22);
    }
	
	//To determine if player want to play again or not
	public void lastEvent(KeyEvent e) {
		int key = e.getKeyCode() ;
		
		if (key == KeyEvent.VK_SPACE) // press space to play again
			initBoard() ;
		
		if (key == KeyEvent.VK_ESCAPE) // press escape to exit game
			System.exit(0) ;
	}
	
	@Override // action in game
    public void actionPerformed(ActionEvent e) {

        inGame(); // check status ingmae

        updateShip();	// update position of spaceship
        updateMissiles(); // update position of missile
        updateAliens();	// update position of aliens

        checkCollisions();	// check if there is any collisions
        
        repaint();	 
    }
	
	// to check status in game
    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }
    
    // to update position of Spaceship
    private void updateShip() {

        if (spaceship.isVisible()) {
            
            spaceship.move();
        }
    }
    
    // to update position of Missiles
    private void updateMissiles() {

        List<Missile> ms = spaceship.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else { // Jika tidak visible, hapus
                ms.remove(i);
            }
        }
    }
    
    //to update position of Aliens
    private void updateAliens() {

    	// If there is no more aliens left, game ends.
        if (aliens.isEmpty()) {
        	this.winStatus = true ;
            ingame = false;
            return;
        }

        for (int i = 0; i < aliens.size(); i++) {

            Alien a = aliens.get(i);
            
            if (a.isVisible()) {
                a.move();
            } else { // Jika tidak visible, hapus
                aliens.remove(i);
            }
        }
    }
    
    // Method to check for possible collisions
    public void checkCollisions() {
    	
    	// get the rectangles of the objects Spacehip
        Rectangle r3 = spaceship.getBounds();

        for (Alien alien : aliens) {
            // get the rectangles of the objects Aliens
            Rectangle r2 = alien.getBounds();
            
            // checks if the two rectangles intersect
            if (r3.intersects(r2)) {
                this.winStatus = false ;
                spaceship.setVisible(false);
                alien.setVisible(false);
                ingame = false;
            }
        }

        List<Missile> ms = spaceship.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (Alien alien : aliens) {

                Rectangle r2 = alien.getBounds();
                // checks if the two rectangles intersect
                //if the intersect (collide) both dissapear 
                if (r1.intersects(r2)) {
                    
                    m.setVisible(false);
                    alien.setVisible(false);
                }
            }
        }
    }
    
    private class TAdapter extends KeyAdapter {

        @Override // Method to detect keyboard button released 
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override // Method to detecte keyboard key is pressed
        public void keyPressed(KeyEvent e) {
        	if (Board.ingame) // if the jika masih in game, ambil input keyboard spaceship
        		spaceship.keyPressed(e);
        	else // Jika game sudah selesai, tanyakan ingin mengulang game atau tidak 
        		lastEvent(e);
        }
    }
	

}
