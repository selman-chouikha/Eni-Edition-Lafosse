package betaboutique.servlets.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.modeles.ModeleNote;
import betaboutique.outils.GestionDroit;
import betaboutique.beans.Client;


/**
 * Classe ServletGestionNotes
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionNotes extends HttpServlet 
{
	// Variables
	private DataSource datasource=null;
	private GestionDroit gestionDroit=null;
	
	// Traitements
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Initialisation de la gestion des droits
		gestionDroit=new GestionDroit(request);
		
		// Récupérer l'objet datasource du context de la servlet
		datasource=(DataSource)getServletContext().getAttribute("datasource");

		// Action à réaliser
		String action=(String)request.getParameter("action");
		
		// Action par défaut (liste)
		if(action==null || action.equalsIgnoreCase(""))
		{
			action="noteclient";
		}
		
		if(action.equals("noteclient"))
		{
			consulterNoteClient(request, response);
		}
		
		if(action.equals("noter"))
		{
			// Seul les clients peuvent noter un article
			if(gestionDroit.estAutorise(false, true, false))
			{
				noterArticle(request, response);
			}
			else
			{
				getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'êtes pas autorisé à afficher cette page!!").forward(request, response);
			}
		}
		
		// Vider l'objet datasource
		this.datasource=null;
	}
	
	
	
	// Consulter une note d'un client
	private void consulterNoteClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleNote modeleNote=null;
		Client client=null;
		int idArticle=0;
		int autorisation=0;
		
		// On initialise le modèle
		modeleNote=new ModeleNote(datasource);
		
		try
		{
			// On récupert l'id de l'article
			idArticle=Integer.parseInt(request.getParameter("idArticle"));
		}
		catch(Exception e) 
		{
			return;
		}
	
		try
		{
			// On récupert le client en session
			if((client=(Client)request.getSession().getAttribute("compte"))!=null)
			{
				if(modeleNote.getNoteClient(idArticle, client.getId())>0)
				{
					// Initialise la variable notée à 1 : le client a déjà noté l'article 
					autorisation=1;
				}
				else
				{
					// Initialise la variable notée à 0 : il peut noter l'article
					autorisation=0;
				}
			}
			else
			{
				// Initialise la variable notée à 2 : le client n'est pas connecté : il ne peut pas noter l'article
				autorisation=2;
			}
		}
		catch(Exception e) 
		{
			// Initialise la variable notée à 3 : l'utilisateur est un administrateur : il ne peut pas noter l'article
			autorisation=3;
		}
		
		// On renvoie à la JSP la liste obtenue
		request.setAttribute("note",0);
		request.setAttribute("vote",0);
		request.setAttribute("autorisation",autorisation);
		
		// On renvoie la vue ajax
		getServletContext().getRequestDispatcher("/vues/ajax/note.jsp").forward(request,response);
	}
	
	
	// Noter un article
	private void noterArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleNote ModeleNote=null;
		int idArticle=0;
		int note=5;
		int vote=0;
		int idClient=0;
		
		try
		{
			// On récupert l'id de l'article
			idArticle=Integer.parseInt(request.getParameter("idArticle"));
			
			// On récupert la note attribuée
			note=Integer.parseInt(request.getParameter("note"));
		}
		catch(Exception e) 
		{
			return;
		}
		
		// On récupert l'id du client
		idClient=((Client)request.getSession().getAttribute("compte")).getId();
		
		// On crée le modèle
		ModeleNote=new ModeleNote(datasource);
		
		// On insert la note
		ModeleNote.noter(idArticle, note, idClient);
		
		// On récupert la note de l'article
		note=ModeleNote.getNoteArticle(idArticle);
		vote=ModeleNote.getNombreVote(idArticle);
		
		// On renvoie à la vue les données
		request.setAttribute("note",note);
		request.setAttribute("vote",vote);
		
		// On renvoie à la vue ajax
		getServletContext().getRequestDispatcher("/vues/ajax/note.jsp").forward(request,response);
	}
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	
}