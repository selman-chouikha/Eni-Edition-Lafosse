package betaboutique.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import betaboutique.beans.Administrateur;
import betaboutique.beans.Commande;
import java.util.ArrayList;
import betaboutique.beans.Client;
import betaboutique.modeles.ModeleAdministrateur;
import betaboutique.modeles.ModeleClient;
import betaboutique.outils.Hachage;
import java.util.List;

/**
 * @author Jérôme Lafosse
 * @copyright 2008
 * @version 0.1
 */
@SuppressWarnings("serial")
public class ServletGestionSession extends HttpServlet 
{
	// Variables de la classe	
	DataSource datasource=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// Récupérer la datasource du context de la servlet
		datasource=(DataSource)getServletContext().getAttribute("datasource");

		// Action a réaliser
		String action=(String)request.getParameter("action");
		
		// Action par défaut (authentification)
		if(action==null || action.equalsIgnoreCase(""))
		{
			action="authentification";
		}
		
		// Affichage du formulaire d'authentification
		if(action.equals("authentification"))
		{	
			authentification(request, response);
		}	
		
		// Validation des informations d'authentification
		if(action.equals("validerauthentification"))
		{	
			validerAuthentification(request, response);
		}		
		
		// Déconnexion
		if(action.equals("deconnexion"))
		{	
			deconnexion(request, response);
		}	
	}
	
	
	// Gestion de l'authentification
	private void authentification(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		// Le client ne doit pas pouvoir s'authentifier deux fois
		if(request.getSession().getAttribute("compte")!=null)
		{
			// Redirection vers la consultation du compte du client
			getServletContext().getRequestDispatcher("/gestionClient?action=consulter").forward(request, response);
		}
		else
		{
			// Redirection vers la page d'authentification
			getServletContext().getRequestDispatcher("/vues/utilisateurs/connexioncompte.jsp").forward(request, response);
		}
	}
	
	
	

	// Vérification de l'authentification
	private void validerAuthentification(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		// Variables
		HttpSession session=null;
		ModeleAdministrateur modeleAdministrateur=null;
		ModeleClient modeleClient=null;
		String identifiant=null;
		String motDePasse=null;
		Client client=null;
		Administrateur administrateur=null;
		List<String> erreurs=null;
		String pagePrecedente=null;
		Commande panier=null;
		
		// Récupération des éléments de requêtes
		identifiant=request.getParameter("identifiant").trim();
		motDePasse=request.getParameter("motDePasse").trim();
		pagePrecedente=request.getParameter("pageprecedente");
			
		// Initialisation de la collection d'erreur
		erreurs=new ArrayList<String>();
		
		// Initialisation des modèles
		modeleAdministrateur=new ModeleAdministrateur(this.datasource);
		modeleClient=new ModeleClient(this.datasource);
		
		// On vérifie que l'identifiant est renseigné
		if(identifiant.equals("") || identifiant==null)
		{
			erreurs.add("Le paramètre [identifiant] est vide");
		}
		
		// On vérifie que le mot de passe est renseigné
		if(motDePasse.equals("") || motDePasse==null)
		{
			erreurs.add("Le paramètre [motDePasse] est vide");
		}
		
		// On récupert la page précédente
		if(pagePrecedente==null || pagePrecedente.equals(""))
		{
			pagePrecedente=request.getHeader("Referer");
		}
		
		// Si le login et/ou le mot de passe sont vides on renvoie à la page d'authentification
		if(erreurs.size() > 0)
		{
			// On joint à la redirection l'identifiant saisit et la page d'appel pour la redirection après authentification				
			request.setAttribute("identifiant", identifiant);
			request.setAttribute("pagePrecedente", pagePrecedente);
			
			// On ajoute la collection d'erreurs
			request.setAttribute("erreurs", erreurs);
			
			// On renvoie à la vue d'authentification
			getServletContext().getRequestDispatcher("/vues/utilisateurs/connexioncompte.jsp").forward(request,response);
		}
		// Sinon on teste le couple login mot de passe
		else
		{
			// On cherche tout d'abord si l'identifiant est celui d'un amdinistrateur
			if((administrateur=modeleAdministrateur.getAdministrateur(identifiant))!=null)
			{
				// Si c'est le cas on test le mot de passe
				if(!administrateur.getMotDePasse().equals(Hachage.SHA1(motDePasse)))
				{
					// Si le mot de passe est incorrect on envoie un message d'erreur
					erreurs.add("Mot de passe incorrect");
				}
			}
			// Si l'identifiant n'est pas celui d'un administrateur on recherche si celui-ci appartient à un client
			else if((client=modeleClient.getClient(identifiant))!=null)
			{
				// Si c'est le cas on test le mot de passe
				if(!client.getMotDePasse().equals(Hachage.SHA1(motDePasse)))
				{
					// Si le mot de passe est incorrect on envoie un message d'erreur
					erreurs.add("Mot de passe incorrect");
				}
			}
			// Si l'identifiant n'appartient ni à un administrateur ni à un client, l'identifiant est incorrect
			else
			{
				erreurs.add("Identifiant incorrect");
			}
			
			// Si il ya des erreurs on renvoi sur la page d'authentification
			if(erreurs.size()>0)
			{
				// On joint à la redirection l'identifiant saisie et la page d'appel pour la redirection après authentification			
				request.setAttribute("identifiant", identifiant);
				request.setAttribute("pagePrecedente", pagePrecedente);
				
				// On ajoute la collection d'erreurs
				request.setAttribute("erreurs", erreurs);
				
				// On renvoie à la vue d'authentification
				getServletContext().getRequestDispatcher("/vues/utilisateurs/connexioncompte.jsp").forward(request,response);
			}
			// Si il n'y a pas d'erreurs on créé en session un objet compte contenant les informationsdu client ou de l'administrateur 
			else
			{
				// Pour les administrateurs
				if(administrateur!=null)
				{
					// Initialisation de la session
					session=request.getSession();
					
					// Si pas de session, on la détruit et on en crée une nouvelle
					if(!session.isNew())
					{
						session.invalidate();
						session=request.getSession();
					}
					
					// On stocke les paramètres de l'utilisateur dans la session
					session.setAttribute("compte", administrateur);
					
					// On renvoie à la page d'index administrateur
					response.sendRedirect("pageFixe?action=admin");
				}
				
				// Pour les clients
				if(client!=null)
				{
					// Initialisation de la session
					session=request.getSession();
					
					// On récupert un éventule panier
					panier=(Commande)session.getAttribute("panier");
					
					// Si le panier n'existe pas on l'initialise
					if(panier==null)
					{
						panier=new Commande();
					}
					
					// Si pas de session, on la détruit et on en crée une nouvelle
					if(!session.isNew())
					{
						session.invalidate();
						session=request.getSession();
					}
					
					// On ajoute le panier à la session
					session.setAttribute("panier", panier);
					
					// On stocke les paramètres de l'utilisateur dans la session
					session.setAttribute("compte", client);
					
					// On renvoie à la page d'index utilisateur
					response.sendRedirect(pagePrecedente);
				}
			}
		}
	}
		
	
	// Déconnexion
	private void deconnexion(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		// Variables
		HttpSession session=null;
		
		// On récupert la session de l'utilisateur
		session=request.getSession();
		
		// Destruction de la session
		session.invalidate();
		
		// Renvoie vers la page d'authentification
		response.sendRedirect("gestionSession?action=authentification");
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		doGet(request, response);
	}
	
}
