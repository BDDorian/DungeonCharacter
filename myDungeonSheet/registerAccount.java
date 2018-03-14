package myDungeonSheet;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class registerAccount extends JFrame {

	private JPanel contentPane;
	private JTextField fieldName;
	private JTextField fieldSurname;
	private JTextField fieldPseudo;
	private JTextField fieldPassword;
	private JTextField fieldMail;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					registerAccount frame = new registerAccount();
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
	public registerAccount() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCrationDeVotre = new JLabel("Cr\u00E9ation de votre compte utilisateur");
		lblCrationDeVotre.setFont(new Font("Goudy Old Style", Font.BOLD | Font.ITALIC, 22));
		lblCrationDeVotre.setBounds(54, 0, 343, 31);
		contentPane.add(lblCrationDeVotre);
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblNom.setBounds(91, 55, 46, 14);
		contentPane.add(lblNom);
		
		JLabel lblPrnom = new JLabel("Pr\u00E9nom :");
		lblPrnom.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblPrnom.setBounds(91, 88, 60, 14);
		contentPane.add(lblPrnom);
		
		JLabel lblPseudo = new JLabel("Pseudo :");
		lblPseudo.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblPseudo.setBounds(91, 124, 60, 14);
		contentPane.add(lblPseudo);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblMotDePasse.setBounds(91, 159, 91, 14);
		contentPane.add(lblMotDePasse);
		
		JLabel lblEmail = new JLabel("E-mail :");
		lblEmail.setFont(new Font("Goudy Old Style", Font.PLAIN, 15));
		lblEmail.setBounds(91, 197, 46, 14);
		contentPane.add(lblEmail);
		
		JButton btnSinscrire = new JButton("S'inscrire");
		btnSinscrire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// *** Pour se connecter à la base de données existantes : ***//
				
				// On crée un objet de type Connection, un autre de type Statement, et un dernier de type Result.
				
				Connection myDatabaseCon;
				PreparedStatement myStatement;
				ResultSet myResult;
				
				try {
					myDatabaseCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/donjonbase", "root", "root");
					String addUser = "INSERT INTO utilisateur(nom_utilisateur, prenom_utilisateur, pseudo_utilisateur,motDePasse_utilisateur, mail_utilisateur) values ( ?, ?, ?, ?, ?)";
					//On initialise ensuite la création de la requête.
					myStatement = myDatabaseCon.prepareStatement(addUser);
					myStatement.setString(1, fieldName.getText());
					myStatement.setString(2, fieldSurname.getText());
					myStatement.setString(3, fieldPseudo.getText());
					myStatement.setString(4, fieldPassword.getText());
					myStatement.setString(5, fieldMail.getText());
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
		btnSinscrire.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnSinscrire.setBounds(192, 227, 89, 23);
		contentPane.add(btnSinscrire);
		
		fieldName = new JTextField();
		fieldName.setBounds(192, 52, 86, 20);
		contentPane.add(fieldName);
		fieldName.setColumns(10);
		
		fieldSurname = new JTextField();
		fieldSurname.setBounds(192, 85, 86, 20);
		contentPane.add(fieldSurname);
		fieldSurname.setColumns(10);
		
		fieldPseudo = new JTextField();
		fieldPseudo.setBounds(192, 121, 86, 20);
		contentPane.add(fieldPseudo);
		fieldPseudo.setColumns(10);
		
		fieldPassword = new JTextField();
		fieldPassword.setBounds(192, 156, 86, 20);
		contentPane.add(fieldPassword);
		fieldPassword.setColumns(10);
		
		fieldMail = new JTextField();
		fieldMail.setBounds(192, 194, 86, 20);
		contentPane.add(fieldMail);
		fieldMail.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/creationAcc.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(0, 0, 54, 49);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(img));
		lblNewLabel_1.setBounds(380, 0, 54, 49);
		contentPane.add(lblNewLabel_1);
		
		
	}
}
