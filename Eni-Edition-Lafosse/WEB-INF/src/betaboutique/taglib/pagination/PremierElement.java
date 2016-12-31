package betaboutique.taglib.pagination;


/**
 * Classe PremierElement
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class PremierElement extends Pager 
{	
	@Override
	protected String getString() 
	{
		int pagePrecedente=Math.max(0, Integer.parseInt(pageActuel)-1);
		int debut=(pagePrecedente*Integer.parseInt(maxParPage))+1;
	
		if(totalElement.equals("0"))
		{
			return "0";
		}
		
		return String.valueOf(debut);
	}
	
	
	
}
