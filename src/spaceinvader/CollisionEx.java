package spaceinvader;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class CollisionEx extends JFrame {
	public CollisionEx() {
		initUI() ;
	}
	
	// Inisialisasi UI
	public void initUI() {
		
		add(new Board()) ;
		
		
		setResizable(false) ;
		pack() ;
		
		setTitle("Space Invader");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		// Membuat Event
		EventQueue.invokeLater(() -> {
			try {
				CollisionEx ex = new CollisionEx();
	            ex.setVisible(true);
			}catch(Exception e) {
                e.printStackTrace();
            }
            
        });
		
	}

}
