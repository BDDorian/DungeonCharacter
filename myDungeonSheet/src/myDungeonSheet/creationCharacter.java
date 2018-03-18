package myDungeonSheet;

//RECUPERER ID CLASSE CORRESPONDANT, ID RACE POUR LE METTRE EN FOREIGN KEY DANS LA TABLE PERSONNAGE ET CE SERA FINI.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class is used for creating a JFrame with the building of a new character.
 * This Jframe will dispose a character sheet where the user can create a character that will be registered in the database. All characters created are linked to the user account.
 * Any Character created will be introduce to the map of the game and be playable.
 *@author Dorian
 */
public class creationCharacter extends JFrame {

	/**
	 * Declaring the JTextField Elements used in this class.
	 */
	
	private static String pseudoField;
	private String myPseudoField;
	private JPanel contentPane;
	private JTextField textFieldNom;
	private JTextField textFieldAge;
	private JTextField textFieldClasseArmure;
	private JTextField textFieldDexterite;
	private JTextField textFieldForce;
	private JTextField textFieldConstitution;
	private JTextField textFieldIntelligence;
	private JTextField textFieldSagesse;
	private JTextField textFieldCharisme;
	private Random myDice;
	private JTextField textFieldPointDeVie;
	private JTextField textFieldNiveauPerso;
	private JTextField textField;

	/**
	 * Declaring the JCheckBox Elements used in this class.
	 * CheckBox are created so as to allow the user to select one of the skills implemented in the databse.
	 * @author Dorian
	 */
	// JCheckBow ELEMENTS //
	private JCheckBox checkBoxBow;
	private JCheckBox checkBoxSword;
	private JCheckBox checkBoxAxe;
	private JCheckBox checkBoxSpear;
	private JCheckBox checkBoxMagic;
	private JCheckBox checkBoxHammer;

	/**
	 * Declaring the JComboBox Elements used in this class.
	 * ComboBox are created in order to allow the user to select a class, a race or a sex in a list linked to the database.
	 * @author Dorian
	 */
	// ComboBox Elements //
	private JComboBox comboBoxClassChosen;
	private JComboBox comboBoxRaceChosen;
	private JComboBox comboBoxSexChosen;

	// Jlabel Elements //
	/**
	 * Declaring the JLabel Elements used in this class.
	 * LabelClasseArmure is automatically filled depending on the class selected.
	 * LabelNom is filled with the user's input.
	 * @author Dorian
	 */
	
	private JLabel jLabelClasseArmure;
	private JLabel labelNom;


	/**
	 * Declaring the int Element used in this class.
	 * numberOfCheckBoxChecked is a counter in order to enable or disable checkBox.
	 */
	// Other Elements //
	
	private int numberOfCheckBoxChecked;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					creationCharacter frame = new creationCharacter(pseudoField);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * the constructor is overload with a String pseudoField that took the pseudo typed from user's login.
	 */
	//Surcharge du constructeur afin de récupérer la valeur rentré par l'utilisateur sur son compte.
	//Et Ainsi pouvoir récupérer l'id correspondant, et l'intégrer en tant que clé étrangère dans la table personnage.
	public creationCharacter(String pseudoField) {
		
		System.out.print(pseudoField);
			
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cr\u00E9ation de votre personnage");
		lblNewLabel.setFont(new Font("Goudy Old Style", Font.BOLD | Font.ITALIC, 22));
		lblNewLabel.setBounds(159, 11, 273, 34);
		panel.add(lblNewLabel);
		
		JLabel labelNom = new JLabel("Nom :");
		labelNom.setToolTipText("Entrez le nom de votre personnage, 20 caractères maximum.");
		labelNom.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		labelNom.setBounds(10, 80, 46, 14);
		panel.add(labelNom);
		
		JLabel lblAge = new JLabel("Age :");
		lblAge.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblAge.setBounds(242, 85, 46, 14);
		panel.add(lblAge);
		
		JLabel lblSexe = new JLabel("Sexe:");
		lblSexe.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblSexe.setBounds(436, 85, 46, 14);
		panel.add(lblSexe);
		
		JLabel lblRace = new JLabel("Race :");
		lblRace.setToolTipText("Choisir un certain type de race augmente les points de vie d'un personnage...");
		lblRace.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblRace.setBounds(10, 112, 46, 14);
		panel.add(lblRace);
		
		JLabel lblClasse = new JLabel("Classe:");
		lblClasse.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblClasse.setBounds(242, 112, 46, 14);
		panel.add(lblClasse);
		
		jLabelClasseArmure = new JLabel("Classe Armure:");
		jLabelClasseArmure.setToolTipText("Détermine le niveau de protection de base d'un personnage.");
		jLabelClasseArmure.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		jLabelClasseArmure.setBounds(436, 114, 96, 14);
		panel.add(jLabelClasseArmure);
		
		textFieldClasseArmure = new JTextField();
		textFieldClasseArmure.setBounds(531, 111, 36, 17);
		panel.add(textFieldClasseArmure);
		textFieldClasseArmure.setColumns(10);
		textFieldClasseArmure.setEditable(false);
		
		textFieldNom = new JTextField();
		//Méthode crée afin de ne rentrer que des lettres pour le nom du personnage.
		textFieldNom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				// Retourne le caractère entré par l'utilisateur.
				char c = event.getKeyChar();
				if(!(Character.isAlphabetic(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
					event.consume();
				}
			}
		});
		
		/**
		 * Declaring a KeyAdapter
		 * This keyAdapter is used in order to limit the number of characters to 20.
		 */
		//Méthode afin de limiter le nombre de caractères saisies à 20 afin de concorder avec la base de données.
		textFieldNom.addKeyListener(new KeyAdapter(){
			@Override
			public void keyTyped(KeyEvent e) {
				if(textFieldNom.getText().length() >= 20) {
					e.consume();// Imposition d'une limite à 20 caractères.
				}
			}
		});
		
		
		textFieldNom.setBounds(81, 77, 96, 22);
		panel.add(textFieldNom);	
		textFieldNom.setColumns(10);
		
		textFieldAge = new JTextField();
		//Méthode crée afin de ne rentrer que des chiffres pour l'age.
		/**
		 * Declaring a KeyAdapter
		 * This keyAdapter is used in order to limit the user from using digital characters or special characters.
		 */
		textFieldAge.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				char c = event.getKeyChar();
				//Condition qui vérifie sur les caractères rentrés sont biens des chiffres.
				if(!(Character.isDigit(c) || c ==KeyEvent.VK_BACK_SPACE || c ==KeyEvent.VK_DELETE)) {
					event.consume();
					
				}
			}
		});
		/**
		 * Declaring a KeyAdapter
		 * This keyAdapter is used in order to limit the number of age to 2.
		 */
		//Méthode afin de limiter le nombre de caractères saisies à 20 afin de concorder avec la base de données.
		textFieldAge.addKeyListener(new KeyAdapter(){
				@Override
				public void keyTyped(KeyEvent e) {
					if(textFieldAge.getText().length() >= 2) {
						e.consume();// Imposition d'une limite à 2 chiffres.
					}
				}
			});
		textFieldAge.setBounds(291, 81, 26, 22);
		panel.add(textFieldAge);
		textFieldAge.setColumns(10);
		
		comboBoxRaceChosen = new JComboBox();
		comboBoxRaceChosen.setBounds(81, 112, 96, 17);
		Connection myDatabaseCon;
		PreparedStatement myStatement;
		ResultSet myResult;
		
		try {
			myDatabaseCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjondatabase", "root", "root");
			myStatement = myDatabaseCon.prepareStatement("SELECT * from race");
			ResultSet result = myStatement.executeQuery();
			while(result.next()) {
				String nameRace = result.getString("nom_race");
				comboBoxRaceChosen.addItem(nameRace);
			}
		}
		
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		comboBoxRaceChosen.addItemListener(new comboBoxRaceChosenListener());
		panel.add(comboBoxRaceChosen);
		
		comboBoxClassChosen = new JComboBox();
		comboBoxClassChosen.setBounds(291, 111, 99, 17);
		//Ajout des éléments inhérents à la table Classe pour que l'utilsateur fasse sa sélection.
		Connection myDatabaseConnexion;
		PreparedStatement myPrepStatement;
		ResultSet myResultClass;
		
		try {
			myDatabaseConnexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjondatabase", "root", "root");
			myPrepStatement = myDatabaseConnexion.prepareStatement("SELECT * from classe");
			myResultClass = myPrepStatement.executeQuery();
			while(myResultClass.next()) {
				String nameClasse = myResultClass.getString("nom_classe");
				comboBoxClassChosen.addItem(nameClasse);
			}
		}
		
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		System.out.print("hahahaha");
		comboBoxClassChosen.getSelectedItem();
		System.out.print(comboBoxClassChosen.getSelectedItem());
		comboBoxClassChosen.addItemListener(new comboBoxClassChosenListener());		
		panel.add(comboBoxClassChosen);
		
		//ComboBoxSex
		String maleSex = "H";
		String femaleSex = "F";
		comboBoxSexChosen = new JComboBox();
		comboBoxSexChosen.setBounds(531, 81, 36, 22);
		comboBoxSexChosen.addItem(maleSex);
		comboBoxSexChosen.addItem(femaleSex);
		panel.add(comboBoxSexChosen);
		
		
		
		JLabel lblCaractristiques = new JLabel("Caract\u00E9ristiques :");
		lblCaractristiques.setFont(new Font("Goudy Old Style", Font.BOLD | Font.ITALIC, 18));
		lblCaractristiques.setBounds(71, 154, 137, 22);
		panel.add(lblCaractristiques);
		
		JLabel lblNewLabel_1 = new JLabel("Force :");
		lblNewLabel_1.setToolTipText("Détermine la puissance d'un personnage, les dégâts physiques envoyés.");
		lblNewLabel_1.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(71, 187, 73, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblDextrit = new JLabel("Dext\u00E9rit\u00E9 :");
		lblDextrit.setToolTipText("Détermine la rapidité d'un personnage, sa vitesse d'action.");
		lblDextrit.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblDextrit.setBounds(71, 227, 73, 14);
		panel.add(lblDextrit);
		
		JLabel lblConstitution = new JLabel("Constitution : ");
		lblConstitution.setToolTipText("Détermine la résistance d'un personnage face aux coups.");
		lblConstitution.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblConstitution.setBounds(71, 266, 82, 14);
		panel.add(lblConstitution);
		
		JLabel lblIntelligence = new JLabel("Intelligence :");
		lblIntelligence.setToolTipText("Détermine l'astuce d'un personnage, sa capacité à s'exprimer et à apprendre.");
		lblIntelligence.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblIntelligence.setBounds(71, 309, 73, 14);
		panel.add(lblIntelligence);
		
		JLabel lblSagesse = new JLabel("Sagesse :");
		lblSagesse.setToolTipText("Détermine le jugement d'un personnage, sa capacité à raisonner");
		lblSagesse.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblSagesse.setBounds(71, 349, 73, 14);
		panel.add(lblSagesse);
		
		JLabel lblCharisme = new JLabel("Charisme :");
		lblCharisme.setToolTipText("Détermine le magnétisme d'un personnage, sa capacité oratoire et de persuasion.");
		lblCharisme.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblCharisme.setBounds(71, 391, 73, 14);
		panel.add(lblCharisme);
		
		// *** Cases concernant les caractéristiques générées par le lancer de dé *** //
		
		textFieldDexterite = new JTextField();
		textFieldDexterite.setBounds(149, 225, 36, 20);
		panel.add(textFieldDexterite);
		textFieldDexterite.setColumns(10);
		textFieldDexterite.setEditable(false);
		
		textFieldForce = new JTextField();
		textFieldForce.setBounds(149, 187, 36, 20);
		panel.add(textFieldForce);
		textFieldForce.setColumns(10);
		textFieldForce.setEditable(false);
		
		textFieldConstitution = new JTextField();
		textFieldConstitution.setBounds(149, 264, 36, 20);
		panel.add(textFieldConstitution);
		textFieldConstitution.setColumns(10);
		textFieldConstitution.setEditable(false);
		
		textFieldIntelligence = new JTextField();
		textFieldIntelligence.setBounds(149, 307, 36, 20);
		panel.add(textFieldIntelligence);
		textFieldIntelligence.setColumns(10);
		textFieldIntelligence.setEditable(false);
		
		textFieldSagesse = new JTextField();
		textFieldSagesse.setBounds(149, 347, 36, 20);
		panel.add(textFieldSagesse);
		textFieldSagesse.setColumns(10);
		textFieldSagesse.setEditable(false);
		
		textFieldCharisme = new JTextField();
		textFieldCharisme.setBounds(149, 389, 36, 20);
		panel.add(textFieldCharisme);
		textFieldCharisme.setColumns(10);
		textFieldCharisme.setEditable(false);
		
		JLabel lblComptences = new JLabel("Comp\u00E9tences :");
		lblComptences.setToolTipText("Choissisez deux compétences maximum !");
		lblComptences.setFont(new Font("Goudy Old Style", Font.BOLD | Font.ITALIC, 18));
		lblComptences.setBounds(426, 158, 117, 18);
		panel.add(lblComptences);
		
		JLabel lblEpee = new JLabel("Ep\u00E9e :");
		lblEpee.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblEpee.setBounds(426, 227, 46, 14);
		panel.add(lblEpee);
		
		JLabel lblHache = new JLabel("Hache : ");
		lblHache.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblHache.setBounds(426, 266, 56, 14);
		panel.add(lblHache);
		
		JLabel lblLance = new JLabel("Lance :");
		lblLance.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblLance.setBounds(426, 309, 46, 14);
		panel.add(lblLance);
		
		JLabel lblMarteau = new JLabel("Marteau :");
		lblMarteau.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblMarteau.setBounds(426, 391, 56, 14);
		panel.add(lblMarteau);
		
		JLabel lblMagie = new JLabel("Magie :");
		lblMagie.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblMagie.setBounds(426, 349, 46, 14);
		panel.add(lblMagie);
		
		JLabel lblArc = new JLabel("Arc :");
		lblArc.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblArc.setBounds(426, 193, 46, 14);
		panel.add(lblArc);
		
		checkBoxBow = new JCheckBox("");
			
		// On ajoute au checkBox sélectionné, un évènement d'écoute afin de récupérer l'information de la sélection.
		checkBoxBow.addItemListener(new comboBoxBowListener());
		checkBoxBow.setBounds(492, 187, 97, 23);
		panel.add(checkBoxBow);
		
		checkBoxSword = new JCheckBox("");
		checkBoxSword.addItemListener(new comboBoxSwordListener());
		checkBoxSword.setBounds(492, 222, 97, 23);
		panel.add(checkBoxSword);
		
		checkBoxAxe = new JCheckBox("");
		checkBoxAxe.addItemListener(new comboBoxAxeListener());
		checkBoxAxe.setBounds(492, 264, 97, 23);
		panel.add(checkBoxAxe);
		
		checkBoxSpear = new JCheckBox("");
		checkBoxSpear.addItemListener(new comboBoxSpearListener());
		checkBoxSpear.setBounds(492, 304, 97, 23);
		panel.add(checkBoxSpear);
		
		checkBoxMagic = new JCheckBox("");
		checkBoxMagic.addItemListener(new comboBoxMagicListener());
		checkBoxMagic.setBounds(492, 344, 97, 23);
		panel.add(checkBoxMagic);
		
		checkBoxHammer = new JCheckBox("");
		checkBoxHammer.addItemListener(new comboBoxHammerListener());
		checkBoxHammer.setBounds(492, 386, 97, 23);
		panel.add(checkBoxHammer);
		
		JButton btnNewButton = new JButton("Valider");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * Declaring a action performed with the click of the btnNewButton Validate.
				 * This function is use for insert a new character into the database "dungeondatabase".
				 */
				//On effectue une requête au préalable afin de récupérer l'id_utlisateur afin de l'insérer en tant que clé étrangère.
				Connection myDatabaseConnexion;
				PreparedStatement myStatementPrepared;
				ResultSet myResultSet;
				
				Connection myDatabaseCon;
				PreparedStatement myStatement;
				PreparedStatement myStatementInsertPersonnageClasse;
				PreparedStatement myStatementInsertPersonnageRace;
				PreparedStatement myStatementNameRace;
				PreparedStatement myStatementNameClasse;
				PreparedStatement myStatementGetLastIdPerso;
				PreparedStatement myStatementNameCompArc;
				PreparedStatement myStatementNameCompEpee;
				PreparedStatement myStatementNameCompHache;
				PreparedStatement myStatementNameCompMarteau;
				PreparedStatement myStatementNameCompMagie;
				PreparedStatement myStatementNameCompLance;
				PreparedStatement myStatementInsertCompArc;
				PreparedStatement myStatementInsertCompEpee;
				PreparedStatement myStatementInsertCompHache;
				PreparedStatement myStatementInsertCompMarteau;
				PreparedStatement myStatementInsertCompMagie;
				PreparedStatement myStatementInsertCompLance;
				PreparedStatement myStatementChosenRace;
				PreparedStatement myStatementChosenClasse;
				PreparedStatement myStatementCheckLastPerso;
				
				ResultSet myResultPersonnageClasse;
				ResultSet myResultNameRace;
				ResultSet myResultNameClasse;
				ResultSet myResult;
				ResultSet myResultGetLastIdPerso;
				ResultSet myResultCompArc;
				ResultSet myResultCompEpee;
				ResultSet myResultCompHache;
				ResultSet myResultCompMarteau;
				ResultSet myResultCompMagie;
				ResultSet myResultCompLance;
				ResultSet myResultChosenRace;
				ResultSet myResultChosenClasse;
				ResultSet myResultCheckLastIdPerso;
				System.out.print(comboBoxSexChosen.getSelectedItem());
				
				try {
					 myPseudoField = pseudoField;
					myDatabaseConnexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjondatabase", "root", "root");
					myStatementPrepared = myDatabaseConnexion.prepareStatement("SELECT id_utilisateur FROM utilisateur WHERE pseudo_utilisateur = '"+pseudoField+"' ");			
					myResultSet = myStatementPrepared.executeQuery();
					
					// A l'aide de la variable result, on regarde si on trouve une ligne qui renvoie TRUE à notre requête préparée
					if(myResultSet.next()) {
						//On indique à l'utilisateur que la création a réussi :
						JOptionPane.showMessageDialog(null, "trouve" +myResultSet.toString());
						int myResultIdUser = myResultSet.getInt(1);
						System.out.println(myResultIdUser); // J'arrive à récupérer l'id_utilisateur.
						
						
						if(comboBoxRaceChosen.isEnabled()) {
							try {
								myStatementChosenRace = myDatabaseConnexion.prepareStatement("SELECT id_race FROM race WHERE nom_race = '"+comboBoxRaceChosen.getSelectedItem()+"' ");
								myResultChosenRace= myStatementChosenRace.executeQuery();
								if(myResultChosenRace.next()) {
									int myResultIdRace = myResultChosenRace.getInt(1);
							
									if(comboBoxClassChosen.isEnabled()) {
										try {
											myStatementChosenClasse = myDatabaseConnexion.prepareStatement("SELECT id_classe FROM classe where nom_classe = '"+comboBoxClassChosen.getSelectedItem()+"' ");
											myResultChosenClasse = myStatementChosenClasse.executeQuery();
											if(myResultChosenClasse.next()) {
												int myResultIdCLasse = myResultChosenClasse.getInt(1);
														
						
						
							
						//Condition pour vérifier que tous les champs ont bien étés remplis
						if(!textFieldNom.getText().isEmpty() && !textFieldAge.getText().isEmpty() && !textFieldClasseArmure.getText().isEmpty() && !textFieldForce.getText().isEmpty() && !textFieldDexterite.getText().isEmpty() && !textFieldConstitution.getText().isEmpty() && !textFieldIntelligence.getText().isEmpty() && !textFieldSagesse.getText().isEmpty() && !textFieldCharisme.getText().isEmpty() && !textFieldPointDeVie.getText().isEmpty() && textFieldNiveauPerso.getText().isEmpty() && comboBoxRaceChosen.isEnabled() && comboBoxClassChosen.isEnabled()) {
						//Le but est de l'envoyer dans l'autre JFrame.
						try {
							//myDatabaseCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjonbase", "root", "root");
							String addCharacter = "INSERT INTO personnage(nom_personnage, age_personnage, sexe_personnage, classeArmure_personnage, force_personnage, dexterite_personnage, constitution_personnage, intelligence_personnage, sagesse_personnage, charisme_personnage, pointDeVie_personnage, niveau_personnage, id_utilisateur, id_race, id_classe) values ('"+textFieldNom.getText()+"','"+textFieldAge.getText()+"','"+comboBoxSexChosen.getSelectedItem()+"', '"+textFieldClasseArmure.getText()+"', '"+textFieldForce.getText()+"', '"+textFieldDexterite.getText()+"', '"+textFieldConstitution.getText()+"', '"+textFieldIntelligence.getText()+"','"+textFieldSagesse.getText()+"', '"+textFieldCharisme.getText()+"','"+textFieldPointDeVie.getText()+"','"+textFieldNiveauPerso.getText()+"','"+myResultIdUser+"', '"+myResultIdRace+"', '"+myResultIdCLasse+"')";
							//On initialise ensuite la création de la requête.
							
							// On exécute la requête désirée :
							//addUser.execute();
							//On initialise ensuite la création de la requête.
							myStatement = myDatabaseConnexion.prepareStatement(addCharacter);
							
							// On exécute la requête désirée :
							myStatement.execute();
							//On indique à l'utilisateur que la création a réussi :
							JOptionPane.showMessageDialog(null, "Votre compte a bien été crée");
							new myCharacterChosen(myPseudoField).setVisible(true);
												
							}
							catch(SQLException e) {
									e.printStackTrace();
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Vous n'avez pas renseigné tous les champs.");
						}
											}
										}
										catch(SQLException e) {
											e.printStackTrace();
										}
									}
								}
							}
							catch(SQLException e) {
								e.printStackTrace();
							}
						}
					
				//Ferme le premier try catch
					
							//Nouvelle requête pour lier les compétences au personnage crée.
									//Condition pour vérifier si c'est la checkbox ARC qui est sélectionnée
								try {
									myStatementCheckLastPerso = myDatabaseConnexion.prepareStatement("SELECT id_personnage FROM personnage ORDER BY id_personnage DESC LIMIT 1");
									myResultCheckLastIdPerso = myStatementCheckLastPerso.executeQuery();
									if(myResultCheckLastIdPerso.next()) {
										int myResultLastId = myResultCheckLastIdPerso.getInt(1);
									
								
							if(checkBoxBow.isSelected()) {
								String arcLabel = "arc";
								try {		
								//A utiliser après.
											myStatementNameCompArc = myDatabaseConnexion.prepareStatement("SELECT id_competence FROM competence WHERE nom_competence = '"+arcLabel+"' ");
											myResultCompArc = myStatementNameCompArc.executeQuery();
											if(myResultCompArc.next()) {
												int myResultIdArc = myResultCompArc.getInt(1);
												
												//Si le résultat trouvé est correct, alors on insère le tout dans la table relationnelle personnage_compétence
												try {
													myStatementInsertCompArc = myDatabaseConnexion.prepareStatement("INSERT INTO personnage_competence(id_personnage, id_competence) values('"+myResultLastId+"', '"+myResultIdArc+"')");
													myStatementInsertCompArc.execute();
												}
												catch(SQLException e) {
													e.printStackTrace();
												}
												
											}
										}
										catch(SQLException e) {
											e.printStackTrace();
										}
									}
									
									//Condition pour vérifiser si c'est la checkbox EPEE qui est sélectionnée
									
									if(checkBoxSword.isSelected()) {
										String epeeLabel = "Epee";
										try {
											myStatementNameCompEpee = myDatabaseConnexion.prepareStatement("SELECT id_competence FROM competence WHERE nom_competence = '"+epeeLabel+"' ");
											myResultCompEpee = myStatementNameCompEpee.executeQuery();
											if(myResultCompEpee.next()) {
												int myResultIdEpee = myResultCompEpee.getInt(1);
												try {
													myStatementInsertCompEpee = myDatabaseConnexion.prepareStatement("INSERT INTO personnage_competence(id_personnage, id_competence) values ('"+myResultLastId+"', '"+myResultIdEpee+"')");
													myStatementInsertCompEpee.execute();
												}
												
												catch(SQLException e) {
													e.printStackTrace();
												}
											}
											
										}
										catch(SQLException e) {
											e.printStackTrace();
										}
									}
									// Condition pour vérifier si c'est la checkbox Hache qui est sélectionée.
									if(checkBoxAxe.isSelected()) {
										String hacheLabel = "Hache";
										try {
											myStatementNameCompHache = myDatabaseConnexion.prepareStatement("SELECT id_competence FROM competence WHERE nom_competence = '"+hacheLabel+"' ");
											myResultCompHache = myStatementNameCompHache.executeQuery();
											if(myResultCompHache.next()) {
												int myResultIdHache = myResultCompHache.getInt(1);
												try {
													myStatementInsertCompHache = myDatabaseConnexion.prepareStatement("INSERT INTO personnage_competence(id_personnage, id_competence) values ('"+myResultLastId+"', '"+myResultIdHache+"')");
													myStatementInsertCompHache.execute();
												}
												
												catch(SQLException e) {
													e.printStackTrace();
												}
											}
											
										}
										catch(SQLException e) {
											e.printStackTrace();
										}
									}
									// Condition pour vérifier si c'est la checkbox Marteau qui est sélectionée.
									if(checkBoxHammer.isSelected()) {
										String marteauLabel = "Marteau";
										try {
											myStatementNameCompMarteau = myDatabaseConnexion.prepareStatement("SELECT id_competence FROM competence WHERE nom_competence = '"+marteauLabel+"' ");
											myResultCompMarteau = myStatementNameCompMarteau.executeQuery();
											if(myResultCompMarteau.next()) {
												int myResultIdMarteau = myResultCompMarteau.getInt(1);
												try {
													myStatementInsertCompMarteau= myDatabaseConnexion.prepareStatement("INSERT INTO personnage_competence(id_personnage, id_competence) values ('"+myResultLastId+"', '"+myResultIdMarteau+"')");
													myStatementInsertCompMarteau.execute();
												}
												
												catch(SQLException e) {
													e.printStackTrace();
												}
											}
											
										}
										catch(SQLException e) {
											e.printStackTrace();
										}
									}
									
									// Condition pour vérifier si c'est la checkbox Magie qui est sélectionée.
									if(checkBoxMagic.isSelected()) {
										String magicLabel = "Magie";
										try {
											myStatementNameCompMagie = myDatabaseConnexion.prepareStatement("SELECT id_competence FROM competence WHERE nom_competence = '"+magicLabel+"' ");
											myResultCompMagie= myStatementNameCompMagie.executeQuery();
											if(myResultCompMagie.next()) {
												int myResultIdMagie = myResultCompMagie.getInt(1);
												try {
													myStatementInsertCompMagie= myDatabaseConnexion.prepareStatement("INSERT INTO personnage_competence(id_personnage, id_competence) values ('"+myResultLastId+"', '"+myResultIdMagie+"')");
													myStatementInsertCompMagie.execute();
												}
												
												catch(SQLException e) {
													e.printStackTrace();
												}
											}
											
										}
										catch(SQLException e) {
											e.printStackTrace();
										}
									}
									// Condition pour vérifier si c'est la checkbox Lance qui est sélectionée.
									if(checkBoxSpear.isSelected()) {
										String lanceLabel = "Lance";
										try {
											myStatementNameCompLance = myDatabaseConnexion.prepareStatement("SELECT id_competence FROM competence WHERE nom_competence = '"+lanceLabel+"' ");
											myResultCompLance= myStatementNameCompLance.executeQuery();
											if(myResultCompLance.next()) {
												int myResultIdLance = myResultCompLance.getInt(1);
												try {
													myStatementInsertCompLance= myDatabaseConnexion.prepareStatement("INSERT INTO personnage_competence(id_personnage, id_competence) values ('"+myResultLastId+"', '"+myResultIdLance+"')");
													myStatementInsertCompLance.execute();
												}
												
												catch(SQLException e) {
													e.printStackTrace();
												}
											}
											
										}
										catch(SQLException e) {
											e.printStackTrace();
										}
									}
				}
								
			}
				catch(SQLException e) {
					e.printStackTrace();
				}
		}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnNewButton.setBounds(262, 397, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnLancerLeD = new JButton("Lancer le d\u00E9");
		btnLancerLeD.setToolTipText("Lancer un dé de 20 faces pour initialiser vos compétences. Attention, un seul lancé est autorisé");
		btnLancerLeD.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent arg0) {
		/**
		 * This function is declared in order to roll the dice to fill the stats of a character.
		 * 
		 */
				//How to add some values with rolling the dice :
				myDice = new Random();
				// On implémente le résultat dans le text Field à l'aide d'un cast d'un int en string.
				textFieldDexterite.setText(Integer.toString(myDice.nextInt(17)+3));
				textFieldForce.setText(Integer.toString(myDice.nextInt(17)+3));
				textFieldConstitution.setText(Integer.toString(myDice.nextInt(17)+3));
				textFieldIntelligence.setText(Integer.toString(myDice.nextInt(17)+3));
				textFieldSagesse.setText(Integer.toString(myDice.nextInt(17)+3));
				textFieldCharisme.setText(Integer.toString(myDice.nextInt(17)+3));
				
				//Ajuster les points de vie selon la race choisie.
				String obj = (String)comboBoxRaceChosen.getSelectedItem();
				System.out.print(obj);
				if(obj.equals("Orc")) {
					textFieldPointDeVie.setText(Integer.toString(myDice.nextInt(11+7)+4));
;
				}
				if(obj.equals("Humain")) {
					textFieldPointDeVie.setText(Integer.toString(myDice.nextInt(11+5)+4));
				}
				if(obj.equals("Nain")) {
					textFieldPointDeVie.setText(Integer.toString(myDice.nextInt(11+3)+4));
				}
				if(obj.equals("Elfe")) {
					textFieldPointDeVie.setText(Integer.toString(myDice.nextInt(11+2)+4));
				}
				
				btnLancerLeD.setEnabled(false);				
			}
		});
		
		btnLancerLeD.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnLancerLeD.setBounds(242, 262, 120, 23);
		panel.add(btnLancerLeD);
		
		
		JLabel lblImage = new JLabel("");
		lblImage.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				System.out.print("Cliquez sur lancer le dé ");
			}
		});
		//Insérer une icône dans le label
		Image img = new ImageIcon(this.getClass().getResource("/DiceResize.png")).getImage();
		lblImage.setIcon(new ImageIcon(img));
		lblImage.setBounds(278, 187, 56, 67);
		panel.add(lblImage);
		
		JLabel lblNewLabel_2 = new JLabel("");
		Image imageCharFemale = new ImageIcon(this.getClass().getResource("/characFemale.png")).getImage();
		lblNewLabel_2.setIcon(new ImageIcon(imageCharFemale));
		lblNewLabel_2.setBounds(71, -35, 89, 109);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		Image imageCharMale = new ImageIcon(this.getClass().getResource("/characMale.png")).getImage();
		lblNewLabel_3.setIcon(new ImageIcon(imageCharMale));
		lblNewLabel_3.setBounds(426, -46, 82, 141);
		panel.add(lblNewLabel_3);
		
		JLabel lblPointsDeVie = new JLabel("Points de vie :");
		lblPointsDeVie.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblPointsDeVie.setBounds(242, 160, 92, 14);
		panel.add(lblPointsDeVie);
		
		textFieldPointDeVie = new JTextField();
		textFieldPointDeVie.setBounds(328, 156, 44, 22);
		panel.add(textFieldPointDeVie);
		textFieldPointDeVie.setColumns(10);
		textFieldPointDeVie.setEditable(false);
		
		textFieldNiveauPerso = new JTextField();
		textFieldNiveauPerso.setBounds(81, 134, 26, 17);
		textFieldNiveauPerso.setText("1");
		textFieldNiveauPerso.setEnabled(false);
		panel.add(textFieldNiveauPerso);
		textFieldNiveauPerso.setColumns(10);
		
		
		JLabel lblNiveau = new JLabel("Niveau :");
		lblNiveau.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblNiveau.setBounds(10, 137, 61, 14);
		panel.add(lblNiveau);
	}

	// *** EVENT LISTENER *** //
	/**
	 * Class that used an ItemListener so as to modify the "classeArmureField" derived from the user's class choice.
	 * 
	 */
	private class comboBoxClassChosenListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			String obj = (String) comboBoxClassChosen.getSelectedItem();
			System.out.print(obj);
			if (obj.equals("Barbare")) {
				textFieldClasseArmure.setText("15");
			} else if (obj.equals("Barde")) {
				textFieldClasseArmure.setText("22");
			} else if (obj.equals("Druide")) {
				textFieldClasseArmure.setText("20");
			} else if (obj.equals("Guerrier")) {
				textFieldClasseArmure.setText("14");
			} else if (obj.equals("Mage")) {
				textFieldClasseArmure.setText("24");
			} else if (obj.equals("Moine")) {
				textFieldClasseArmure.setText("23");
			} else if (obj.equals("Paladin")) {
				textFieldClasseArmure.setText("16");
			} else if (obj.equals("Prêtre")) {
				textFieldClasseArmure.setText("21");
			} else if (obj.equals("Rôdeur")) {
				textFieldClasseArmure.setText("16");
			} else if (obj.equals("Voleur")) {
				textFieldClasseArmure.setText("18");
			}
		}
	}
	/**
	 * Class that use an ItemListener in order to check the item selected from the race table.
	 */
	private class comboBoxRaceChosenListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			String obj = (String) comboBoxRaceChosen.getSelectedItem();
			System.out.print(obj);

		}
	}

	// Pour la checkbox ARC.
	private class comboBoxBowListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			// On vérifie si le nombre de checkbox est inférieur à 2.
			if (numberOfCheckBoxChecked < 2) {
				// On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
				checkBoxBow.setEnabled(true);
				// On impose la condition pour voir si il a bien été cliqué, alors on lui
				// attribue un booléen de la valeur true. Afin de récupérer cette information et
				// de l'insérer dans la base de données.
				if (checkBoxBow.isSelected()) {
					checkBoxBow.setEnabled(false);
					numberOfCheckBoxChecked++;
				}
			} else {
				checkBoxBow.setEnabled(false);
			}

		}
	}

	// Pour la checkbox EPEE.

	private class comboBoxSwordListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (numberOfCheckBoxChecked < 2) {
				// On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
				checkBoxSword.setEnabled(true);
				// On impose la condition pour voir si il a bien été cliqué, alors on lui
				// attribue un booléen de la valeur true. Afin de récupérer cette information et
				// de l'insérer dans la base de données.
				if (checkBoxSword.isSelected()) {
					checkBoxSword.setEnabled(false);
					numberOfCheckBoxChecked++;
				}
			} else {
				checkBoxSword.setSelected(false);
			}

		}
	}

	// Pour la checkbox HACHE.

	private class comboBoxAxeListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (numberOfCheckBoxChecked < 2) {
				// On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
				checkBoxAxe.setEnabled(true);
				// On impose la condition pour voir si il a bien été cliqué, alors on lui
				// attribue un booléen de la valeur true. Afin de récupérer cette information et
				// de l'insérer dans la base de données.
				if (checkBoxAxe.isSelected()) {
					checkBoxAxe.setEnabled(false);
					numberOfCheckBoxChecked++;
				}
			} else {
				checkBoxAxe.setSelected(false);
			}

		}

	}

	// Pour la checkbox SPEAR.
	private class comboBoxSpearListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {

			if (numberOfCheckBoxChecked < 2) {
				// On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
				checkBoxSpear.setEnabled(true);
				// On impose la condition pour voir si il a bien été cliqué, alors on lui
				// attribue un booléen de la valeur true. Afin de récupérer cette information et
				// de l'insérer dans la base de données.
				if (checkBoxSpear.isSelected()) {
					checkBoxSpear.setEnabled(false);
					numberOfCheckBoxChecked++;
				}
			} else {
				checkBoxSpear.setSelected(false);
			}

		}
	}

	// Pour la checkbox MAGIC.
	private class comboBoxMagicListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (numberOfCheckBoxChecked < 2) {
				// On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
				checkBoxMagic.setEnabled(true);
				// On impose la condition pour voir si il a bien été cliqué, alors on lui
				// attribue un booléen de la valeur true. Afin de récupérer cette information et
				// de l'insérer dans la base de données.
				if (checkBoxMagic.isSelected()) {
					checkBoxMagic.setEnabled(false);
					numberOfCheckBoxChecked++;
				}
			} else {
				checkBoxMagic.setSelected(false);
			}

		}

	}

	// Pour la checkbox MARTEAU.
	private class comboBoxHammerListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (numberOfCheckBoxChecked < 2) {
				// On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
				checkBoxHammer.setEnabled(true);
				// On impose la condition pour voir si il a bien été cliqué, alors on lui
				// attribue un booléen de la valeur true. Afin de récupérer cette information et
				// de l'insérer dans la base de données.
				if (checkBoxHammer.isSelected()) {
					checkBoxHammer.setEnabled(false);
					numberOfCheckBoxChecked++;
				}
			} else {
				checkBoxHammer.setSelected(false);
			}

		}

	}
}
