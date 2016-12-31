package betaboutique.servlets.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.List;
import betaboutique.modeles.ModeleRaccourci;
import betaboutique.outils.GestionDroit;
import betaboutique.beans.Raccourci;
import betaboutique.beans.Client;


/**
 * Classe ServletGestionRaccourci
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionRaccourci extends HttpServlet 
{
	// Variables
	private DataSource datasource=null;
	private GestionDroit gestionDroit=null;
	
	// Traitements
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{		
		// Initialisation de la gestion des droits
		gestionDroit=new GestionDroit(request);
		
		// On autorise uniquement les clients
		if(gestionDroit.estAutorise(false,true,false))
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
			
			if(action.equals("lister"))
			{
				listerRaccourci(request, response);
			}
			
			if(action.equals("ajouter"))
			{
				ajouterRaccourci(request, response);
			}
			
			if(action.equals("supprimer"))
			{
				supprimerRaccourci(request, response);
			}
		}
		else
		{
			getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'êtes pas autorisé à afficher cette page!!").forward(request, response);
		}
		
		// Vider l'objet datasource 
		this.datasource=null;
	}
	
	
	// Lister les raccourcis de la personne
	private void listerRaccourci(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		ModeleRaccourci modeleRaccourci=null;
		List<Raccourci> listeRaccourci=null;
		Client client=null;
		
		try
		{
			// On réqupert le compte en session
			client=(Client)request.getSession().getAttribute("compte");
			
			// On initialise le modèle 
			modeleRaccourci=new ModeleRaccourci(this.datasource);
			
			// On récupert la liste des raccourcis 
			listeRaccourci=modeleRaccourci.listerRaccourci(client.getId());
		
			// On place cette liste dans la requête 
			request.setAttribute("liste", listeRaccourci);
			
			// On renvoie la vue ajax 
			getServletContext().getRequestDispatcher("/vues/ajax/raccourci.jsp").forward(request,response);
		}
		catch(Exception e) 
		{
			
		}	
	}
	
	
	// Ajouter un raccourci
	private void ajouterRaccourci(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		ModeleRaccourci modeleRaccourci=null;
		Raccourci raccourci=null;
		List<Raccourci> listeRaccourci=null;
		Client client=null;
		String nom=null;
		String url=null;
		int codeErreur=0;
		
		try
		{
			// On initialise le modèle 
			modeleRaccourci=new ModeleRaccourci(this.datasource);
			
			// On réqupert le compte en session 
			client=(Client)request.getSession().getAttribute("compte");
			
			// On récupert le nom et l'url du raccourci 
			nom=request.getParameter("nom").trim();
			url=request.getHeader("referer").trim();
			
			// On vérifie le nombre de raccourcis de l'utilisateur 
			if(modeleRaccourci.getNombreRaccourci(client.getId())>=10)
			{
				// Erreur 1 : Maximum de raccourcis
				codeErreur=1;
			}
			// On vérifie que le lien n'a pas été ajouté plus d'une fois 
			else if(modeleRaccourci.getRaccourci(nom,client.getId())!=null || modeleRaccourci.getRaccourci(url,client.getId()) != null)
			{
				// Erreur 2 : raccourci existant 
				codeErreur=2;
			}
			
			// Si il n'y a pas d'erreur on ajoute le raccourci dans la base 
			if(codeErreur == 0)
			{
				// Initialisation du raccourci 
				raccourci=new Raccourci();
				raccourci.setNom(nom);
				raccourci.setUrl(url);
						
				// Si il n'y a pas d'erreur durant l'ajout dans la base 
				if(modeleRaccourci.ajouterRaccourci(raccourci, client.getId())==1)
				{	
					// On récupert la liste des raccourcis
					listeRaccourci=modeleRaccourci.listerRaccourci(client.getId());
					
					// On place cette liste dans la requête
					request.setAttribute("liste", listeRaccourci);
				}
				else
				{
					// Erreur 3 : Erreur durant l'ajout 
					codeErreur=3;
				}
			}
			
			// On ajoute le code d'erreur 
			request.setAttribute("codeErreur", codeErreur);
			
			// On renvoie la vue ajax 
			getServletContext().getRequestDispatcher("/vues/ajax/raccourci.jsp").forward(request,response);
		}
		catch (Exception e) 
		{

		}
	}
	
	
	// Supprimer un raccourci
	private void supprimerRaccourci(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		ModeleRaccourci modeleRaccourci=null;
		List<Raccourci> listeRaccourci=null;
		Client client=null;
		int idRaccourci=0;
		int codeErreur=0;
		
		try
		{
			// On initialise le modèle 
			modeleRaccourci=new ModeleRaccourci(this.datasource);
			
			// On réqupert le compte en session
			client=(Client)request.getSession().getAttribute("compte");
			
			// On récupert l'id du raccourci à supprimer
			idRaccourci=Integer.parseInt(request.getParameter("idRaccourci").trim());
			
			// On vérifie que le raccourci existe 
			if(modeleRaccourci.getRaccourci(idRaccourci)!=null)
			{
				// Si il existe on le supprime
				codeErreur=modeleRaccourci.supprimerRaccourci(idRaccourci);
				
				// Si il n'y a pas eut d'erreur durant la suppression du raccourci
				if(codeErreur==1)
				{
					// On récupert la liste des raccourcis du client
					listeRaccourci=modeleRaccourci.listerRaccourci(client.getId());
					
					// On place cette liste dans la requête 
					request.setAttribute("liste", listeRaccourci);
					
					// On place le code d'erreur dans la requete (0 : suppression avec succès) 
					request.setAttribute("codeErreur", 0);
				}
				else
				{
					// On place le code d'erreur dans la requete (1 : erreur durant l'ajout) 
					request.setAttribute("codeErreur", 1);
				}
			}
			else
			{
				// On place le code d'erreur dans la requete (2 : le raccourci n'existe pas) 
				request.setAttribute("codeErreur", 2);
			}
			
			// On renvoie la vue ajax 
			getServletContext().getRequestDispatcher("/vues/ajax/raccourci.jsp").forward(request,response);
		}
		catch (Exception e) 
		{
			
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
}