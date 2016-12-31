package betaboutique.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * Classe Resumer
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class Resumer extends TagSupport 
{
	private String longueur;
	private String texte;
	
	public void setLongueur(String longueur)
	{
		this.longueur=evaluer(longueur);
	}
	
	public void setTexte(String texte)
	{
		this.texte=evaluer(texte);
	}
	
	public int doStartTag() throws JspException 
	{		
		// D�finition des variables
		String[] tableau=null;
		String resume=new String();
		
		try 
		{		
			// On r�cupert chaque mot du texte s�par� par un espace
			tableau=texte.split(" ");
			
			if(tableau.length > Integer.parseInt(longueur))
			{
				// On cr�e le resume de la longueur (en mot) d�sir�e
				for(int compteur=0; compteur<Integer.parseInt(longueur);compteur++)
				{
					resume+=tableau[compteur]+" "; 
				}
				resume += "...";
			}
			else
			{
				resume=texte;
			}
			
			// Affichage du r�sum�
			pageContext.getOut().print(resume);	
		} 
		catch (Exception e) 
		{
			throw new JspTagException(e.getMessage());
		}
		
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