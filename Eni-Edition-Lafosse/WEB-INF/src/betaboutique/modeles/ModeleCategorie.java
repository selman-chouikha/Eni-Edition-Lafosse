package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import betaboutique.beans.Categorie;
import betaboutique.outils.GestionBaseDeDonnees;


/**
 * Classe ModeleCategorie
 * @author J�r�me Lafosse
 * @version 0.01
 */ 
public class ModeleCategorie 
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
	public ModeleCategorie(DataSource datasource)
	{		
		// On r�cup�re le DataSource de la servlet
		this.datasource=datasource;
	}
	
	// Accesseurs
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
	
	// Retourner la liste des cat�gories
	public List<Categorie> listeCategorie()
	{
		// Variables
		PreparedStatement requete=null;
		Categorie categorie=null;
		String requeteString=null;
		List<Categorie> listeCategorie=new ArrayList<Categorie>();
		
		try
		{
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Cr�ation de la requ�te
			requeteString="SELECT * FROM categorie ORDER BY nomcategorie ASC";
			
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
				
			// Execution de la requ�te
			resultat=requete.executeQuery();

			// On stocke le resultat dans une liste
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					categorie=categorieAttributsMapper(resultat);
					
					// Ajoute l'objet a la liste des cat�gories
					listeCategorie.add((Categorie)categorie);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleCategorie fonction listeCategorie");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleCategorie fonction listeCategorie");
			}	
		}
		
		// Retourner la liste des categories
		return listeCategorie;
	}
	
	
	
	// R�cup�rer une cat�gorie
	public Categorie getCategorie(int idCategorie)
	{
		// Variables
		PreparedStatement requete=null;
		Categorie categorie=null;
		String requeteString=null;
		
		try
		{
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Cr�ation de la requ�te
			requeteString="SELECT * FROM categorie where id_categorie=?";
			
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idCategorie);
				
			// Execution de la requ�te
			resultat=requete.executeQuery();

			// On stocke le resultat dans l'objet categorie
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					categorie=categorieAttributsMapper(resultat);
				}
			}
		}
		catch(Exception e)
		{
			categorie=null;
			System.out.println("Erreur dans la requete dans la classe ModeleCategorie fonction GetCategorie");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleCategorie fonction GetCategorie");
			}	
		}
		

		// Retourner la categorie souhait�e
		return categorie;
	}
	
	
	// R�cup�rer la cat�gorie
	public Categorie getCategorie(String nom)
	{
		// Variables
		PreparedStatement requete=null;
		Categorie categorie=null;
		String requeteString=null;
		
		try
		{
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Cr�ation de la requ�te
			requeteString="SELECT * FROM categorie where nomcategorie = ?";
			
			// On pr�pare la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setString(1,nom);
				
			// Execution de la requ�te
			resultat=requete.executeQuery();

			// On stocke le resultat dans l'objet categorie
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					categorie=categorieAttributsMapper(resultat);
				}
			}
		}
		catch(Exception e)
		{
			categorie=null;
			System.out.println("Erreur dans la requete dans la classe ModeleCategorie fonction GetCategorie");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleCategorie fonction GetCategorie");
			}	
		}
		
		// Retourner la categorie souhait�e
		return categorie;
	}
	
	
	
	
	/* =========================================
	 * Partie Admin
	 * =========================================
	 */
	
	// Retourner la liste des cat�gories
	public List<Categorie> listeCategoriePaginer(int maxParPage, int pageActuel, String recherche, String typerecherche, String champTri, String typeTri)
	{	
		// Variablesv
		PreparedStatement requete=null;
		Categorie categorie=null;
		String requeteString=null;
		List<Categorie> listeCategorie=new ArrayList<Categorie>();
		int position=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Premi�re requ�te : on r�cupert le nombre total de cat�gories
			requeteString="SELECT COUNT(DISTINCT(id_categorie)) as total FROM categorie WHERE 1";
			
			// Si il s'agit d'une recherche, on modifie la requ�te en cons�quence
			if((recherche!=null && !recherche.equalsIgnoreCase("")) && (typerecherche!=null && !typerecherche.equals("")))
			{
				requeteString+=" AND " + typerecherche + " like ?";
				
				// Pr�paration de la requ�te
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}
			// Dans compte le nombre total de cat�gories
			else
			{
				// Pr�paration de la requ�te
				requete = connexion.prepareStatement(requeteString);
			}
			
			// Execution de la requ�te
			resultat=requete.executeQuery();
			
			// On r�cupert le nombre de cat�gories
			if(resultat!=null)
			{
				if(resultat.next())
				{
					this.totalElement=resultat.getInt("total");
				}
			}
			
			// On v�rifie que la page demand� est valable
			position=maxParPage*(pageActuel-1);
			if(position>totalElement || maxParPage>totalElement)
			{
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
			requeteString="SELECT * FROM categorie WHERE 1";
			
			// Si il s'agit d'une recherche, on modifie la requ�te en cons�quence
			if((recherche!=null && !recherche.equalsIgnoreCase("")) && (typerecherche!=null && !typerecherche.equals("")))
			{
				requeteString+=" AND " + typerecherche + " like ?";
				requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
				
				// Pr�paration de la requ�te
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}
			// Dans le cas contraire on liste toutes les cat�gories
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
					// On effectue le mappage des attributs avec les champs de la table SQL
					categorie=categorieAttributsMapper(resultat);
					
					// Ajoute l'objet a la liste des cat�gories
					listeCategorie.add((Categorie)categorie);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleCategorie fonction listeCategoriePaginer");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleCategorie fonction listeCategoriePaginer");
			}	
		}
		
		// Retourner la liste des categories
		return listeCategorie;
	}
	
	
	
	// Ajouter une cat�gorie
	public int ajouterCategorie(Categorie categorie)
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
			requeteString="INSERT INTO categorie (nomcategorie) VALUES(?)";
			
			// Pr�paration de la requ�te
			requete=connexion.prepareStatement(requeteString);
			requete.setString(1, categorie.getNom());
			
			// On vide la categorie par s�curit�
			categorie=null;
			
			// Execution de la requ�te
			codeErreur=requete.executeUpdate();
		}
		catch(Exception e)
		{
			codeErreur=0;
			System.out.println("Erreur dans la requete dans la classe ModeleCategorie fonction ajouterCategorie");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleCategorie fonction ajouterCategorie");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
	
	
	
	// Supprimer une cat�gorie
	public int supprimerCategorie(int idCategorie)
	{	
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int codeErreur=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// Cr�ation de la premi�re requ�te : on d�place tous les articles de la cat�gorie dans la cat�gorie nomm�e "autre"
			requeteString="UPDATE article SET id_categorie = 1 WHERE id_categorie = ?";
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idCategorie);
			
			// Execution de la requ�te
			codeErreur=requete.executeUpdate();
			
			// Cr�ation de la deuxi�me requ�te : suppression de la cat�gorie
			requeteString="DELETE FROM categorie WHERE id_categorie = ?";
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idCategorie);
			
			// On vide l'id de la cat�gorie par s�curit�
			idCategorie=0;
			
			// Execution de la requ�te
			codeErreur=requete.executeUpdate();
		}
		catch(Exception e)
		{
			codeErreur=0;
			System.out.println("Erreur dans la requete dans la classe ModeleCategorie fonction supprimerCategorie");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleCategorie fonction supprimerCategorie");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
	
	
	// Modifier une cat�gorie
	public int modifierCategorie(Categorie categorie)
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
			requeteString="UPDATE categorie set nomcategorie = ? WHERE id_categorie = ?";
			requete=connexion.prepareStatement(requeteString);
			requete.setString(1, categorie.getNom());
			requete.setInt(2, categorie.getId());
				
			// On vide la cat�gorie par s�curit�
			categorie=null;
			
			// Execution de la requ�te
			codeErreur=requete.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("Erreur dans la requete dans la classe ModeleCategorie fonction modifierCategorie");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de donn�es dans la classe ModeleCategorie fonction modifierCategorie");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
	

	
	
	// Transformation du Relationnel vers Objet
	public Categorie categorieAttributsMapper(ResultSet resultat)
	{
		// Variables
		Categorie categorie=new Categorie();
		
		try
		{	
			// On renseigne l'objet categorie
			if(resultat.getString("id_categorie")==null)
			{
				categorie.setId(0);
			}
			else
			{
				categorie.setId(resultat.getInt("id_categorie"));
			}
			
			if(resultat.getString("nomcategorie")==null)
			{
				categorie.setNom("");
			}
			else
			{
				categorie.setNom(resultat.getString("nomcategorie"));
			}
		}
		catch (Exception e) 
		{
			// Si il y a une erreur durant le mapping des attributs avec la base on renvoi un objet null
			categorie=null;
			System.out.println("Erreur lors du mappage des attributs d'une categorie la classe ModeleCategorie fonction CategorieAttributsMapper");
		}
		
		// On retourne la categorie 
		return categorie;
	}
	
}