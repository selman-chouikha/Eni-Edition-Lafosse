package betaboutique.taglib.pagination;


/**
 * Classe PagePrecedente
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class PagePrecedente extends Pager 
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
		int pagePrecedente=Integer.parseInt(pageActuel)-1;
		if(pagePrecedente<=0)
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
			return "<a href=\"" + Pager.url + "&page=" + pagePrecedente + "&maxParPage=" + Pager.maxParPage + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri + "\">" + pagePrecedente + "</a>";
		}
		
		if(format.equals("image") && !cheminImage.equals(""))
		{
			return "<a href=\"" + Pager.url + "&page=" + pagePrecedente + "&maxParPage=" + Pager.maxParPage + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri + "\">" + 
				   "<img title=\"Page précédente\" src=\"" + cheminImage + "\" />" +
				   "</a>" ;
		}
		
		if(format.equals("valeur"))
		{
			return String.valueOf(pagePrecedente);
		}
		
		return null;
	}
	
}
