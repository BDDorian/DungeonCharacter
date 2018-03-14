package myDungeonSheet;

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

public class creationCharacter extends JFrame {
	
	private static String pseudoField;

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
	
	// JCheckBow ELEMENTS //
	private JCheckBox checkBoxBow;
	private JCheckBox checkBoxSword;
	private JCheckBox checkBoxAxe;
	private JCheckBox checkBoxSpear;
	private JCheckBox checkBoxMagic;
	private JCheckBox checkBoxHammer;
	
	
	// ComboBox Elements //
	private JComboBox comboBoxClassChosen;
	private JComboBox comboBoxRaceChosen;
	private JComboBox comboBoxSexChosen;
	
	
	//Jlabel Elements //
	
	private JLabel jLabelClasseArmure;
	private JLabel labelNom;
	
	// Handler Elements //
	
	// Other Elements //
	private int numberOfCheckBoxChecked;
	private int myIdUser = 2;
	private int myIdPerso = 1;
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
	 */
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
		labelNom.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		labelNom.setBounds(10, 85, 46, 14);
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
		lblRace.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblRace.setBounds(10, 112, 46, 14);
		panel.add(lblRace);
		
		JLabel lblClasse = new JLabel("Classe:");
		lblClasse.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblClasse.setBounds(242, 112, 46, 14);
		panel.add(lblClasse);
		
		jLabelClasseArmure = new JLabel("Classe Armure:");
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
		
		//Méthode afin de limiter le nombre de caractères saisies à 20 afin de concorder avec la base de données.
		textFieldNom.addKeyListener(new KeyAdapter(){
			@Override
			public void keyTyped(KeyEvent e) {
				if(textFieldNom.getText().length() >= 20) {
					e.consume();// Imposition d'une limite à 20 caractères.
				}
			}
		});
		
		
		textFieldNom.setBounds(98, 77, 96, 22);
		panel.add(textFieldNom);	
		textFieldNom.setColumns(10);
		
		textFieldAge = new JTextField();
		//Méthode crée afin de ne rentrer que des chiffres pour l'age.
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
		comboBoxRaceChosen.setBounds(98, 112, 96, 17);
		Connection myDatabaseCon;
		PreparedStatement myStatement;
		ResultSet myResult;
		
		try {
			myDatabaseCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjonbase", "root", "root");
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
			myDatabaseConnexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjonbase", "root", "root");
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
		lblNewLabel_1.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(71, 187, 73, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblDextrit = new JLabel("Dext\u00E9rit\u00E9 :");
		lblDextrit.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblDextrit.setBounds(71, 227, 73, 14);
		panel.add(lblDextrit);
		
		JLabel lblConstitution = new JLabel("Constitution : ");
		lblConstitution.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblConstitution.setBounds(71, 266, 82, 14);
		panel.add(lblConstitution);
		
		JLabel lblIntelligence = new JLabel("Intelligence :");
		lblIntelligence.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblIntelligence.setBounds(71, 309, 73, 14);
		panel.add(lblIntelligence);
		
		JLabel lblSagesse = new JLabel("Sagesse :");
		lblSagesse.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblSagesse.setBounds(71, 349, 73, 14);
		panel.add(lblSagesse);
		
		JLabel lblCharisme = new JLabel("Charisme :");
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
		lblComptences.setFont(new Font("Goudy Old Style", Font.BOLD | Font.ITALIC, 18));
		lblComptences.setBounds(426, 158, 117, 18);
		panel.add(lblComptences);
		
		JLabel lblEpe = new JLabel("Ep\u00E9e :");
		lblEpe.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblEpe.setBounds(426, 227, 46, 14);
		panel.add(lblEpe);
		
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
	// *** Pour se connecter à la base de données existantes : ***//
				
				// On crée un objet de type Connection, un autre de type Statement, et un dernier de type Result.
				
				Connection myDatabaseCon;
				PreparedStatement myStatement;
				ResultSet myResult;
				System.out.print(comboBoxSexChosen.getSelectedItem());
				
				try {
					myDatabaseCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjonbase", "root", "root");
					String addCharacter = "INSERT INTO personnage(id_personnage, nom_personnage, age_personnage, sexe_personnage, classeArmure_personnage, force_personnage, dexterite_personnage, constitution_personnage, intelligence_personnage, sagesse_personnage, charisme_personnage, id_utilisateur) values ('" +myIdPerso+"', '"+textFieldNom.getText()+"','"+textFieldAge.getText()+"','"+comboBoxSexChosen.getSelectedItem()+"', '"+textFieldClasseArmure.getText()+"', '"+textFieldForce.getText()+"', '"+textFieldDexterite.getText()+"', '"+textFieldConstitution.getText()+"', '"+textFieldIntelligence.getText()+"','"+textFieldSagesse.getText()+"', '"+textFieldCharisme.getText()+"','"+myIdUser+"')";
					//On initialise ensuite la création de la requête.
					
					// On exécute la requête désirée :
					//addUser.execute();
					//On initialise ensuite la création de la requête.
					myStatement = myDatabaseCon.prepareStatement(addCharacter);
					
					// On exécute la requête désirée :
					myStatement.execute();
					//On indique à l'utilisateur que la création a réussi :
					JOptionPane.showMessageDialog(null, "Votre compte a bien été crée");
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnNewButton.setBounds(262, 397, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnLancerLeD = new JButton("Lancer le d\u00E9");
		btnLancerLeD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
	}
	

	// *** EVENT LISTENER *** //
	
		
		private class comboBoxClassChosenListener implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				String obj = (String)comboBoxClassChosen.getSelectedItem();
				System.out.print(obj);
				if(obj.equals("Barbare")) {
					textFieldClasseArmure.setText("15");
				}
				else if(obj.equals("Barde")) {
					textFieldClasseArmure.setText("22");
				}
				else if(obj.equals("Druide")){
					textFieldClasseArmure.setText("20");
				}
				else if(obj.equals("Guerrier")) {
					textFieldClasseArmure.setText("14");
				}
				else if(obj.equals("Mage")) {
					textFieldClasseArmure.setText("24");
				}
				else if(obj.equals("Moine")) {
					textFieldClasseArmure.setText("23");
				}
				else if(obj.equals("Paladin")) {
					textFieldClasseArmure.setText("16");
				}
				else if(obj.equals("Prêtre")) {
					textFieldClasseArmure.setText("21");
				}
				else if(obj.equals("Rôdeur")) {
					textFieldClasseArmure.setText("16");
				}
				else if(obj.equals("Voleur")) {
					textFieldClasseArmure.setText("18");
				}
		}
		}
		
		private class comboBoxRaceChosenListener implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				String obj = (String)comboBoxRaceChosen.getSelectedItem();
				System.out.print(obj);
				
			}
		}
		
		//Pour la checkbox ARC.
				private class comboBoxBowListener implements ItemListener{
					@Override
					public void itemStateChanged(ItemEvent e) {
						//On vérifie si le nombre de checkbox est inférieur à 2.
						if(numberOfCheckBoxChecked < 2) {
							//On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
							checkBoxBow.setEnabled(true);
							//On impose la condition pour voir si il a bien été cliqué, alors on lui attribue un booléen de la valeur true. Afin de récupérer cette information et de l'insérer dans la base de données.
							if(checkBoxBow.isSelected()) {
								checkBoxBow.setEnabled(false);
								numberOfCheckBoxChecked ++;
						}			
						}
						else {
							checkBoxBow.setEnabled(false);
						}
							
					}
				}
		
		//Pour la checkbox EPEE.
		
		private class comboBoxSwordListener implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(numberOfCheckBoxChecked < 2) {
					//On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
					checkBoxSword.setEnabled(true);
					//On impose la condition pour voir si il a bien été cliqué, alors on lui attribue un booléen de la valeur true. Afin de récupérer cette information et de l'insérer dans la base de données.
					if(checkBoxSword.isSelected()) {
						checkBoxSword.setEnabled(false);
						numberOfCheckBoxChecked ++;
				}			
				}
				else {
					checkBoxSword.setSelected(false);
				}
					
			}
		}
		
		//Pour la checkbox HACHE.
		
		private class comboBoxAxeListener implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(numberOfCheckBoxChecked < 2) {
					//On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
					checkBoxAxe.setEnabled(true);
					//On impose la condition pour voir si il a bien été cliqué, alors on lui attribue un booléen de la valeur true. Afin de récupérer cette information et de l'insérer dans la base de données.
					if(checkBoxAxe.isSelected()) {
						checkBoxAxe.setEnabled(false);
						numberOfCheckBoxChecked ++;
				}			
				}
				else {
					checkBoxAxe.setSelected(false);
				}
					
			}
			
			}
		
		//Pour la checkbox SPEAR.
		private class comboBoxSpearListener implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if(numberOfCheckBoxChecked < 2) {
					//On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
					checkBoxSpear.setEnabled(true);
					//On impose la condition pour voir si il a bien été cliqué, alors on lui attribue un booléen de la valeur true. Afin de récupérer cette information et de l'insérer dans la base de données.
					if(checkBoxSpear.isSelected()) {
						checkBoxSpear.setEnabled(false);
						numberOfCheckBoxChecked ++;
					}			
				}
				else {
					checkBoxSpear.setSelected(false);
				}
					
			}
		}
		
		//Pour la checkbox MAGIC.
		private class comboBoxMagicListener implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(numberOfCheckBoxChecked < 2) {
					//On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
					checkBoxMagic.setEnabled(true);
					//On impose la condition pour voir si il a bien été cliqué, alors on lui attribue un booléen de la valeur true. Afin de récupérer cette information et de l'insérer dans la base de données.
					if(checkBoxMagic.isSelected()) {
						checkBoxMagic.setEnabled(false);
						numberOfCheckBoxChecked ++;
					}			
				}
					else {
					checkBoxMagic.setSelected(false);
					}
					
			}
				
		}
		
		
		//Pour la checkbox MARTEAU.
		private class comboBoxHammerListener implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(numberOfCheckBoxChecked < 2) {
					//On place la valeur de la checkbox a TRUE pour qu'elle soit sélectionnable.
					checkBoxHammer.setEnabled(true);
					//On impose la condition pour voir si il a bien été cliqué, alors on lui attribue un booléen de la valeur true. Afin de récupérer cette information et de l'insérer dans la base de données.
					if(checkBoxHammer.isSelected()) {
						checkBoxHammer.setEnabled(false);
						numberOfCheckBoxChecked ++;
					}			
				}
					else {
					checkBoxHammer.setSelected(false);
					}
					
			}
				
			}
			
}
