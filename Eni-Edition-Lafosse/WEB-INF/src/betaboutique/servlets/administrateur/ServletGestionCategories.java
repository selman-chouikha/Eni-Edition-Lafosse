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
 * @author Jérôme Lafosse
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
			getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'êtes pas autorisé à afficher cette page!!").forward(request, response);
		}
		else
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
			
			// Listing des catégories
			if(action.equals("lister"))
			{
				lister(request,response);
			}
			
			// Consulter une categorie
			if(action.equals("consulter"))
			{
				consulter(request,response);
			}
			
			// Formulaire d'ajout d'une catégorie
			if(action.equals("ajouter"))
			{
				ajouter(request,response);
			}
			
			// Validation du formulaire d'ajout
			if(action.equals("validerajout"))
			{
				validerAjout(request,response);
			}
			
			// Suppression d'une catégorie
			if(action.equals("supprimer"))
			{
				supprimer(request, response);
			}
			
			// Formulaire de modification d'une catégorie
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
	

	// Lister les catégories
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
			// On récupert la page demandée
			page=Integer.parseInt(request.getParameter("page").trim());
			
			// Si la page vaut 0 ou inférieur on prend la valeur 1
			page=Math.max(page,1);
		}
		catch (Exception e) 
		{
			// Si dans la requete l'attribut page n'est pas un nombre, on initialise la page à 1
			page=1;
		}
		
		try 
		{
			// On récupert le maximum d'éléments a afficher
			maxParPage=Integer.parseInt(request.getParameter("maxParPage").trim());
			
			// Si la page vaut 0 ou inférieur on prend la valeur 1
			maxParPage=Math.max(maxParPage, 5);
		}
		catch (Exception e) 
		{
			// Si dans la requete le paramètre n'est pas un nombre, on initialise le maximum d'élément à 20
			maxParPage=20;
		}
	
		// On récupert le reste des éléments de la requete
		recherche=(String)request.getParameter("recherche");
		typerecherche=(String)request.getParameter("typerecherche");
		champTri=(String)request.getParameter("champTri");
		typeTri=(String)request.getParameter("typeTri");
				
		// Si aucun tri n'est demandé, ou que le tri est incorrect, on réalise un tri sur l'id de la categorie
		if(champTri==null || (!champTri.equals("nomcategorie") && !champTri.equals("id_categorie")))
		{
			champTri="id_categorie";
		}
		
		// Si aucun type de tri n'est demandé, ou que le type de tri est incorrect, on réalise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}
	
		// On initialise le modèle
		modeleCategorie=new ModeleCategorie(datasource);
		
		// On récupert la liste des catégories depuis le modèle
		listeCategorie=(ArrayList<Categorie>)modeleCategorie.listeCategoriePaginer(maxParPage, page, recherche,typerecherche, champTri, typeTri);
		
		// On renvoie à la JSP la liste des catégories obtenu ainsi que les informations de pagination
		request.setAttribute("listeCategorie",listeCategorie);
		request.setAttribute("maxParPage", modeleCategorie.getMaxParPage());
		request.setAttribute("pageActuel", modeleCategorie.getPageActuel());
		request.setAttribute("totalElement", modeleCategorie.getTotalElement());
		request.setAttribute("champTri", modeleCategorie.getChampTri());
		request.setAttribute("typeTri", modeleCategorie.getTypeTri());
		request.setAttribute("recherche", modeleCategorie.getRecherche());
		request.setAttribute("typerecherche", modeleCategorie.getTyperecherche());
		
		// On renvoie de plus à la vue les messages d'erreurs ou de succès
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// On vide la liste des catégories par sécurité
		listeCategorie=null;
		
		// On renvoie la page de listing des catégories
		getServletContext().getRequestDispatcher("/vues/administrateurs/listecategories.jsp").forward(request,response);
	}
	
	
	

	// Consulter une catégorie
	private void consulter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
		int idCategorie=0;
		Categorie categorie=null;
		
		try
		{
			// On récupert l'id de la catégorie
			idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
		}
		catch (Exception e) 
		{
			// Si l'id de la catégorie passé en paramètre n'est pas un nombre, on initialise l'id à 0
			idCategorie=0;
		}
		
		// On initialise le modèle
		modeleCategorie=new ModeleCategorie(datasource);
		
		// On récupert la catégorie correspondante, renvoyer null si l'id de de la catégorie n'existe pas dans la base
		categorie=(Categorie)modeleCategorie.getCategorie(idCategorie);
		
		// Si l'objet catégorie est null (id non trouvé) on renvoie sur la page de listing avec un message d'erreur
		if(categorie!=null)
		{
			// On renvoie à la JSP la catégorie demandée
			request.setAttribute("categorie",categorie);
			
			// On vide cette catégorie par sécurité
			categorie=null;
			
			// On renvoie à la vue de consultation des informations d'une catégorie
			getServletContext().getRequestDispatcher("/vues/administrateurs/consultercategorie.jsp").forward(request,response);
		}
		else
		{
			// On renvoie à la page de liste des catégories avec un message d'erreur
			response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Cette catégorie n'est pas référencée dans la base");
		}
	}
	
	
	// Ajouter une catégorie
	private void ajouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		// On revoie à la vue d'ajout d'une catégorie
		getServletContext().getRequestDispatcher("/vues/administrateurs/ajoutercategorie.jsp").forward(request,response);
	}
	
	
	// Valider ajouter une catégorie
	private void validerAjout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
		Categorie categorie=null;
		List<String> erreurs=null;
		String nom=null;
		int codeErreur=0;
		
		// On initialise le modèle
		modeleCategorie=new ModeleCategorie(datasource);
		
		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();
		
		// On récupert les informations du formualire sur lesquels on va appliquer une validation
		nom=request.getParameter("nom").trim();
		
		// On vérifie que le nom est renseigné
		if(nom.trim().equals("") || nom==null)
		{
			erreurs.add("Le paramètre [nom] est vide");
		}
		else if (nom.trim().length()<5)
		{
			erreurs.add("Le paramètre [nom] doit contenir au moin 5 caractères");
		}
		else if (!nom.trim().matches("^[\\p{L}\\s\\d-']{5,}$"))
		{
			erreurs.add("Le paramètre [nom] contient une erreur de syntaxe");
		}
		
		// On vérifie que le nom de la catégorie n'est pas déjà utilisé
		if(!nom.equals("") && (modeleCategorie.getCategorie(nom) != null))
		{
			erreurs.add("La catégorie que vous avez choisi existe déjà");
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on retourne à la page d'ajout
		if(erreurs.size()>0)
		{	
			// On place le nom de la catégorie dans la requête
			request.setAttribute("nom", nom);
			
			// On ajoute la collection d'erreurs
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie à la vue d'ajout d'une catégorie
			getServletContext().getRequestDispatcher("/vues/administrateurs/ajoutercategorie.jsp").forward(request,response);
		}
		// Si aucune erreur, on ajoute la catégorie à la base de données
		else
		{
			// Initialisation de la catégorie
			categorie=new Categorie();
			categorie.setNom(nom);
				
			// On ajoute la categorie à la base de données, renvoi 1 si l'ajout s'est déroulé avec succès
			codeErreur=modeleCategorie.ajouterCategorie(categorie);
				
			// On teste le code de retour pour afficher un message de succès ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie à la page de listing des catégories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Une erreur s'est produite durant l'ajout d'une catégorie. Veuillez recommencer");
			}
			else
			{	
				// On renvoie à la page de listing des catégories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&succes=" + "La catégorie à été ajoutée avec succès");
			}
		}
	}
	
	
	// Supprimer une catégorie
	private void supprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
 		int idCategorie=0;
		int codeErreur=0;
	
		try
		{
			// On récupert l'id de la catégorie
			idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
		}
		catch (Exception e) 
		{
			// Si l'id de la catégorie passé en paramètre n'est pas un nombre, on initialise l'id à 0
			idCategorie=0;
		}
		
		// On initialise le modèle
		modeleCategorie=new ModeleCategorie(datasource);
		
		// On teste si la categorie à supprimer n'est pas la cartégorie "Autre"
		if(idCategorie!=1)
		{
			// Si la catégorie existe en base on peut la supprimer
			if(modeleCategorie.getCategorie(idCategorie)!=null)
			{	
				// On envoie au modèle l'id de la catégorie à supprimer, renvoi 1 si la catégorie a bien été supprimée
				codeErreur=modeleCategorie.supprimerCategorie(idCategorie);
				
				// On vide l'id de la catégorie par sécurité
				idCategorie=0;
				
				// On teste le code de retour pour afficher un message de succès ou d'erreur
				if(codeErreur!=1)
				{		
					// On renvoie à la page de liste des catégories avec un message d'erreur
					response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Une erreur s'est produite durant la suppression de la catégorie, Veuillez recommencer");
				}
				else
				{	
					// On renvoie à la page de liste des catégories avec un message d'erreur
					response.sendRedirect("administrationCategories?action=lister&succes=" + "La catégorie à été supprimée avec succès");
				}
			}
			else
			{
				// On renvoie à la page de liste des catégories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Cette catégorie n'est pas référencée dans la base");
			}
		}
		else
		{
			// On renvoie à la page de liste des catégories avec un message d'erreur
			response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Pour des raisons administratives cette catégorie ne peut être supprimée");
		}
	}
	
	
	// Modifier une catégorie
	private void modifier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		int idCategorie=0;
		Categorie categorie=null;
		ModeleCategorie modeleCategorie=null;
		
		try
		{
			// On récupert l'id de la catégorie
			idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
		}
		catch (Exception e) 
		{
			// Si l'id de la catégorie passé en paramètre n'est pas un nombre, on initialise l'id à 0
			idCategorie=0;
		}
		
		// On initialise le modèle
		modeleCategorie=new ModeleCategorie(datasource);
		
		// On récupert la catégorie correspondante, renvoi null si l'id de la catégorie n'existe pas dans la base
		categorie=modeleCategorie.getCategorie(idCategorie);
		
		// Si l'objet catégorie est null (id non trouvé) on renvoie sur la page de listing avec un message d'erreur
		if(categorie!=null)
		{
			// On renvoie à la vue l'article à modifier
			request.setAttribute("categorie",categorie);
						
			// On vide la catégorie par sécurité
			categorie=null;
		
			// On renvoie à la vue de modification d'une catégorie
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifiercategorie.jsp").forward(request,response);
		}
		else
		{
			// On renvoie à la page de liste des catégories avec un message d'erreur
			response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Cette catégorie n'est pas référencée dans la base");
		}
	}
	
	
	// Valider modifier une catégorie
	private void validerModification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
		Categorie categorie=null;
		List<String> erreurs=null;
		String nom=null;
		String id=null;
		int codeErreur=0;
		
		// On initialise le modèle
		modeleCategorie=new ModeleCategorie(datasource);
		
		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();
		
		// On récupert les informations du formulaire sur lesquels on va appliquer une validation
		id=request.getParameter("id").trim();
		nom=request.getParameter("nom").trim();
		
		// On vérifie que le nom est renseigné
		if(nom.trim().equals("") || nom==null)
		{
			erreurs.add("Le paramètre [nom] est vide");
		}
		else if (nom.trim().length()<5)
		{
			erreurs.add("Le paramètre [nom] doit contenir au moin 5 caractères");
		}
		else if (!nom.trim().matches("^[\\p{L}\\s\\d-']{5,}$"))
		{
			erreurs.add("Le paramètre [nom] contient une erreur de syntaxe");
		}
		
		// On vérifie que le nom de la catégorie n'est pas déjà utilisé
		if(modeleCategorie.getCategorie(nom)!=null && !nom.equals(""))
		{
			if(modeleCategorie.getCategorie(nom).getId()!=Integer.parseInt(id))
			{
				erreurs.add("La catégorie que vous avez choisi existe déjà");
			}
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on renvoi à la page de modification
		if(erreurs.size()>0)
		{	
			// On ajoute le nom et l'id de la catégorie dans la requête
			request.setAttribute("id", id);
			request.setAttribute("nom", nom);
			
			// On ajoute la collection d'erreurs
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie à la vue de modification de la catégorie
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifiercategorie.jsp").forward(request,response);
		}
		// Si aucune erreur, on modifie la catégorie dans la base de données
		else
		{
			// Initialisation de la catégorie
			categorie=new Categorie();
			categorie.setId(Integer.parseInt(id));
			categorie.setNom(nom);
			
			// On modifie la catégorie dans la base de données, renvoi 1 si la modification s'est déroulée avec succès
			codeErreur=modeleCategorie.modifierCategorie(categorie);
			
			// On teste le code de retour pour afficher un message de succès ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie à la page de liste des catégories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&erreurs=" + "Une erreur s'est produite durant la modification de la catégorie, Veuillez recommencer");
			}
			else
			{	
				// On renvoie à la page de liste des catégories avec un message d'erreur
				response.sendRedirect("administrationCategories?action=lister&succes=" + "La catégorie à été modifiée avec succès");
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	
	
	
}