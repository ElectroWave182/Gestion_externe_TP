import java.sql.*;
import java.util.Scanner;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ngarric
 */
public class Tp3Etape2 {

	private static String URL = "jdbc:mysql://licinfo2.univ-jfc.fr:10003/ecauss04";
	private static String login = "root";
	private static String password = "licinfo2020";
	private Connection connexion;
	private Statement stmt;

	public Tp3Etape2() {
		try {
			// Etablisement de la connexion avec la base
			connexion = DriverManager.getConnection(URL, login, password);
			stmt = connexion.createStatement();

		} catch (SQLException c) {
			System.out.println("Connexion echouee ou base de donnees inconnue : " + c);
		} catch (Exception d) {
			System.out.println("Problème sur connexion");
		}
	}

	public void menu() {

		int res = -1;
		while (res != 0) {
			// on propose à l'utilisateur de choisir entre plusieurs options
			Scanner scan = new Scanner(System.in);
			do {
				System.out.println("Menu\n0=Fin\n1=Lister tous les sports\n2=Ajout d'un sport\n3=Lister tous les sportifs d'un sport");
				res = scan.nextInt();
				if (res<0 || res > 3) {
					System.out.println("mauvais choix! Recommencez.");
				}
			} while (res<0 || res > 3);
			switch (res) {

			// affichage de tous les noms de sports
			case 1:
				try {
					// on lance la requête
					String requete = "SELECT INTITULE FROM SPORT";
					ResultSet resultat = stmt.executeQuery(requete);

					System.out.println("Voici tous les noms de sports :");

					// on parcourt le résultat
					while (resultat.next()) {

						System.out.println(resultat.getString(1));
					}
					System.out.println();
				} catch (SQLException c) {
					System.out.println("Connexion echouee ou base de donnees inconnue : " + c);
				}
				break;
				
			// ajout d'un sport
			case 2:
				try {
					// saisie du nom du sport
					System.out.print("Entrer le nom du sport à ajouter : ");
					String nom = scan.next();
					
					PreparedStatement requetePreparee = connexion.prepareStatement("INSERT INTO SPORT(INTITULE) VALUES (?)"); 
					requetePreparee.setString(1,nom); 
					

					// on lance la requête
					requetePreparee.executeUpdate();
					System.out.println("Ajout réussi" );

				} catch (SQLException c) {
					System.out.println("Problème lors de l'ajout d'un sport: " + c);
				}
				break;
			
			// lister tous les sportifs d'un sport
			case 3:
				try {
					// saisie du nom du sport
					System.out.print("Entrer le nom du sport : ");
					String sport = scan.next();
					
					String requete = "SELECT NOM, PRENOM FROM SPORT, SPORTIF WHERE SPORT.INTITULE = " + '"' + sport + '"' + " AND SPORT.CODE_SPORT = SPORTIF.CODE_SPORT"; 
					ResultSet resultat = stmt.executeQuery(requete);

					System.out.println("Voici tous les noms et prénoms de sportifs pratiquant ce sport :");

					// on parcourt le résultat
					while (resultat.next()) {

						System.out.println(resultat.getString(1));
					}
					System.out.println();

				} catch (SQLException c) {
					System.out.println("Problème lors du listage des sportifs : " + c);
				}
				break;

			}
		}

		// fermeture de la connexion
		try {
			connexion.close();
			System.out.println("Programme terminé");
		} catch (SQLException c) {
			System.out.println("Problème de fermeture de connexion: " + c);
		}

	}

}
