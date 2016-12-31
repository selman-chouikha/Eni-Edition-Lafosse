package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import betaboutique.outils.GestionBaseDeDonnees;
import java.util.ArrayList;

/**
 * Classe ModeleAutoComplete
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class ModeleAutoComplete
{
	// Attributs
	private DataSource datasource=null;
	private Connection connexion=null;
	private ResultSet resultat=null;

	// Constructeur
	public ModeleAutoComplete(DataSource datasource)
	{
		this.datasource=datasource;
	}
	
	// Retourner la liste des données
	public ArrayList<String> autoCompleteUtilisateur(String saisie, String limit)
	{
		// Variables 
		PreparedStatement requete=null;
		String requeteString=null;
		ArrayList<String> liste=new ArrayList<String>();
		
		try
		{
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// On créé la requête
			requeteString="SELECT DISTINCT(nomarticle) FROM article WHERE etatarticle=1 AND nomarticle LIKE ? ORDER BY nomarticle LIMIT 0,"+limit;
	
			// Préparation de la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setString(1,(String)"%"+saisie+"%");
			resultat=requete.executeQuery();
			
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat dans une liste
			if(resultat!=null)
			{
				while(resultat.next())
				{
					if(resultat.getString("nomarticle")!=null)
					{
						liste.add((String)resultat.getString("nomarticle"));
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la classe ModeleAutoComplete.java fonction ListeAutoCompleteArticle");
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
				System.out.println("Erreur dans la classe ModeleAutoComplete.java fonction ListeAutoCompleteArticle");

			}
		}
		
		// On retourne la liste
		return liste;
	}
	
	
	// Retourner la liste des donneés
	public ArrayList<String> autoCompleteAdministrateur(String saisie, String limit, String attribut, String table)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		ArrayList<String> liste=new ArrayList<String>();
		
		try
		{
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// On créé la requête
			requeteString="SELECT DISTINCT(" + attribut + ") FROM " + table + " WHERE " + attribut + " LIKE ? ORDER BY " + attribut + " LIMIT 0,"+limit;
			
			// Préparation de la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setString(1,(String)"%"+saisie+"%");
			resultat=requete.executeQuery();
			
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat dans une listev
			if(resultat!=null)
			{
				while(resultat.next())
				{
					if(resultat.getString(attribut)!=null)
					{
						liste.add((String)resultat.getString(attribut));
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la classe ModeleAutoComplete.java fonction ListeAutoCompleteArticle");
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
				System.out.println("Erreur dans la classe ModeleAutoComplete.java fonction ListeAutoCompleteArticle");

			}
		}
		
		// On retourne la liste
		return liste;
	}
	
	
	
}