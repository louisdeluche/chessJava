package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
				cases[y][x] = new Case();
				tab[y][x] = new JLabel();
			}
		}
		
		init();
	}
	
	public void init()
	{
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
		
		GestionnaireEvenement eventListenner = new GestionnaireEvenement();
		
		int a = 1;
        for (int ligne = 0; ligne < 8; ligne++) {
            a = a == 1 ? 0 : 1;
            for (int colonne = 0; colonne < 8; colonne++) {
                tab[colonne][ligne] = new JLabel(); // création du JLabel
                tab[colonne][ligne].setOpaque(true);
                panelGrille.add(tab[colonne][ligne]); // ajouter au Panel
                tab[colonne][ligne].setOpaque(true);
                tab[colonne][ligne].setHorizontalAlignment(SwingConstants.CENTER); // pour
                tab[colonne][ligne].addMouseListener(eventListenner); // ajouter l'ecouteur aux
                if ((colonne + 1) % 2 == a)
                {
                    tab[colonne][ligne].setBackground(new Color(255, 255, 255));
                }
                else
                    tab[colonne][ligne].setBackground(new Color(100, 100, 100));

            }	
        }
        
		nouvellePartie();        
	}
	
	public boolean cheminPossible(Piece piece, Deplacement d)
	{		
		if(piece.getNom() == "Cavalier" || piece.getNom() == "Roi")
		{
			return true;
		}
		else if (piece.getNom() == "Pion")
		{	
			if((d.deplacementX == 1 && d.deplacementY == 1) && !cases[d.arrive.posX][d.arrive.posY].containPiece())
			{
				return false;
			}
			else if(d.deplacementX == 0 && d.deplacementY > 0)
			{
				int depart = d.depart.posY+1;
				int arrive = d.arrive.posY;
				
				if(depart > arrive)
				{
					depart = d.arrive.posY;
					arrive = d.depart.posY;
				}
				for(int i=depart;i < arrive; i++ )
				{
					if(cases[d.depart.posX][i].containPiece()) return false;
				}
			}
			
			return true;
		}
		
		if(piece.getNom() == "Fou" || (piece.getNom() == "Reine" && d.deplacementX != 0 && d.deplacementY != 0))
		{
			int departY = d.depart.posY;
			int departX = d.depart.posX;
			
			if(d.getDirectionX() == "droite" && d.getDirectionY() == "haut")
			{
				for(int y = departY-1,x = departX+1; y> d.arrive.posY; y--, x++)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}
			else if(d.getDirectionX() == "droite" && d.getDirectionY() == "bas")
			{
				for(int y = departY+1,x = departX+1; y< d.arrive.posY; y++, x++)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}
			else if(d.getDirectionX() == "gauche" && d.getDirectionY() == "haut")
			{
				for(int y = departY-1,x = departX-1; y> d.arrive.posY; y--, x--)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}		
			else if(d.getDirectionX() == "gauche" && d.getDirectionY() == "bas")
			{

				for(int y = departY+1,x = departX-1; y< d.arrive.posY; y++, x--)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}	
		}
		if(piece.getNom() == "Tour" || (piece.getNom() == "Reine" && ((d.deplacementX == 0 && d.deplacementY > 1) || (d.deplacementX > 1 && d.deplacementY == 0))))
		{			
			if(d.axeX)
			{				
				int depart = d.depart.posX + 1;
				int arrive = d.arrive.posX;
				if(d.getDirectionX() == "gauche")
				{
					depart = d.arrive.posX;
					arrive = d.depart.posX;
				}
				
				for(int i = depart; i < arrive; i++)
				{
					if(cases[i][d.depart.posY].containPiece()) return false;					
				}	
				return true;
			}
			else if(d.axeY)
			{
				int depart = d.depart.posY+1;
				int arrive = d.arrive.posY;

				if(d.getDirectionY() == "haut")
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
		}

		return true;	
	}
	
	public boolean deplacerPiece(Piece piece, Deplacement d)
	{
		Case cible = cases[d.arrive.posX][d.arrive.posY];
	
		//Permet de savoir si la couleur est la meme que le pion joué
		boolean color = true;
		if (cible.containPiece())
		{
			color = !cible.getPiece().getCouleur().equals(piece.getCouleur());
		}
		if(color && piece.deplacementPossible(d) && cheminPossible(piece, d))
		{
			if(cible.containPiece() && cible.getPiece().getNom() == "Roi") finPartie();			
			cible.setPiece(piece);		
			return true;
		}	
		return false;
	}

	public void nouvellePartie()
	{
		String[] ordre = {"Tour", "Cavalier", "Fou", "Reine", "Roi", "Fou", "Cavalier", "Tour"};
		
		for(int i = 0; i < ordre.length; i++)
		{
			cases[i][1] = new Case(new Pion("noir"));
			cases[i][6] = new Case(new Pion("blanc"));
			tab[i][1].setIcon(new ImageIcon(dirImg+"PionNoir.gif"));
			tab[i][6].setIcon(new ImageIcon(dirImg+"PionBlanc.gif"));
			
			switch (ordre[i])
			{
				case "Tour":
					cases[i][0] = new Case(new Tour("noir") );
					cases[i][7] = new Case(new Tour("blanc"));	
					tab[i][0].setIcon(new ImageIcon(dirImg+"TourNoir.gif"));
					tab[i][7].setIcon(new ImageIcon(dirImg+"TourBlanc.gif"));					
				break;
				case "Cavalier":
					cases[i][0] = new Case(new Cavalier("noir"));
					cases[i][7] = new Case(new Cavalier("blanc"));	
					tab[i][0].setIcon(new ImageIcon(dirImg+"CavalierNoir.gif"));
					tab[i][7].setIcon(new ImageIcon(dirImg+"CavalierBlanc.gif"));								
				break;	
				case "Fou":
					cases[i][0] = new Case(new Fou("noir"));
					cases[i][7] = new Case(new Fou("blanc"));	
					tab[i][0].setIcon(new ImageIcon(dirImg+"FouNoir.gif"));
					tab[i][7].setIcon(new ImageIcon(dirImg+"FouBlanc.gif"));								
				break;	
				case "Reine":
					cases[i][0] = new Case(new Reine("noir"));
					cases[i][7] = new Case(new Reine("blanc"));	
					tab[i][0].setIcon(new ImageIcon(dirImg+"ReineNoir.gif"));
					tab[i][7].setIcon(new ImageIcon(dirImg+"ReineBlanc.gif"));								
				break;	
				case "Roi":
					cases[i][0] = new Case(new Roi("noir"));
					cases[i][7] = new Case(new Roi("blanc"));	
					tab[i][0].setIcon(new ImageIcon(dirImg+"RoiNoir.gif"));
					tab[i][7].setIcon(new ImageIcon(dirImg+"RoiBlanc.gif"));								
				break;					
			}
		}
		
	}
	
	public void finPartie()
	{
		setVisible(false);
		System.out.println("Fin de partie");
	}
	
	class GestionnaireEvenement extends MouseAdapter {

		Piece pieceTampon = null;
		ImageIcon iconeTampon;
		int ligneClic;
		int colonneClic;
		Position depart, arrivee;
		String couleurControle = "blanc";
		Position temp = null;



		/** methode s'excutant si l'on clique sur la surface de jeu. La methode determine ensuite ou est-ce que l'on cliquer
		 * et fait les action en consequence
		 *
		 */		
		public void mouseClicked(MouseEvent eve) {
			
			if (eve.getSource() instanceof JLabel) //clique sur un Label
			{

				for (int i = 0; i < 8; i++)
				{
					for (int j = 0; j < 8; j++) {
						if (eve.getSource() == tab[j][i]) {
							ligneClic = i;
							colonneClic = j;
						}
					}		
				}				

				
				if((cases[colonneClic][ligneClic].getPiece() != null || pieceTampon != null))
				{
					Piece cible = cases[colonneClic][ligneClic].getPiece();
					if(cases[colonneClic][ligneClic].containPiece() && cases[colonneClic][ligneClic].getPiece().getCouleur().equals(couleurControle))
					{
						if(pieceTampon != null)
						{	
							tab[temp.posX][temp.posY].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),0));	
						}
						pieceTampon = cible;
						iconeTampon = (ImageIcon)tab[colonneClic][ligneClic].getIcon();
						temp = new Position(colonneClic,ligneClic);
						tab[colonneClic][ligneClic].setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0),5));
					}					
					else if(pieceTampon != null)
					{
						Deplacement deplacement = new Deplacement(new Position(colonneClic,ligneClic), temp);
						if(deplacerPiece(pieceTampon, deplacement))
						{
							cases[temp.posX][temp.posY].setPiece(null);
							tab[temp.posX][temp.posY].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),0));

							tab[colonneClic][ligneClic].setIcon(iconeTampon);
							cases[colonneClic][ligneClic].setPiece(pieceTampon);
							tab[temp.posX][temp.posY].setIcon(null);
							
							pieceTampon = null;
							iconeTampon = null;
							temp = null;

							couleurControle = couleurControle.equals("blanc") ? "noir" : "blanc";
						}												
					}					
				}
			}
		}
	}	
}


