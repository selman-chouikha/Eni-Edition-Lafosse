package betaboutique.servlets.client;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.modeles.ModeleArticle;
import betaboutique.modeles.ModeleCategorie;
import betaboutique.beans.Article;
import betaboutique.beans.Categorie;

/**
 * Classe ServletGestionArticles
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionArticles extends HttpServlet 
{
	// Variables 
	private DataSource datasource=null;
	
	// Traitements
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{		
		// Récupérer l'objet datasource du context de la servlet
		datasource=(DataSource)getServletContext().getAttribute("datasource");
		
		// Action a réaliser
		String action=(String)request.getParameter("action");
		
		// Action par défaut (liste)
		if(action==null || action.equalsIgnoreCase(""))
		{
			action="lister";
		}
		
		// Listing des articles
		if(action.equals("lister"))
		{
			lister(request,response);
		}
		
		// Detail de l'article
		if(action.equals("consulter"))
		{
			consulter(request, response);
		}
		
		// Afficher les promotions 
		if(action.equals("promo"))
		{
			listerPromotion(request,response);
		}
		
		// Afficher les nouveautés 
		if(action.equals("nouveaute"))
		{
			listerNouveaute(request,response);
		}
		
		// Vider l'objet datasource
		this.datasource=null;
	}
	
	
	
	
	// Listing des articles 
	private void lister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleArticle modeleArticle=null;
		ModeleCategorie modeleCategorie=null;
		int idCategorie=0;
		int page=0;
		int maxParPage=0;
		String champTri=null;
		String typeTri=null;
		String recherche=null;
		Categorie categorie=null;
		ArrayList<Article> listeArticles=null;
		
		try 
		{
			// On récupert l'id de la catégorie
			idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
			
			// Si l'id de la catégorie est inférieur à 0, on crée une fausse catégorie
			if(idCategorie<=0)
			{
				categorie=new Categorie();
				categorie.setId(0);
				categorie.setNom("Toutes");
			}
			// Dans le cas contraire on récupert l'objet catégorie avec le modèle
			else
			{
				modeleCategorie=new ModeleCategorie(datasource);
				categorie=modeleCategorie.getCategorie(idCategorie);
			}
		}
		catch(Exception e) 
		{
			// Si le paramètre idCategorie passé dans la requête n'est pas un nombre
			categorie=new Categorie();
			categorie.setId(0);
			categorie.setNom("Toutes");
		}
		
		try 
		{
			// On récupert la page demandée
			page=Integer.parseInt(request.getParameter("page"));
			
			// Si la page vaut 0 ou inférieur on prend la valeur 1
			page=Math.max(page, 1);
		}
		catch(Exception e) 
		{
			// Si le paramètre page passée dans la requête n'est pas un nombre on l'initialise à 1
			page=1;
		}
		
		try 
		{
			// On récupert le maximum d'éléments a afficher 
			maxParPage=Integer.parseInt(request.getParameter("maxParPage"));
			
			// Si la page vaut 0 ou inférieur on prend la valeur 1
			maxParPage=Math.max(maxParPage, 5);
		}
		catch(Exception e) 
		{
			// Si le paramètre maxParPage passée dans la requête n'est pas un nombre on l'initialise à 5 
			maxParPage=5;
		}
	
		// On récupert le reste des éléments de la requete
		recherche=(String)request.getParameter("recherche");
		champTri=(String)request.getParameter("champTri");
		typeTri=(String)request.getParameter("typeTri");
				
		// Si aucun tri n'est demandé, ou que le tri est incorrect, on réalise un tri le nom de l'article
		if(champTri==null || (!champTri.equals("nomarticle") && !champTri.equals("prixarticle")))
		{
			champTri="nomarticle";
		}
		
		// Si aucun type de tri n'est demandé, ou que le type de tri est incorrect, on réalise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}

		// On initialise le modèle
		modeleArticle=new ModeleArticle(datasource);
	
		// On récupert la liste des articles correspondant à la categorie demandée
		listeArticles=(ArrayList<Article>)modeleArticle.listerArticle(idCategorie, maxParPage, page, recherche, champTri, typeTri);
		
		// On renvoie à la JSP la liste de obtenue ainsi que les informations de pagination
		request.setAttribute("listeArticles",listeArticles);
		request.setAttribute("maxParPage", modeleArticle.getMaxParPage());
		request.setAttribute("pageActuel", modeleArticle.getPageActuel());
		request.setAttribute("totalElement", modeleArticle.getTotalElement());
		request.setAttribute("champTri", modeleArticle.getChampTri());
		request.setAttribute("typeTri", modeleArticle.getTypeTri());
		request.setAttribute("recherche", modeleArticle.getRecherche());
		request.setAttribute("categorie", categorie);
		
		// On renvoie de plus à la vue les messages d'erreurs
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// On vide cette liste par sécurité
		listeArticles=null;
		
		// On renvoie la vue de liste des articles
		getServletContext().getRequestDispatcher("/vues/utilisateurs/listearticles.jsp").forward(request,response);
	}
	
	
	
	// Afficher les promotions
	private void listerPromotion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleArticle modeleArticle=null;
		int page=0;
		int maxParPage=0;
		String champTri=null;
		String typeTri=null;
		ArrayList<Article> listeArticles=null;
			
		try 
		{
			// On récupert la page demandée
			page=Integer.parseInt(request.getParameter("page"));
			
			// Si la page vaut 0 ou inférieur on prend la valeur 1
			page=Math.max(page, 1);
		}
		catch(Exception e) 
		{
			// Si le paramètre page passée dans la requête n'est pas un nombre on l'initialise à 1
			page=1;
		}
		
		try 
		{
			// On récupert le maximum d'éléments à afficher
			maxParPage=Integer.parseInt(request.getParameter("maxParPage"));
			
			// Si la page vaut 0 ou inférieur on prend la valeur 1
			maxParPage=Math.max(maxParPage, 5);
		}
		catch(Exception e) 
		{
			// Si le paramètre maxParPage passé dans la requête n'est pas un nombre on l'initialise à 5
			maxParPage=5;
		}
	
		// On récupert le reste des éléments de la requete
		champTri=(String)request.getParameter("champTri");
		typeTri=(String)request.getParameter("typeTri");
				
		// Si aucun tri n'est demandé, ou que le tri est incorrect, on réalise un tri sur le nom de l'article
		if(champTri==null || (!champTri.equals("nomarticle") && !champTri.equals("prixarticle")))
		{
			champTri="nomarticle";
		}
		
		// Si aucun type de tri n'est demandé, ou que le type de tri est incorrect, on réalise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}

		// On initialise le modèle
		modeleArticle=new ModeleArticle(datasource);
	
		// On récupert la liste des articles correspondant à la categorie demandée
		listeArticles=(ArrayList<Article>)modeleArticle.listerArticleEnPromotion(maxParPage, page, champTri, typeTri);
		
		// On renvoie à la JSP la liste obtenue ainsi que les informations de pagination
		request.setAttribute("listeArticles",listeArticles);
		request.setAttribute("maxParPage", modeleArticle.getMaxParPage());
		request.setAttribute("pageActuel", modeleArticle.getPageActuel());
		request.setAttribute("totalElement", modeleArticle.getTotalElement());
		request.setAttribute("champTri", modeleArticle.getChampTri());
		request.setAttribute("typeTri", modeleArticle.getTypeTri());
		
		// On vide cette liste par sécurité
		listeArticles=null;
		
		// On renvoie la vue de liste des articles
		getServletContext().getRequestDispatcher("/vues/utilisateurs/promotion.jsp").forward(request,response);
	}
	
	
	
	// Afficher les nouveautés
	private void listerNouveaute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleArticle modeleArticle=null;
		int page=0;
		int maxParPage=0;
		String champTri=null;
		String typeTri=null;
		ArrayList<Article> listeArticles=null;
			
		try 
		{
			// Récupération de la page demandée
			page=Integer.parseInt(request.getParameter("page"));
			
			// Si la page vaut 0 ou inférieur on prend la valeur 1
			page=Math.max(page, 1);
		}
		catch(Exception e) 
		{
			// Si le paramètre page passé dans la requête n'est pas un nombre on l'initialise à 1
			page=1;
		}
		
		try 
		{
			// Récupération du maximum d'éléments à afficher
			maxParPage=Integer.parseInt(request.getParameter("maxParPage"));
			
			// Si la page vaut 0 ou inférieur on prend la valeur 1
			maxParPage=Math.max(maxParPage, 5);
		}
		catch(Exception e) 
		{
			// Si le paramètre maxParPage passé dans la requête n'est pas un nombre on l'initialise à 5
			maxParPage=5;
		}
	
		// On récupert le reste des éléments de la requete
		champTri=(String)request.getParameter("champTri");
		typeTri=(String)request.getParameter("typeTri");
				
		// Si aucun tri n'est demandé, ou que le tri est incorrect, on réalise un tri sur le nom de l'article
		if(champTri==null || (!champTri.equals("nomarticle") && !champTri.equals("prixarticle")))
		{
			champTri="nomarticle";
		}
		
		// Si aucun type de tri n'est demandé, ou que le type de tri est incorrect, on réalise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}

		// On initialise le modèle
		modeleArticle=new ModeleArticle(datasource);
	
		// On récupert la liste des articles correspondant à la categorie demandée
		listeArticles=(ArrayList<Article>)modeleArticle.listerNouveauArticlePaginer(maxParPage, page, champTri, typeTri);
		
		// On renvoie à la JSP la liste obtenue ainsi que les informations de pagination
		request.setAttribute("listeArticles",listeArticles);
		request.setAttribute("maxParPage", modeleArticle.getMaxParPage());
		request.setAttribute("pageActuel", modeleArticle.getPageActuel());
		request.setAttribute("totalElement", modeleArticle.getTotalElement());
		request.setAttribute("champTri", modeleArticle.getChampTri());
		request.setAttribute("typeTri", modeleArticle.getTypeTri());
		
		// On vide cette liste par sécurité
		listeArticles=null;
		
		// On renvoie la vue de liste des articles
		getServletContext().getRequestDispatcher("/vues/utilisateurs/nouveaute.jsp").forward(request,response);
	}
	
	
	
	
	// Detail de l'article
	private void consulter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleArticle modeleArticle=null;
		int idArticle=0;
		Article article=null;
		
		try
		{
			// On récupert l'id de l'article
			idArticle=Integer.parseInt(request.getParameter("idArticle"));
		}
		catch(Exception e) 
		{
			// Si l'id de l'article passé en paramètre n'est pas un nombre, on initialise l'id à 0
			idArticle=0;
		}
		
		// On initialise le modèle
		modeleArticle=new ModeleArticle(datasource);
		
		// On récupert l'article correspondant, renvoi null si l'id de l'article n'existe pas dans la base
		article=(Article)modeleArticle.getArticle(idArticle);
		
		// Si l'objet article est null (id non trouvé) on renvoie sur la page de listing avec un message d'erreur
		if(article!=null)
		{
			// On renvoie à la JSP l'article demandé
			request.setAttribute("article",article);
			
			// On vide cet article par sécurité
			article=null;
			
			// On renvoie à la vue de consultation des informations d'un article
			getServletContext().getRequestDispatcher("/vues/utilisateurs/consulterarticle.jsp").forward(request,response);
		}
		else
		{
			// On renvoie à la page de liste des articles avec un message d'erreur
			response.sendRedirect("gestionArticles?action=lister&erreurs=" + "Cet article n'est pas ou plus référencé dans la base");
		}		
	}
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	
}