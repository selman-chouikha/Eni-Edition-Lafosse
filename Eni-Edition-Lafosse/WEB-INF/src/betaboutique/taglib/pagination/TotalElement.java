package betaboutique.taglib.pagination;


/**
 * Classe TotalElement
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class TotalElement extends Pager 
{	
	@Override
	protected String getString() 
	{
		return String.valueOf(totalElement);
	}
}
