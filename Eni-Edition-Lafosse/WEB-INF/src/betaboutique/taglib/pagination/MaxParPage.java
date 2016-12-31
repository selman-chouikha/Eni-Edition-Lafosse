package betaboutique.taglib.pagination;


/**
 * Classe MaxParPage
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class MaxParPage extends Pager 
{	
	private String valeur=new String();
	private String format=new String();
	
	public void setValeur(String valeur) 
	{
		this.valeur=valeur;
	}
	
	public void setFormat(String format) 
	{
		this.format=format;
	}

	@Override
	protected String getString() 
	{	
		// On vérifie le format demandé 
		if(!format.equals("lien") && !format.equals("url"))
		{
			format="lien";
		}
		
		if(format.equals("lien"))
		{
			return "<a href=\"" + Pager.url + "&page=1&maxParPage=" + valeur + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri + "\">" + valeur + "</a>";
		}
		
		if(format.equals("url"))
		{
			return Pager.url + "&page=1&maxParPage=" + valeur + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri;
		}
		
		return null;
	}
	
	
}
