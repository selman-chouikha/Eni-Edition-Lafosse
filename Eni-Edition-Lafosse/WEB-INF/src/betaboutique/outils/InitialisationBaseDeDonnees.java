package betaboutique.outils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;


/**
 * Classe InitialisationBaseDeDonnees
 * @author Jérôme Lafosse
 * @version 0.01
 */
public class InitialisationBaseDeDonnees implements ServletContextListener 
{
	// Action déclenchée lors du chargement du contexte
	public void contextInitialized(ServletContextEvent event) 
	{
		// Initaliser le contexte
		Context contextInitial=null;
		
		try 
		{
			contextInitial=new InitialContext();
			if (contextInitial==null) 
			{
				throw new Exception("Impossible de charger le contexte");
			}

			// Connexion JNDI
			Context environnement=(Context)contextInitial.lookup("java:comp/env");
			DataSource datasource=(DataSource)environnement.lookup("jdbc_betaboutiquejavaee_MySQL");
			if (datasource==null) 
			{
				throw new Exception("Erreur lors du chargement du datasource");
			}
			// Sauvegarder le datasource dans le contexte de l'application
			ServletContext servletContext=event.getServletContext();
			servletContext.setAttribute("datasource", datasource);
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			try
			{
				// On ferme le contexte
				if (contextInitial!=null) 
				{
					contextInitial.close();
					//System.out.println("Contexte correctement decharger");
				}
			} 
			catch (Exception e) 
			{
				System.out.println("Erreur lors de la fermeture du contexte");
			}
		}
	}

	
	// Action déclenchée a la fermeture du contexte
	public void contextDestroyed(ServletContextEvent event) 
	{
		//System.out.println("Fermeture du Datasource");
	}
	
}