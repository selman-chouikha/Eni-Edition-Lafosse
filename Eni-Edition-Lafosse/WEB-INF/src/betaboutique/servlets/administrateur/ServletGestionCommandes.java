package betaboutique.servlets.administrateur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Date;
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
	// Variables de la classe
	private DataSource datasource=null;
	private GestionDroit gestionDroit=null;
	
	// Traitements 
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{		
		// Initialisation de la gestion des droits 
		gestionDroit=new GestionDroit(request);
		
		// On autorise uniquement les administrateurs
		if(!gestionDroit.estAutorise(true,false,false))
		{	
			getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
		}
		else
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
			
			// Listing des commandes 
			if(action.equals("lister"))
			{
				listerCommande(request,response);
			}
			
			// Consultation d'une commande 
			if(action.equals("consulter"))
			{
				consulterCommande(request,response);
			}
			
			// Affiche le formulaire de modification d'une commande 
			if(action.equals("modifier"))
			{
				modifierCommande(request,response);
			}
			
			// Valider la modification d'une commande 
			if(action.equals("validermodification"))
			{
				validerModification(request,response);
			}
		}
		
		// Vider le datasource 
		this.datasource=null;
	}
	
	
	// Lister les commandes
	private void listerCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables 
		int page=0;
		int maxParPage=0;
		String champTri=null;
		String typeTri=null;
		String recherche=null;
		String typerecherche=null;
		ModeleCommande modeleCommande=null;
		ArrayList<Commande> listeCommandes=null;
		
		try 
		{
			// On r�cupert la page demand�e
			page=Integer.parseInt(request.getParameter("page").trim());
			
			// Si la page vaut 0 ou inf�rieur on prend la valeur 1 
			page=Math.max(page,1);
		}
		catch (Exception e) 
		{
			// Si dans la requete l'attribut page n'est pas un nombre, on initialise la page � 1 
			page=1;
		}
		
		try 
		{
			// On r�cupert le maximum d'�l�ments � afficher 
			maxParPage=Integer.parseInt(request.getParameter("maxParPage").trim());
			
			// Si la page vaut 0 ou inf�rieur on prend la valeur 1 
			maxParPage = Math.max(maxParPage, 5);
		}
		catch (Exception e) 
		{
			// Si dans la requete le param�tre n'est pas un nombre, on initialise le maximum d'�l�ment � 20 
			maxParPage=20;
		}
	
		// On r�cupert le reste des �l�ments de la requete 
		recherche=(String)request.getParameter("recherche");
		typerecherche=(String)request.getParameter("typerecherche");
		champTri=(String)request.getParameter("champTri");
		typeTri=(String)request.getParameter("typeTri");
				
		// Si aucun tri n'est pas demand�, ou que le tri est incorrect, on r�alise un tri sur l'id de la commande 
		if(champTri==null || (!champTri.equals("datecreationcommande") && !champTri.equals("identifiantclient") && !champTri.equals("etatcommande") && !champTri.equals("totalpoids") && !champTri.equals("totalcommande") && !champTri.equals("id_commande")))
		{
			champTri="id_commande";
		}
		
		// Si aucun type de tri n'est demand�, ou que le type de tri est incorrect, on r�alise un tri croissant 
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}
	
		// On initialise le mod�le 
		modeleCommande=new ModeleCommande(datasource);
		
		// On r�cupert la liste des commandes avec le mod�le 
		listeCommandes=(ArrayList<Commande>)modeleCommande.listerCommande(maxParPage, page, recherche,typerecherche, champTri, typeTri);
		
		// On renvoie � la JSP la liste des commandes ainsi que les informations de pagination
		request.setAttribute("listeCommandes",listeCommandes);
		request.setAttribute("maxParPage", modeleCommande.getMaxParPage());
		request.setAttribute("pageActuel", modeleCommande.getPageActuel());
		request.setAttribute("totalElement", modeleCommande.getTotalElement());
		request.setAttribute("champTri", modeleCommande.getChampTri());
		request.setAttribute("typeTri", modeleCommande.getTypeTri());
		request.setAttribute("recherche", modeleCommande.getRecherche());
		request.setAttribute("typerecherche", modeleCommande.getTyperecherche());
		
		// On renvoie de plus � la vue les messages d'erreurs ou de succ�s
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// On vide la liste des commandes par s�curit�
		listeCommandes=null;
		
		// On renvoie la page de listing des commandes 
		getServletContext().getRequestDispatcher("/vues/administrateurs/listecommandes.jsp").forward(request,response);
	}
	
	
	
	// Consulter une commande
	private void consulterCommande(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables
		Commande commande=null;
		ModeleCommande modeleCommande=null;
		int idCommande=0;
		
		try
		{
			// On r�cupert la commande
			idCommande=Integer.parseInt(request.getParameter("idCommande"));
		}
		catch (Exception e) 
		{
			// La commande n'existe pas
			idCommande=0;
		}
		
		// On initialise le mod�le
		modeleCommande=new ModeleCommande(datasource);
		
		// On r�cupert la commande depuis le mod�le : renvoi null si elle n'existe pas
		commande=modeleCommande.consulterCommande(idCommande);
		
		// Si la commande n'existe pas on renvoie sur la page de listing des commandes 
		if(commande!=null)
		{
			// On ajoute la commande � la requete 
			request.setAttribute("commande", commande);
			
			// On redirige sur la vue de consultation de la commande 
			getServletContext().getRequestDispatcher("/vues/administrateurs/consultercommande.jsp").forward(request,response);
		}
		else
		{
			// On renvoie sur le la liste des commandes avec un message d'erreur 
			response.sendRedirect("administrationCommandes?action=lister&erreurs=" + "Cette commande n'existe pas");
		}
	}
	
	
	// Modifier une commande
	private void modifierCommande(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables 
		Commande commande=null;
		ModeleCommande modeleCommande=null;
		int idCommande=0;
		
		try
		{
			// On r�cupert la commande 
			idCommande=Integer.parseInt(request.getParameter("idCommande"));
		}
		catch (Exception e) 
		{
			// La commande n'existe pas 
			idCommande=0;
		}
		
		// On initialise le mod�le 
		modeleCommande=new ModeleCommande(datasource);
		
		// On r�cupert la commande depuis le mod�le : renvoi null si elle n'existe pas
		commande=modeleCommande.consulterCommande(idCommande);
		
		// Si la commande n'existe pas on renvoie sur la page de lisiting des commandes 
		if(commande!=null)
		{
			// On ne peut pas modifier une commande annul�e ou exp�di�e 
			if(commande.getEtat()==0)
			{
				// On ajoute la commande � la requete 
				request.setAttribute("commande", commande);
				
				// On redirige sur la vue de modification de la commande 
				getServletContext().getRequestDispatcher("/vues/administrateurs/modifiercommande.jsp").forward(request,response);
			}
			else
			{
				// On renvoie sur le la liste des commandes avec un message d'erreur 
				response.sendRedirect("administrationCommandes?action=lister&erreurs=" + "Cette commande ne peut plus �tre modifi�");
			}
		}
		else
		{
			// On renvoie sur le la liste des commandes avec un message d'erreur 
			response.sendRedirect("administrationCommandes?action=lister&erreurs=" + "Cette commande n'existe pas");
		}
	}
	
	
	// Valider modifier une commande
	private void validerModification(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Variables 
		int codeErreur=0;
		Commande commande=null;
		Client client=null;
		ModeleCommande modeleCommande=null;
		List<String> erreurs=null;
		Enumeration parametres=null;
		String parametre=null;
		String nom=null;
		String prenom=null;
		String adresse=null;
		String codePostal=null;
		String ville=null;
		
		// On r�cupert les informations du formulaire sur lesquels on va appliquer une validation
		nom=request.getParameter("nom").trim();
		prenom=request.getParameter("prenom").trim();
		adresse=request.getParameter("adresse").trim();
		codePostal=request.getParameter("codePostal").trim();
		ville=request.getParameter("ville").trim();
		
		// Initialisation de la collection d'erreurs 
		erreurs=new ArrayList<String>();
		
		// On v�rifie que le nom est renseign� 
		if(nom.equals("") || nom==null)
		{
			erreurs.add("Le param�tre [nom] est vide");
		}
		else if(!nom.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le param�tre [nom] contient une erreur de syntaxe");
		}
			
		// On v�rifie que le pr�nom est renseign�
		if(prenom.equals("") || prenom==null)
		{
			erreurs.add("Le param�tre [prenom] est vide");
		}
		else if(!prenom.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le param�tre [prenom] contient une erreur de syntaxe");
		}
							
		// On v�rifie que l'adresse est renseign�e
		if(adresse.equals("") || adresse==null)
		{
			erreurs.add("Le param�tre [adresse] est vide");
		}
		else if(!adresse.matches("[\\d\\p{L}]+([ '-]?[\\d\\p{L}]+)+"))
		{
			erreurs.add("Le param�tre [adresse] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le code postal est renseign� 
		if(codePostal.equals("") || codePostal==null)
		{
			erreurs.add("Le param�tre [codePostal] est vide");
		}
		else if(!codePostal.matches("^\\d{5}$"))
		{
			erreurs.add("Le param�tre [codePostal] contient une erreur de syntaxe");
		}
		
		// On v�rifie que la ville est renseign�e
		if(ville.equals("") || ville==null)
		{
			erreurs.add("Le param�tre [ville] est vide");
		}
		else if(!ville.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le param�tre [ville] contient une erreur de syntaxe");
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on retourne � la page de modification d'une commande 
		if(erreurs.size()>0)
		{
			// On ajoute tous les attributs du formulaire dans la requ�te 
			parametres=request.getParameterNames();
			while (parametres.hasMoreElements()) 
			{
				parametre=(String)parametres.nextElement();
			    request.setAttribute(parametre,(String)request.getParameterValues(parametre)[0]);
			}
			
			// On ajoute la collection d'erreurs 
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie � la vue de modification d'une commande 
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifiercommande.jsp").forward(request,response);
		}
		else
		{
			// On cr�e un nouvel objet client 
			client=new Client();
			client.setId(Integer.parseInt(request.getParameter("idClient")));
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setAdresse(adresse);
			client.setCodePostal(codePostal);
			client.setVille(ville);
			
			// On cr�e un nouvel objet commande � partir des informations du formulaire 
			commande=new Commande();
			commande.setId(Integer.parseInt(request.getParameter("idCommande")));
			commande.setEtat(Integer.parseInt(request.getParameter("etat")));
			commande.setDateModification(new Date());
			commande.setClient(client);
			
			// Initialisation du mod�le 
			modeleCommande=new ModeleCommande(this.datasource);
			
			// On met � jour la commande 
			codeErreur=modeleCommande.modifierCommande(commande);
			
			// On test si la modification a �t� effectu�e avec succ�s 
			if(codeErreur!=1)
			{
				// On renvoie � la vue de listing des commandes avec un message d'erreur 
				response.sendRedirect("administrationCommandes?action=lister&erreurs=" + "Une erreur s'est produite durant la modification des informations de la commande. Veuillez recommencer");
			}
			else
			{
				// On renvoie � la vue de listing des commandes avec un message de succ�s 
				response.sendRedirect("administrationCommandes?action=lister&succes=" + "Les informations de la commande ont �t� enregistr�es avec succ�s");
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	
	
}