package betaboutique.taglib.pagination;

/**
 * Classe PageActuel
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class PageActuel extends Pager 
{		
	@Override
	protected String getString() 
	{		
		return String.valueOf(pageActuel);
	}
}
