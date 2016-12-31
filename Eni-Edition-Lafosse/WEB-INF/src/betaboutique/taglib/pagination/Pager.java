package betaboutique.taglib.pagination;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import javax.servlet.jsp.tagext.*;


/**
 * Classe Pager
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class Pager extends TagSupport 
{
	protected static String servlet=new String();
	protected static String pageActuel=new String();
	protected static String totalElement=new String();
	protected static String maxParPage="5";
	protected static String champTri=new String();
	protected static String typeTri=new String();
	protected static String url=new String();
	
	public void setMaxParPage(String maxParPage) 
	{
		Pager.maxParPage=evaluer(maxParPage);
	}

	public void setPageActuel(String pageActuel) 
	{
		Pager.pageActuel=evaluer(pageActuel);
	}

	public void setTotalElement(String totalElement) 
	{
		Pager.totalElement =evaluer(totalElement);
	}
	
	public void setChampTri(String champTri)
	{
		Pager.champTri=evaluer(champTri);
	}

	public void setTypeTri(String typeTri) 
	{
		Pager.typeTri=evaluer(typeTri);
	}

	public void setServlet(String servlet) 
	{
		Pager.servlet=evaluer(servlet);
		Pager.url=evaluer(servlet);
	}
	
	public int doStartTag() throws JspException 
	{
		try
		{
			String affichage=getString();
			if(affichage!=null)
			{
				pageContext.getOut().print(getString());
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		return EVAL_BODY_INCLUDE;
	}

	protected String evaluer(String parametre)
	{
		try
		{
			return (String) ExpressionEvaluatorManager.evaluate(null, parametre, String.class, this, pageContext);
		}
		catch (Exception e) 
		{
			return parametre;
		}
	}
		
	protected String getString()
	{
		// Vérification des champs 
		if(Integer.parseInt(Pager.maxParPage)<=0)
		{
			Pager.maxParPage="5";
		}
		
		if(Integer.parseInt(Pager.pageActuel)<=0)
		{
			Pager.pageActuel="1";
		}
		
		if(Integer.parseInt(Pager.totalElement)<=0)
		{
			Pager.totalElement="0";
			Pager.pageActuel="1";
		}
		
		if(Pager.champTri.equals(""))
		{
			Pager.typeTri="";
		}
		
		return null;
	}
	
	
	
}
