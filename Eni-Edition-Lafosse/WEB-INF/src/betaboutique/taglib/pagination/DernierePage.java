package betaboutique.taglib.pagination;

/**
 * Classe DernierePage
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class DernierePage extends Pager 
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
		int dernierePage=(int)Math.ceil(Double.parseDouble(totalElement) / Double.parseDouble(maxParPage));
		if(totalElement.equals("0"))
		{
			dernierePage=1;
		}
		
		if(pageActuel.equals(String.valueOf(dernierePage)) && format.equals("valeur"))
		{
			return String.valueOf(dernierePage);
		}
		
		if(pageActuel.equals(String.valueOf(dernierePage)))
		{
			return null;
		}
		
		// On vérifie le format demandé 
		if(!format.equals("lien") && !format.equals("valeur") && !format.equals("image"))
		{
			format="valeur";
		}
		
		if(format.equals("lien"))
		{
			return "<a href=\"" + Pager.url + "&page=" + dernierePage + "&maxParPage=" + Pager.maxParPage + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri + "\">Dernière page</a>";
		}
		
		if(format.equals("image") && !cheminImage.equals(""))
		{
			return "<a href=\"" + Pager.url + "&page=" + dernierePage + "&maxParPage=" + Pager.maxParPage + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri + "\">" +
				   "<img title=\"Dernière page\" src=\"" + cheminImage + "\" />" +
			       "</a>" ;
		}
		
		if(format.equals("valeur"))
		{
			return String.valueOf(dernierePage);
		}
		
		return null;
	}
	
	
	
	
}
