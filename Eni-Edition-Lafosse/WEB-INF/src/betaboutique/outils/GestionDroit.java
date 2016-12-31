package betaboutique.outils;

import javax.servlet.http.HttpServletRequest;


/**
 * Classe GestionDroit
 * @author Jérôme Lafosse
 * @version 0.01
 */
public class GestionDroit 
{
	private HttpServletRequest request;
	
	// Constructeur
	public GestionDroit(HttpServletRequest request)
	{
		this.request=request;
	}
	
	// Vérifier les droits 
	public boolean estAutorise(Boolean administrateur, Boolean client, Boolean publique)
	{
		if(request.getSession().getAttribute("compte")==null)
		{
			if(publique==Boolean.FALSE)
			{
				return false;
			}
		}
		else
		{
			if(request.getSession().getAttribute("compte").getClass()==betaboutique.beans.Administrateur.class && administrateur==Boolean.FALSE)
			{
				return false;
			} 
			else if(request.getSession().getAttribute("compte").getClass()==betaboutique.beans.Client.class && client==Boolean.FALSE)
			{
				return false;
			}
		}
		
		return true;
	}
	
}
