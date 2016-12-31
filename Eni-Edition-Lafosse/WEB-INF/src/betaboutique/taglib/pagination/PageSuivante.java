package betaboutique.taglib.pagination;


/**
 * Classe PageSuivante
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class PageSuivante extends Pager 
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
		int dernierePage=(int)Math.ceil(Double.parseDouble(totalElement)/Double.parseDouble(maxParPage));
		int pageSuivante=Integer.parseInt(pageActuel)+1;
		
		if(pageSuivante>dernierePage)
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
			return "<a href=\"" + Pager.url + "&page=" + pageSuivante + "&maxParPage=" + Pager.maxParPage + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri + "\">" + pageSuivante + "</a>";
		}
		
		if(format.equals("image") && !cheminImage.equals(""))
		{
			return "<a href=\"" + Pager.url + "&page=" + pageSuivante + "&maxParPage=" + Pager.maxParPage + "&champTri=" + Pager.champTri + "&typeTri=" + Pager.typeTri + "\">" + 
				   "<img title=\"Page suivante\" src=\"" + cheminImage + "\" />" +
				   "</a>" ;
		}
		
		if(format.equals("valeur"))
		{
			return String.valueOf(pageSuivante);
		}
		
		return null;
	}
	
	
	
}
