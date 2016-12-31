package betaboutique.servlets.administrateur;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import betaboutique.modeles.ModeleAutoComplete;

/**
 * Classe ServletAutoComplete
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletAutoComplete extends HttpServlet 
{
	// Attributs
	private DataSource datasource;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// On positionne le datasource
		datasource=(DataSource)getServletContext().getAttribute("datasource");
		
		// Variables
		ModeleAutoComplete modele;
		String saisie=null;
		String limit=null;
		String attribut=null;
		String table=null;
		ArrayList<String> liste=new ArrayList<String>();
		
		// On r�cupert les attributs de la requ�te
		saisie=(String)request.getParameter("q").trim();
		limit=(String)request.getParameter("limit").trim();
		table=(String)request.getParameter("table").trim();
		attribut=(String)request.getParameter("attribut").trim();
		
		// V�rification de la validit� des param�tres
		if( (attribut!=null && !attribut.equals("")) && (table!=null && !table.equals("")) && (saisie!=null && !saisie.equals("")) &&(limit!=null && !limit.equals("")))
		{
			// On cr�e le mod�le
			modele=new ModeleAutoComplete(datasource);
			
			// On r�cupert la liste depuis le mod�le
			liste=modele.autoCompleteAdministrateur(saisie, limit,attribut, table);

			// On retourne la liste
			request.setAttribute("liste", liste);

			// Vide la liste par s�curit�
			liste=null;

			// On renvoie � la jsp
			getServletContext().getRequestDispatcher("/vues/ajax/autocomplete.jsp").forward(request, response);
		}
		
		// On vide le datasource
		this.datasource=null;
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
}