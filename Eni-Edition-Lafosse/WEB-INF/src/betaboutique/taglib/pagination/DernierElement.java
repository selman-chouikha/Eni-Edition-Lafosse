package betaboutique.taglib.pagination;

/**
 * Classe DernierElement
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class DernierElement extends Pager 
{	
	@Override
	protected String getString() 
	{
		int fin=Math.min(Integer.parseInt(pageActuel) * Integer.parseInt(maxParPage), Integer.parseInt(totalElement));
		
		if(totalElement.equals("0"))
		{
			return "0";
		}
		
		return String.valueOf(fin);
	}
}
