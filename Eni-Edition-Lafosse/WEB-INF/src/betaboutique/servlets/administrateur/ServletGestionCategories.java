package betaboutique.servlets.administrateur;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.modeles.ModeleCategorie;
import betaboutique.outils.GestionDroit;
import betaboutique.beans.Categorie;


/**
 * Classe ServletGestionCategories
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionCategories extends HttpServlet 
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
			
			// Listing des cat�gories
			if(action.equals("lister"))
			{
				lister(request,response);
			}
			
			// Consulter une categorie
			if(action.equals("consulter"))
			{
				consulter(request,response);
			}
			
			// Formulaire d'ajout d'une cat�gorie
			if(action.equals("ajouter"))
			{
				ajouter(request,response);
			}
			
			// Validation du formulaire d'ajout
			if(action.equals("validerajout"))
			{
				validerAjout(request,response);
			}
			
			// Suppression d'une cat�gorie
			if(action.equals("supprimer"))
			{
				supprimer(request, response);
			}
			
			// Formulaire de modification d'une cat�gorie
			if(action.equals("modifier"))
			{
				modifier(request, response);
			}
			
			// Validation du formulaire de modification
			if(action.equals("validermodification"))
			{
				validerModification(request, response);
			}
		}
		
		// Vider le datasource
		this.datasource=null;
	}
	

	// Lister les cat�gories
	private void lister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		int page=0;
		int maxParPage=0;
		String champTri=null;
		String typeTri=null;
		String recherche=null;
		String typerecherche=null;
		ModeleCategorie modeleCategorie=null;
		ArrayList<Categorie> listeCategorie=null;
		
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
			// On r�cupert le maximum d'�l�ments a afficher
			maxParPage=Integer.parseInt(request.getParameter("maxParPage").trim());
			
			// Si la page vaut 0 ou inf�rieur on prend la valeur 1
			maxParPage=Math.max(maxParPage, 5);
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
				
		// Si aucun tri n'est demand�, ou que le tri est incorrect, on r�alise un tri sur l'id de la categorie
		if(champTri==null || (!champTri.equals("nomcategorie") && !champTri.equals("id_categorie")))
		{
			champTri="id_categorie";
		}
		
		// Si aucun type de tri n'est demand�, ou que le type de tri est incorrect, on r�alise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}
	
		// On initialise le mod�le
		modeleCategorie=new ModeleCategorie(datasource);
		
		// On r�cupert la liste des cat�gories depuis le mod�le
		listeCategorie=(ArrayList<Categorie>)modeleCategorie.listeCategoriePaginer(maxParPage, page, recherche,typerecherche, champTri, typeTri);
		
		// On renvoie � la JSP la liste des cat�gories obtenu ainsi que les informations de pagination
		request.setAttribute("listeCategorie",listeCategorie);
		request.setAttribute("maxParPage", modeleCategorie.getMaxParPage());
		request.setAttribute("pageActuel", modeleCategorie.getPageActuel());
		request.setAttribute("totalElement", modeleCategorie.getTotalElement());
		request.setAttribute("champTri", modeleCategorie.getChampTri());
		request.setAttribute("typeTri", modeleCategorie.getTypeTri());
		request.setAttribute("recherche", modeleCategorie.getRecherche());
		request.setAttribute("typerecherche", modeleCategorie.getTyperecherche());
		
		// On renvoie de plus � la vue les messages d'erreurs ou de succ�s
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// On vide la liste des cat�gories par s�curit�
		listeCategorie=null;
		
		// On renvoie la page de listing des cat�gories
		getServletContext().getRequestDispatcher("/vues/administrateurs/listecategories.jsp").forward(request,response);
	}
	
	
	

	// Consulter une cat�gorie
	private void consulter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
		int idCategorie=0;
		Categorie categorie=null;
		
		try
		{
			// On r�cupert l'id de la cat�gorie
			idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
		}
		catch (Exception e) 
		{
			// Si l'id de la cat�gorie pass� en param�tre n'est pas un nombre, on initialise l'id � 0
			idCategorie=0;
		}
		
		// On initialise le mod�le
		modeleCategorie=new ModeleCategorie(datasource);
		
		// On r�cupert la cat�gorie correspondante, renvoyer null si l'id de de la cat�gorie n'existe pas dans la base
		categorie=(Categorie)modeleCategorie.getCategorie(idCategorie);
		
		// Si l'objet cat�gorie est null (id non trouv�) on renvoie sur la page de listing avec un message d'erreur
		if(categorie!=null)
		{
			// On renvoie � la JSP la cat�gorie demand�e
			request.setAttribute("categorie",categorie);
			
			// On vide cette cat�gorie par s�curit�
			categorie=null;
			
			// On renvoie � la vue de consultation des informations d'une cat�gorie
			getServletContext().getRequestDispatcher("/vues/administrateurs/consultercategorie.jsp").forward(request,response);
		}
		else
		{
			// On renvoie � la page de liste des cat�gories avec un message d'erreur
			response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Cette cat�gorie n'est pas r�f�renc�e dans la base");
		}
	}
	
	
	// Ajouter une cat�gorie
	private void ajouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		// On revoie � la vue d'ajout d'une cat�gorie
		getServletContext().getRequestDispatcher("/vues/administrateurs/ajoutercategorie.jsp").forward(request,response);
	}
	
	
	// Valider ajouter une cat�gorie
	private void validerAjout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
		Categorie categorie=null;
		List<String> erreurs=null;
		String nom=null;
		int codeErreur=0;
		
		// On initialise le mod�le
		modeleCategorie=new ModeleCategorie(datasource);
		
		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();
		
		// On r�cupert les informations du formualire sur lesquels on va appliquer une validation
		nom=request.getParameter("nom").trim();
		
		// On v�rifie que le nom est renseign�
		if(nom.trim().equals("") || nom==null)
		{
			erreurs.add("Le param�tre [nom] est vide");
		}
		else if (nom.trim().length()<5)
		{
			erreurs.add("Le param�tre [nom] doit contenir au moin 5 caract�res");
		}
		else if (!nom.trim().matches("^[\\p{L}\\s\\d-']{5,}$"))
		{
			erreurs.add("Le param�tre [nom] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le nom de la cat�gorie n'est pas d�j� utilis�
		if(!nom.equals("") && (modeleCategorie.getCategorie(nom) != null))
		{
			erreurs.add("La cat�gorie que vous avez choisi existe d�j�");
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on retourne � la page d'ajout
		if(erreurs.size()>0)
		{	
			// On place le nom de la cat�gorie dans la requ�te
			request.setAttribute("nom", nom);
			
			// On ajoute la collection d'erreurs
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie � la vue d'ajout d'une cat�gorie
			getServletContext().getRequestDispatcher("/vues/administrateurs/ajoutercategorie.jsp").forward(request,response);
		}
		// Si aucune erreur, on ajoute la cat�gorie � la base de donn�es
		else
		{
			// Initialisation de la cat�gorie
			categorie=new Categorie();
			categorie.setNom(nom);
				
			// On ajoute la categorie � la base de donn�es, renvoi 1 si l'ajout s'est d�roul� avec succ�s
			codeErreur=modeleCategorie.ajouterCategorie(categorie);
				
			// On teste le code de retour pour afficher un message de succ�s ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie � la page de listing des cat�gories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Une erreur s'est produite durant l'ajout d'une cat�gorie. Veuillez recommencer");
			}
			else
			{	
				// On renvoie � la page de listing des cat�gories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&succes=" + "La cat�gorie � �t� ajout�e avec succ�s");
			}
		}
	}
	
	
	// Supprimer une cat�gorie
	private void supprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
 		int idCategorie=0;
		int codeErreur=0;
	
		try
		{
			// On r�cupert l'id de la cat�gorie
			idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
		}
		catch (Exception e) 
		{
			// Si l'id de la cat�gorie pass� en param�tre n'est pas un nombre, on initialise l'id � 0
			idCategorie=0;
		}
		
		// On initialise le mod�le
		modeleCategorie=new ModeleCategorie(datasource);
		
		// On teste si la categorie � supprimer n'est pas la cart�gorie "Autre"
		if(idCategorie!=1)
		{
			// Si la cat�gorie existe en base on peut la supprimer
			if(modeleCategorie.getCategorie(idCategorie)!=null)
			{	
				// On envoie au mod�le l'id de la cat�gorie � supprimer, renvoi 1 si la cat�gorie a bien �t� supprim�e
				codeErreur=modeleCategorie.supprimerCategorie(idCategorie);
				
				// On vide l'id de la cat�gorie par s�curit�
				idCategorie=0;
				
				// On teste le code de retour pour afficher un message de succ�s ou d'erreur
				if(codeErreur!=1)
				{		
					// On renvoie � la page de liste des cat�gories avec un message d'erreur
					response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Une erreur s'est produite durant la suppression de la cat�gorie, Veuillez recommencer");
				}
				else
				{	
					// On renvoie � la page de liste des cat�gories avec un message d'erreur
					response.sendRedirect("administrationCategories?action=lister&succes=" + "La cat�gorie � �t� supprim�e avec succ�s");
				}
			}
			else
			{
				// On renvoie � la page de liste des cat�gories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Cette cat�gorie n'est pas r�f�renc�e dans la base");
			}
		}
		else
		{
			// On renvoie � la page de liste des cat�gories avec un message d'erreur
			response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Pour des raisons administratives cette cat�gorie ne peut �tre supprim�e");
		}
	}
	
	
	// Modifier une cat�gorie
	private void modifier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		int idCategorie=0;
		Categorie categorie=null;
		ModeleCategorie modeleCategorie=null;
		
		try
		{
			// On r�cupert l'id de la cat�gorie
			idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
		}
		catch (Exception e) 
		{
			// Si l'id de la cat�gorie pass� en param�tre n'est pas un nombre, on initialise l'id � 0
			idCategorie=0;
		}
		
		// On initialise le mod�le
		modeleCategorie=new ModeleCategorie(datasource);
		
		// On r�cupert la cat�gorie correspondante, renvoi null si l'id de la cat�gorie n'existe pas dans la base
		categorie=modeleCategorie.getCategorie(idCategorie);
		
		// Si l'objet cat�gorie est null (id non trouv�) on renvoie sur la page de listing avec un message d'erreur
		if(categorie!=null)
		{
			// On renvoie � la vue l'article � modifier
			request.setAttribute("categorie",categorie);
						
			// On vide la cat�gorie par s�curit�
			categorie=null;
		
			// On renvoie � la vue de modification d'une cat�gorie
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifiercategorie.jsp").forward(request,response);
		}
		else
		{
			// On renvoie � la page de liste des cat�gories avec un message d'erreur
			response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Cette cat�gorie n'est pas r�f�renc�e dans la base");
		}
	}
	
	
	// Valider modifier une cat�gorie
	private void validerModification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
		Categorie categorie=null;
		List<String> erreurs=null;
		String nom=null;
		String id=null;
		int codeErreur=0;
		
		// On initialise le mod�le
		modeleCategorie=new ModeleCategorie(datasource);
		
		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();
		
		// On r�cupert les informations du formulaire sur lesquels on va appliquer une validation
		id=request.getParameter("id").trim();
		nom=request.getParameter("nom").trim();
		
		// On v�rifie que le nom est renseign�
		if(nom.trim().equals("") || nom==null)
		{
			erreurs.add("Le param�tre [nom] est vide");
		}
		else if (nom.trim().length()<5)
		{
			erreurs.add("Le param�tre [nom] doit contenir au moin 5 caract�res");
		}
		else if (!nom.trim().matches("^[\\p{L}\\s\\d-']{5,}$"))
		{
			erreurs.add("Le param�tre [nom] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le nom de la cat�gorie n'est pas d�j� utilis�
		if(modeleCategorie.getCategorie(nom)!=null && !nom.equals(""))
		{
			if(modeleCategorie.getCategorie(nom).getId()!=Integer.parseInt(id))
			{
				erreurs.add("La cat�gorie que vous avez choisi existe d�j�");
			}
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on renvoi � la page de modification
		if(erreurs.size()>0)
		{	
			// On ajoute le nom et l'id de la cat�gorie dans la requ�te
			request.setAttribute("id", id);
			request.setAttribute("nom", nom);
			
			// On ajoute la collection d'erreurs
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie � la vue de modification de la cat�gorie
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifiercategorie.jsp").forward(request,response);
		}
		// Si aucune erreur, on modifie la cat�gorie dans la base de donn�es
		else
		{
			// Initialisation de la cat�gorie
			categorie=new Categorie();
			categorie.setId(Integer.parseInt(id));
			categorie.setNom(nom);
			
			// On modifie la cat�gorie dans la base de donn�es, renvoi 1 si la modification s'est d�roul�e avec succ�s
			codeErreur=modeleCategorie.modifierCategorie(categorie);
			
			// On teste le code de retour pour afficher un message de succ�s ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie � la page de liste des cat�gories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Une erreur s'est produite durant la modification de la cat�gorie, Veuillez recommencer");
			}
			else
			{	
				// On renvoie � la page de liste des cat�gories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&succes=" + "La cat�gorie � �t� modifi�e avec succ�s");
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	
	
	
}