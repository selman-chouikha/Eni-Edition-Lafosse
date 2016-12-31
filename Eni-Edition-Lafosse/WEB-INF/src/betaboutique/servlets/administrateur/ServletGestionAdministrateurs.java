package betaboutique.servlets.administrateur;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.modeles.ModeleAdministrateur;
import betaboutique.modeles.ModeleClient;
import betaboutique.outils.GestionDroit;
import betaboutique.outils.Hachage;
import betaboutique.beans.Administrateur;

/**
 * Classe ServletGestionAdministrateurs
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionAdministrateurs extends HttpServlet 
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
			// R�cup�rer la datasource du context de la servlet
			datasource=(DataSource)getServletContext().getAttribute("datasource");
			
			// Action a r�aliser
			String action=(String)request.getParameter("action");
			
			// Action par d�faut (liste)
			if(action==null || action.equalsIgnoreCase(""))
			{
				action="lister";
			}
			
			// Listing des administrateurs
			if(action.equals("lister"))
			{
				lister(request,response);
			}
			
			// Consulter un compte administrateur
			if(action.equals("consulter"))
			{
				consulter(request,response);
			}
			
			// Suppression d'un compte administrateur
			if(action.equals("supprimer"))
			{
				supprimer(request, response);
			}
			
			// Formulaire de modification d'un administrateur
			if(action.equals("modifier"))
			{
				modifier(request, response);
			}
			
			// Validation du formulaire de modification
			if(action.equals("validermodification"))
			{
				validerModification(request, response);
			}
		
			// Formulaire d'ajout d'un administrateur
			if(action.equals("ajouter"))
			{
				ajouter(request,response);
			}
			
			// Validation du formulaire de modification
			if(action.equals("validerajout"))
			{
				validerAjout(request, response);
			}
		}
		else
		{
			getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
		}	
		
		// Vider le datasource
		this.datasource=null;
	}
	
	
	
	// Ajouter un administrateur
	private void ajouter(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// Renvoi vers la page d'ajout de compte
		getServletContext().getRequestDispatcher("/vues/administrateurs/ajouteradministrateur.jsp").forward(request, response);
	}
	
	
	
	//Valider un ajout d'administrateur
	private void validerAjout(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// Variables
		int codeErreur=0;
		ModeleAdministrateur modeleAdministrateur=null;
		ModeleClient modeleClient=null;
		Enumeration parametres=null;
		String parametre=null;
		List<String> erreurs=null;
		Administrateur administrateur=null;
		String nom=null;
		String prenom=null;
		String mail=null;
		String identifiant=null;
		String motDePasse=null;
		String confirmation=null;
		
		// On r�cupert les �l�ments de requ�tes sur lequels on applique une validation
		nom=request.getParameter("nom").trim();
		prenom=request.getParameter("prenom").trim();
		mail=request.getParameter("mail").trim();
		identifiant=request.getParameter("identifiant").trim();
		motDePasse=request.getParameter("motDePasse").trim();
		confirmation=request.getParameter("confirmation").trim();
	
		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();

		// Initialisation des mod�les
		modeleClient=new ModeleClient(this.datasource);
		modeleAdministrateur=new ModeleAdministrateur(this.datasource);
		
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
			
		// On v�rifie que le mail est renseign�
		if(mail.equals("") || mail==null)
		{
			erreurs.add("Le param�tre [mail] est vide");
		}
		else if(!mail.matches("^[\\d\\w]+([-_.]?[\\d\\w]+)+@[\\d\\w]+([-_.]?[\\w\\d])+\\.[a-z]{2,4}"))
		{
			erreurs.add("Le param�tre [mail] contient une erreur de syntaxe");
		}
						
		// On v�rifie que l'identifiant est renseign�
		if(identifiant.equals("") || identifiant==null)
		{
			erreurs.add("Le param�tre [identifiant] est vide");
		}
		else if(identifiant.length() < 5)
		{
			erreurs.add("Le param�tre [identifiant] doit contenir 5 caract�res minimum");
		}
		else if(!identifiant.matches("^[\\d\\w]{5,}$"))
		{
			erreurs.add("Le param�tre [identifiant] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le mot de passe est renseign�
		if(motDePasse.equals("") || motDePasse==null)
		{
			erreurs.add("Le param�tre [motDePasse] est vide");
		}
		else if(motDePasse.length() < 5)
		{
			erreurs.add("Le param�tre [motDePasse] doit contenir 5 caract�res minimum");
		}
		else if(!motDePasse.matches("^[\\d\\w]{5,}$"))
		{
			erreurs.add("Le param�tre [motDePasse] contient une erreur de syntaxe");
		}
		
		// On v�rifie que la confirmation est renseign�
		if(confirmation.equals("") || confirmation==null)
		{
			erreurs.add("Le param�tre [confirmation] est vide");
		}
				
		// On v�rifie que le mot de passe correpond � la confirmation
		if(!motDePasse.equals(confirmation))
		{
			erreurs.add("Le param�tre [confirmation] ne correspond pas au param�tre [motDePasse]");
		}
			
		// On v�rifie que l'identifiant utilis� n'existe pas ni chez les clients ou chez les administrateurs
		if(!identifiant.equals("") && (modeleClient.getClient(identifiant)!=null || modeleAdministrateur.getAdministrateur(identifiant)!=null))
		{
			erreurs.add("L'identifiant que vous avez choisi existe d�j�");
		}
		
		// On regarde le nombre d'erreur, s'il y en a au moins une on retourne � la page de cr�ation d'un compte administrateur
		if(erreurs.size() > 0)
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
			
			// On renvoie � la vue de cr�ation d'un compte administrateur
			getServletContext().getRequestDispatcher("/vues/administrateurs/ajouteradministrateur.jsp").forward(request,response);
		}
		// Si aucune erreur, on ajoute l'administrateur � la base de donn�es
		else
		{
			// Initialisation de l'objet administrateur
			administrateur=new Administrateur();
			
			// On d�finit les attributs de l'administrateur
			administrateur.setNom(nom);
			administrateur.setPrenom(prenom);
			administrateur.setMail(mail);
			administrateur.setIdentifiant(identifiant);
			administrateur.setMotDePasse(Hachage.SHA1(motDePasse));
			
			// On ajoute l'administrateur � la base de donn�es, renvoi 1 si l'ajout s'est d�roul� avec succ�s
			codeErreur=modeleAdministrateur.creerAdministrateur(administrateur);
			
			// On test le code de retour pour afficher un message de succes ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie � la page d'index utilisateur avec un message d'erreur
				response.sendRedirect("administrationAdministrateurs?action=lister&erreurs=" + "Une erreur s'est produite durant la cr�ation d'un compte administrateur. Veuillez recommencer");
			}
			else
			{	
				// On renvoie � la page d'index utilisateur avec un message d'erreur
				response.sendRedirect("administrationAdministrateurs?action=lister&succes=" + "Le compte administrateur � �t� cr�� avec succ�s.");
			}
		}
	}
	
	
	// Lister les administrateurs
	private void lister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		int page=0;
		int maxParPage=0;
		String champTri=null;
		String typeTri=null;
		String recherche=null;
		String typerecherche=null;
		ModeleAdministrateur modeleAdministrateur=null;
		ArrayList<Administrateur> listeAdministrateurs=null;
		
		try 
		{
			// On r�cupert la page demand�e
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
				
		// Si aucun tri n'est demand�, ou que le tri est incorrect, on r�alise un tri le nom du client
		if(champTri==null || (!champTri.equals("nomadministrateur") && !champTri.equals("prenomadministrateur") && !champTri.equals("mailadministrateur") && !champTri.equals("identifiantadministrateur") && !champTri.equals("id_administrateur")))
		{
			champTri="id_administrateur";
		}
		
		// Si aucun type de tri n'est demand�, ou que le type de tri est incorrect, on r�alise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}
	
		// On initialise le mod�le
		modeleAdministrateur=new ModeleAdministrateur(datasource);
				
		// On r�cupert la liste des administrateurs depuis le mod�le
		listeAdministrateurs=(ArrayList<Administrateur>)modeleAdministrateur.listerAdministrateur(maxParPage, page, recherche,typerecherche, champTri, typeTri);
		
		// On renvoie � la JSP la liste des Administrateurs obtenus ainsi que les informations de pagination
		request.setAttribute("listeAdministrateurs",listeAdministrateurs);
		request.setAttribute("maxParPage", modeleAdministrateur.getMaxParPage());
		request.setAttribute("pageActuel", modeleAdministrateur.getPageActuel());
		request.setAttribute("totalElement", modeleAdministrateur.getTotalElement());
		request.setAttribute("champTri", modeleAdministrateur.getChampTri());
		request.setAttribute("typeTri", modeleAdministrateur.getTypeTri());
		request.setAttribute("recherche", modeleAdministrateur.getRecherche());
		request.setAttribute("typerecherche", modeleAdministrateur.getTyperecherche());
		
		// On renvoie � la vue les messages d'erreurs et ou de succ�s
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// On vide la liste d'administrateurs par s�curit�
		listeAdministrateurs=null;
		
		// On renvoie la de listing des Administrateurs
		getServletContext().getRequestDispatcher("/vues/administrateurs/listeadministrateurs.jsp").forward(request,response);
	}
	
	
	
	// Consulter un administrateur
	private void consulter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleAdministrateur modeleAdministrateur=null;
		int idAdministrateur=0;
		Administrateur administrateur=null;
		
		try
		{
			// On r�cupert l'id de l'administrateur
			idAdministrateur=Integer.parseInt(request.getParameter("idAdministrateur"));
		}
		catch (Exception e) 
		{
			// Si l'id de l'administrateur pass� en param�tre n'est pas un nombre, on initialise l'id � 0
			idAdministrateur=0;
		}
		
		// On initialise le mod�le
		modeleAdministrateur=new ModeleAdministrateur(datasource);
		
		// On r�cupert l'administrateur � partir du mod�le, renvoi null si l'id de l'administrateur n'existe pas dans la base
		administrateur=(Administrateur)modeleAdministrateur.getAdministrateur(idAdministrateur);
		
		// Si l'administrateur existe on renvoie sur la page de consultation
		if(administrateur!=null)
		{
			// On renvoie � la JSP l'administrateur demand�
			request.setAttribute("administrateur",administrateur);
			
			// On vide l'objet administrateur par s�curit�
			administrateur=null;
			
			// On renvoie � la vue de les informations de l'administrateur
			getServletContext().getRequestDispatcher("/vues/administrateurs/consulteradministrateur.jsp").forward(request,response);
		}
		// Si l'objet administrateur est null (id non trouv�) on renvoie sur la page de listing avec un message d'erreur
		else
		{
			// On renvoie � la page de liste des administrateurs avec un message d'erreur
			response.sendRedirect("administrationAdministrateurs?action=lister&erreurs=" + "Cet Administrateur n'est pas r�f�renc� dans la base");
		}
	}
	
	
	// Supprimer un administrateur
	private void supprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleAdministrateur modeleAdministrateur=null;
 		int idAdministrateur=0;
		int codeErreur=0;
	
		// On initialise le mod�le
		modeleAdministrateur=new ModeleAdministrateur(this.datasource);
		
		try
		{
			// On r�cupert l'id de l'administrateur
			idAdministrateur=Integer.parseInt(request.getParameter("idAdministrateur"));
		}
		catch (Exception e) 
		{
			// Si l'id de l'administrateur pass� en param�tre n'est pas un nombre, on initialise l'id � 0
			idAdministrateur=0;
		}
		
		// Si l'id est sup�rieur � 0 on supprime l'objet de la base de donn�es
		if(modeleAdministrateur.getAdministrateur(idAdministrateur) != null)
		{
			// On envoie au mod�le l'id de l'Administrateur � supprimer, renvoi 1 si l'Administrateur a bien �t� supprim�
			codeErreur=modeleAdministrateur.supprimerAdministrateur(idAdministrateur);
			
			// On teste le code de retour pour afficher un message de succes ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie � la page de listing des administrateurs avec un message d'erreur
				response.sendRedirect("administrationAdministrateurs?action=lister&erreurs=" + "Une erreur s'est produite durant la suppression de l'administrateur, veuillez recommencer");
			}
			else
			{	
				// On renvoie � la page de listing des administrateurs avec un message d'erreur
				response.sendRedirect("administrationAdministrateurs?action=lister&succes=" + "L'administrateur � �t� supprim� avec succ�s");
			}
		}
		else
		{
			// On renvoie � la page de listing des administrateurs avec un message d'erreur
			response.sendRedirect("administrationAdministrateurs?action=lister&erreurs=" + "Cet aministrateur n'est pas r�f�renc� dans la base");
		}
	}
	
	
	// Modifier un administrateur
	private void modifier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		int idAdministrateur=0;
		Administrateur administrateur=null;
		ModeleAdministrateur modeleAdministrateur=null;
	
		try
		{
			// On r�cupert l'id de l'administrateur
			idAdministrateur=Integer.parseInt(request.getParameter("idAdministrateur"));
		}
		catch (Exception e) 
		{
			//* Si l'id de l'administrateur pass� en param�tre n'est pas un nombre, on initialise l'id � 0
			idAdministrateur=0;
		}
			
		// On initialise le mod�le
		modeleAdministrateur=new ModeleAdministrateur(datasource);
		
		// On r�cupert l'administrateur depuis le mod�le, renvoi null si l'id de administrateur n'existe pas dans la base
		administrateur=modeleAdministrateur.getAdministrateur(idAdministrateur);
		
		// Si l'objet administrateur est null (id non trouv�) on renvoie sur la page de listing avec un message d'erreur
		if(administrateur!=null)
		{
			// On renvoie � la vue l'administrateur � modifier
			request.setAttribute("administrateur",administrateur);
			
			// On vide les objets par s�curit�
			administrateur=null;
			
			// On renvoie � la vue de modification d'un administrateur
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifieradministrateur.jsp").forward(request,response);
		}
		else
		{
			// On renvoie � la vue de listing des administrateurs avec un message d'erreur
			response.sendRedirect("administrationAdministrateurs?action=lister&erreurs=" + "Cet administrateur n'est pas r�f�renc� dans la base");
		}
	}
	
	
	// Valider modifier
	private void validerModification(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// Varibales
		int codeErreur=0;
		ModeleAdministrateur modeleAdministrateur=null;
		Enumeration parametres=null;
		String parametre=null;
		List<String> erreurs=null;
		Administrateur administrateur=null;
		String id=null;
		String nom=null;
		String prenom=null;
		String mail=null;
		String motDePasse=null;
		String confirmation=null;
		
		// On r�cupert les �l�ments de requ�tes auxquels ont applique une validation
		id=request.getParameter("id").trim();
		nom=request.getParameter("nom").trim();
		prenom=request.getParameter("prenom").trim();
		mail=request.getParameter("mail").trim();
		motDePasse=request.getParameter("motDePasse").trim();
		confirmation=request.getParameter("confirmation").trim();
		
		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();
		
		// Initialisation du mod�le
		modeleAdministrateur=new ModeleAdministrateur(this.datasource);
			
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
		
		// On v�rifie que le mail est renseign�
		if(mail.equals("") || mail==null)
		{
			erreurs.add("Le param�tre [mail] est vide");
		}
		else if(!mail.matches("^[\\d\\w]+([-_.]?[\\d\\w]+)+@[\\d\\w]+([-_.]?[\\w\\d])+\\.[a-z]{2,4}"))
		{
			erreurs.add("Le param�tre [mail] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le mot de passe est renseign�
		if(motDePasse.equals("") || motDePasse==null)
		{
			erreurs.add("Le param�tre [motDePasse] est vide");
		}
		else if(motDePasse.length() < 5)
		{
			erreurs.add("Le param�tre [motDePasse] doit contenir 5 caract�res minimum");
		}
		else if(!motDePasse.matches("^[\\d\\w]{5,}$") && !motDePasse.equals("**********"))
		{
			erreurs.add("Le param�tre [motDePasse] contient une erreur de syntaxe");
		}
		
		// On v�rifie que la confirmation est renseign�
		if(confirmation.equals("") || confirmation==null)
		{
			erreurs.add("Le param�tre [confirmation] est vide");
		}
				
		// On v�rifie que le mot de passe correponde � la confirmation
		if(!motDePasse.equals(confirmation))
		{
			erreurs.add("Le param�tre [confirmation] ne correspond pas au param�tre [motDePasse]");
		}
		
		// On regarde le nombre d'erreur, s'il y en a au moins une on retourne � la page de modification d'un administrateur
		if(erreurs.size() > 0)
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
			
			// On renvoie � la vue de modification d'un administrateur
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifieradministrateur.jsp").forward(request,response);
		}
		// Si aucune erreur, on modifie l'administrateur dans la base de donn�es
		else
		{
			// Initialisation de l'administrateur
			administrateur=new Administrateur();
			
			// On d�finit les attributs du client
			administrateur.setId(Integer.parseInt(id));
			administrateur.setNom(nom);
			administrateur.setPrenom(prenom);
			administrateur.setMail(mail);
			administrateur.setIdentifiant(modeleAdministrateur.getAdministrateur(Integer.parseInt(id)).getIdentifiant());
			
			// Si le mot de passe du compte administrateur � chang� on va l'enregistrer, donc on le Hash
			if(!motDePasse.equals("**********"))
			{
				administrateur.setMotDePasse(Hachage.SHA1(motDePasse));
			}
			// Si le mot de passe du compte administrateur n'a pas �t� modifi� on r�cupert le mot de passe du compte administrateur
			else
			{
				administrateur.setMotDePasse(modeleAdministrateur.getAdministrateur(Integer.parseInt(id)).getMotDePasse());
			}
			
			// On modifie l'administrateur dans la base de donn�es, renvoie 1 si la modification s'est d�roul� avec succ�s
			codeErreur=modeleAdministrateur.modifierAdministrateur(administrateur);
			
			// On teste le code de retour pour afficher un message de succes ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie � la page de listing des administrateurs avec un message d'erreur
				response.sendRedirect("administrationAdministrateurs?action=lister&erreurs=" + "Une erreur s'est produite durant la modification des informations d'un compte administrateur. Veuillez recommencer");
			}
			else
			{	
				// On renvoie � la page de listing des administrateurs avec un message de succes
				response.sendRedirect("administrationAdministrateurs?action=lister&succes=" + "Modification des informations du compte administrateur avec succ�s");
			}
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
}