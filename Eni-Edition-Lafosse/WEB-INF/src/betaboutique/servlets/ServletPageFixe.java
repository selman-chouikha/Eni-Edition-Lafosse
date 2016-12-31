package betaboutique.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.beans.Article;
import betaboutique.modeles.ModeleArticle;
import betaboutique.outils.GestionDroit;

/**
 * @author Jérôme Lafosse
 * @copyright 2008
 * @version 0.1
 */
@SuppressWarnings("serial")
public class ServletPageFixe extends HttpServlet
{
	// Attributs
	private DataSource datasource;
	private GestionDroit gestionDroit=null;
	
	// Traitements
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{		
		// Initialisation de la gestion des droits
		gestionDroit=new GestionDroit(request);
	
		// Récupérer la datasource du context de la servlet
		datasource=(DataSource)getServletContext().getAttribute("datasource");
		
		// Action a réaliser
		String action=(String)request.getParameter("action");
		
		// Action par défaut (index utilisateur)
		if(action==null || action.equalsIgnoreCase(""))
		{
			action="index";
		}
		
		// Affiche la page d'index
		if(action.equals("index"))
		{	
			index(request, response);
		}	
		
		// Page d'index administrateur
		if(action.equals("admin"))
		{	
			// On autorise uniquement les administrateurs
			if(gestionDroit.estAutorise(true,false,false))
			{
				admin(request, response);
			}
			else
			{
				getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'êtes pas autorisé à afficher cette page!!").forward(request, response);
			}
		}	
		// Page des conditions de vente
		if(action.equals("cgv"))
		{	
			cgv(request, response);
		}	
		// Page de contact
		if(action.equals("contact"))
		{	
			contact(request, response);
		}	
		// Page d'erreurs
		if(action.equals("erreur"))
		{	
			erreur(request, response);
		}	
	
		// Vidage du datasource
		this.datasource=null;
	}
	
	
	
	// Page d'index
	private void index(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		ModeleArticle modeleArticle=null;
		List<Article> promotion=null;
		List<Article> nouveaute=null;
		
		// Initialisation du modèle
		modeleArticle=new ModeleArticle(this.datasource);
		
		// On récupère les promotions et nouveautés
		promotion=modeleArticle.listerArticleEnPromotion(4, 1, "nomarticle", "ASC");
		nouveaute=modeleArticle.listerNouveauArticlePaginer(4, 1, "nomarticle", "ASC");
		
		// On place dans la requête les nouveaux articles et les articles en promotion
		request.setAttribute("promotion", promotion);
		request.setAttribute("nouveaute", nouveaute);
		
		// On renvoie de plus à la vue les messages d'erreurs et ou de succès passés à cette servlet lors de la création d'un nouveau compte
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// Redirection vers la page d'index utilisateur
		getServletContext().getRequestDispatcher("/vues/utilisateurs/index.jsp").forward(request, response);
	}
	
	
	// Page d'accueil de l'administrateur
	private void admin(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		// Redirection vers la page d'index administrateur
		getServletContext().getRequestDispatcher("/vues/administrateurs/index.jsp").forward(request, response);
	}
	
	
	// Page des conditions de vente
	private void cgv(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		// Redirection vers la cgv
		getServletContext().getRequestDispatcher("/vues/utilisateurs/cgv.jsp").forward(request, response);
	}
	
	// Page d'erreurs
	private void erreur(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		// Redirection vers la page d'erreurs
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		getServletContext().getRequestDispatcher("/vues/erreurs/erreur.jsp").forward(request, response);
	}
	
	// Page de contact
	private void contact(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		// Redirect vers la page de contact
		getServletContext().getRequestDispatcher("/vues/utilisateurs/contact.jsp").forward(request, response);
	}
	
	
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		doGet(request, response);
	}
	
}
