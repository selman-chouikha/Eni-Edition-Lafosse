package betaboutique.servlets.administrateur;

import java.util.Enumeration;
import java.util.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.beans.Client;
import betaboutique.modeles.ModeleAdministrateur;
import betaboutique.modeles.ModeleClient;
import betaboutique.modeles.ModeleCommande;
import betaboutique.outils.GestionDroit;
import betaboutique.outils.Hachage;


/**
 * Classe ServletGestionClients
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionClients extends HttpServlet
{
	// Variables de la classe
	DataSource datasource=null;
	Connection connection=null;
	PreparedStatement requete=null;
	ResultSet resultat=null;
	GestionDroit gestionDroit=null;
	
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
			
			// Listing des comptes clients
			if(action.equals("lister"))
			{
				lister(request, response);
			}
			
			// Affiche le formulaire de création d'un compte client
			if(action.equals("ajouter"))
			{	
				ajouter(request, response);
			}		
	
			// Valide le formulaire d'ajout d'un compte client
			if(action.equals("validerajout"))
			{	
				validerAjout(request, response);
			}	
			
			// Consultation d'un compte client
			if (action.equals("consulter"))
			{
				consulter(request, response);
			}
			
			// Affiche le formulaire de modification d'un compte client
			if (action.equals("modifier"))
			{
				modifier(request, response);
			}
			
			// Valide le formulaire de modification d'un compte client
			if (action.equals("validermodification"))
			{
				validerModification(request, response);
			}
			
			// Suppression du client
			if (action.equals("supprimer"))
			{
				supprimer(request, response);
			}
		}
		
		// Vide le datasource
		this.datasource=null;
	}
	
	
	// Lister les clients
	private void lister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		int page=0;
		int maxParPage=0;
		String champTri=null;
		String typeTri=null;
		String recherche=null;
		String typerecherche=null;
		ModeleClient modeleClient=null;
		ArrayList<Client> listeClients=null;
		
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
				
		// Si aucun tri n'est demandé, ou que le tri est incorrect, on réalise un tri sur le nom du client
		if(champTri==null || (!champTri.equals("nomclient") && !champTri.equals("prenomclient") && !champTri.equals("identifiantclient") && !champTri.equals("emailclient") && !champTri.equals("id_client")))
		{
			champTri="id_client";
		}
		
		// Si aucun type de tri n'est demandé, ou que le type de tri est incorrect, on réalise un tri croissant
		if(typeTri==null || (!typeTri.equals("ASC") && !typeTri.equals("DESC")))
		{
			typeTri="ASC";
		}
	
		// On initialise le modèle
		modeleClient=new ModeleClient(datasource);
				
		// On récupert la liste des clients depuis le modèle
		listeClients=(ArrayList<Client>)modeleClient.listeClient(maxParPage, page, recherche,typerecherche, champTri, typeTri);
		
		// On renvoie à la JSP la liste des clients obtenus ainsi que les informations de pagination
		request.setAttribute("listeClients",listeClients);
		request.setAttribute("maxParPage", modeleClient.getMaxParPage());
		request.setAttribute("pageActuel", modeleClient.getPageActuel());
		request.setAttribute("totalElement", modeleClient.getTotalElement());
		request.setAttribute("champTri", modeleClient.getChampTri());
		request.setAttribute("typeTri", modeleClient.getTypeTri());
		request.setAttribute("recherche", modeleClient.getRecherche());
		request.setAttribute("typerecherche", modeleClient.getTyperecherche());
		
		// On renvoie de plus à la vue les messages d'erreurs et ou de succès
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// On vide la liste des clients par sécurité
		listeClients=null;
		
		// On renvoie la de listing des clients
		getServletContext().getRequestDispatcher("/vues/administrateurs/listeclients.jsp").forward(request,response);
	}
	
	
	// Consulter un client
	private void consulter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		ModeleClient modeleClient=null;
		int idClient=0;
		Client client=null;
		
		try
		{
			// On récupert l'id du client
			idClient=Integer.parseInt(request.getParameter("idClient"));
		}
		catch (Exception e) 
		{
			// Si l'id du client passé en paramètre n'est pas un nombre, on initialise l'id à 0
			idClient=0;
		}
		
		// On initialise le modèle
		modeleClient=new ModeleClient(datasource);
		
		// On récupert le client par le modèle, renvoi null si l'id du client n'existe pas dans la base
		client=(Client)modeleClient.getClient(idClient);
		
		// Si l'objet client est null (id non trouvé) on renvoie sur la page de listing avec un message d'erreur
		if(client!=null)
		{
			// On renvoie à la JSP le client demandé
			request.setAttribute("client",client);
			
			// On vide cette l'objet client par sécurité
			client=null;
			
			// On renvoie à la vue de consultation des informations d'un client
			getServletContext().getRequestDispatcher("/vues/administrateurs/consulterclient.jsp").forward(request,response);
		}
		else
		{
			// On renvoie à la page de liste des clients avec un message d'erreur
			response.sendRedirect("administrationClients?action=lister&erreurs=" + "Ce client n'est pas référencé dans la base");
		}
	}
	
	
	
	// Ajouter un client
	private void ajouter(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// Renvoyer vers la page d'ajout d'un compte client
		getServletContext().getRequestDispatcher("/vues/administrateurs/ajouterclient.jsp").forward(request, response);
	}
	
	
	// Valider ajouter un client
	private void validerAjout(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// Varibales
		int codeErreur=0;
		ModeleClient modeleClient=null;
		ModeleAdministrateur modeleAdministrateur=null;
		Enumeration parametres=null;
		String parametre=null;
		List<String> erreurs=null;
		Client client=null;
		String nom=null;
		String prenom=null;
		String mail=null;
		String adresse=null;
		String codePostal=null;
		String ville=null;
		String pays=null;
		String telephone=null;
		String identifiant=null;
		String motDePasse=null;
		String confirmation=null;
		
		// On récupert les éléments de la requête auxquelles ont va appliquér une validation
		nom=request.getParameter("nom").trim();
		prenom=request.getParameter("prenom").trim();
		mail=request.getParameter("mail").trim();
		adresse=request.getParameter("adresse").trim();
		telephone=request.getParameter("telephone").trim();
		codePostal=request.getParameter("codePostal").trim();
		ville=request.getParameter("ville").trim();
		pays=request.getParameter("pays").trim();
		identifiant=request.getParameter("identifiant").trim();
		motDePasse=request.getParameter("motDePasse").trim();
		confirmation=request.getParameter("confirmation").trim();
			
		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();
		
		// Initialisation des modèles
		modeleClient=new ModeleClient(this.datasource);
		modeleAdministrateur=new ModeleAdministrateur(this.datasource);
			
		// On vérifie que le nom est renseigné
		if(nom.equals("") || nom==null)
		{
			erreurs.add("Le paramètre [nom] est vide");
		}
		else if(!nom.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le paramètre [nom] contient une erreur de syntaxe");
		}
			
		// On vérifie que le prénom est renseigné
		if(prenom.equals("") || prenom==null)
		{
			erreurs.add("Le paramètre [prenom] est vide");
		}
		else if(!prenom.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le paramètre [prenom] contient une erreur de syntaxe");
		}
			
		// On vérifie que le mail est renseigné
		if(mail.equals("") || mail==null)
		{
			erreurs.add("Le paramètre [mail] est vide");
		}
		else if(!mail.matches("^[\\d\\w]+([-_.]?[\\d\\w]+)+@[\\d\\w]+([-_.]?[\\w\\d])+\\.[a-z]{2,4}"))
		{
			erreurs.add("Le paramètre [mail] contient une erreur de syntaxe");
		}
						
		// On vérifie que l'adresse est renseignée
		if(adresse.equals("") || adresse==null)
		{
			erreurs.add("Le paramètre [adresse] est vide");
		}
		else if(!adresse.matches("[\\d\\p{L}]+([ '-]?[\\d\\p{L}]+)+"))
		{
			erreurs.add("Le paramètre [adresse] contient une erreur de syntaxe");
		}
		
		// On vérifie que le code postal est renseigné
		if(codePostal.equals("") || codePostal==null)
		{
			erreurs.add("Le paramètre [codePostal] est vide");
		}
		else if(!codePostal.matches("^\\d{5}$"))
		{
			erreurs.add("Le paramètre [codePostal] contient une erreur de syntaxe");
		}
		
		// On vérifie que la ville est renseignée
		if(ville.equals("") || ville==null)
		{
			erreurs.add("Le paramètre [ville] est vide");
		}
		else if(!ville.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le paramètre [ville] contient une erreur de syntaxe");
		}
			
		// On vérifie que l'adresse est renseignée
		if(telephone.equals("") || telephone==null)
		{
			erreurs.add("Le paramètre [telephone] est vide");
		}
		else if(!telephone.matches("^(\\+){1}\\d{2,3}( )?\\d{1}(( )?\\d{2}){4}$") && !telephone.matches("^(0){1}\\d{1}([ \\.]?\\d{2}){4}$"))
		{
			erreurs.add("Le paramètre [telephone] contient une erreur de syntaxe");
		}
		
		// On vérifie que le pays est renseigné
		if(pays.equals("") || pays==null)
		{
			erreurs.add("Le paramètre [pays] est vide");
		}
		else if(!pays.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le paramètre [pays] contient une erreur de syntaxe");
		}
		
		// On vérifie que l'identifiant est renseigné
		if(identifiant.equals("") || identifiant==null)
		{
			erreurs.add("Le paramètre [identifiant] est vide");
		}
		else if(identifiant.length()<5)
		{
			erreurs.add("Le paramètre [identifiant] doit contenir 5 caractères minimum");
		}
		else if(!identifiant.matches("^[\\d\\w]{5,}$"))
		{
			erreurs.add("Le paramètre [identifiant] contient une erreur de syntaxe");
		}
		
		// On vérifie que le mot de passe est renseigné
		if(motDePasse.equals("") || motDePasse==null)
		{
			erreurs.add("Le paramètre [motDePasse] est vide");
		}
		else if(motDePasse.length()<5)
		{
			erreurs.add("Le paramètre [motDePasse] doit contenir 5 caractères minimum");
		}
		else if(!motDePasse.matches("^[\\d\\w]{5,}$"))
		{
			erreurs.add("Le paramètre [motDePasse] contient une erreur de syntaxe");
		}
		
		// On vérifie que la confirmation est renseignée
		if(confirmation.equals("") || confirmation==null)
		{
			erreurs.add("Le paramètre [confirmation] est vide");
		}
				
		// On vérifie que le mot de passe correpond à la confirmation
		if(!motDePasse.equals(confirmation))
		{
			erreurs.add("Le paramètre [confirmation] ne correspond pas au paramètre [motDePasse]");
		}
			
		// On vérifie que l'identifiant utilisé n'existe pas ni chez les clients ou chez les administrateurs
		if(!identifiant.equals("") && (modeleClient.getClient(identifiant)!=null || modeleAdministrateur.getAdministrateur(identifiant)!=null))
		{
			erreurs.add("L'identifiant que vous avez choisi existe déjà");
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on retourne à la page de création d'un compte client
		if(erreurs.size()>0)
		{
			// On ajoute tous les attributs du formulaire dans la requête
			parametres=request.getParameterNames();
			while (parametres.hasMoreElements()) 
			{
				parametre = (String)parametres.nextElement();
			    request.setAttribute(parametre,(String)request.getParameterValues(parametre)[0]);
			}
			
			// On ajoute la collection d'erreurs
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie à la vue de création de compte client
			getServletContext().getRequestDispatcher("/vues/administrateurs/ajouterclient.jsp").forward(request,response);
		}
		// Si aucune erreur, on ajoute le client à la base de données
		else
		{
			// Initialisation du client
			client=new Client();
			
			// On définit les attributs du client
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setMail(mail);
			client.setAdresse(adresse);
			client.setCodePostal(codePostal);
			client.setVille(ville);
			client.setTelephone(telephone);
			client.setPays(pays);
			client.setIdentifiant(identifiant);
			client.setMotDePasse(Hachage.SHA1(motDePasse));
			
			// On ajoute le client à la base de données, renvoi 1 si l'ajout s'est déroulé avec succès
			codeErreur=modeleClient.creerClient(client);
			
			// On teste le code de retour pour afficher un message de succès ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie à la page de listing des clients avec un message d'erreur
				response.sendRedirect("administrationClients?action=lister&erreurs=" + "Une erreur s'est produite durant la création d'un compte client. Veuillez recommencer");
			}
			else
			{	
				// On renvoie à la page de listing des clients avec un message d'erreur
				response.sendRedirect("administrationClients?action=lister&succes=" + "Le compte client à été ajouté avec succès.");
			}
		}
	}
	
	
	
	// Modifier un client
	private void modifier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Variables
		int idClient=0;
		Client client=null;
		ModeleClient modeleClient=null;
	
		try
		{
			// On récupert l'id du compte client à modifier
			idClient=Integer.parseInt(request.getParameter("idClient"));
		}
		catch (Exception e) 
		{
			// Si l'id du client passé en paramètre n'est pas un nombre, on initialise l'id à 0
			idClient=0;
		}
			
		// On initialise le modèle
		modeleClient=new ModeleClient(datasource);
		
		// On récupert le client à modifier, renvoi null si l'id du client n'existe pas dans la base
		client=(Client)modeleClient.getClient(idClient);
		
		// Si l'objet client est null (id non trouvé) on renvoie sur la page de listing avec un message d'erreur
		if(client!=null)
		{
			// On renvoie à la vue le client à modifier
			request.setAttribute("client",client);
						
			// On vide les objets par sécurité
			client=null;
			
			// On renvoie à la vue de modification d'un client
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifierclient.jsp").forward(request,response);
		}
		else
		{
			// On renvoie à la page de listing des clients avec un message d'erreur
			response.sendRedirect("administrationClients?action=lister&erreurs=" + "Ce client n'est pas référencé dans la base");
		}
	}
	
	
	
	// Valider modifier un client
	private void validerModification(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// Variables
		int codeErreur=0;
		ModeleClient modeleClient=null;
		Enumeration parametres=null;
		String parametre=null;
		List<String> erreurs=null;
		Client client=null;
		String id=null;
		String nom=null;
		String prenom=null;
		String mail=null;
		String adresse=null;
		String codePostal=null;
		String ville=null;
		String pays=null;
		String telephone=null;
		String motDePasse=null;
		String confirmation=null;
		
		// On récupert les éléments de requêtes auxquels ont appliquera une validation
		id=request.getParameter("id").trim();
		nom=request.getParameter("nom").trim();
		prenom=request.getParameter("prenom").trim();
		mail=request.getParameter("mail").trim();
		adresse=request.getParameter("adresse").trim();
		telephone=request.getParameter("telephone").trim();
		codePostal=request.getParameter("codePostal").trim();
		ville=request.getParameter("ville").trim();
		pays=request.getParameter("pays").trim();
		motDePasse=request.getParameter("motDePasse").trim();
		confirmation=request.getParameter("confirmation").trim();
		
		// Initialisation de la collection d'erreurs
		erreurs=new ArrayList<String>();
		
		// Initialisation du modèle
		modeleClient=new ModeleClient(this.datasource);
			
		// On vérifie que le nom est renseigné
		if(nom.equals("") || nom==null)
		{
			erreurs.add("Le paramètre [nom] est vide");
		}
		else if(!nom.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le paramètre [nom] contient une erreur de syntaxe");
		}
			
		// On vérifie que le prénom est renseigné
		if(prenom.equals("") || prenom==null)
		{
			erreurs.add("Le paramètre [prenom] est vide");
		}
		else if(!prenom.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le paramètre [prenom] contient une erreur de syntaxe");
		}
			
		// On vérifie que le mail est renseigné
		if(mail.equals("") || mail==null)
		{
			erreurs.add("Le paramètre [mail] est vide");
		}
		else if(!mail.matches("^[\\d\\w]+([-_.]?[\\d\\w]+)+@[\\d\\w]+([-_.]?[\\w\\d])+\\.[a-z]{2,4}"))
		{
			erreurs.add("Le paramètre [mail] contient une erreur de syntaxe");
		}
						
		// On vérifie que l'adresse est renseignée 
		if(adresse.equals("") || adresse==null)
		{
			erreurs.add("Le paramètre [adresse] est vide");
		}
		else if(!adresse.matches("[\\d\\p{L}]+([ '-]?[\\d\\p{L}]+)+"))
		{
			erreurs.add("Le paramètre [adresse] contient une erreur de syntaxe");
		}
		
		// On vérifie que le code postal est renseigné
		if(codePostal.equals("") || codePostal==null)
		{
			erreurs.add("Le paramètre [codePostal] est vide");
		}
		else if(!codePostal.matches("^\\d{5}$"))
		{
			erreurs.add("Le paramètre [codePostal] contient une erreur de syntaxe");
		}
		
		// On vérifie que la ville est renseignée 
		if(ville.equals("") || ville==null)
		{
			erreurs.add("Le paramètre [ville] est vide");
		}
		else if(!ville.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le paramètre [ville] contient une erreur de syntaxe");
		}
			
		// On vérifie que l'adresse est renseignée
		if(telephone.equals("") || telephone==null)
		{
			erreurs.add("Le paramètre [telephone] est vide");
		}
		else if(!telephone.matches("^(\\+){1}\\d{2,3}( )?\\d{1}(( )?\\d{2}){4}$") && !telephone.matches("^(0){1}\\d{1}([ \\.]?\\d{2}){4}$"))
		{
			erreurs.add("Le paramètre [telephone] contient une erreur de syntaxe");
		}
		
		// On vérifie que le pays est renseigné 
		if(pays.equals("") || pays==null)
		{
			erreurs.add("Le paramètre [pays] est vide");
		}
		else if(!pays.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le paramètre [pays] contient une erreur de syntaxe");
		}
		
		// On vérifie que le mot de passe est renseigné 
		if(motDePasse.equals("") || motDePasse==null)
		{
			erreurs.add("Le paramètre [motDePasse] est vide");
		}
		else if(motDePasse.length() < 5)
		{
			erreurs.add("Le paramètre [motDePasse] doit contenir 5 caractères minimum");
		}
		else if(!motDePasse.matches("^[\\d\\w]{5,}$") && !motDePasse.equals("**********"))
		{
			erreurs.add("Le paramètre [motDePasse] contient une erreur de syntaxe");
		}
		
		// On vérifie que la confirmation est renseigné 
		if(confirmation.equals("") || confirmation == null)
		{
			erreurs.add("Le paramètre [confirmation] est vide");
		}
				
		// On vérifie que le mot de passe correpond à la confirmation 
		if(!motDePasse.equals(confirmation))
		{
			erreurs.add("Le paramètre [confirmation] ne correspond pas au paramètre [motDePasse]");
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on retourne à la page de modification d'un compte client 
		if(erreurs.size()>0)
		{
			// On ajoute tous les attributs du formulaire dans la requête
			parametres=request.getParameterNames();
			while (parametres.hasMoreElements()) 
			{
				parametre=(String)parametres.nextElement();
			    request.setAttribute(parametre,(String)request.getParameterValues(parametre)[0]);
			}
			
			// On ajoute la collection d'erreurs 
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie à la vue de modification de compte 
			getServletContext().getRequestDispatcher("/vues/administrateurs/modifierclient.jsp").forward(request,response);
		}
		// Si aucune erreur, on modifie le client dans la base de données 
		else
		{
			// Initialisation du client 
			client=new Client();
			
			// On définit les attributs du client
			client.setId(Integer.parseInt(id));
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setMail(mail);
			client.setAdresse(adresse);
			client.setCodePostal(codePostal);
			client.setVille(ville);
			client.setTelephone(telephone);
			client.setPays(pays);
			client.setIdentifiant(modeleClient.getClient(Integer.parseInt(id)).getIdentifiant());
			
			// Si le mot de passe du compte client à été changé on va le mettre à jour (on le Hash) 
			if(!motDePasse.equals("**********"))
			{
				client.setMotDePasse(Hachage.SHA1(motDePasse));
			}
			// Si le mot de passe du compte client n'a pas été modifié, on récupert le mot de passe par le modèle 
			else
			{
				client.setMotDePasse(modeleClient.getClient(Integer.parseInt(id)).getMotDePasse());
			}
			
			// On modifie le client dans la base de données, renvoi 1 si la modification s'est déroulée avec succès 
			codeErreur=modeleClient.modifierClient(client);
			
			// On teste le code de retour pour afficher un message de succès ou d'erreur 
			if(codeErreur!=1)
			{		
				// On renvoie à la page de listing des compte clients avec un message d'erreur 
				response.sendRedirect("administrationClients?action=lister&erreurs=" + "Une erreur s'est produite durant la modification des informations d'un compte client. Veuillez recommencer");
			}
			else
			{	
				// On renvoie à la page de listing des compte clients avec un message de succès 
				response.sendRedirect("administrationClients?action=lister&succes=" + "La modification du compte client a été réalisée avec succès");
			}
		}
	}
	
	
	
	// Supprimer un client
	private void supprimer(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// Variables 
		ModeleClient modeleClient=null;
		ModeleCommande modeleCommande=null;
 		int idClient=0;
		int codeErreur=0;
	
		try
		{
			// On récupert l'id du client
			idClient=Integer.parseInt(request.getParameter("idClient"));
		}
		catch (Exception e) 
		{
			// Si l'id du client passé en paramètre n'est pas un nombre, on initialise l'id à 0 
			idClient=0;
		}
		
		// On initialise les modèles 
		modeleClient=new ModeleClient(this.datasource);
		modeleCommande=new ModeleCommande(this.datasource);
		
		// On teste si le client existe 
		if(modeleClient.getClient(idClient)!=null)
		{
			// Si le client possède des commande en cours, on ne peut le supprimer 
			if(modeleCommande.getNombreCommandeEnAttente(idClient) == 0)
			{
				// On envoie au modèle l'id du client à supprimer, renvoi 1 si le client a bien été supprimé 
				codeErreur=modeleClient.supprimerClient(idClient);
				
				// On vide l'id du client par sécurité 
				idClient=0;
				
				// On teste le code de retour pour afficher un message de succès ou d'erreur 
				if(codeErreur!=1)
				{		
					// On renvoie à la page de liste des clients avec un message d'erreur 
					response.sendRedirect("administrationClients?action=lister&erreurs=" + "Une erreur s'est produite durant la suppression de ce compte client. Veuillez recommencer");
				}
				else
				{	
					// On renvoie à la page de liste des clients avec un message d'erreur 
					response.sendRedirect("administrationClients?action=lister&succes=" + "Le compte client à été supprimé avec succès");
				}
			}
			else
			{
				// On renvoie à la page de liste des clients avec un message d'erreur 
				response.sendRedirect("administrationClients?action=lister&erreurs=" + "Ce client possède des commandes en cours de traitement. Vous ne pouvez le supprimer");
			}
		}
		else
		{
			// On renvoie à la page de liste des clients avec un message d'erreur 
			response.sendRedirect("administrationClients?action=lister&erreurs=" + "Ce client n'est pas référencé dans la base");
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		doGet(request, response);
	}
	
}