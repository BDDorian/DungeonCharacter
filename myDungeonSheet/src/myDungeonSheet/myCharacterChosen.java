package myDungeonSheet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;


/**
 * This class is created in order to dispose two choice for the user :  choose a character that already exists or create a new one.
 * This class use a JFrame and a JPanel so as to dispose some fields and buttons for the GUI.
 * @author Dorian
 *
 */
public class myCharacterChosen extends JFrame {

	/**
	 * @author Dorian
	 * Declaring the elements used in this class such as JPanel, JFrame and JComboBox.
	 */
	private JPanel contentPane;
	private JFrame frame;
	private static String ChosenPseudo;
	private String myPseudoField;
	private JComboBox comboBoxCharacter;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myCharacterChosen frame = new myCharacterChosen(ChosenPseudo);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * the constructor is overloaded with the string of the pseudo field from LoginPage.
	 */
	public myCharacterChosen(String ChosenPseudo) {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 153, 153));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);
		
		myPseudoField = ChosenPseudo.toString();
		System.out.println(myPseudoField);
		
		comboBoxCharacter = new JComboBox();
		comboBoxCharacter.setBounds(211, 134, 179, 17);
		JButton btnNewButton = new JButton("Choisir");
		btnNewButton.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		btnNewButton.setBounds(82, 131, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection myDatabaseCon;
				PreparedStatement myStatement;
				ResultSet myResult;
				try {
		//Requête SQL pour sélectionner les personnage selon l'id_utilisateur
				myDatabaseCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjondatabase", "root", "root");
				myStatement = myDatabaseCon.prepareStatement("SELECT id_utilisateur FROM utilisateur WHERE pseudo_utilisateur = '"+myPseudoField+"' ");			
				myResult = myStatement.executeQuery();
		
		// A l'aide de la variable result, on regarde si on trouve une ligne qui renvoie TRUE à notre requête préparée
				if(myResult.next()) {
			//On indique à l'utilisateur que la création a réussi :
					JOptionPane.showMessageDialog(null, " Choississez un personnage dans la liste");
					int myResultIdUser = myResult.getInt(1);
					System.out.println(myResultIdUser); // J'arrive à récupérer l'id_utilisateur.
			//Le but est de l'envoyer dans l'autre JFrame.
				
				
					try {
				
						myStatement = myDatabaseCon.prepareStatement("SELECT nom_personnage FROM personnage WHERE id_utilisateur = '"+myResultIdUser+"'");
						myResult = myStatement.executeQuery();
							while(myResult.next()) {
								System.out.print(myResult.getString("nom_personnage"));
								String nameCharacter = myResult.getString("nom_personnage");
								System.out.print(nameCharacter);
								comboBoxCharacter.addItem(nameCharacter);
					
								}
				
					}
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
				}
			}
					catch(SQLException e){
						
						e.printStackTrace();
					}
				
				}
				

			});
		panel.add(comboBoxCharacter);
		panel.add(btnNewButton);
		
		JButton btnCrerUnNouveau = new JButton("Cr\u00E9er un nouveau personnage");
		btnCrerUnNouveau.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		btnCrerUnNouveau.setBounds(116, 203, 205, 23);
		btnCrerUnNouveau.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Bienvenue sur la création de votre personnage.");
				frame.dispose();
				//creationCharacter myGoPage = new creationCharacter();
				//new creationCharacter(myFieldPseudo).setVisible(true);
				new creationCharacter(myPseudoField).setVisible(true);
			}
		});
		panel.add(btnCrerUnNouveau);
		
		JLabel lblChoixDuPersonnage = new JLabel("Choix du personnage");
		lblChoixDuPersonnage.setFont(new Font("Goudy Old Style", Font.BOLD | Font.ITALIC, 22));
		lblChoixDuPersonnage.setBounds(116, 21, 246, 23);
		panel.add(lblChoixDuPersonnage);
		
		
		//Nouvelle image importée
		//Insérer une icône dans le label
		Image img = new ImageIcon(this.getClass().getResource("/Choice.png")).getImage();
		JLabel iconChoix = new JLabel();
		iconChoix.setBounds(60, 11, 59, 51);
		iconChoix.setIcon(new ImageIcon(img));
		panel.add(iconChoix);
		
		JLabel icon2Choix = new JLabel();
		icon2Choix.setIcon(new ImageIcon(img));
		icon2Choix.setBounds(303, 11, 50, 51);
		panel.add(icon2Choix);
	
	}
}
