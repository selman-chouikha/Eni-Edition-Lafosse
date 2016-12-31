package betaboutique.taglib.pagination;


/**
 * Classe PremierePage
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class PremierePage extends Pager 
{	
	private String format=new String();
	private String cheminImage=new String();
		
	public void setFormat(String format) 
	{
		this.format=format;
	}
	
	public void setCheminImage(String cheminImage)
	{
		this.cheminImage=cheminImage;
	}
	
	@Override
	protected String getString() 
	{
		if(pageActuel.equals("1"))
		{
			return null;
		}
		
		// On v�rifie le format demand� 
		if(!format.equals("lien") && !format.equals("valeur") && !format.equals("image"))
		{
			format="valeur";
		}
		
		if(format.equals("lien"))
		{
			return "<a href=\"" + Pager.url + "&page=1&maxParPage=" + Pager.maxParPage + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri + "\">Premi�re page</a>";
		}
		
		if(format.equals("image") && !cheminImage.equals(""))
		{
			return "<a href=\"" + Pager.url + "&page=1&maxParPage=" + Pager.maxParPage + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri + "\">" + 
				   "<img title=\"Premi�re page\" src=\"" + cheminImage + "\" />" +
		           "</a>" ;
		}
			
		if(format.equals("valeur"))
		{
			return "1";
		}
		
		return null;
	}
	
	
}
