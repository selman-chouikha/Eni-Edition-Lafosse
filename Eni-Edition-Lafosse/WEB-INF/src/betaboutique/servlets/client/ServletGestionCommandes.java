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
 * @author J�r�me Lafosse
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
			getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
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
			// On r�cupert la page demand�e
			page=Integer.parseInt(request.getParameter("page"));
			
			// Si la page vaut 0 ou inf�rieur on prend la valeur 1
			page = Math.max(page, 1);
		}
		catch(Exception e) 
		{
			// Si le param�tre page pass� dans la requ�te n'est pas un nombre on l'initialise � 1
			page=1;
		}
		
		try 
		{
			// On r�cupert le maximum d'�l�ments a afficher
			maxParPage=Integer.parseInt(request.getParameter("maxParPage"));
			
			// Si la page vaut 0 ou inf�rieur on prend la valeur 1
			maxParPage=Math.max(maxParPage, 5);
		}
		catch(Exception e) 
		{
			// Si le param�tre maxParPage pass� dans la requ�te n'est pas un nombre on l'initialise � 5
			maxParPage=5;
		}
	
		// On r�cupert le reste des �l�ments de la requete
		champTri=(String)request.getParameter("champTri");
		typeTri=(String)request.getParameter("typeTri");
				
		// Si aucun tri n'est demand�, ou que le tri est incorrect, on r�alise un tri sur l'id de la commande
		if(champTri==null || (!champTri.equals("datecreationcommande") && !champTri.equals("etatcommande") && !champTri.equals("totalcommande") && !champTri.equals("id_commande")))
		{
			champTri="id_commande";
		}
		
		// Si aucun type de tri n'est demand�, ou que le type de tri est incorrect, on r�alise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}

		// On r�cupert le client en session
		client=(Client)request.getSession().getAttribute("compte");
		
		// On initialise le mod�le
		modeleCommande=new ModeleCommande(datasource);
	
		// On r�cupert la liste des commandes du client
		listeCommandes=(ArrayList<Commande>)modeleCommande.listerCommande(client.getId(), maxParPage, page, champTri, typeTri);
		
		// On renvoie � la JSP la liste de obtenue ainsi que les informations de pagination
		request.setAttribute("listeCommandes",listeCommandes);
		request.setAttribute("maxParPage", modeleCommande.getMaxParPage());
		request.setAttribute("pageActuel", modeleCommande.getPageActuel());
		request.setAttribute("totalElement", modeleCommande.getTotalElement());
		request.setAttribute("champTri", modeleCommande.getChampTri());
		request.setAttribute("typeTri", modeleCommande.getTypeTri());
		
		// On vide cette liste par s�curit�
		listeCommandes=null;
	
		// On renvoie � la vue les messages d'erreurs et ou de succ�s
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

		// On initialise le mod�le
		modeleCommande=new ModeleCommande(datasource);
		
		// On r�cupert le panier en session
		panier=(Commande)request.getSession().getAttribute("panier");
		
		// On r�cupert le client en session
		client=(Client)request.getSession().getAttribute("compte");
			
		// Si n'est pas vide on l'ajoute � la base de donn�es
		if(panier.getTotal()>0)
		{
			// On ajoute la commande � la base de donn�es
			codeErreur=modeleCommande.ajouterCommande(panier, client.getId());
			
			// On teste le code de retour pour afficher un message de succ�s ou d'erreur
			if(codeErreur!=1)
			{
				// On renvoie sur le panier avec un message d'erreur
				response.sendRedirect("gestionPanier?action=lister&erreurs=" + "Une erreur s'est produite durant l'ajout de votre commande");
			}
			else
			{
				// On cr�e un nouveau panier
				request.getSession().setAttribute("panier",new Commande());
				
				// On renvoie � la page de liste des commandes avec un message de succ�s
				response.sendRedirect("gestionCommandes?action=lister&succes=" + "Merci d'avoir command� sur notre site. Vous pouvez maintenant consulter l'avancement de votre commande.");
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
			// On r�cupert la commande
			idCommande=Integer.parseInt(request.getParameter("idCommande"));
		}
		catch(Exception e) 
		{
			// La commande n'existe pas
			idCommande=0;
		}
		
		// On r�cupert le client en session
		client=(Client)request.getSession().getAttribute("compte");
		
		// On initialise le mod�le
		modeleCommande=new ModeleCommande(datasource);
		
		// On r�cupert la commande depuis le mod�le : renvoi null si elle n'existe pas
		commande=modeleCommande.consulterCommande(client.getId(), idCommande);
		
		// Si la commande n'existe pas on renvoie sur la page liste des commandes
		if(commande!=null)
		{
			// On ajoute la commande � la requete
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