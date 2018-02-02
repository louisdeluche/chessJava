package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class Plateau extends JFrame {
	
	private Case[][] cases = new Case[8][8];
	public JPanel panelGrille = new JPanel();//conteneur de la grille	
	GridLayout grille = new GridLayout();//grille du plateau
	public JPanel caseblanc = new JPanel();//case blanche du plateau
	public JPanel casenoir = new JPanel();//case noire du plateau	
	public JLabel[][] tab = new JLabel[8][8];
	String dirImg = "src/images/";
	
	public Plateau () {
		for(int x = 0; x < 8; x++)
		{
			for(int y = 0; y < 8; y++)
			{
				cases[x][y] = new Case();
				tab[x][y] = new JLabel();
			
				//grille.add(tab[x][y]);
			}
		}
		
		init();
	}
	
	public void init()
	{
		//nouvellePartie();
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(565, 500));
		this.setTitle("Jeu d'Echecs");
		

		panelGrille.setBounds(new Rectangle(0, 0, 550, 465));
		panelGrille.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		panelGrille.setLayout(grille);
		grille.setColumns(8);
		grille.setRows(8);
		this.getContentPane().add(casenoir, null);
		this.getContentPane().add(caseblanc, null);
		this.getContentPane().add(panelGrille, null);	
		
		int a = 1;
        for (int ligne = 0; ligne < 8; ligne++) {
            a = a == 1 ? 0 : 1;
            for (int colonne = 0; colonne < 8; colonne++) {
                tab[colonne][ligne] = new JLabel(); // crÌöation du JLabel
                tab[colonne][ligne].setOpaque(true);
                panelGrille.add(tab[colonne][ligne]); // ajouter au Panel
                tab[colonne][ligne].setOpaque(true);
                tab[colonne][ligne].setHorizontalAlignment(SwingConstants.CENTER); // pour
                //tab[colonne][ligne].addMouseListener(gest); // ajouter l'ecouteur aux
                if ((colonne + 1) % 2 == a)
                {
                    tab[colonne][ligne].setIcon(new ImageIcon("src/images/PionNoir.gif"));
                    tab[colonne][ligne].setBackground(new Color(255, 255, 255));
                }
                else
                    tab[colonne][ligne].setBackground(new Color(100, 100, 100));

            }	
        }
	}
	
	public boolean cheminPossible(Piece piece, Deplacement d)
	{			
		if(piece.getNom() != "Cavalier" || piece.getNom() == "Roi")
		{
			return true;
		}
		else if (piece.getNom() == "Pion")
		{					
			return !cases[d.arrive.posX][d.arrive.posY].containPiece();
		}
		
		if(piece.getNom() == "Fou" || piece.getNom() == "Reine")
		{
			int departY = d.depart.posY;
			int departX = d.depart.posX;
			
			if(d.getDirectionX() == "droite" && d.getDirectionY() == "haut")
			{
				for(int y = departY,x = departX; y< d.arrive.posY; y++, x--)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}
			else if(d.getDirectionX() == "droite" && d.getDirectionY() == "bas")
			{
				for(int y = departY,x = departX; y< d.arrive.posY; y++, x++)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}
			else if(d.getDirectionX() == "gauche" && d.getDirectionY() == "haut")
			{
				for(int y = departY,x = departX; y> d.arrive.posY; y--, x--)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}		
			else if(d.getDirectionX() == "droite" && d.getDirectionY() == "haut")
			{
				for(int y = departY,x = departX; y> d.arrive.posY; y--, x++)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}	
			
			return true;
			
		}
		if(piece.getNom() == "Tour" || piece.getNom() == "Reine")
		{
			if(d.axeX)
			{
				int depart = d.depart.posY;
				int arrive = d.arrive.posY;
				if(d.getDirectionX() == "gauche")
				{
					depart = d.arrive.posY;
					arrive = d.depart.posY;
				}
				
				for(int i = depart; i < arrive; i++)
				{
					if(cases[d.depart.posX][i].containPiece()) return false;
					
				}	
				return true;
			}
			else if(d.axeY)
			{
				int depart = d.depart.posX;
				int arrive = d.arrive.posX;
				if(d.getDirectionX() == "haut")
				{
					depart = d.arrive.posX;
					arrive = d.depart.posX;
				}
				
				for(int i = depart; i < arrive; i++)
				{
					if(cases[i][d.depart.posY].containPiece())
					{
						return false;
					}
					
				}
				
				return true;
			}
		}

		return false;	
	}
	
	/*public Deplacement selectionCase()
	{
		//Recuperer les valeurs des positions de depart et d'arrivé
		//return new Deplacement(new Position(), new Position());
	}*/
	
	public void deplacerPiece(Piece piece, Deplacement d)
	{
		Case cible = cases[d.arrive.posX][d.arrive.posY];
		
		if(cible.containPiece() && cible.getPiece().getCouleur() != piece.getCouleur() && cheminPossible(piece, d) && piece.deplacementPossible(d))
		{
			if(cible.getPiece().getNom() == "Roi") finPartie();			
			cible.setPiece(piece);			
		}		
	}

	public void nouvellePartie()
	{
		String[] ordre = {"Tour", "Cavalier", "Fou", "Reine", "Roi", "Fou", "Cavalier", "Tour"};
		System.out.println(new File(dirImg+"charte.png").isFile());
		
		for(int i = 0; i < ordre.length; i++)
		{
			cases[1][i] = new Case(new Pion("noir"));
			cases[6][i] = new Case(new Pion("blanc"));
			tab[1][i].setIcon(new ImageIcon(dirImg+"PionNoir"));
			tab[6][i] = new JLabel(new ImageIcon(dirImg+"PionBlanc"));
			
			switch (ordre[i])
			{
				case "Tour":
					cases[0][i] = new Case(new Tour("noir") );
					cases[7][i] = new Case(new Tour("blanc"));	
					tab[0][i] = new JLabel(new ImageIcon(dirImg+"TourNoir"));
					tab[7][i] = new JLabel(new ImageIcon(dirImg+"TourBlanc"));					
				break;
				case "Cavalier":
					cases[0][i] = new Case(new Cavalier("noir"));
					cases[7][i] = new Case(new Cavalier("blanc"));	
					tab[0][i] = new JLabel(new ImageIcon(dirImg+"CavalierNoir"));
					tab[7][i] = new JLabel(new ImageIcon(dirImg+"CavalierBlanc"));								
				break;	
				case "Fou":
					cases[0][i] = new Case(new Fou("noir"));
					cases[7][i] = new Case(new Fou("blanc"));	
					tab[0][i] = new JLabel(new ImageIcon(dirImg+"FouNoir"));
					tab[7][i] = new JLabel(new ImageIcon(dirImg+"FouBlanc"));								
				break;	
				case "Reine":
					cases[0][i] = new Case(new Reine("noir"));
					cases[7][i] = new Case(new Reine("blanc"));	
					tab[0][i] = new JLabel(new ImageIcon(dirImg+"FouNoir"));
					tab[7][i] = new JLabel(new ImageIcon(dirImg+"FouBlanc"));								
				break;	
				case "Roi":
					cases[0][i] = new Case(new Roi("noir"));
					cases[7][i] = new Case(new Roi("blanc"));	
					tab[0][i] = new JLabel(new ImageIcon(dirImg+"RoiNoir"));
					tab[7][i] = new JLabel(new ImageIcon(dirImg+"RoiBlanc"));								
				break;					
			}
		}
		
	}
	
	public void finPartie()
	{
		//disable event
		System.out.println("Fin de partie");
	}

}
