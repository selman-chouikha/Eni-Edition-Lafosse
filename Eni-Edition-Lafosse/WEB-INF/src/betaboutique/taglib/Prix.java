package betaboutique.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * Classe Prix
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class Prix extends TagSupport 
{
	private String montant;
		
	public void setMontant(String montant) 
	{
		this.montant=evaluer(montant);
	}

	public int doStartTag() throws JspException 
	{		
		try 
		{		
			pageContext.getOut().print(mettreEnForme(montant));	
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
	
	// Mise en forme du prix de l'article
	private String mettreEnForme(String texte)
	{
		// Variables
		double montant=0;
		String [] tableau=null;
		
		try
		{
			// On parse le montant en double
			montant=Double.parseDouble(texte);
			
			// On fait un arrondi � 10-2 pr�s
			montant*=100;
			montant=(int)(montant+0.5);
			montant/=100;
			
			// Transformer le montant (double) en String
			texte=String.valueOf(montant);
			
			// On regarde le nombre de chiffres apr�s la virgule
			tableau=texte.split("\\.");
			if(tableau[1].length()==1)
			{
				// Enl�ve le z�ro apr�s la virgule
				if(tableau[1].equals("0"))
				{
					texte=tableau[0];
				}
				// Rajoute un z�ro si il n'y a qu'un chiffre apr�s la virgule
				else
				{
					texte+="0";
				}
			}
			
			return texte;
		}
		catch (Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}
}
