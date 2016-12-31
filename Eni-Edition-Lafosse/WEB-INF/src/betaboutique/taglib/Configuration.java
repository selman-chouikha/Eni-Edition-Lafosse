package betaboutique.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.*;

/**
 * Classe Configuration
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class Configuration extends TagSupport 
{
	private String attribut;

	public int doStartTag() throws JspException 
	{
		try 
		{
			// On affiche le paramètre contenu dans le context de l'application
			pageContext.getOut().print(pageContext.getServletContext().getInitParameter(attribut));	
		} 
		catch (Exception e) 
		{
			throw new JspTagException(e.getMessage());
		}
		
		return SKIP_BODY;
	}

	public int doEndTag() 
	{
		return SKIP_BODY;
	}

	public void setAttribut(String attribut) 
	{
		this.attribut = attribut;
	}
	
}
