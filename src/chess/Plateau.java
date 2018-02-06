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
		if(piece.getNom() != "Cavalier" || piece.getNom() == "Roi")
		{
			return true;
		}
		else if (piece.getNom() == "Pion")
		{					
			return !cases[d.arrive.posY][d.arrive.posX].containPiece();
		}
		
		if(piece.getNom() == "Fou" || piece.getNom() == "Reine")
		{
			int departY = d.depart.posY;
			int departX = d.depart.posX;
			
			if(d.getDirectionX() == "droite" && d.getDirectionY() == "haut")
			{
				for(int y = departY,x = departX; y< d.arrive.posY; y++, x--)
				{
					if(cases[y][x].containPiece()) return false;
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
					if(cases[y][x].containPiece()) return false;
				}
			}		
			else if(d.getDirectionX() == "droite" && d.getDirectionY() == "haut")
			{
				for(int y = departY,x = departX; y> d.arrive.posY; y--, x++)
				{
					if(cases[y][x].containPiece()) return false;
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
					if(cases[i][d.depart.posX].containPiece()) return false;
					
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
					if(cases[d.depart.posY][i].containPiece())
					{
						return false;
					}
					
				}
				
				return true;
			}
		}

		return false;	
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
				
		/*System.out.println(color);
		System.out.println(piece.deplacementPossible(d));*/
		if(color && piece.deplacementPossible(d))
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
		
		//initialise toute les variables 

		public void mouseClicked(MouseEvent eve) {
			
	
			

			if (eve.getSource() instanceof JLabel) // donc on a cliqué sur un Label
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
				
				//System.out.println(cases[colonneClic][ligneClic]);
				
				/*
				if((cases[colonneClic][ligneClic].getPiece() != null | pieceTampon != null) )
				{
					//si le tampon est null
					if(pieceTampon == null )
					{
						//si c'est au tour de la couleur de controle ˆ jouer
						if(cases[colonneClic, ligneClic].getPiece().getCouleur().equals(couleurControle)){
							//J'initialise la piece tampon a la piece sur laquelle on a cliquŽ
							pieceTampon = e.getCase(colonneClic, ligneClic).getPiece();
							iconeTampon = (ImageIcon)tab[colonneClic][ligneClic].getIcon();
							temp = new Position(colonneClic,ligneClic);
							tab[colonneClic][ligneClic].setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0),5));
						}
						
					}
					else
					{
						//je crŽŽ un dŽplacement
						Deplacement deplacement = new Deplacement(temp, new Position(colonneClic,ligneClic));
						//je vŽrifie si le dŽplacement est valide, si le chemin est possible et si il est possible, pour un pion de manger la piece
						if ((pieceTampon.estValide(deplacement) && plateau.cheminPossible(deplacement)) | e.captureParUnPionPossible(deplacement))
						{
							//je crŽŽ un jLabel avec l'ic™ne de la pi�ce manger
							JLabel manger = new JLabel(tab[colonneClic][ligneClic].getIcon());
							manger.setHorizontalAlignment(SwingConstants.CENTER);
							
							//je l'ajoute au bon jPanel
							if (couleurControle.equals("blanc"))
								panelblanc.add(manger);
							else		
								panelnoir.add(manger);
							
							//je vŽrifie si la pi�ce manger est un roi, si oui le jeu est terminŽ et L'utilisateurs 
							//peut choisir si il veut continuer a jouer ou non
							if(e.getCase(colonneClic, ligneClic).getPiece() instanceof Roi)
							{
								if(JOptionPane.showConfirmDialog(null, "FŽlicitation vous avez gagnŽ ! DŽsirez-vous jouer de nouveau ?\n", "Mine !", JOptionPane.YES_NO_OPTION) == 0){
									RAZ();
									tab[temp.getColonne()][temp.getLigne()].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),0));
								}

								else
									System.exit(0);

							}
							else//si on dŽpose la piece sur une case vide
							{
								//on met le tampon sur la case vide et on vide le tampon apr�s
								e.getCase(temp.getColonne(), temp.getLigne()).setPiece(null);
								tab[temp.getColonne()][temp.getLigne()].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),0));

								tab[colonneClic][ligneClic].setIcon(iconeTampon);
								e.getCase(colonneClic, ligneClic).setPiece(pieceTampon);
								tab[temp.getColonne()][temp.getLigne()].setIcon(null);


								pieceTampon = null;
								iconeTampon = null;
								temp = null;

								couleurControle = couleurControle.equals("blanc") ? "noir" : "blanc";
								champTexte.setText("C'est le tour aux " + couleurControle);
							}
						}
						else
						{
							tab[temp.getColonne()][temp.getLigne()].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),0));
							pieceTampon = null;
							iconeTampon = null;
							temp = null;

						}
					
					}

				}				

			}
			/*
				for (int i = 0; i < 8; i++)
					//je dŽtermine sur quelle Jlabel on a cliquŽ
					for (int j = 0; j < 8; j++) 
						if (eve.getSource() == tab[j][i]) {
							ligneClic = i;
							colonneClic = j;
						}
					//si on a cliquŽ sur une case non vide et que le tampon n'est pas null
					if((plateau.getCase(colonneClic, ligneClic).getPiece() != null | pieceTampon != null) )
					{
						//si le tampon est null
						if(pieceTampon == null )
						{
							//si c'est au tour de la couleur de controle ˆ jouer
							if(plateau.getCase(colonneClic, ligneClic).getPiece().getCouleur().equals(couleurControle)){
								//J'initialise la piece tampon a la piece sur laquelle on a cliquŽ
								pieceTampon = e.getCase(colonneClic, ligneClic).getPiece();
								iconeTampon = (ImageIcon)tab[colonneClic][ligneClic].getIcon();
								temp = new Position(colonneClic,ligneClic);
								tab[colonneClic][ligneClic].setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0),5));
							}
							
						}
						else
						{
							//je crŽŽ un dŽplacement
							Deplacement deplacement = new Deplacement(temp, new Position(colonneClic,ligneClic));
							//je vŽrifie si le dŽplacement est valide, si le chemin est possible et si il est possible, pour un pion de manger la piece
							if ((pieceTampon.estValide(deplacement) && plateau.cheminPossible(deplacement)) | e.captureParUnPionPossible(deplacement))
							{
								//je crŽŽ un jLabel avec l'ic™ne de la pi�ce manger
								JLabel manger = new JLabel(tab[colonneClic][ligneClic].getIcon());
								manger.setHorizontalAlignment(SwingConstants.CENTER);
								
								//je l'ajoute au bon jPanel
								if (couleurControle.equals("blanc"))
									panelblanc.add(manger);
								else		
									panelnoir.add(manger);
								
								//je vŽrifie si la pi�ce manger est un roi, si oui le jeu est terminŽ et L'utilisateurs 
								//peut choisir si il veut continuer a jouer ou non
								if(e.getCase(colonneClic, ligneClic).getPiece() instanceof Roi)
								{
									if(JOptionPane.showConfirmDialog(null, "FŽlicitation vous avez gagnŽ ! DŽsirez-vous jouer de nouveau ?\n", "Mine !", JOptionPane.YES_NO_OPTION) == 0){
										RAZ();
										tab[temp.getColonne()][temp.getLigne()].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),0));
									}

									else
										System.exit(0);

								}
								else//si on dŽpose la piece sur une case vide
								{
									//on met le tampon sur la case vide et on vide le tampon apr�s
									e.getCase(temp.getColonne(), temp.getLigne()).setPiece(null);
									tab[temp.getColonne()][temp.getLigne()].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),0));

									tab[colonneClic][ligneClic].setIcon(iconeTampon);
									e.getCase(colonneClic, ligneClic).setPiece(pieceTampon);
									tab[temp.getColonne()][temp.getLigne()].setIcon(null);


									pieceTampon = null;
									iconeTampon = null;
									temp = null;

									couleurControle = couleurControle.equals("blanc") ? "noir" : "blanc";
									champTexte.setText("C'est le tour aux " + couleurControle);
								}
							}
							else
							{
								tab[temp.getColonne()][temp.getLigne()].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),0));
								pieceTampon = null;
								iconeTampon = null;
								temp = null;

							}
						
						}

					}*/
					
				}

		}
	}	

}


