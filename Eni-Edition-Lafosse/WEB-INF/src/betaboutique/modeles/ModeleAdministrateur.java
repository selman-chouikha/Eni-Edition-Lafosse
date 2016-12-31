package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import betaboutique.outils.GestionBaseDeDonnees;
import betaboutique.beans.Administrateur;

/**
 * Classe ModeleAdministrateur
 * @author J�r�me Lafosse
 * @version 0.01
 */ 
public class ModeleAdministrateur 
{
	// Variables
	DataSource datasource=null;
	Connection connexion=null;
	ResultSet resultat=null;
	
	// Variables de pagination
	private int maxParPage;
	private int pageActuel;
	private int totalElement;
	private String champTri;
	private String typeTri;
	private String recherche;
	private String typerecherche;
	
	
	// Constructeur
	public ModeleAdministrateur (DataSource datasource)
	{
		// On r�cup�re le DataSource de la servlet
		this.datasource=datasource;
	}
	
	public int getMaxParPage() 
	{
		return maxParPage;
	}

	public int getPageActuel() 
	{
		return pageActuel;
	}

	public int getTotalElement() 
	{
		return totalElement;
	}
	
	public String getChampTri()
	{
		return champTri;
	}

	public String getRecherche()
	{
		return recherche;
	}

	public String getTypeTri() 
	{
		return typeTri;
	}
	
	public String getTyperecherche() 
	{
		return typerecherche;
	}
	
	// Ajouter un administrateur
	public int creerAdministrateur(Administrateur administrateur) 
	{
		// Variables
		int codeErreur=0;
		String requeteString=null;
		PreparedStatement requete=null;
		
		try 
		{
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// On cr�� la requ�te
			requeteString="INSERT INTO administrateur(identifiantadministrateur,motdepasseadministrateur,nomadministrateur,prenomadministrateur,mailadministrateur) VALUES (?,?,?,?,?)";
				
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setString(1,(String)administrateur.getIdentifiant());
			requete.setString(2,(String)(administrateur.getMotDePasse()));
			requete.setString(3,(String)(administrateur.getNom()));
			requete.setString(4,(String)(administrateur.getPrenom()));
			requete.setString(5,(String)(administrateur.getMail()));
				
			// On vide l'administrateur par s�curit�
			administrateur=null;
			
			// Execution de la requ�te
			codeErreur=requete.executeUpdate();
		} 
		catch (Exception e) 
		{	
			codeErreur=0;
			System.out.println(e);System.out.println("Erreur dans la requ�te dans la classe ModeleAdministrateur.java fonction creerAdministrateur");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModelClient fonction creerAdministrateur");
			}	
		}
			
		// Retourne le code d'erreur
		return codeErreur;
	}
	
	
	// Modifier un administrateur
	public int modifierAdministrateur(Administrateur administrateur) 
	{
		// Variables
		int codeErreur=0;
		String requeteString=null;
		PreparedStatement requete=null;
		
		try 
		{
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// On cr�� la requ�te
			requeteString="UPDATE administrateur set identifiantadministrateur = ? ,motdepasseadministrateur = ?,nomadministrateur = ?,prenomadministrateur = ?,mailadministrateur = ? WHERE id_administrateur = ?";
				
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setString(1,(String)administrateur.getIdentifiant());
			requete.setString(2,(String)(administrateur.getMotDePasse()));
			requete.setString(3,(String)(administrateur.getNom()));
			requete.setString(4,(String)(administrateur.getPrenom()));
			requete.setString(5,(String)(administrateur.getMail()));
			requete.setInt(6,administrateur.getId());
				
			// On vide l'administrateur par s�curit�
			administrateur=null;
			
			// Execution de la requ�te
			codeErreur=requete.executeUpdate();
		} 
		catch (Exception e) 
		{	
			codeErreur=0;
			System.out.println(e);System.out.println("Erreur dans la requ�te dans la classe ModeleAdministrateur.java fonction modifierAdministrateur");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModelClient fonction modifierAdministrateur");
			}	
		}
			
		// Retourne le code d'erreur
		return codeErreur;
	}
	
	
	
	// Supprimer un administrateur
	public int supprimerAdministrateur(int idAdministrateur)
	{	
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int codeErreur=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Cr�ation de la requ�te
			requeteString="DELETE FROM administrateur WHERE id_administrateur = ?";
			
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idAdministrateur);
			
			// On vide l'id de Administrateur par s�curit�
			idAdministrateur=0;
			
			// Execution de la requ�te
			codeErreur=requete.executeUpdate();
		}
		catch(Exception e)
		{
			codeErreur=0;
			System.out.println("Erreur dans la requete dans la classe ModeleAdministrateur fonction supprimerAdministrateur");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleAdministrateur fonction supprimerAdministrateur");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}

	
	// Liste des administrateurs
	public List<Administrateur> listerAdministrateur(int maxParPage, int pageActuel, String recherche, String typerecherche, String champTri, String typeTri)
	{	
		// Variables
		PreparedStatement requete=null;
		Administrateur administrateur=null;
		String requeteString=null;
		List<Administrateur> listeAdministrateurs=new ArrayList<Administrateur>();
		int position=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion = datasource.getConnection();
			
			// Premi�re requ�te : on r�cupert le nombre total d'administrateurs
			
			// On cr�� la requ�te
			requeteString="SELECT COUNT(DISTINCT(administrateur.id_administrateur)) as total FROM administrateur WHERE 1";
			
			// Si il s'agit d'une recherche, on modifie la requ�te en cons�quence
			if((recherche!=null && !recherche.equalsIgnoreCase("")) && (typerecherche != null && !typerecherche.equals("")))
			{
				requeteString+=" AND " + typerecherche + " like ?";
				
				// Pr�paration de la requ�te
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}
			// Dans le cas contraire on prend en compte tous les administrateurs
			else
			{
				// Pr�paration de la requ�te
				requete=connexion.prepareStatement(requeteString);
			}
			
			// Execution de la requ�te
			resultat=requete.executeQuery();
			
			// On r�cupert le nombre d'administrateurs
			if(resultat!=null)
			{
				if(resultat.next())
				{
					this.totalElement=resultat.getInt("total");
				}
			}
			
			// On v�rifie que la page demand� est valable
			position=maxParPage * (pageActuel - 1);
			if(position > totalElement || maxParPage > totalElement)
			{
				// On modifie les variables de pagination envoy� dans la requ�te
				pageActuel=1;
			}
			
			// On d�finit les variables de pagination finales
			position=maxParPage*(pageActuel-1);
			this.maxParPage=maxParPage;
			this.pageActuel=pageActuel;
			this.recherche=recherche;
			this.champTri=champTri;
			this.typeTri=typeTri;
			this.typerecherche=typerecherche;
		
			// Deuxi�me requ�te : on r�cupert la liste suivant une pagination
			// Cr�ation de la requ�te
			requeteString="SELECT * FROM administrateur WHERE 1";
			
			// Si il s'agit d'une recherche, on modifie la requ�te en cons�quence
			if((recherche!=null && !recherche.equalsIgnoreCase("")) && (typerecherche!=null && !typerecherche.equals("")))
			{
				requeteString+=" AND " + typerecherche + " like ?";
				requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
				
				// Pr�paration de la requ�te
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}
			// Dans le cas contraire on liste tous les administrateurs
			else
			{
				requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
				
				// Pr�paration de la requ�te
				requete=connexion.prepareStatement(requeteString);
			}
	
			// Execution de la requ�te
			resultat=requete.executeQuery();

			// On stocke le resultat dans une liste
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					administrateur=AdministrateurAttribusMapper(resultat);
					
					// Ajoute l'objet a la liste de administrateur
					listeAdministrateurs.add((Administrateur)administrateur);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleAdministrateur fonction listerAdministrateur");
		}
		finally
		{
			try
			{
				// Fermeture de la connexion
				if(resultat!=null)
				{
					GestionBaseDeDonnees.closeResulset(resultat);
				}
				if(requete!=null)
				{
					GestionBaseDeDonnees.closeRequest(requete);
				}
				if(connexion!=null)
				{
					GestionBaseDeDonnees.closeConnection(connexion);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleAdministrateur fonction listerAdministrateur");
			}	
		}
		
		// Retourner la liste des administrateurs
		return listeAdministrateurs;
	}

	
	// R�cup�rer un administrateur
    public Administrateur getAdministrateur(String identifiant)
    {
    	// Variables
    	PreparedStatement requete=null;
    	Administrateur administrateur=null;
    	String requeteString=null;
    	
    	try
    	{
    		// Ouverture d'une connexion
    		connexion=datasource.getConnection();
    		
    		// Cr�ation de la requ�te
    		requeteString="SELECT * FROM administrateur where identifiantadministrateur=?";
    		
    		// Preparer la requete
    		requete=connexion.prepareStatement(requeteString);
    		requete.setString(1,identifiant);
    					
    		// Execution de la requ�te
    		resultat = requete.executeQuery();
    		
    		// On stocke le resultat dans la liste
    		if(resultat!=null)
    		{
    			while(resultat.next())
    			{
    				// On r�alise le mapping des attribus de l'administrateur dans la classe avec la base
    				administrateur = AdministrateurAttribusMapper(resultat);    							    				
    			}
    		}    		
       	}
    	catch(Exception e)
    	{
    		// Si l'identifant de l'administrateur n'existe pas, on initialise l'objet administrateur � null
			administrateur=null;
    		System.out.println("Erreur dans la requ�te dans la classe ModeleAdministrateur fonction getAdministrateur");
    	}
    	finally
    	{
    		try
    		{
    			// Fermeture de la connexion
    			if(resultat!=null)
    			{
    				GestionBaseDeDonnees.closeResulset(resultat);
    			}
    			if(requete!=null)
    			{
    				GestionBaseDeDonnees.closeRequest(requete);
    			}
    			if(connexion!=null)
    			{
    				GestionBaseDeDonnees.closeConnection(connexion);
    			}
    		}
    		catch(Exception ex)
    		{
    			System.out.println("Erreur lors de la fermeture avec la base de donn�es dans la classe ModeleAdministrateur fonction getAdministrateurs");
    		}	
    	}
    	
    	// Retourner un administrateur
    	return administrateur;
    }
    
    
    
    // R�cup�rer un administrateur
    public Administrateur getAdministrateur(int idAdministrateur)
    {
    	// Variables
    	PreparedStatement requete=null;
    	Administrateur administrateur=null;
    	String requeteString=null;
    	
    	try
    	{
    		// Ouverture d'une connexion
    		connexion=datasource.getConnection();
    		
    		// Cr�ation de la requ�te
    		requeteString="SELECT * FROM administrateur where id_administrateur = ?";
    		
    		// Preparer la requete
    		requete=connexion.prepareStatement(requeteString);
    		requete.setInt(1,idAdministrateur);
    					
    		// Execution de la requ�te
    		resultat=requete.executeQuery();
    		
    		// On stocke le resultat dans la liste
    		if(resultat!=null)
    		{
    			while(resultat.next())
    			{
    				// On  r�alise le mapping les attribus de l'administrateur dans la classe avec la base
    				administrateur = AdministrateurAttribusMapper(resultat);    							    				
    			}
    		}    		
       	}
    	catch(Exception e)
    	{
    		// Si l'id de l'administrateur n'existe pas, on initialise l'objet administrateur � null
			administrateur=null;
    		System.out.println("Erreur dans la requ�te dans la classe ModeleAdministrateur fonction getAdministrateur");
    	}
    	finally
    	{
    		try
    		{
    			// Fermeture de la connexion
    			if(resultat!=null)
    			{
    				GestionBaseDeDonnees.closeResulset(resultat);
    			}
    			if(requete!=null)
    			{
    				GestionBaseDeDonnees.closeRequest(requete);
    			}
    			if(connexion!=null)
    			{
    				GestionBaseDeDonnees.closeConnection(connexion);
    			}
    		}
    		catch(Exception ex)
    		{
    			System.out.println("Erreur lors de la fermeture avec la base de donn�es dans la classe ModeleAdministrateur fonction getAdministrateurs");
    		}	
    	}
    	
    	// Retourner un administrateur
    	return administrateur;
    }
    
    
    
    // R�aliser la transformation Relationnel vers Objet
    public Administrateur AdministrateurAttribusMapper (ResultSet resultat)
    {
    	// Variables
    	Administrateur administrateur=null;
    	
    	// Initialisation de l'administrateur
    	administrateur=new Administrateur();
    	
    	try 
    	{	
	    	if (resultat.getString("id_administrateur")==null)
	    	{
	    		administrateur.setId(0);
	    	}
	    	else 
	    	{
	    		administrateur.setId(resultat.getInt("id_administrateur"));		
	    	}
	    	
	    	if (resultat.getString("identifiantadministrateur")==null)
	    	{
	    		administrateur.setIdentifiant("");		
	    	}
	    	else 
	    	{
	    		administrateur.setIdentifiant(resultat.getString("identifiantadministrateur"));	
	    	}
	    	
	    	if (resultat.getString("motdepasseadministrateur")==null)
	    	{
	    		administrateur.setMotDePasse("");
	    	}
	    	else 
	    	{
	    		administrateur.setMotDePasse(resultat.getString("motdepasseadministrateur"));
	    	}
	    		
	    	if (resultat.getString("nomadministrateur")==null)
	    	{
	    		administrateur.setNom("");
	    	}
	    	else 
	    	{
	    		administrateur.setNom(resultat.getString("nomadministrateur"));	
	    	}
	    	
	    	if (resultat.getString("prenomadministrateur")==null)
	    	{
	    		administrateur.setPrenom("");
	    	}
	    	else 
	    	{
	    		administrateur.setPrenom(resultat.getString("prenomadministrateur"));	
	    	}
	    	
	    	if (resultat.getString("mailadministrateur")==null)
	    	{
	    		administrateur.setMail("");
	    	}
	    	else 
	    	{
	    		administrateur.setMail(resultat.getString("mailadministrateur"));			
	    	}
    	}
        catch (Exception e)
        {
        	// Si il y a une erreur durant le mappage des attributs avec la base on renvoi un objet null
	    	administrateur=null;
	    	System.out.println("Erreur lors du mappage des attributs d'un administrateur dans la class ModeleAdministrateur, fonction administrateurAttribusMapper");
        }
        
        // On retourne l'administrateur
        return administrateur;
    }
    
    
    
    
}




