package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import betaboutique.outils.GestionBaseDeDonnees;


/**
 * Classe ModeleNote
 * @author J�r�me Lafosse
 * @version 0.01
 */ 
public class ModeleNote 
{
	// Variables
	DataSource datasource=null;
	Connection connexion=null;
	ResultSet resultat=null;
	
	// Constructeur
	public ModeleNote(DataSource datasource)
	{		
		this.datasource=datasource;
	}
	
	
	// R�cup�rer une note
	public int getNoteArticle(int idArticle)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int note=5;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Cr�ation de la requ�te
			requeteString="SELECT AVG(note) as moyenne FROM notearticle WHERE id_article=?";
			
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idArticle);
		
			// Execution de la requ�te
			resultat=requete.executeQuery();

			// On stocke le resultat dans l'objet note 
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// Si le l'article n'a pas encore �t� not� on initalise la note � 5
					if(resultat.getString("moyenne")==null)
					{
						note=5;
					}
					else
					{
						note=resultat.getInt("moyenne");
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleNote fonction getNoteArticle");
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
				System.out.println(ex);
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleNote fonction getNoteArticle");
			}	
		}
		
		// Retourner la note de l'article
		return note;
	}
	
	
	
	
	
	// Retourner la note
	public int getNoteClient(int idArticle,int idClient)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int note=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Cr�ation de la requ�te
			requeteString="SELECT note FROM notearticle WHERE id_article=? AND id_client = ?";
			
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idArticle);
			requete.setInt(2,idClient);
			
			// Execution de la requ�te
			resultat=requete.executeQuery();

			// On stocke le resultat dans l'objet note
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// Si l'utilisateur n'a pas not� l'article on initialise la note � 0
					if(resultat.getString("note")==null)
					{
						note=0;
					}
					else
					{
						note=resultat.getInt("note");
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleNote fonction getNoteClient");
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
				System.out.println(ex);
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleNote fonction getNoteClient");
			}	
		}
		
		// Retourner la note de l'article
		return note;
	}
	
	
	
	// Retourner le nombre de votes
	public int getNombreVote(int idArticle)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int vote=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Cr�ation de la requ�te
			requeteString="SELECT count(*) as vote FROM notearticle WHERE id_article=?";
			
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idArticle);
		
			// Execution de la requ�te
			resultat=requete.executeQuery();

			// On stocke le resultat dans l'objet vote
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// Si aucun vote n'a �t� effectu� pour cet article on initalise le nombre de vote � 1
					if(resultat.getString("vote")==null)
					{
						vote=0;
					}
					else
					{
						vote=resultat.getInt("vote");
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Erreur dans la requete dans la classe ModeleNote fonction getNombreVote");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleNote fonction getNombreVote");
			}	
		}
		
		// Retourner la note de l'article
		return vote;
	}
	
	
	
	
	// Pour noter un article
	public void noter(int idArticle, int note, int idClient)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
			
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Cr�ation de la requ�te
			requeteString="INSERT INTO notearticle VALUES(?,?,?)";
			
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idArticle);
			requete.setInt(2,idClient);
			requete.setInt(3,note);
		
			// Execution de la requ�te
			requete.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleNote fonction noter");
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
				if(connexion != null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleNote fonction noter");
			}	
		}
	}
	
	
	// Supprimer une note
	public int supprimerNote(int idArticle)
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
			
			// Cr�ation de la requ�te
			requeteString="DELETE FROM notearticle WHERE id_article = ?";
			
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idArticle);
			
			// Execution de la requ�te
			requete.executeUpdate();
			codeErreur=1;
			// Si il ya une erreur durant la suppression des notes d'un article on fait un rollback
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
			System.out.println("Erreur dans la requete dans la classe ModeleNote fonction supprimerNote");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleNote fonction supprimerNote");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
}
