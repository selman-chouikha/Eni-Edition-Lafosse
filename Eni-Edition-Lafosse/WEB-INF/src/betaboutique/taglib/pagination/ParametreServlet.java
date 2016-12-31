package betaboutique.taglib.pagination;


/**
 * Classe ParametreServlet
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ParametreServlet extends Pager 
{
	private String nom;
	private String valeur;
	
	
	public void setNom(String nom) 
	{
		this.nom=evaluer(nom);
	}


	public void setValeur(String valeur) 
	{
		this.valeur=evaluer(valeur);
	}


	@Override
	protected String getString() 
	{
		if(Pager.servlet.equals(Pager.url))
		{
			Pager.url += "?" + nom + "=" + valeur;
		}
		else
		{
			Pager.url += "&" + nom + "=" + valeur;
		}
		
		return null;
	}
	
	
}
