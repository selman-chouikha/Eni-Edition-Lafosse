package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import betaboutique.beans.Article;
import betaboutique.beans.LigneCommande;
import betaboutique.outils.GestionBaseDeDonnees;

/**
 * Classe ModeleLigneCommande
 * @author J�r�me Lafosse
 * @version 0.01
 */ 
public class ModeleLigneCommande 
{
	// Variables
	DataSource datasource=null;
	Connection connexion=null;
	ResultSet resultat=null;
	
	// Constructeur
	public ModeleLigneCommande(DataSource datasource)
	{		
		this.datasource=datasource;
	}
	
	// Lister les commandes
	public List<LigneCommande> listerLigneCommande(int idCommande)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		LigneCommande ligneCommande=null;
		List<LigneCommande> listeLigneCommande=null;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			    	
	      	// Cr�ation de la requ�te
	    	requeteString="SELECT * FROM lignecommande WHERE id_commande = ?";
	    			
	    	// Pr�paration de la requ�te
	    	requete=connexion.prepareStatement(requeteString);
	    	requete.setInt(1, idCommande);
	    	    	
	    	// Execution de la requ�te
	    	resultat=requete.executeQuery();
	    	
	    	// Initialisation de la liste des lignes commandes
	    	listeLigneCommande=new ArrayList<LigneCommande>();
	    	
	    	// On stocke le resultat dans une liste de ligneCommande
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					ligneCommande = ligneCommandeAttributsMapper(resultat);
					
					// Ajoute l'objet a la liste des lignes de la commande
					listeLigneCommande.add(ligneCommande);
				}
			}
			
			// On annule l'id par s�curit�
			idCommande=0;
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleLigneCommande fonction listerLigneCommande");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleLigneCommande fonction listerLigneCommande");
			}	
		}
		
		// Retourner la liste des lignes commandes
		return listeLigneCommande;
	}
	
	
	// Ajouter une ligne
	public int ajouterListeLigneCommande(List<LigneCommande> listeLigneCommande,int idCommande)
	{	
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		LigneCommande ligneCommande=null;
		int codeErreur=1;
		int compteur=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Annulation de l'autocommit
			connexion.setAutoCommit(false);
			
	        // Chaque ligne de la commande est ajout�e � la base de donn�es
	        while(compteur<listeLigneCommande.size() && codeErreur==1)
	        {
	          	// On r�cupert la ligne
	           	ligneCommande=listeLigneCommande.get(compteur);
	            	
	           	// Cr�ation de la requ�te
	    		requeteString = "INSERT INTO lignecommande (id_article,id_commande,prixunitaire,quantitearticle) VALUES(?,?,?,?)";
	    			
	    		// Pr�paration de la requ�te
	    		requete=connexion.prepareStatement(requeteString);
	    		requete.setInt(1, ligneCommande.getArticle().getId());
	    		requete.setInt(2, idCommande);
	    		requete.setDouble(3, ligneCommande.getPrixUnitaire());
	    		requete.setInt(4, ligneCommande.getQuantite());
	            	
	    		// Execution de la requ�te
	    		codeErreur=requete.executeUpdate();
	    			
	    		// Incr�mentation
	    		compteur++;
			}
			
			// Si il y a une erreur durant l'ajout des lignes de la commande en base, on fait un rollback
			if(codeErreur!=1)
			{
				GestionBaseDeDonnees.rollback(connexion);
			}
			else
			{
				GestionBaseDeDonnees.commit(connexion);
			}
			
			// On vide la la liste de ligneCommande par s�curit�
			listeLigneCommande=null;
		}
		catch(Exception e)
		{
			codeErreur=0;
			GestionBaseDeDonnees.rollback(connexion);
			System.out.println("Erreur dans la requete dans la classe ModeleLigneCommande fonction ajouterListeLigneCommande");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleLigneCommande fonction ajouterListeLigneCommande");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
	
	
	
	// R�aliser la transformation Relationnel vers Objet
	private LigneCommande ligneCommandeAttributsMapper(ResultSet resultat)
	{
		// Variables
		LigneCommande ligneCommande=null;
		ModeleArticleCommande modeleArticle=null;
		
		// Initialisation du mod�le
		modeleArticle=new ModeleArticleCommande(this.datasource);
		
		// Initalisation de l'objet commande
		ligneCommande=new LigneCommande();
		
		try
		{	
			// On renseigne l'objet ligneCommande
			if(resultat.getString("id_lignecommande")==null)
			{
				ligneCommande.setId(0);
			}
			else
			{
				ligneCommande.setId(resultat.getInt("id_lignecommande"));
			}
			
			if(resultat.getString("id_article")==null)
			{
				ligneCommande.setArticle(new Article());
			}
			else
			{
				ligneCommande.setArticle(modeleArticle.getArticle(resultat.getInt("id_article")));
			}
			
			if(resultat.getString("prixunitaire")==null)
			{
				
				ligneCommande.setPrixUnitaire(0);
			}
			else
			{
				ligneCommande.setPrixUnitaire(resultat.getDouble("prixunitaire"));
			}
			
			if(resultat.getString("quantitearticle")==null)
			{
				
				ligneCommande.setQuantite(0);
			}
			else
			{
				ligneCommande.setQuantite(resultat.getInt("quantitearticle"));
			}
			
		}
		catch (Exception e) 
		{
			// Si il y a une erreur durant le mappage des attributs avec la base on renvoie un objet null
			ligneCommande=null;
			System.out.println("Erreur lors du mappage des attributs d'un articles dans la classe ModeleArticle fonction ArticleAttributsMapper");
		}
		
		// On retourne la commande
		return ligneCommande;
	}
	
	
	
}
