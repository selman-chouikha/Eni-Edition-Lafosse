package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import betaboutique.outils.GestionBaseDeDonnees;
import betaboutique.beans.Raccourci;


/**
 * Classe ModeleRaccourci
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class ModeleRaccourci 
{
	// Variables
	DataSource datasource=null;
	Connection connexion=null;
	ResultSet resultat=null;
	
	// Constructeur
	public ModeleRaccourci(DataSource datasource)
	{		
		this.datasource=datasource;
	}
	
	
	// Retourner la liste des raccourcis
	public List<Raccourci> listerRaccourci(int idClient)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		List<Raccourci> listeRaccourci=null;
		Raccourci raccourci=null;
		
		// Initalisation de la liste des raccourcis
		listeRaccourci=new ArrayList<Raccourci>();
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Création de la requête
			requeteString="SELECT * FROM raccourci WHERE id_client = ? ORDER BY nomraccourci ASC";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idClient);
		
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat dans une la liste de raccourcis
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					raccourci=raccourciAttributsMapper(resultat);
					
					// Ajoute l'objet a la liste des raccourcis
					listeRaccourci.add(raccourci);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleRaccourci fonction listerRaccourci");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleRaccourci fonction listerRaccourci");
			}	
		}
		
		// Retourner la liste des raccourcis du client
		return listeRaccourci;
	}
	
	
	
	// Ajouter un raccourci
	public int ajouterRaccourci(Raccourci raccourci, int idClient)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int codeErreur=0;
		
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Création de la requête
			requeteString="INSERT INTO raccourci (id_client,nomraccourci,urlraccourci) VALUES (?,?,?)";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idClient);
			requete.setString(2, raccourci.getNom());
			requete.setString(3, raccourci.getUrl());
			
			// Execution de la requête
			codeErreur=requete.executeUpdate();
		}
		catch(Exception e)
		{
			codeErreur=0;
			System.out.println("Erreur dans la requete dans la classe ModeleRaccourci fonction ajouterRaccourci");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleRaccourci fonction ajouterRaccourci");
			}	
		}
		
		// On retourne le code d'erreur
		return codeErreur;
	}
	
	
	
	// Récupérer un raccourci
	public Raccourci getRaccourci(String nom, int idClient)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		Raccourci raccourci=null;
		
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Création de la requête
			requeteString="SELECT * FROM raccourci WHERE id_client = ? AND (nomraccourci = ? OR urlraccourci = ?)";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idClient);
			requete.setString(2, nom);
			requete.setString(3, nom);
			
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On stocke le resultat dans l'objet raccourci
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					raccourci = raccourciAttributsMapper(resultat);
				}
			}
		}
		catch(Exception e)
		{
			raccourci=null;
			System.out.println("Erreur dans la requete dans la classe ModeleRaccourci fonction getRaccourci");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleRaccourci fonction getRaccourci");
			}	
		}
		
		// On retourne le raccourci
		return raccourci;
	}
	
	
	
	// Retourner le raccourci
	public Raccourci getRaccourci(int idRaccourci)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		Raccourci raccourci=null;
		
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Création de la requête
			requeteString="SELECT * FROM raccourci WHERE id_raccourci = ?";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idRaccourci);
			
			// Execution de la requête
			resultat = requete.executeQuery();
			
			// On stocke le resultat dans l'objet raccourci
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					raccourci=raccourciAttributsMapper(resultat);
				}
			}
		}
		catch(Exception e)
		{
			raccourci=null;
			System.out.println("Erreur dans la requete dans la classe ModeleRaccourci fonction getRaccourci");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleRaccourci fonction getRaccourci");
			}	
		}
		
		// On retourne le raccourci
		return raccourci;
	}
	
	
	
	// Retourner le nombre de raccourcis
	public int getNombreRaccourci(int idClient)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int nombre=0;
		
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Création de la requête
			requeteString="SELECT count(*) as total FROM raccourci WHERE id_client = ?";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idClient);
			
			// Execution de la requête
			resultat = requete.executeQuery();
			
			// On stocke le resultat dans l'objet nombre
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// Si l'utilisateur n'a pas de raccourci on initialise le nombre à 0
					if(resultat.getString("total")==null)
					{
						nombre=0;
					}
					else
					{
						nombre=resultat.getInt("total");
					}
				}
			}
		}
		catch(Exception e)
		{
			nombre=0;
			System.out.println("Erreur dans la requete dans la classe ModeleRaccourci fonction getNombreRaccourci");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleRaccourci fonction getNombreRaccourci");
			}	
		}
		
		// On retourne le nombre de raccourcis de l'utilisateur
		return nombre;
	}
	
	
	// Supprimer un raccourci
	public int supprimerRaccourci(int idRaccourci)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int codeErreur=0;
		
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Création de la requête
			requeteString="DELETE FROM raccourci WHERE id_raccourci = ?";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idRaccourci);
			
			// Execution de la requête
			codeErreur=requete.executeUpdate();
		}
		catch(Exception e)
		{
			codeErreur=0;
			System.out.println("Erreur dans la requete dans la classe ModeleRaccourci fonction supprimerRaccourci");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleRaccourci fonction supprimerRaccourci");
			}	
		}
		
		// On retourne le code d'erreur
		return codeErreur;
	}
	
	
	// Supprimer un raccourci
	public int supprimerRaccourcisClient(int idClient)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int codeErreur=0;
			
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// On annuler l'autocommit
			connexion.setAutoCommit(false);
			
			// Création de la requête
			requeteString="DELETE FROM raccourci WHERE id_client = ?";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idClient);
			
			// Execution de la requête
			codeErreur=requete.executeUpdate();
			
			// Si il ya une erreur durant la suppression des raccourcis d'un client on fait un rollback
			if(codeErreur!=1)
			{
				GestionBaseDeDonnees.rollback(connexion);
			}
			else
			{
				GestionBaseDeDonnees.commit(connexion);
			}
		}
		catch(Exception e)
		{
			codeErreur=0;
			GestionBaseDeDonnees.rollback(connexion);
			System.out.println("Erreur dans la requete dans la classe ModeleRaccourci fonction supprimerRaccourcisClient");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleRaccourci fonction supprimerRaccourcisClient");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
	
	// Réaliser la transformation Relationnel vers Objet
	public Raccourci raccourciAttributsMapper(ResultSet resultat)
	{
		// Variables
		Raccourci raccourci=null;
				
		// Initialiser le raccourci
		raccourci=new Raccourci();
		
		try
		{	
			// On renseigne l'objet article
			if(resultat.getString("id_raccourci")==null)
			{
				raccourci.setId(0);
			}
			else
			{
				raccourci.setId(resultat.getInt("id_raccourci"));
			}
			
			if(resultat.getString("nomraccourci")==null)
			{
				raccourci.setNom("");
			}
			else
			{
				raccourci.setNom(resultat.getString("nomraccourci"));
			}	
			
			if(resultat.getString("urlraccourci")==null)
			{
				raccourci.setUrl("");
			}
			else
			{
				raccourci.setUrl(resultat.getString("urlraccourci"));
			}	
		}
		catch (Exception e) 
		{
			// Si il y a une erreur durant le mapping des attributs avec la base on renvoie un objet null
			raccourci=null;
			System.out.println("Erreur lors du mappage des attributs d'un articles dans la classe ModeleRaccourci fonction raccourciAttributsMapper");
		}
		
		// On retourne le raccourci
		return raccourci;
	}
	
	
	
}
