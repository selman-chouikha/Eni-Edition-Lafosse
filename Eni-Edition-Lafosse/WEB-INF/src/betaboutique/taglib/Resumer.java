package betaboutique.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * Classe Resumer
 * @author Jérôme Lafosse
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
		// Définition des variables
		String[] tableau=null;
		String resume=new String();
		
		try 
		{		
			// On récupert chaque mot du texte séparé par un espace
			tableau=texte.split(" ");
			
			if(tableau.length > Integer.parseInt(longueur))
			{
				// On crée le resume de la longueur (en mot) désirée
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
			
			// Affichage du résumé
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