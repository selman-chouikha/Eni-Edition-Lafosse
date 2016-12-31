package betaboutique.servlets.administrateur;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.modeles.ModeleArticle;
import betaboutique.modeles.ModeleCategorie;
import betaboutique.outils.GestionDroit;
import betaboutique.beans.Article;
import betaboutique.beans.Categorie;

/**
 * Classe ServletGestionArticles
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionArticles extends HttpServlet 
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
		if(gestionDroit.estAutorise(true,false,false))
		{
			// R�cup�rer l'objet datasource du context de la servlet
			datasource=(DataSource)getServletContext().getAttribute("datasource");
			
			// Action � r�aliser
			String action=(String)request.getParameter("action");
			
			// Action par d�faut (liste)
			if(action==null || action.equalsIgnoreCase(""))
			{
				action="lister";
			}
			
			// Listing des articles
			if(action.equals("lister"))
			{
				lister(request,response);
			}
			
			// Consulter un article
			if(action.equals("consulter"))
			{
				consulter(request,response);
			}
			
			// Formulaire d'ajout d'un article
			if(action.equals("ajouter"))
			{
				ajouter(request,response);
			}
			
			// Validation du formulaire d'ajout
			if(action.equals("validerajout"))
			{
				validerAjout(request,response);
			}
			
			// Validation du formulaire de choix de la vignette
			if(action.equals("finaliserajout"))
			{
				finaliserajout(request,response);
			}
			
			// Suppression d'un article
			if(action.equals("supprimer"))
			{
				supprimer(request, response);
			}
			
			// Formulaire de modification d'un article
			if(action.equals("modifier"))
			{
				modifier(request, response);
			}
			
			// Validation du formulaire de modification
			if(action.equals("validermodification"))
			{
				validerModification(request, response);
			}
			
			// Validation du formulaire de choix de la vignette
			if(action.equals("finalisermodification"))
			{
				finaliserModification(request, response);
			}
		}
		else
		{
			getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
		}	
		
		// Vider le datasource
		this.datasource=null;
	}
	
	
	
	
	// Lister les articles
	private void lister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		int page=0;
		int maxParPage=0;
		String champTri=null;
		String typeTri=null;
		String recherche=null;
		String typerecherche=null;
		ModeleArticle modeleArticle=null;
		ArrayList<Article> listeArticles=null;
		
		try 
		{
			// On r�cupert la page demand�
			page=Integer.parseInt(request.getParameter("page").trim());
			
			// Si la page vaut 0 ou inf�rieur on prend la valeur 1
			page=Math.max(page, 1);
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
				
		// Si aucun tri n'est demand�, ou que le tri est incorrect, on r�alise un tri sur l'id de l'article
		if(champTri==null || (!champTri.equals("nomarticle") && !champTri.equals("id_article") && !champTri.equals("prixarticle") && !champTri.equals("datearticle") && !champTri.equals("id_categorie") && !champTri.equals("etatarticle")))
		{
			champTri="id_article";
		}
		
		// Si aucun type de tri n'est demand�, ou que le type de tri est incorrect, on r�alise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}
	
		// On initialise le mod�le
		modeleArticle=new ModeleArticle(datasource);
		
		// On r�cupert la liste des articles avec le mod�le
		listeArticles=(ArrayList<Article>)modeleArticle.listeArticleAdmin(maxParPage, page, recherche,typerecherche, champTri, typeTri);
		
		// On renvoie � la JSP la liste des articles obtenus ainsi que les informations de pagination
		request.setAttribute("listeArticles",listeArticles);
		request.setAttribute("maxParPage", modeleArticle.getMaxParPage());
		request.setAttribute("pageActuel", modeleArticle.getPageActuel());
		request.setAttribute("totalElement", modeleArticle.getTotalElement());
		request.setAttribute("champTri", modeleArticle.getChampTri());
		request.setAttribute("typeTri", modeleArticle.getTypeTri());
		request.setAttribute("recherche", modeleArticle.getRecherche());
		request.setAttribute("typerecherche", modeleArticle.getTyperecherche());
		
		// On renvoie de plus � la vue les messages d'erreurs et ou de succ�s
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// On vide la liste d'articles par s�curit�
		listeArticles=null;
		
		// On renvoie la de listing des articles
		getServletContext().getRequestDispatcher("/vues/administrateurs/listearticles.jsp").forward(request,response);
	}
	
	
	// Consulter un article
	private void consulter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleArticle modeleArticle=null;
		int idArticle=0;
		Article article=null;
		
		try
		{
			// On r�cupert l'id de l'article
			idArticle=Integer.parseInt(request.getParameter("idArticle"));
		}
		catch (Exception e) 
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
			// On renvoie � la JSP l'article demand�
			request.setAttribute("article",article);
			
			// On vide cette l'article par s�curit�
			article=null;
			
			// On renvoie � la vue de consultation des informations d'un article
			getServletContext().getRequestDispatcher("/vues/administrateurs/consulterarticle.jsp").forward(request,response);
		}
		else
		{
			// On renvoie la page de liste des articles avec un message d'erreur
			response.sendRedirect("administrationArticles?action=lister&erreurs=" + "Cet article n'est pas r�f�renc� dans la base");
		}
	}
	
	
	
	// Ajouter un article
	private void ajouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		List<Categorie> listeCategorie=null;
		ModeleCategorie modeleCategorie=null;
		
		// Initialisation du mod�le
		modeleCategorie=new ModeleCategorie(this.datasource);
		
		// On r�cupert la liste des cat�gories depuis le mod�le
		listeCategorie=modeleCategorie.listeCategorie();
		
		// On place dans la requ�te la liste des cat�gories
		request.setAttribute("categories", listeCategorie);
		
		// on revoie � la vue d'ajout d'un article
		getServletContext().getRequestDispatcher("/vues/administrateurs/ajouterarticle.jsp").forward(request,response);
	}
	
	
	// Valider l'ajout d'un article
	private void validerAjout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
		List<Categorie> listeCategorie=null;
		Enumeration parametres=null;
		String parametre=null;
		List<String> erreurs=null;
		String nom=null;
		String description=null;
		String prix=null;
		String stock=null;
		String poids=null;
		
		// On r�cupert les informations du formualire sur lesquels on va appliquer une validation
		nom=request.getParameter("nom").trim();
		description=request.getParameter("description").trim();
		prix=request.getParameter("prix").trim();
		stock=request.getParameter("stock").trim();
		poids=request.getParameter("poids").trim();
				
		// On ajoute tous les attributs du formulaire dans la requ�te soit pour les transf�rer � la vue
		parametres=request.getParameterNames();
		while (parametres.hasMoreElements()) 
		{
			parametre=(String)parametres.nextElement();
		    request.setAttribute(parametre,(String)request.getParameterValues(parametre)[0]);
		}

		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();
		
		// On v�rifie que le nom est renseign�
		if(nom.trim().equals("") || nom==null)
		{
			erreurs.add("Le param�tre [nom] est vide");
		}
		else if (nom.trim().length()<5)
		{
			// Test de la longeur du nom (5 caract�re minimum)
			erreurs.add("Le param�tre [nom] doit contenir au moin 5 caract�res");
		}
		else if (!nom.trim().matches("^[\\p{L}\\s\\d-']{5,}$"))
		{
			erreurs.add("Le param�tre [nom] contient une erreur de syntaxe");
		}
		
		// On v�rifie que la description est renseign�
		if(description.trim().equals("") || description==null)
		{
			erreurs.add("Le param�tre [description] est vide");
		}
		else if (description.trim().length()<20)
		{
			erreurs.add("Le param�tre [description] doit contenir au moin 20 caract�res");
		}
		
		// On v�rifie que le prix est renseign�
		if(prix.trim().equals("") || prix == null)
		{
			erreurs.add("Le param�tre [prix] est vide");
		}
		else if(!prix.trim().matches("^(\\d){1,}((\\.){1,1}(\\d){1,2})?$"))
		{
			erreurs.add("Le param�tre [prix] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le stock est renseign�
		if(stock.trim().equals("") || stock==null)
		{
			erreurs.add("Le param�tre [stock] est vide");
		}
		else if(!stock.trim().matches("^(\\d){1,}$"))
		{
			erreurs.add("Le param�tre [stock] doit contenir au moin 1 caract�re num�rique");
		}
		
		// On v�rifie que le poids est renseign�
		if(poids.trim().equals("") || poids==null)
		{
			erreurs.add("Le param�tre [poids] est vide");
		}
		else if(!poids.trim().matches("^(\\d){1,}$"))
		{
			erreurs.add("Le param�tre [poids] doit contenir au moin 1 caract�re num�rique");
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on retourne � la page d'ajout
		if(erreurs.size() > 0)
		{	
			// On initialise le modele de categorie
			modeleCategorie=new ModeleCategorie(datasource);
			
			// On r�cupert la liste des cat�gories
			listeCategorie=modeleCategorie.listeCategorie();
			
			// On place la liste dans la requ�te
			request.setAttribute("categories", listeCategorie);
			
			// On ajoute la collection d'erreurs
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie � la vue d'ajout d'un article
			getServletContext().getRequestDispatcher("/vues/administrateurs/ajouterarticle.jsp").forward(request,response);
		}
		else
		{
			// On renvoie � la page de de choix de la vignette
			getServletContext().getRequestDispatcher("/vues/administrateurs/ajouterphoto.jsp").forward(request,response);
		}
	}
	
	
	// Finaliser l'ajout avec le chargement de la photo
	private void finaliserajout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		Article article=null;
		ModeleCategorie modeleCategorie=null;
		ModeleArticle modeleArticle=null;
		int codeErreur=0;
			
		// On initialise les mod�les
		modeleArticle=new ModeleArticle(this.datasource);
		modeleCategorie=new ModeleCategorie(this.datasource);
				
		// On initialise le nouvel article � ajouter
		article=new Article();
				
		// On d�finit les attributs de l'article avec les informations fournit dans les deux formulaires pr�c�dent
		article.setCategorie(modeleCategorie.getCategorie(Integer.parseInt(request.getParameter("categorie").trim())));
		article.setNom(request.getParameter("nom").trim());
		article.setDescription(request.getParameter("description").trim());
		article.setPoids(Integer.parseInt(request.getParameter("poids").trim()));
		article.setStock(Integer.parseInt(request.getParameter("stock").trim()));
		article.setPrix(Double.parseDouble(request.getParameter("prix").trim()));
		article.setReduction(Integer.parseInt(request.getParameter("reduction").trim()));
		article.setEtat(Integer.parseInt(request.getParameter("etat").trim()));
		article.setDate(new Date());
		article.setPhoto(request.getParameter("photoarticle").trim());
		article.setVignette(request.getParameter("vignettearticle").trim());  
			
		// On ajoute l'article � la base de donn�es en passant par le mod�le
		codeErreur=modeleArticle.ajouterArticle(article);
		
		// On vide l'objet article par s�curit�
		article=null;
			
		// On test le code de retour pour afficher un message de succ�s ou d'erreur
		if(codeErreur!=1)
		{		
			// On renvoie � la page de liste des articles avec un message d'erreur
			response.sendRedirect("administrationArticles?action=lister&erreurs=" + "Une erreur s'est produite durant l'ajout de l'article, veuillez recommencer");
		}
		else
		{	
			// On renvoie � la page de liste des articles avec un message d'erreur
			response.sendRedirect("administrationArticles?action=lister&succes=" + "L'article � �t� ajout� avec succ�s");
		}
	}
	
	
	// Supprimer un article
	private void supprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleArticle modeleArticle=null;
 		int idArticle=0;
		int codeErreur=0;
	
		try
		{
			// On r�cupert l'id de l'article
			idArticle=Integer.parseInt(request.getParameter("idArticle"));
		}
		catch (Exception e) 
		{
			// Si l'id de l'article pass� en param�tre n'est pas un nombre, on initialise l'id � 0
			idArticle=0;
		}
		
		// On initialise le mod�le
		modeleArticle=new ModeleArticle(this.datasource);
		
		// Sil'article existe on le supprime
		if(modeleArticle.getArticle(idArticle)!=null)
		{
			// On envoie au mod�le l'id de l'article � supprimer, renvoi 1 si l'article a bien �t� supprim�
			codeErreur=modeleArticle.supprimerArticle(idArticle);
			
			// On vide l'id de l'article par s�curit�
			idArticle=0;
			
			// On teste le code de retour pour afficher un message de succes ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie � la page de liste des articles avec un message d'erreur
				response.sendRedirect("administrationArticles?action=lister&erreurs=" + "Une erreur s'est produite durant la suppression de l'article. Veuillez recommencer");
			}
			else
			{	
				// On renvoie � la page de liste des articles avec un message d'erreur
				response.sendRedirect("administrationArticles?action=lister&succes=" + "L'article � �t� supprim� avec succ�s");
			}
		}
		else
		{
			// On renvoie � la page de liste des articles avec un message d'erreur
			response.sendRedirect("administrationArticles?action=lister&erreurs=" + "Cet article n'est pas r�f�renc� dans la base");
		}
	}
	
	
	// Modifier un article
	private void modifier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		int idArticle=0;
		Article article=null;
		List<Categorie> liste=null;
		ModeleCategorie modeleCategorie=null;
		ModeleArticle modeleArticle=null;
		
		try
		{
			// On r�cupert l'id de l'article
			idArticle=Integer.parseInt(request.getParameter("idArticle"));
		}
		catch (Exception e) 
		{
			// Si l'id de l'article pass� en param�tre n'est pas un nombre, on initialise l'id � 0
			idArticle=0;
		}
		
		// On initialise les mod�les
		modeleArticle=new ModeleArticle(datasource);
		modeleCategorie=new ModeleCategorie(datasource);
		
		// On r�cupert l'article correspondant, renvoie null si l'id de l'article n'existe pas dans la base
		article=(Article)modeleArticle.getArticle(idArticle);
		
		// Si l'objet article est null (id non trouv�) on renvoie sur la page de listing avec un message d'erreur
		if(article!=null)
		{
			// On r�cupert la liste des cat�gories depuis le mod�le
			liste=modeleCategorie.listeCategorie();
			
			// On renvoie � la vue l'article � modifi�
			request.setAttribute("article",article);
			
			// On place dans la requ�te la liste des cat�gories
			request.setAttribute("categories", liste);
			
			// On vide les objets par s�curit�
			article=null;
			liste=null;
			
			// On renvoie � la vue de modification d'un article
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifierarticle.jsp").forward(request,response);
		}
		else
		{
			// On renvoie � la page de liste des articles avec un message d'erreur
			response.sendRedirect("administrationArticles?action=lister&erreurs=" + "Cet article n'est pas r�f�renc� dans la base");
		}
	}
	
	
	// Valider la modification d'un article
	private void validerModification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleCategorie modeleCategorie=null;
		List<Categorie> listeCategorie=null;
		List<String> erreurs=null;
		Enumeration parametres=null;
		String parametre=null;
		String nom=null;
		String description=null;
		String prix=null;
		String stock=null;
		String poids=null;
		
		// On r�cupert les informations du formualire sur lesquels on va appliquer une validation
		nom=request.getParameter("nom").trim();
		description=request.getParameter("description").trim();
		prix=request.getParameter("prix").trim();
		stock=request.getParameter("stock").trim();
		poids=request.getParameter("poids").trim();
				
		// On ajoute tous les attributs du formulaire dans la requ�te pour les transf�rer � la vue
		parametres=request.getParameterNames();
		while (parametres.hasMoreElements()) 
		{
			parametre=(String)parametres.nextElement();
		    request.setAttribute(parametre,(String)request.getParameterValues(parametre)[0]);
		}
		
		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();
		
		// On v�rifie que le nom est renseign�
		if(nom.trim().equals("") || nom==null)
		{
			erreurs.add("Le param�tre [nom] est vide");
		}
		else if (nom.trim().length()<5)
		{
			// Test de la longeur du nom (5 caract�re minimum)
			erreurs.add("Le param�tre [nom] doit contenir au moin 5 caract�res");
		}
		else if (!nom.trim().matches("^[\\p{L}\\s\\d-']{5,}$"))
		{
			erreurs.add("Le param�tre [nom] contient une erreur de syntaxe");
		}
		
		// On v�rifie que la description est renseign�
		if(description.trim().equals("") || description==null)
		{
			erreurs.add("Le param�tre [description] est vide");
		}
		else if (description.trim().length()<20)
		{
			erreurs.add("Le param�tre [description] doit contenir au moin 20 caract�res");
		}
		
		// On v�rifie que le prix est renseign�
		if(prix.trim().equals("") || prix==null)
		{
			erreurs.add("Le param�tre [prix] est vide");
		}
		else if(!prix.trim().matches("^(\\d){1,}((\\.){1,1}(\\d){1,2})?$"))
		{
			erreurs.add("Le param�tre [prix] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le stock est renseign�
		if(stock.trim().equals("") || stock==null)
		{
			erreurs.add("Le param�tre [stock] est vide");
		}
		else if(!stock.trim().matches("^(\\d){1,}$"))
		{
			erreurs.add("Le param�tre [stock] doit contenir au moin 1 caract�re num�rique");
		}
		
		// On v�rifie que le poids est renseign�
		if(poids.trim().equals("") || poids == null)
		{
			erreurs.add("Le param�tre [poids] est vide");
		}
		else if(!poids.trim().matches("^(\\d){1,}$"))
		{
			erreurs.add("Le param�tre [poids] doit contenir au moin 1 caract�re num�rique");
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on renvoie � la page de modification
		if(erreurs.size()>0)
		{	
			// On initialise le modele de categorie
			modeleCategorie=new ModeleCategorie(datasource);
			
			// On r�cupert la liste des cat�gories
			listeCategorie=modeleCategorie.listeCategorie();
			
			// On place la liste dans la requ�te
			request.setAttribute("categories", listeCategorie);
			
			// On ajoute la collection d'erreurs
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie � la vue de modification de l'article
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifierarticle.jsp").forward(request,response);
		}
		else
		{
			// On renvoie � la page de de modification de la vignette
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifierphoto.jsp").forward(request,response);
		}
	}
	
	
	
	// Finalisation de la modification de l'article
	private void finaliserModification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		Article article=null;
		ModeleCategorie modeleCategorie=null;
		ModeleArticle modeleArticle=null;
		int codeErreur=0;
			
		// On initialise les mod�les
		modeleArticle=new ModeleArticle(this.datasource);
		modeleCategorie=new ModeleCategorie(this.datasource);
				
		// On initialise l'article � modifier
		article=new Article();
				
		// On d�finit les attributs de l'article avec les informations des deux formulaires pr�c�dent
		article.setId(Integer.parseInt(request.getParameter("id").trim()));
		article.setCategorie(modeleCategorie.getCategorie(Integer.parseInt(request.getParameter("categorie").trim())));
		article.setNom(request.getParameter("nom").trim());
		article.setDescription(request.getParameter("description").trim());
		article.setPoids(Integer.parseInt(request.getParameter("poids").trim()));
		article.setStock(Integer.parseInt(request.getParameter("stock").trim()));
		article.setPrix(Double.parseDouble(request.getParameter("prix").trim()));
		article.setReduction(Integer.parseInt(request.getParameter("reduction").trim()));
		article.setEtat(Integer.parseInt(request.getParameter("etat").trim()));
		article.setDate(new Date());
		article.setPhoto(request.getParameter("photoarticle").trim());
		article.setVignette(request.getParameter("vignettearticle").trim());  
			
		// On modifie l'article dans la base de donn�es en passant par le mod�le
		codeErreur=modeleArticle.modifierArticle(article);
		
		// On vide l'objet article par s�curit�
		article=null;
			
		// On teste le code de retour pour afficher un message de succ�s ou d'erreur
		if(codeErreur!=1)
		{		
			// On renvoie � la page de liste des articles avec un message d'erreur
			response.sendRedirect("administrationArticles?action=lister&erreurs=" + "Une erreur s'est produite durant la modification de l'article, veuillez recommencer");
		}
		else
		{	
			// On renvoie � la page de liste des articles avec un message d'erreur
			response.sendRedirect("administrationArticles?action=lister&succes=" + "L'article � �t� modifi� avec succ�s");
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	
}