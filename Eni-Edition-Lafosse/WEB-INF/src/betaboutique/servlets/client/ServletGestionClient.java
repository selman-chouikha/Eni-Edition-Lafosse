package betaboutique.servlets.client;

import java.util.Enumeration;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.beans.Client;
import betaboutique.modeles.ModeleAdministrateur;
import betaboutique.modeles.ModeleClient;
import betaboutique.outils.GestionDroit;
import betaboutique.outils.Hachage;

/**
 * Classe ServletGestionClient
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletGestionClient extends HttpServlet
{
	// Variables
	private DataSource datasource=null;
	private GestionDroit gestionDroit=null;
	
	// Traitements
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Initialisation de la gestion des droits
		gestionDroit=new GestionDroit(request);
		
		// R�cup�rer l'objet datasource du context de la servlet
		datasource=(DataSource)getServletContext().getAttribute("datasource");

		// Action � r�aliser
		String action=(String)request.getParameter("action");
		
		// Action par d�faut (liste)
		if(action==null || action.equalsIgnoreCase(""))
		{
			action="ajouter";
		}
		
		// Affiche le formulaire de cr�ation d'un compte
		if(action.equals("ajouter"))
		{	
			// On autorise uniquement les utilisateurs anonymes 
			if(gestionDroit.estAutorise(false,false,true))
			{
				ajouterCompte(request, response);
			}
			else
			{
				getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
			}
		}		

		// Valider le formulaire de cr�ation d'un compte client
		if(action.equals("validerajout"))
		{	
			// On autorise uniquement les utilisateurs anonymes
			if(gestionDroit.estAutorise(true,false,true))
			{
				validerAjoutCompte(request, response);
			}
			else
			{
				getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
			}
		}	
		
		// Consultation d'un compte
		if(action.equals("consulter"))
		{
			// On autorise uniquement les clients
			if(gestionDroit.estAutorise(false,true,false))
			{
				consulterCompte(request, response);
			}
			else
			{
				getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
			}
		}
		
		// Affiche le formulaire de modification d'un compte
		if (action.equals("modifier"))
		{
			// On autorise uniquement les clients
			if(gestionDroit.estAutorise(false,true,false))
			{
				modifierCompte(request,response);
			}
			else
			{
				getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
			}
		}
		
		// Affiche le formulaire de modification d'un compte
		if (action.equals("validermodification"))
		{
			// On autorise uniquement les clients
			if(gestionDroit.estAutorise(false,true,false))
			{
				validerModificationCompte(request,response);
			}
			else
			{
				getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
			}
		}
	}
	
	
	
	
	// Consulter un compte
	private void consulterCompte(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// On renvoie de plus � la vue les messages d'erreurs et de succ�s
		request.setAttribute("succes",(String)request.getParameter("succes"));
		request.setAttribute("erreurs",(String)request.getParameter("erreurs"));
		
		// Renvoie vers la page de consultation de compte
		getServletContext().getRequestDispatcher("/vues/utilisateurs/consultercompte.jsp").forward(request, response);
	}
	
	
	// Ajouter un compte
	private void ajouterCompte(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// Le client ne doit pas pouvoir cr�er un compte une fois authentifi� 
		if(request.getSession().getAttribute("compte") != null)
		{
			// Renvoi vers la consultation du compte du client
			getServletContext().getRequestDispatcher("/gestionClient?action=consulter").forward(request, response);
		}
		else
		{
			// Renvoi vers la page de cr�ation de compte
			getServletContext().getRequestDispatcher("/vues/utilisateurs/ajoutercompte.jsp").forward(request, response);
		}
	}
	
	
	// Validater ajouter compte
	private void validerAjoutCompte(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// Variables
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
		
		// On r�cupert les �l�ments de requ�tes
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
			
		// On v�rifie que l'adresse est renseign�e
		if(telephone.equals("") || telephone==null)
		{
			erreurs.add("Le param�tre [telephone] est vide");
		}
		else if(!telephone.matches("^(\\+){1}\\d{2,3}( )?\\d{1}(( )?\\d{2}){4}$") && !telephone.matches("^(0){1}\\d{1}([ \\.]?\\d{2}){4}$"))
		{
			erreurs.add("Le param�tre [telephone] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le pays est renseign�
		if(pays.equals("") || pays==null)
		{
			erreurs.add("Le param�tre [pays] est vide");
		}
		else if(!pays.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le param�tre [pays] contient une erreur de syntaxe");
		}
		
		// On v�rifie que l'identifiant est renseign�
		if(identifiant.equals("") || identifiant==null)
		{
			erreurs.add("Le param�tre [identifiant] est vide");
		}
		else if(identifiant.length()<5)
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
		else if(motDePasse.length()<5)
		{
			erreurs.add("Le param�tre [motDePasse] doit contenir 5 caract�res minimum");
		}
		else if(!motDePasse.matches("^[\\d\\w]{5,}$"))
		{
			erreurs.add("Le param�tre [motDePasse] contient une erreur de syntaxe");
		}
		
		// On v�rifie que la confirmation est renseign�e
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
		if(!identifiant.equals("") && (modeleClient.getClient(identifiant) != null || modeleAdministrateur.getAdministrateur(identifiant)!= null))
		{
			erreurs.add("L'identifiant que vous avez choisi existe d�j�");
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on retourne � la page de cr�ation de compte
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
			
			// On renvoie � la vue de cr�ation de compte
			getServletContext().getRequestDispatcher("/vues/utilisateurs/ajoutercompte.jsp").forward(request,response);
		}
		// Si aucune erreur, on ajoute le client � la base de donn�es
		else
		{
			// Initialisation du client
			client=new Client();
			
			// On d�finit les attributs du client
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
			
			// On ajoute le client � la base de donn�es, renvoi 1 si l'ajout s'est d�roul� avec succ�s
			codeErreur=modeleClient.creerClient(client);
			
			// On teste le code de retour pour afficher un message de succ�s ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie � la page d'index utilisateur avec un message d'erreur
				response.sendRedirect("pageFixe?action=index&erreurs=" + "Une erreur s'est produite durant la cr�ation de votre compte. Veuillez recommencer");
			}
			else
			{	
				// On renvoie � la page d'index utilisateur avec un message d'erreur
				response.sendRedirect("pageFixe?action=index&succes=" + "Votre compte � �t� cr�� avec succ�s. Vous pouvez maintenant vous identifier");
			}
		}
	}
	
	
	
	// Modifier un compte
	private void modifierCompte(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		// On renvoie le client contenu dans la session dans la requ�te
		request.setAttribute("client", request.getSession().getAttribute("compte"));
		
		// Renvoie vers la page de modification de compte
		getServletContext().getRequestDispatcher("/vues/utilisateurs/modifiercompte.jsp").forward(request, response);
	}
	
	
	
	// Valider modifier compte
	private void validerModificationCompte(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
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
		
		// On r�cupert les �l�ments de requ�tes auxquels on appliquera une validation
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
		
		// Initialisation du mod�le
		modeleClient=new ModeleClient(this.datasource);
			
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
			
		// On v�rifie que l'adresse est renseign�e
		if(telephone.equals("") || telephone==null)
		{
			erreurs.add("Le param�tre [telephone] est vide");
		}
		else if(!telephone.matches("^(\\+){1}\\d{2,3}( )?\\d{1}(( )?\\d{2}){4}$") && !telephone.matches("^(0){1}\\d{1}([ \\.]?\\d{2}){4}$"))
		{
			erreurs.add("Le param�tre [telephone] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le pays est renseign�
		if(pays.equals("") || pays==null)
		{
			erreurs.add("Le param�tre [pays] est vide");
		}
		else if(!pays.matches("\\p{L}+([ -]?\\p{L}+)+"))
		{
			erreurs.add("Le param�tre [pays] contient une erreur de syntaxe");
		}
		
		// On v�rifie que le mot de passe est renseign�
		if(motDePasse.equals("") || motDePasse==null)
		{
			erreurs.add("Le param�tre [motDePasse] est vide");
		}
		else if(motDePasse.length()<5)
		{
			erreurs.add("Le param�tre [motDePasse] doit contenir 5 caract�res minimum");
		}
		else if(!motDePasse.matches("^[\\d\\w]{5,}$") && !motDePasse.equals("**********"))
		{
			erreurs.add("Le param�tre [motDePasse] contient une erreur de syntaxe");
		}
		
		// On v�rifie que la confirmation est renseign�e
		if(confirmation.equals("") || confirmation==null)
		{
			erreurs.add("Le param�tre [confirmation] est vide");
		}
				
		// On v�rifie que le mot de passe correpond � la confirmation
		if(!motDePasse.equals(confirmation))
		{
			erreurs.add("Le param�tre [confirmation] ne correspond pas au param�tre [motDePasse]");
		}
		
		// On regarde le nombre d'erreurs, s'il y en a au moins une on retourne � la page de modification de compte
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
			
			// On renvoie � la vue de modification de compte
			getServletContext().getRequestDispatcher("/vues/utilisateurs/modifiercompte.jsp").forward(request,response);
		}
		// Si aucune erreur, on modifie le client dans la base de donn�es
		else
		{
			// Initialisation du client
			client=new Client();
			
			// On d�finit les attributs du client
			client.setId(Integer.parseInt(id));
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setMail(mail);
			client.setAdresse(adresse);
			client.setCodePostal(codePostal);
			client.setVille(ville);
			client.setTelephone(telephone);
			client.setPays(pays);
			client.setIdentifiant(((Client)request.getSession().getAttribute("compte")).getIdentifiant());
			
			// Si l'utilisateur � chang� son mot de passe on l'enregiste, donc on le Hash
			if(!motDePasse.equals("**********"))
			{
				client.setMotDePasse(Hachage.SHA1(motDePasse));
			}
			// Si l'utilisateur n'a pas chang� son mot de passe, on r�cupert son mot de passe hach� en session
			else
			{
				client.setMotDePasse(((Client)request.getSession().getAttribute("compte")).getMotDePasse());
			}
			
			// On modifie le client dans la base de donn�es, renvoi 1 si la modification s'est d�roul�e avec succ�s
			codeErreur=modeleClient.modifierClient(client);
			
			// On teste le code de retour pour afficher un message de succ�s ou d'erreur
			if(codeErreur!=1)
			{		
				// On renvoie � la page de consultation du compte avec un message d'erreur
				response.sendRedirect("gestionClient?action=consulter&erreurs=" + "Une erreur s'est produite durant la modification des informations de votre compte. Veuillez recommencer");
			}
			else
			{	
				// On remplace l'objet client dans la session par le nouvel objet
				request.getSession().setAttribute("compte", client);
				
				// On renvoie � la page de consultation du compte avec un message d'erreur
				response.sendRedirect("gestionClient?action=consulter&succes=" + "Les informations de votre compte ont �t� modifi�es avec succ�s");
			}
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		doGet(request, response);
	}
	
	
}