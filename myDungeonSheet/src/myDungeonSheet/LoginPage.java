package myDungeonSheet;
//MODIFICATION DE DONJONBASE EN DONJONDATABASE AVEC LA NOUVELLE BASE DE DONNEES.

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;

//import com.mysql.jdbc.Connection;
import java.sql.*;
import java.util.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;


/**
 * This class represents the Home Page of the application.
 * The user can create an account or register to an account that already exists.
 * @author Dorian
 *
 */
public class LoginPage {

	private JFrame frame;
	private JTextField fieldLoginPseudo;
	private JTextField fieldLoginPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 153, 153));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSinscrire = new JButton("S'inscrire");
		btnSinscrire.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnSinscrire.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//Afficher un message lors du clic d'un bouton.
				JOptionPane.showMessageDialog(null, "Bienvenue sur la création de votre premier personnage");
				
				// On ferme la fenêtre actuelle afin d'accéder à la suivante.
				frame.dispose();
				// On instancie par la suite un objet de la classe registerAccount
				registerAccount mySecondPage = new registerAccount();
				//On la rend visible.
				mySecondPage.setVisible(true);
				
			}
		});
		btnSinscrire.setBackground(new Color(255, 255, 255));
		btnSinscrire.setBounds(174, 92, 89, 23);
		frame.getContentPane().add(btnSinscrire);
		
		fieldLoginPseudo = new JTextField();
		fieldLoginPseudo.setBounds(174, 159, 104, 20);
		frame.getContentPane().add(fieldLoginPseudo);
		fieldLoginPseudo.setColumns(10);
		
		fieldLoginPassword = new JPasswordField();
		fieldLoginPassword.setBounds(174, 190, 104, 20);
		frame.getContentPane().add(fieldLoginPassword);
		fieldLoginPassword.setColumns(10);
		
		JButton btnValider = new JButton("Valider");
		btnValider.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//On récupère le champ pseudo.
				
				String myFieldPseudo = fieldLoginPseudo.getText();
				
	// *** Pour se connecter à la base de données existantes : ***//
				
				// On crée un objet de type Connection, un autre de type requête préparée, et un dernier de type Result.
				
				Connection myDatabaseCon;
				PreparedStatement myStatement;
				ResultSet myResult;
				
				try {
					String pseudoChecked = fieldLoginPseudo.getText();
					myDatabaseCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjondatabase", "root", "root");
					myStatement = myDatabaseCon.prepareStatement("SELECT * FROM utilisateur WHERE pseudo_utilisateur = '"+fieldLoginPseudo.getText()+"' AND motDePasse_utilisateur = '"+fieldLoginPassword.getText()+"' ");			
					ResultSet result = myStatement.executeQuery();
					
					// A l'aide de la variable result, on regarde si on trouve une ligne qui renvoie TRUE à notre requête préparée
					if(result.next()) {
						//On indique à l'utilisateur que la création a réussi :
						JOptionPane.showMessageDialog(null, "Bienvenue.");
						frame.dispose();
						//creationCharacter myGoPage = new creationCharacter();
						//new creationCharacter(myFieldPseudo).setVisible(true);
						new myCharacterChosen(myFieldPseudo).setVisible(true);
						//myGoPage(myFieldPseudo).setVisible(true);
					
					}
					else {
						JOptionPane.showMessageDialog(null, "Erreur, votre compte n'existe pas.");
					}
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		btnValider.setBounds(174, 227, 89, 23);
		frame.getContentPane().add(btnValider);
		
		//Icone écran accueil
		JLabel HomeLabel = new JLabel();
		JLabel Home2Label = new JLabel();
		
		//Insérer une icône dans le label
		Image img = new ImageIcon(this.getClass().getResource("/Home.png")).getImage();
		HomeLabel.setIcon(new ImageIcon(img));
		HomeLabel.setBounds(368, 0, 56, 67);
		Home2Label.setIcon(new ImageIcon(img));
		Home2Label.setBounds(34, 0, 56, 67);
		frame.getContentPane().add(Home2Label);
		frame.getContentPane().add(HomeLabel);
		
		JLabel lblBienvenueSurDungeon = new JLabel("Bienvenue sur Dungeon Sheet");
		lblBienvenueSurDungeon.setForeground(new Color(0, 0, 51));
		lblBienvenueSurDungeon.setFont(new Font("Goudy Old Style", Font.BOLD | Font.ITALIC, 22));
		lblBienvenueSurDungeon.setBounds(96, 11, 291, 32);
		frame.getContentPane().add(lblBienvenueSurDungeon);
		
	
		
		JLabel lblNouveauMembre = new JLabel("Nouveau membre ?");
		lblNouveauMembre.setFont(new Font("Goudy Old Style", Font.BOLD, 14));
		lblNouveauMembre.setBounds(162, 67, 144, 14);
		frame.getContentPane().add(lblNouveauMembre);
		
		JLabel lblDjInscrit = new JLabel("D\u00E9j\u00E0 inscrit ?");
		lblDjInscrit.setFont(new Font("Goudy Old Style", Font.BOLD, 14));
		lblDjInscrit.setBounds(174, 134, 89, 14);
		frame.getContentPane().add(lblDjInscrit);
		
		JLabel lblPseudo = new JLabel("Pseudo:");
		lblPseudo.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblPseudo.setBounds(96, 162, 46, 14);
		frame.getContentPane().add(lblPseudo);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe:");
		lblMotDePasse.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblMotDePasse.setBounds(96, 193, 79, 14);
		frame.getContentPane().add(lblMotDePasse);
		
		JButton btnAutre = new JButton("Valider");
		btnAutre.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		
		btnAutre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		

	}

			
		});
		btnAutre.setBounds(174, 227, 89, 23);
		btnAutre.setText("TestBDD");;
		frame.getContentPane().add(btnAutre);
			
	}

	@Override
	public String toString() {
		return "LoginPage [frame=" + frame + ", fieldLoginPseudo=" + fieldLoginPseudo + ", fieldLoginPassword="
				+ fieldLoginPassword + "]";
	}
}

