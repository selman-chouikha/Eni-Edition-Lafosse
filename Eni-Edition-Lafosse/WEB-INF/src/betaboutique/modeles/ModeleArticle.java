package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import betaboutique.beans.Article;
import betaboutique.beans.Categorie;
import betaboutique.outils.GestionBaseDeDonnees;
import betaboutique.modeles.ModeleCategorie;


/**
 * Classe ModeleArticle
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class ModeleArticle 
{
	// Variables
	DataSource datasource=null;
	Connection connexion=null;
	ResultSet resultat=null;
	
	// Variables de pagination
	private int maxParPage;
	private int pageActuel;
	private int totalElement;
	private String champTri;
	private String typeTri;
	private String recherche;
	private String typerecherche;
	
	// Constructeur par défaut
	public ModeleArticle(DataSource datasource)
	{		
		this.datasource=datasource;
	}
	
	// Accesseurs
	public int getMaxParPage() 
	{
		return maxParPage;
	}

	public int getPageActuel() 
	{
		return pageActuel;
	}

	public int getTotalElement() 
	{
		return totalElement;
	}
	
	public String getChampTri()
	{
		return champTri;
	}

	public String getRecherche()
	{
		return recherche;
	}

	public String getTypeTri() 
	{
		return typeTri;
	}
	
	public String getTyperecherche() 
	{
		return typerecherche;
	}

	
	// Récupérer la liste des articles
	public List<Article> listerArticle(int idCategorie, int maxParPage, int pageActuel, String recherche, String champTri, String typeTri)
	{	
		// Variables
		PreparedStatement requete=null;
		Article article=null;
		String requeteString=null;
		List<Article> listeArticle=new ArrayList<Article>();
		int position=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion = datasource.getConnection();
		
			// Première requête : on récupert le nombre total d'articles
			
			// On créé la requête
			requeteString="SELECT COUNT(DISTINCT(article.id_article)) as total FROM article WHERE etatarticle=1";
			
			// Si il s'agit d'une recherche, on modifie la requête en conséquence
			if(recherche!=null && !recherche.equalsIgnoreCase(""))
			{
				requeteString+=" AND nomarticle like ?";
				
				// On prépare la requête
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}
			else
			{
				// Si il s'agit d'un listing d'article en fonction de la catégorie, on modifie la requête en conséquence
				if(idCategorie!=0)
				{
					requeteString+=" AND id_categorie=?";
					
					// On prépare la requête
					requete=connexion.prepareStatement(requeteString);
					requete.setInt(1,idCategorie);
				}
				// Dans le cas contraire on liste tous les articles
				else
				{
					// On prépare la requête
					requete=connexion.prepareStatement(requeteString);
				}
			}
			
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On récupert le nombre d'article
			if(resultat!=null)
			{
				if(resultat.next())
				{
					this.totalElement=resultat.getInt("total");
				}
			}
			
			// On vérifie que la page demandé est valable
			position=maxParPage*(pageActuel-1);
			if(position>totalElement || maxParPage>totalElement)
			{
				pageActuel=1;
			}
			
			// On définit les variables de pagination finales
			position=maxParPage*(pageActuel-1);
			this.maxParPage=maxParPage;
			this.pageActuel=pageActuel;
			this.recherche=recherche;
			this.champTri=champTri;
			this.typeTri=typeTri;
			
			// Deuxième requête : on récupert la liste suivant une pagination
			requeteString="SELECT * FROM article WHERE etatarticle=1";
			
			// Si il s'agit d'une recherche, on modifie la requête en conséquence
			if(recherche!=null && !recherche.equalsIgnoreCase(""))
			{
				requeteString+=" AND nomarticle like ?";
				requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
				
				// On prépare la requête
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}
			else
			{
				// Si il s'agit d'un listing d'article en fonction de la catégorie, on modifie la requête en conséquence
				if(idCategorie!=0)
				{
					requeteString+=" AND id_categorie=?";
					requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
					
					// On prépare la requête
					requete=connexion.prepareStatement(requeteString);
					requete.setInt(1, idCategorie);
				}
				// Dans le cas contraire on liste tous les articles
				else
				{
					requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
					
					// On prépare la requête
					requete=connexion.prepareStatement(requeteString);
				}
			}
	
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat une la liste d'article
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mappage des attributs avec les champs de la table SQL
					article=articleAttributsMapper(resultat);
					
					// Ajoute l'objet a la liste d'article
					listeArticle.add((Article)article);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Erreur dans la requete dans la classe ModeleArticle fonction listeArticle");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticle fonction listerArticle");
			}	
		}
		
		// Retourner la liste des articles
		return listeArticle;
	}
	
	
	
	// Retourner la liste des articles en promotion
	public List<Article> listerArticleEnPromotion(int maxParPage, int pageActuel, String champTri, String typeTri)
	{	
		// Variables
		PreparedStatement requete=null;
		Article article=null;
		String requeteString=null;
		List<Article> listeArticle=new ArrayList<Article>();
		int position=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// Première requête : on récupert le nombre total d'articles
			requeteString="SELECT COUNT(DISTINCT(article.id_article)) as total FROM article WHERE etatarticle=1 AND reductionarticle > 0";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On récupert le nombre d'articles
			if(resultat!=null)
			{
				if(resultat.next())
				{
					this.totalElement=resultat.getInt("total");
				}
			}
			
			// On vérifie que la page demandé est valable
			position=maxParPage*(pageActuel-1);
			if(position>totalElement || maxParPage>totalElement)
			{
				pageActuel=1;
			}
			
			// On définit les variables de pagination finales
			position=maxParPage*(pageActuel-1);
			this.maxParPage=maxParPage;
			this.pageActuel=pageActuel;
			this.champTri=champTri;
			this.typeTri=typeTri;
			
			// Deuxième requête : on récupert la liste suivant une pagination
			requeteString="SELECT * FROM article WHERE etatarticle=1 AND reductionarticle > 0 ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
					
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat une la liste d'articles
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					article=articleAttributsMapper(resultat);
					
					// Ajoute l'objet à la liste d'articles
					listeArticle.add((Article)article);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleArticle fonction listerArticleEnPromotion");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticle fonction listerArticleEnPromotion");
			}	
		}
		
		// Retourner la liste des articles
		return listeArticle;
	}
	
	
	// Retourner la liste des nouveaux articles
	public List<Article> listerNouveauArticlePaginer(int maxParPage, int pageActuel, String champTri, String typeTri)
	{	
		//* Variables
		PreparedStatement requete=null;
		Article article=null;
		String requeteString=null;
		Date dateLimite=null;
		Calendar calendrier=null;
		SimpleDateFormat dateFormat=null;
		List<Article> listeArticle=new ArrayList<Article>();
		int position=0;
	
		try
		{	
			// Initialisation du format de la date
			dateFormat=new SimpleDateFormat("yyyyMMdd");
			
			// Date limite : 30 jours avant
	        calendrier=new java.util.GregorianCalendar(); 
	        calendrier.add(Calendar.DAY_OF_YEAR, -30);
	        dateLimite=calendrier.getTime();
			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// Première requête : on récupert le nombre total d'articles
			requeteString="SELECT COUNT(DISTINCT(article.id_article)) as total FROM article WHERE etatarticle=1 AND datearticle >= ?";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, Integer.parseInt(dateFormat.format(dateLimite)));
			
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On récupert le nombre d'articles
			if(resultat!=null)
			{
				if(resultat.next())
				{
					this.totalElement=resultat.getInt("total");
				}
			}
			
			// On vérifie que la page demandé est valable
			position=maxParPage*(pageActuel-1);
			if(position>totalElement || maxParPage>totalElement)
			{
				pageActuel=1;
			}
			
			// On définit les variables de pagination finales
			position=maxParPage*(pageActuel-1);
			this.maxParPage=maxParPage;
			this.pageActuel=pageActuel;
			this.champTri=champTri;
			this.typeTri=typeTri;
			
			// Deuxième requête : on récupert la liste suivant une pagination
			requeteString="SELECT * FROM article WHERE etatarticle=1 AND datearticle >= ? ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
					
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, Integer.parseInt(dateFormat.format(dateLimite)));
			
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat une la liste d'articles
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					article=articleAttributsMapper(resultat);
					
					// Ajoute l'objet a la liste d'article
					listeArticle.add((Article)article);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Erreur dans la requete dans la classe ModeleArticle fonction listerNouveauArticlePaginer");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticle fonction listerNouveauArticlePaginer");
			}	
		}
		
		// Retourner la liste des articles
		return listeArticle;
	}
	
	
	
	
	// Retourner la liste des nouveaux articles
	public List<Article> listerNouveauArticle()
	{	
		// Variables
		PreparedStatement requete=null;
		Article article=null;
		String requeteString=null;
		Date dateLimite=null;
		Calendar calendrier=null;
		SimpleDateFormat dateFormat=null;
		List<Article> listeArticle=new ArrayList<Article>();
	
		try
		{	
			// Initialisation du format de la date
			dateFormat=new SimpleDateFormat("yyyyMMdd");
			
			// Date limite : 30 jours avant
	        calendrier=new java.util.GregorianCalendar(); 
	        calendrier.add(Calendar.DAY_OF_YEAR, -30);
	        dateLimite=calendrier.getTime();
			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Création de la requête
			requeteString="SELECT * FROM article WHERE etatarticle=1 AND datearticle >= ?";
					
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, Integer.parseInt(dateFormat.format(dateLimite)));
			
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat une la liste d'articles
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					article=articleAttributsMapper(resultat);
					
					// Ajoute l'objet à la liste d'articles
					listeArticle.add((Article)article);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Erreur dans la requete dans la classe ModeleArticle fonction listerNouveauArticle");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticle fonction listerNouveauArticle");
			}	
		}
		
		// Retourner la liste des articles
		return listeArticle;
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
			requeteString="SELECT * FROM article WHERE id_article=?";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idArticle);
				
			// Execution de la requête
			resultat=requete.executeQuery();

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
			System.out.println("Erreur dans la requete dans la classe ModeleArticle fonction getArticle");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticle fonction getArticle");
			}	
		}
		
		// Retourner l'article
		return article;
	}
	
	
	
	
	/* =========================================
	 * Partie Admin
	 * =========================================
	 */
	
	// Retourner la liste des articles
	public List<Article> listeArticleAdmin(int maxParPage, int pageActuel, String recherche, String typerecherche, String champTri, String typeTri)
	{	
		// Variables
		PreparedStatement requete=null;
		Article article=null;
		String requeteString=null;
		List<Article> listeArticle=new ArrayList<Article>();
		int position=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Première requête : on récupert le nombre total d'articles
			requeteString="SELECT COUNT(DISTINCT(article.id_article)) as total FROM article WHERE 1";
			
			// Si il s'agit d'une recherche, on modifie la requête en conséquence
			if((recherche!=null && !recherche.equalsIgnoreCase("")) && (typerecherche!=null && !typerecherche.equals("")))
			{
				requeteString+=" AND " + typerecherche + " like ?";
				
				// Préparation de la requête
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}
			// Dans le cas contraire on prend en compte tous les article
			else
			{
				// Préparation de la requête
				requete=connexion.prepareStatement(requeteString);
			}
			
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On récupert le nombre d'articles
			if(resultat!=null)
			{
				if(resultat.next())
				{
					this.totalElement=resultat.getInt("total");
				}
			}
			// On vérifie que la page demandée est valable
			position=maxParPage*(pageActuel-1);
			if(position>totalElement || maxParPage>totalElement)
			{
				pageActuel=1;
			}
			
			// On définit les variables de pagination finale
			position=maxParPage*(pageActuel-1);
			this.maxParPage=maxParPage;
			this.pageActuel=pageActuel;
			this.recherche=recherche;
			this.champTri=champTri;
			this.typeTri=typeTri;
			this.typerecherche=typerecherche;
		
			// Deuxième requête : on récupert la liste suivant une pagination
			requeteString="SELECT * FROM article WHERE 1";
			
			// Si il s'agit d'une recherche, on modifie la requête en conséquence
			if((recherche!=null && !recherche.equalsIgnoreCase("")) && (typerecherche!=null && !typerecherche.equals("")))
			{
				requeteString+=" AND " + typerecherche + " like ?";
				requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
				
				// Préparation de la requête
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}			
			// Dans le cas contraire on liste tous les articles
			else
			{
				requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
				
				// Préparation de la requête
				requete=connexion.prepareStatement(requeteString);
			}
	
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat dans une liste
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					article=articleAttributsMapper(resultat);
					
					// Ajoute l'objet a la liste d'articles
					listeArticle.add((Article)article);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleArticle fonction ListeArticleAdmin");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticle fonction ListeArticleAdmin");
			}	
		}
		
		// Retourner la liste des articles
		return listeArticle;
	}
	
	
	
	// Ajouter un article
	public int ajouterArticle(Article article)
	{	
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		int codeErreur=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Création de la requête
			requeteString="INSERT INTO article (nomarticle, descriptionarticle, prixarticle, reductionarticle, datearticle, photoarticle, vignettearticle, etatarticle, id_categorie, poidsarticle, stockarticle) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			
			// Préparation de la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setString(1, article.getNom());
			requete.setString(2, article.getDescription());
			requete.setDouble(3,article.getPrix());
			requete.setInt(4, article.getReduction());
			requete.setInt(5, Integer.parseInt(format.format(article.getDate())));
			requete.setString(6, article.getPhoto());
			requete.setString(7, article.getVignette());
			requete.setInt(8, article.getEtat());
			requete.setInt(9, article.getCategorie().getId());
			requete.setInt(10, article.getPoids());
			requete.setInt(11, article.getStock());
			
			//* On vide l'article par sécurité
			article = null;
			
			// Execution de la requête
			codeErreur=requete.executeUpdate();
		}
		catch(Exception e)
		{
			codeErreur=0;
			System.out.println("Erreur dans la requete dans la classe ModeleArticle fonction ajouterArticle");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticle fonction ajouterArticle");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
	
	
	// Supprimer un article
	public int supprimerArticle(int idArticle)
	{	
		// Variables
		PreparedStatement requete=null;
		ModeleNote modeleNote=null;
		String requeteString=null;
		int codeErreur=0;
	
		try
		{	
			// Initialisation du modèle note
			modeleNote=new ModeleNote(this.datasource);
			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Annulation de l'autocommit
			connexion.setAutoCommit(false);
			
			// Première requête : suppression de l'article
			requeteString="DELETE FROM article WHERE id_article = ?";
			
			// Préparation de la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idArticle);
			
			// Execution de la requête
			codeErreur=requete.executeUpdate();
			
			// Si il n'y a pas eu d'erreur durant la suppression de l'article, supprimer les notes de l'article associé.
			if(codeErreur==1)
			{
				// Deuxième requête : suppression des notes de l'article
				codeErreur=modeleNote.supprimerNote(idArticle);
			}
			// Si il n'y a pas eu d'erreur durant la suppression des notes de l'articles, ou de l'article lui même : on fait un commit, sinon un rollback
			if(codeErreur==1)
			{
				GestionBaseDeDonnees.commit(connexion);
			}
			else
			{
				GestionBaseDeDonnees.rollback(connexion);
			}
		}
		catch(Exception e)
		{
			codeErreur=0;
			GestionBaseDeDonnees.rollback(connexion);
			System.out.println("Erreur dans la requete dans la classe ModeleArticle fonction supprimerArticle");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticle fonction supprimerArticle");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
	
	
	
	// Modifier un article
	public int modifierArticle(Article article)
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
			requeteString="UPDATE article set nomarticle = ?, descriptionarticle = ?, prixarticle = ?, reductionarticle = ?, photoarticle = ?, vignettearticle = ?, etatarticle = ?, id_categorie = ?, poidsarticle = ?, stockarticle = ? WHERE id_article = ?";
			requete=connexion.prepareStatement(requeteString);
			requete.setString(1, article.getNom());
			requete.setString(2, article.getDescription());
			requete.setDouble(3,article.getPrix());
			requete.setInt(4, article.getReduction());
			requete.setString(5, article.getPhoto());
			requete.setString(6, article.getVignette());
			requete.setInt(7, article.getEtat());
			requete.setInt(8, article.getCategorie().getId());
			requete.setInt(9, article.getPoids());
			requete.setInt(10, article.getStock());
			requete.setInt(11, article.getId());
			
			// On vide l'article par sécurité
			article=null;
			
			// Execution de la requête
			codeErreur=requete.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleArticle fonction modifierArticle");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleArticle fonction modifierArticle");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
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
			if(resultat.getString("id_article")==null)
			{
				article.setId(0);
			}
			else
			{
				article.setId(resultat.getInt("id_article"));
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
			// Si il y a une erreur durant le mapping des attributs avec la base on renvoi un objet null
			article=null;
			System.out.println("Erreur lors du mappage des attributs d'un articles dans la classe ModeleArticle fonction ArticleAttributsMapper");
		}
		
		// On retourne l'article
		return article;
	}
	
	
}
