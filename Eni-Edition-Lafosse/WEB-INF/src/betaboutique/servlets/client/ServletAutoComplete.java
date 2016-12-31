package betaboutique.servlets.client;

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
 * @author Jérôme Lafosse
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
		// On récupert l'objet datasource 
		datasource=(DataSource)getServletContext().getAttribute("datasource");
		
		// Variables 
		ModeleAutoComplete modele=null;
		String saisie=null;
		String limit=null;
		ArrayList<String> liste=new ArrayList<String>();
		
		// On récupert les attributs de la requête 
		saisie=(String)request.getParameter("q").trim();
		limit=(String)request.getParameter("limit").trim();
		
		// Vérification de la validité des paramètres 
		if(saisie!=null && !saisie.equals("") && limit!=null && !limit.equals(""))
		{
			// On crée le modèle 
			modele=new ModeleAutoComplete(datasource);
			
			// On récupert la liste avec le modèle 
			liste=modele.autoCompleteUtilisateur(saisie, limit);

			// On retourne la liste
			request.setAttribute("liste", liste);

			// Vide la liste par sécurité 
			liste=null;

			// On renvoie à la vue ajax 
			getServletContext().getRequestDispatcher("/vues/ajax/autocomplete.jsp").forward(request, response);
		}
		
		// On vide l'objet datasource 
		this.datasource=null;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	
}