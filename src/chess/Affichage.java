package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.EtchedBorder;


public class Affichage extends JFrame{
	public Plateau plateau;
	
	public JPanel panelGrille = new JPanel();//conteneur de la grille
		JLabel[][] tab = new JLabel[8][8];
	
	GridLayout grille = new GridLayout();//grille du plateau

	public JPanel caseblanc = new JPanel();//case blanche du plateau
	public JPanel casenoir = new JPanel();//case noire du plateau
	
	public Affichage() 
	{
		try {
			affichageInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void affichageInit() {

		
	/*	for(int x = 0; x < 8; x++)
		{
			for(int y = 0; y < 8; y++)
			{
				tab[x][y] = new JLabel();
			}
		}*/
		
		
		/*
			String[] ordre = {"Tour", "Cavalier", "Fou", "Reine", "Roi", "Fou", "Cavalier", "Tour"};
		
	
		
		for(int i = 0; i < ordre.length; i++)
		{
			
			String dir = "src/images/";
			tab[1][i].setIcon(new ImageIcon(dir+"PionNoir.gif"));
			tab[6][i].setIcon(new ImageIcon(dir+"PionBlanc.gif"));
			
			switch (ordre[i])
			{
				case "Tour":
					tab[0][i].setIcon(new ImageIcon(dir+"TourNoir.gif"));
					tab[7][i].setIcon(new ImageIcon(dir+"TourBlanc.gif"));					
				break;
				case "Cavalier":	
					tab[0][i].setIcon(new ImageIcon(dir+"CavalierNoir.gif"));
					tab[7][i].setIcon(new ImageIcon(dir+"CavalierBlanc.gif"));							
				break;	
				case "Fou":
					tab[0][i].setIcon(new ImageIcon(dir+"FouNoir.gif"));
					tab[7][i].setIcon(new ImageIcon(dir+"FouBlanc.gif"));							
				break;	
				case "Reine":	
					tab[0][i].setIcon(new ImageIcon(dir+"ReineNoir.gif"));
					tab[7][i].setIcon(new ImageIcon(dir+"ReineBlanc.gif"));							
				break;	
				case "Roi":
					tab[0][i].setIcon(new ImageIcon(dir+"RoiNoir.gif"));
					tab[7][i].setIcon(new ImageIcon(dir+"RoiBlanc.gif"));							
				break;					
			}
		}
		
		*/
		;
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
		
		//J'attribue la couleur aux JLabels
			/*int a = 1;
			for (int ligne = 0; ligne < 8; ligne++) {
				a = a == 1 ? 0 : 1;
				for (int colonne = 0; colonne < 8; colonne++) {
					tab[colonne][ligne] = new JLabel(); // création du JLabel
					tab[colonne][ligne].setOpaque(true);//opacité : 100%;
					panelGrille.add(tab[colonne][ligne]); // ajouter au Panel
					tab[colonne][ligne].setOpaque(true);
					tab[colonne][ligne].setHorizontalAlignment(SwingConstants.CENTER); 
					tab[colonne][ligne].addMouseListener(gest); // ajoute l'écouteur aux deplacement
					if ((colonne + 1) % 2 == a)
						tab[colonne][ligne].setBackground(new Color(255, 255, 255));//blanc
					else
						tab[colonne][ligne].setBackground(new Color(80, 80, 80));//gris

				}
			}*/
			
			// les écouteurs
	        GestionnaireEvenement gest = new GestionnaireEvenement();
	        
	        //J'attribue la couleur aux JLabels
	        int a = 1;
	        for (int ligne = 0; ligne < 8; ligne++) {
	            a = a == 1 ? 0 : 1;
	            for (int colonne = 0; colonne < 8; colonne++) {
	                tab[colonne][ligne] = new JLabel(); // crÌöation du JLabel
	                tab[colonne][ligne].setOpaque(true);
	                panelGrille.add(tab[colonne][ligne]); // ajouter au Panel
	                tab[colonne][ligne].setOpaque(true);
	                tab[colonne][ligne].setHorizontalAlignment(SwingConstants.CENTER); // pour
	                tab[colonne][ligne].addMouseListener(gest); // ajouter l'ecouteur aux
	                if ((colonne + 1) % 2 == a)
	                {
	                    tab[colonne][ligne].setIcon(new ImageIcon("src/images/charte.png"));
	                    tab[colonne][ligne].setBackground(new Color(255, 255, 255));
	                }
	                else
	                    tab[colonne][ligne].setBackground(new Color(100, 100, 100));

	            }	
	        }
	}	
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

	
	

	
	
	
	
	
	
	/*
	public void mouseClicked(MouseEvent eve) {
		

		if (eve.getSource() instanceof JLabel) // donc on a cliqué sur un Label
		{
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
							
							/* je vŽrifie si la pi�ce manger est un roi, si oui le jeu est terminŽ et L'utilisateurs 
							peut choisir si il veut continuer a jouer ou non*/
							/*if(e.getCase(colonneClic, ligneClic).getPiece() instanceof Roi)
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

	}*/
}


