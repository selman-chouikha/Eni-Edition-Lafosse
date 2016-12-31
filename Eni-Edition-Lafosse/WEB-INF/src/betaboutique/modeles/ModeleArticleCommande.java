package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import betaboutique.beans.Article;
import betaboutique.beans.Categorie;
import betaboutique.outils.GestionBaseDeDonnees;
import betaboutique.modeles.ModeleCategorie;


/**
 * Classe ModeleArticleCommande
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class ModeleArticleCommande 
{
	// Variables
	DataSource datasource=null;
	Connection connexion=null;
	ResultSet resultat=null;
	
	// Constructeur
	public ModeleArticleCommande(DataSource datasource)
	{		
		this.datasource=datasource;
	}
	
	
	// Récupérer un article
	public Article getArticle(int idArticle)
	{	
		// Variables
		PreparedStatement requete=null;
		Article article=null;
		String requeteString=null;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Création de la requête
			requeteString="SELECT * FROM articlecommande WHERE id_articlecommande =?";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idArticle);
				
			// Execution de la requête
			resultat = requete.executeQuery();

			// On stocke le resultat dans l'objet article
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					article=articleAttributsMapper(resultat);
				}
			}
		}
		catch(Exception e)
		{
			// L'id de l'article n'exite pas, on initialise l'article à null
			article=null;
			System.out.println("Erreur dans la requete dans la classe ModeleArticleCommande fonction getArticle");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticleCommande fonction getArticle");
			}	
		}
		
		// Retourner l'article
		return article;
	}
	
	
	// Réaliser la transformation Relationnel vers Objet
	public Article articleAttributsMapper(ResultSet resultat)
	{
		// Variables
		Article article=null;
		SimpleDateFormat dateFormat=null;
		ModeleCategorie modeleCategorie=null;
		ModeleNote modeleNote=null;
		
		// Initialisation des modèles
		modeleCategorie=new ModeleCategorie(this.datasource);
		modeleNote=new ModeleNote(this.datasource);
		
		// Initalisation de l'objet article
		article=new Article();
		
		// Initialisation du format de la date
		dateFormat=new SimpleDateFormat("yyyyMMdd");
		
		try
		{	
			// On renseigne l'objet article
			if(resultat.getString("id_articlecommande")==null)
			{
				article.setId(0);
			}
			else
			{
				article.setId(resultat.getInt("id_articlecommande"));
			}
			
			if(resultat.getString("referencearticle")==null)
			{
				article.setReference(0);
			}
			else
			{
				article.setReference(resultat.getInt("referencearticle"));
			}
			
			if(resultat.getString("nomarticle")==null)
			{
				article.setNom("");
			}
			else
			{
				article.setNom(resultat.getString("nomarticle"));
			}
			
			if(resultat.getString("descriptionarticle")==null)
			{
				article.setDescription("");
			}
			else
			{
				article.setDescription(resultat.getString("descriptionarticle"));
			}
		
			if(resultat.getString("prixarticle")==null)
			{
				article.setPrix(0.0);
			}
			else
			{
				article.setPrix((resultat.getDouble("prixarticle")));
			}
			
			if(resultat.getString("reductionarticle")==null)
			{
				article.setReduction(0);
			}
			else
			{
				article.setReduction((resultat.getInt("reductionarticle")));
			}
			
			if(resultat.getString("datearticle")==null)
			{
				
				article.setDate(dateFormat.parse(""));
			}
			else
			{
				article.setDate(dateFormat.parse(resultat.getString("datearticle")));
			}
			
			if(resultat.getString("photoarticle")==null)
			{
				
				article.setPhoto("");
			}
			else
			{
				article.setPhoto(resultat.getString("photoarticle"));
			}
			
			if(resultat.getString("vignettearticle")==null)
			{
				
				article.setVignette("");
			}
			else
			{
				article.setVignette(resultat.getString("vignettearticle"));
			}
			
			if(resultat.getString("etatarticle")==null)
			{
				
				article.setEtat(0);
			}
			else
			{
				article.setEtat(resultat.getInt("etatarticle"));
			}
			
			if(resultat.getString("poidsarticle")==null)
			{
				
				article.setPoids(0);
			}
			else
			{
				article.setPoids(resultat.getInt("poidsarticle"));
			}
			
			if(resultat.getString("stockarticle")==null)
			{
				
				article.setStock(0);
			}
			else
			{
				article.setStock(resultat.getInt("stockarticle"));
			}
			
			if(resultat.getString("id_categorie")==null)
			{
				article.setCategorie(new Categorie());
			}
			else
			{
				article.setCategorie(modeleCategorie.getCategorie(resultat.getInt("id_categorie")));
			}
			
			// On récupert la note de l'article
			article.setNote(modeleNote.getNoteArticle(article.getId()));
			
			// On récupert le nombre de votes pour cet article
			article.setVote(modeleNote.getNombreVote(article.getId()));
			
		}
		catch (Exception e) 
		{
			System.out.println(e);
			// Si il y a une erreur durant le mapping des attributs avec la base on renvoi un objet null
			article=null;
			System.out.println("Erreur lors du mappage des attributs d'un articles dans la classe ModeleArticleCommande fonction ArticleAttributsMapper");
		}
		
		// On retourne l'article
		return article;
	}
	
	
}
