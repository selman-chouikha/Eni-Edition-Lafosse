package betaboutique.servlets.client;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.modeles.ModeleCommande;
import betaboutique.outils.GestionDroit;
import betaboutique.beans.Commande;
import betaboutique.beans.Client;


/**
 * Classe ServletGestionCommandes
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionCommandes extends HttpServlet 
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
				listerCommandes(request, response);
			}
			
			if(action.equals("ajouter"))
			{
				ajouterCommande(request, response);
			}
			
			if(action.equals("consulter"))
			{
				consulterCommande(request, response);
			}
		}
		else
		{
			getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'êtes pas autorisé à afficher cette page!!").forward(request, response);
		}
		
		// Vider l'objet datasource
		this.datasource=null;
	}
	
	
	// Lister les commandes
	private void listerCommandes(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		ModeleCommande modeleCommande=null;
		int page=0;
		int maxParPage=0;
		String champTri=null;
		String typeTri=null;
		ArrayList<Commande> listeCommandes=null;
		Client client=null;
		
		try 
		{
			// On récupert la page demandée
			page=Integer.parseInt(request.getParameter("page"));
			
			// Si la page vaut 0 ou inférieur on prend la valeur 1
			page = Math.max(page, 1);
		}
		catch(Exception e) 
		{
			// Si le paramètre page passé dans la requête n'est pas un nombre on l'initialise à 1
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
			// Si le paramètre maxParPage passé dans la requête n'est pas un nombre on l'initialise à 5
			maxParPage=5;
		}
	
		// On récupert le reste des éléments de la requete
		champTri=(String)request.getParameter("champTri");
		typeTri=(String)request.getParameter("typeTri");
				
		// Si aucun tri n'est demandé, ou que le tri est incorrect, on réalise un tri sur l'id de la commande
		if(champTri==null || (!champTri.equals("datecreationcommande") && !champTri.equals("etatcommande") && !champTri.equals("totalcommande") && !champTri.equals("id_commande")))
		{
			champTri="id_commande";
		}
		
		// Si aucun type de tri n'est demandé, ou que le type de tri est incorrect, on réalise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}

		// On récupert le client en session
		client=(Client)request.getSession().getAttribute("compte");
		
		// On initialise le modèle
		modeleCommande=new ModeleCommande(datasource);
	
		// On récupert la liste des commandes du client
		listeCommandes=(ArrayList<Commande>)modeleCommande.listerCommande(client.getId(), maxParPage, page, champTri, typeTri);
		
		// On renvoie à la JSP la liste de obtenue ainsi que les informations de pagination
		request.setAttribute("listeCommandes",listeCommandes);
		request.setAttribute("maxParPage", modeleCommande.getMaxParPage());
		request.setAttribute("pageActuel", modeleCommande.getPageActuel());
		request.setAttribute("totalElement", modeleCommande.getTotalElement());
		request.setAttribute("champTri", modeleCommande.getChampTri());
		request.setAttribute("typeTri", modeleCommande.getTypeTri());
		
		// On vide cette liste par sécurité
		listeCommandes=null;
	
		// On renvoie à la vue les messages d'erreurs et ou de succès
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// On renvoie au panier
		getServletContext().getRequestDispatcher("/vues/utilisateurs/listecommandes.jsp").forward(request,response);
	}
	
	
	
	// Ajouter une commande dans le panier
	private void ajouterCommande(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		Commande panier=null;
		ModeleCommande modeleCommande=null;
		int codeErreur=0;
		Client client=null;

		// On initialise le modèle
		modeleCommande=new ModeleCommande(datasource);
		
		// On récupert le panier en session
		panier=(Commande)request.getSession().getAttribute("panier");
		
		// On récupert le client en session
		client=(Client)request.getSession().getAttribute("compte");
			
		// Si n'est pas vide on l'ajoute à la base de données
		if(panier.getTotal()>0)
		{
			// On ajoute la commande à la base de données
			codeErreur=modeleCommande.ajouterCommande(panier, client.getId());
			
			// On teste le code de retour pour afficher un message de succès ou d'erreur
			if(codeErreur!=1)
			{
				// On renvoie sur le panier avec un message d'erreur
				response.sendRedirect("gestionPanier?action=lister&erreurs=" + "Une erreur s'est produite durant l'ajout de votre commande");
			}
			else
			{
				// On crée un nouveau panier
				request.getSession().setAttribute("panier",new Commande());
				
				// On renvoie à la page de liste des commandes avec un message de succès
				response.sendRedirect("gestionCommandes?action=lister&succes=" + "Merci d'avoir commandé sur notre site. Vous pouvez maintenant consulter l'avancement de votre commande.");
			}
		}
		else
		{
			// On renvoie sur le panier avec un message d'erreur
			response.sendRedirect("gestionPanier?action=lister&erreurs=" + "Votre panier est vide");
		}
	}
	
	
	// Consulter une commande
	private void consulterCommande(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		Commande commande=null;
		ModeleCommande modeleCommande=null;
		int idCommande=0;
		Client client=null;

		try
		{
			// On récupert la commande
			idCommande=Integer.parseInt(request.getParameter("idCommande"));
		}
		catch(Exception e) 
		{
			// La commande n'existe pas
			idCommande=0;
		}
		
		// On récupert le client en session
		client=(Client)request.getSession().getAttribute("compte");
		
		// On initialise le modèle
		modeleCommande=new ModeleCommande(datasource);
		
		// On récupert la commande depuis le modèle : renvoi null si elle n'existe pas
		commande=modeleCommande.consulterCommande(client.getId(), idCommande);
		
		// Si la commande n'existe pas on renvoie sur la page liste des commandes
		if(commande!=null)
		{
			// On ajoute la commande à la requete
			request.setAttribute("commande", commande);
			
			// On redirige sur la vue de consultation de la commande
			getServletContext().getRequestDispatcher("/vues/utilisateurs/consultercommande.jsp").forward(request,response);
		}
		else
		{
			// On renvoie sur le la liste des commandes du client avec un message d'erreur
			response.sendRedirect("gestionCommandes?action=lister&erreurs=" + "Cette commande n'existe pas");
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
		
}