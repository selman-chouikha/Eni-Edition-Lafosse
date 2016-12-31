package betaboutique.taglib.pagination;


/**
 * Classe Tri
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class Tri extends Pager 
{	
	private String champ=new String();
	private String imageCroissant=new String();
	private String imageDecroissant=new String();
	private String format=new String();
	
	public void setChamp(String champ) 
	{
		this.champ=evaluer(champ);
	}

	public void setFormat(String format) 
	{
		this.format=evaluer(format);
	}
	
	public void setImageCroissant(String imageCroissant) 
	{
		this.imageCroissant=evaluer(imageCroissant);
	}

	public void setImageDecroissant(String imageDecroissant) 
	{
		this.imageDecroissant=evaluer(imageDecroissant);
	}

	@Override
	protected String getString() 
	{
		// Variables 
		String type=new String();
		String image=new String();
		
		// On teste si le tri a été effectué sur le champ demandé 
		if(champTri.equals(champ))
		{
			if(typeTri.equals("ASC"))
			{
				type="DESC";
			}
			
			if(typeTri.equals("DESC"))
			{
				type="ASC";
			}
		}
		else
		{
			type="ASC";
		}
		
		// On définit l'image pour le lien 
		if(type.equals("ASC"))
		{
			image=imageCroissant;
		}
		
		if(type.equals("DESC"))
		{
			image = imageDecroissant;
		}
		
		// On définit le type de format par défaut 
		if(!format.equals("lien") && !format.equals("image") && !format.equals("url"))
		{
			format="lien";
		}
		
		// On revoie dans le format demandé 
		if(format.equals("url"))
		{
			return Pager.url + "&page=" + pageActuel + "&maxParPage=" + Pager.maxParPage + "&champTri=" + champ + "&typeTri=" + type;
		}
		
		if(format.equals("image") && !image.equals(""))
		{
			return "<a href=\"" + Pager.url + "&page=" + pageActuel + "&maxParPage=" + Pager.maxParPage + "&champTri=" + champ + "&typeTri=" + type + "\">" +
				   "<img src=\"" + image + "\" />" +
				   "</a>";
		}
		
		if(format.equals("lien"))
		{
			return "<a href=\"" + Pager.url + "&page=" + pageActuel + "&maxParPage=" + Pager.maxParPage + "&champTri=" + champ + "&typeTri=" + type + "\">" + champ + "</a>";
		}
		
		return null;
	}
	
}
