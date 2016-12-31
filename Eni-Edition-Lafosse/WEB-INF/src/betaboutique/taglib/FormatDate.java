package betaboutique.taglib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * Classe FormatDate
 * @author Jérôme Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class FormatDate extends TagSupport 
{
	private String date;
	private String format;

	public void setDate(String date) 
	{
		this.date=evaluer(date);
	}

	public void setFormat(String format) 
	{
		this.format=evaluer(format);
	}
	
	public int doStartTag() throws JspException 
	{
		// Variables
		Date DateFormater=new Date();
		SimpleDateFormat dateFormat=null;
		
		try 
		{
			// Transformer la date en format date
			dateFormat=new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy",Locale.ENGLISH);
			DateFormater=dateFormat.parse(date);
			
			// On applique le bon format a la date
			dateFormat=new SimpleDateFormat(format);
			
			// Affichage de la date
			pageContext.getOut().print(dateFormat.format(DateFormater));	
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
	
	private String evaluer(String parametre)
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
	
	
	
	
}
