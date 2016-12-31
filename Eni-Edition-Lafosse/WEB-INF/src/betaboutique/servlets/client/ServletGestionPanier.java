package betaboutique.servlets.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.modeles.ModeleArticle;
import betaboutique.outils.GestionDroit;
import betaboutique.beans.Commande;
import betaboutique.beans.Article;

/**
 * Classe ServletGestionPanier
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionPanier extends HttpServlet 
{
	// Variables
	private DataSource datasource=null;
	private GestionDroit gestionDroit=null;
	
	// Traitements 
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{		
		// Initialisation de la gestion des droits
		gestionDroit=new GestionDroit(request);
		
		// On autorise uniquement les clients et utilisateurs anonymes
		if(gestionDroit.estAutorise(false,true,true))
		{				
			// R�cup�rer l'objet datasource du context de la servlet
			datasource=(DataSource)getServletContext().getAttribute("datasource");
	
			// Action a r�aliser
			String action=(String)request.getParameter("action");
			
			// Action par d�faut (liste)
			if(action==null || action.equalsIgnoreCase(""))
			{
				action="lister";
			}
			
			if(action.equals("lister"))
			{
				listerPanier(request, response);
			}
			
			if(action.equals("ajouter"))
			{
				ajouterArticle(request, response);
			}
			
			if(action.equals("supprimer"))
			{
				supprimerArticle(request, response);
			}
			
			if(action.equals("supprimerdefinitif"))
			{
				supprimerDefinitifArticle(request, response);
			}
		}
		else
		{
			getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
		}
		
		// Vider l'objet datasource 
		this.datasource=null;
	}
	
	
	
	
	// Lister les articles du panier
	private void listerPanier(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// On renvoie � la vue les messages d'erreurs et de succ�s
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// On renvoie au panier 
		getServletContext().getRequestDispatcher("/vues/utilisateurs/panier.jsp").forward(request,response);
	}
	
	
	// Ajouter un article dans le panier
	private void ajouterArticle(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		Article article=null;
		Commande panier=null;
		ModeleArticle modeleArticle=null;
		int idArticle=0;
		
		try
		{
			// On r�cupert l'id de l'article
			idArticle=Integer.parseInt(request.getParameter("idArticle"));
		}
		catch(Exception e) 
		{
			// Si l'id de l'article pass� en param�tre n'est pas un nombre, on initialise l'id � 0
			idArticle=0;
		}
		
		// On initialise le mod�le
		modeleArticle=new ModeleArticle(datasource);
		
		// On r�cupert l'article correspondant, renvoi null si l'id de l'article n'existe pas dans la base
		article=(Article)modeleArticle.getArticle(idArticle);
		
		// Si l'objet article est null (id non trouv�) on renvoie sur la page de listing avec un message d'erreur
		if(article!=null)
		{
			// On r�cupert le panier en session
			panier=(Commande)request.getSession().getAttribute("panier");
			
			// Si le panier est null on le cr�e
			if(panier==null)
			{
				panier=new Commande();
			}
			
			// On ajoute l'article au panier
			panier.ajouterArticle(article);
			
			// On remplace le panier en session
			request.getSession().setAttribute("panier", panier);
			
			// On place un code d'erreur dans la requ�te (0 : ajout avec succ�s)
			request.setAttribute("codeErreur", 0);
		}	
		else
		{
			// On place un code d'erreur dans la requ�te (1 : article inconnu )
			request.setAttribute("codeErreur", 1);
		}
		
		if(request.getParameter("methode")!=null)
		{
			// On renvoie � la vue ajax des raccourcis qui int�gre la variable codeErreurs
			getServletContext().getRequestDispatcher("/vues/ajax/raccourci.jsp").forward(request,response);
		}
		else
		{
			// On renvoie � la vue du panier
			response.sendRedirect("gestionPanier?action=lister");
		}
	}
	
	
	
	// Supprimer un article dans le panier
	private void supprimerArticle(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		Article article=null;
		Commande panier=null;
		ModeleArticle modeleArticle=null;
		int idArticle=0;
		
		try
		{
			// On r�cupert l'id de l'article
			idArticle=Integer.parseInt(request.getParameter("idArticle"));
		}
		catch(Exception e) 
		{
			// Si l'id de l'article pass� en param�tre n'est pas un nombre, on initialise l'id � 0 
			idArticle=0;
		}
		
		// On initialise le mod�le
		modeleArticle=new ModeleArticle(datasource);
		
		// On r�cupert l'article correspondant, renvoi null si l'id de l'article n'existe pas dans la base
		article=(Article)modeleArticle.getArticle(idArticle);
		
		// Si l'objet article est null (id non trouv�) on renvoie sur la page de listing avec un message d'erreur
		if(article!=null)
		{
			// On r�cupert le panier en session
			panier=(Commande)request.getSession().getAttribute("panier");
			
			// Si le panier est null on le cr�e
			if(panier==null)
			{
				panier=new Commande();
			}
			
			// On supprime l'article du panier
			panier.supprimerUnArticle(article);
			
			// On remplace le panier en session
			request.getSession().setAttribute("panier", panier);
		}	
	
		// On renvoie � la vue du panier
		response.sendRedirect("gestionPanier?action=lister");
	}
	
	
	// Supprimer les articles d'un panier pour un article indiqu� en param�tre
	private void supprimerDefinitifArticle(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		Article article=null;
		Commande panier=null;
		ModeleArticle modeleArticle=null;
		int idArticle=0;
		
		try
		{
			// On r�cupert l'id de l'article
			idArticle=Integer.parseInt(request.getParameter("idArticle"));
		}
		catch(Exception e) 
		{
			// Si l'id de l'article pass� en param�tre n'est pas un nombre, on initialise l'id � 0 
			idArticle=0;
		}
		
		// On initialise le mod�le
		modeleArticle=new ModeleArticle(datasource);
		
		// On r�cupert l'article correspondant, renvoi null si l'id de l'article n'existe pas dans la base
		article=(Article)modeleArticle.getArticle(idArticle);
		
		// Si l'objet article est null (id non trouv�) on renvoie sur la page de listing avec un message d'erreur
		if(article!=null)
		{
			// On r�cupert le panier en session
			panier=(Commande)request.getSession().getAttribute("panier");
			
			// Si le panier est null on le cr�e
			if(panier==null)
			{
				panier=new Commande();
			}
			
			// On supprime la ligne correspondant � l'article du panier 
			panier.supprimerArticle(article);
			
			// On remplace le panier en session 
			request.getSession().setAttribute("panier", panier);
		}	
	
		// On renvoie � la vue du panier 
		response.sendRedirect("gestionPanier?action=lister");
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	
}