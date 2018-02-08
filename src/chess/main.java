package chess;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class main extends JFrame{

	public static void main(String[] args) {
		Plateau jeu = new Plateau();
		jeu.setVisible(true);
		jeu.setLocation(100, 130);
		jeu.setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}

}
