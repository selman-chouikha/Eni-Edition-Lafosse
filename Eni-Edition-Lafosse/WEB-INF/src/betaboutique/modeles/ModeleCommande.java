package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import betaboutique.beans.Commande;
import betaboutique.beans.Client;
import betaboutique.outils.GestionBaseDeDonnees;

/**
 * Classe ModeleCommande
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class ModeleCommande 
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
	public ModeleCommande(DataSource datasource)
	{		
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
	
	
	// Retourner la liste des commandes
	public List<Commande> listerCommande(int idClient, int maxParPage, int pageActuel, String champTri, String typeTri)
	{	
		// Variables
		PreparedStatement requete=null;
		Commande commande=null;
		String requeteString=null;
		List<Commande> listeCommande=new ArrayList<Commande>();
		int position=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// Première requête : on récupert le nombre total de commandes passées par le client
			requeteString="SELECT COUNT(DISTINCT(commande.id_commande)) as total FROM commande WHERE id_client IN (SELECT id_clientcommande FROM clientcommande WHERE referenceclient = ?)";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idClient);
				
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On récupert le nombre de commande du client
			if(resultat!=null)
			{
				if(resultat.next())
				{
					this.totalElement=resultat.getInt("total");
				}
			}
			
			// On vérifie que la page demandé est valable
			position=maxParPage*(pageActuel-1);
			if(position>totalElement || maxParPage>totalElement)
			{
				pageActuel=1;
			}
			
			// On définit les variables de pagination finales
			position=maxParPage*(pageActuel-1);
			this.maxParPage=maxParPage;
			this.pageActuel=pageActuel;
			this.champTri=champTri;
			this.typeTri=typeTri;
			
			// Deuxième requête : on récupert la liste suivant une pagination
			requeteString = "SELECT * FROM commande WHERE id_client IN (SELECT id_clientcommande FROM clientcommande WHERE referenceclient = ?) ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
				
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idClient);
			
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat dans une liste de commande
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					commande=commandeAttributsMapper(resultat);
					
					// Ajoute l'objet a la liste de commande
					listeCommande.add(commande);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Erreur dans la requete dans la classe ModeleCommande fonction listerCommande");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleCommande fonction listerCommande");
			}	
		}
		
		// Retourner la liste des commandes
		return listeCommande;
	}
	
	
	
	
	
	// Consulter une commande par client
	public Commande consulterCommande(int idClient, int idCommande)
	{	
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		Commande commande=null;
		
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// On créé la requête
			requeteString="SELECT * FROM commande WHERE id_client IN (SELECT id_clientcommande FROM clientcommande WHERE referenceclient = ?) AND id_commande = ?";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idClient);
			requete.setInt(2, idCommande);
				
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On récupert la commande du client
			if(resultat!=null)
			{
				if(resultat.next())
				{
					commande=commandeAttributsMapper(resultat);
				}
			}
		}
		catch(Exception e)
		{
			commande =null;
			System.out.println("Erreur dans la requete dans la classe ModeleCommande fonction consulterCommande");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleCommande fonction consulterCommande");
			}	
		}
		
		// Retourner la commande du client
		return commande;
	}
	
	
	
	
	
	// Ajouter une commande
	public int ajouterCommande(Commande commande,int idClient)
	{	
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		ModeleLigneCommande modeleLigneCommande=null;
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		int codeErreur=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Annulation de l'autocommit
			connexion.setAutoCommit(false);
			
			// Création de la requête
			requeteString="INSERT INTO commande (id_client,datecreationcommande,datemodificationcommande,totalcommande,totalpoids,etatcommande) VALUES(?,?,?,?,?,?)";
			
			// Préparation de la requête
			requete=connexion.prepareStatement(requeteString,PreparedStatement.RETURN_GENERATED_KEYS);
			requete.setInt(1, idClient);
			requete.setInt(2, Integer.parseInt(format.format(commande.getDateCreation())));
			requete.setInt(3, Integer.parseInt(format.format(commande.getDateModification())));
			requete.setDouble(4, commande.getTotal());
			requete.setDouble(5, commande.getTotalPoids());
			requete.setInt(6, commande.getEtat());
			
			// Execution de la requête
			codeErreur=requete.executeUpdate();
			
			// Si l'ajout s'est déroulé avec succès on ajoute les lignes de la commande
			if(codeErreur==1)
			{
				// On récupert l'id de la nouvelle commande
				resultat=requete.getGeneratedKeys();
	            if (resultat.next())
	            {
	            	commande.setId(Integer.parseInt(resultat.getString(1)));
	            }
	            
	            // Initialisation du modèle ligneCommande
	            modeleLigneCommande=new ModeleLigneCommande(this.datasource);
	            
	            // On ajoute les lignes de la commande
	            codeErreur=modeleLigneCommande.ajouterListeLigneCommande(commande.getListeLigneCommande(), commande.getId());
			}
			
			// Si il y a une erreur durant l'ajout des lignes ou de la commande en base, on fait un rollback
			if(codeErreur!=1)
			{
				GestionBaseDeDonnees.rollback(connexion);
			}
			else
			{
				GestionBaseDeDonnees.commit(connexion);
			}
			
			// On vide la commande par sécurité
			commande=null;
		}
		catch(Exception e)
		{
			codeErreur=0;
			System.out.println(e);
			GestionBaseDeDonnees.rollback(connexion);
			System.out.println("Erreur dans la requete dans la classe ModeleCommande fonction ajouterCommande");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleCommande fonction ajouterCommande");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
	
	
	
	
	// Modifier une commande
	public int modifierCommande(Commande commande)
	{	
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		ModeleClientCommande modeleClientCommande=null;
		int codeErreur=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Annulation de l'autocommit
			connexion.setAutoCommit(false);
			
			// Création de la requête
			requeteString="UPDATE commande SET etatcommande = ?, datemodificationcommande = ? WHERE id_commande = ?";
			
			// Préparation de la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, commande.getEtat());
			requete.setInt(2, Integer.parseInt(format.format(commande.getDateModification())));
			requete.setInt(3, commande.getId());
			
			// Execution de la requête
			codeErreur=requete.executeUpdate();
			
			// Si l'ajout s'est déroulé avec succès on modifie l'adresse de facturation
			if(codeErreur==1)
			{
	            // Initialisation du modèle
	            modeleClientCommande=new ModeleClientCommande(this.datasource);
	            
	            // On modifie l'adresse de livraison
	            codeErreur=modeleClientCommande.modifierAdresseLivraison(commande.getClient());
			}
			
			// Si il y a une erreur durant la modification de la commande ou de l'adresse de livraison en base, on fait un rollback
			if(codeErreur!=1)
			{
				GestionBaseDeDonnees.rollback(connexion);
			}
			else
			{
				GestionBaseDeDonnees.commit(connexion);
			}
			
			// On vide la commande par sécurité
			commande=null;
		}
		catch(Exception e)
		{
			codeErreur=0;
			System.out.println(e);
			GestionBaseDeDonnees.rollback(connexion);
			System.out.println("Erreur dans la requete dans la classe ModeleCommande fonction modifierCommande");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleCommande fonction modifierCommande");
			}	
		}
		
		// Retourner le code d'erreur
		return codeErreur;
	}
	
	
	
	
	
	// Retourner le nombre de commandes en attente
	public int getNombreCommandeEnAttente(int idClient)
	{
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		int total=0;
		
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// On créé la requête
			requeteString="SELECT count(*) as total FROM commande WHERE id_client IN (SELECT id_clientcommande FROM clientcommande WHERE referenceclient = ?) AND etatcommande = 0";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1,idClient);
				
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On récupert la commande du client
			if(resultat!=null)
			{
				if(resultat.next())
				{
					// Si le client n'a pas encore passé de commande, on passe le total de commande à 0
					if(resultat.getString("total")==null)
					{
						total=0;
					}
					else
					{
						total=resultat.getInt("total");
					}
				}
			}
		}
		catch(Exception e)
		{
			total=0;
			System.out.println("Erreur dans la requete dans la classe ModeleCommande fonction getNombreCommandeEnAttente");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleCommande fonction getNombreCommandeEnAttente");
			}	
		}
		
		// Retourner le nombre de commande
		return total;
	}
	
	
	
	
	
	
	
	/* ======================
	 * Partie admin
	 * ======================
	 */
	
	
	// Retourner la liste des commandes
	public List<Commande> listerCommande (int maxParPage, int pageActuel, String recherche, String typerecherche, String champTri, String typeTri)
	{	
		// Variables
		PreparedStatement requete=null;
		Commande commande=null;
		String requeteString=null;
		List<Commande> listeCommandes=new ArrayList<Commande>();
		int position=0;
	
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
			
			// Première requête : on récupert le nombre total de commandes
			requeteString="SELECT COUNT(DISTINCT(commande.id_commande)) as total FROM commande,clientcommande WHERE commande.id_client = clientcommande.id_clientcommande";
			
			// Si il s'agit d'une recherche, on modifie la requête en conséquence
			if((recherche!=null && !recherche.equalsIgnoreCase("")) && (typerecherche!=null && !typerecherche.equals("")))
			{
				requeteString+=" AND " + typerecherche + " like ?";
				
				// Préparation de la requête
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}
			// Dans le cas contraire on prend en compte tous les clients
			else
			{
				// Préparation de la requête
				requete=connexion.prepareStatement(requeteString);
			}
			
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On récupert le nombre de commande
			if(resultat!=null)
			{
				if(resultat.next())
				{
					this.totalElement=resultat.getInt("total");
				}
			}
			
			// On vérifie que la page demandé est valable
			position=maxParPage*(pageActuel-1);
			if(position>totalElement || maxParPage>totalElement)
			{
				// On modifie les variables de pagination envoyées dans la requête
				pageActuel=1;
			}
			
			// On définit les variables de pagination finales
			position=maxParPage*(pageActuel-1);
			this.maxParPage=maxParPage;
			this.pageActuel=pageActuel;
			this.recherche=recherche;
			this.champTri=champTri;
			this.typeTri=typeTri;
			this.typerecherche=typerecherche;
		
			// Deuxième requête : on récupert la liste suivant une pagination
			
			// Création de la requête
			requeteString="SELECT * FROM commande,clientcommande WHERE commande.id_client = clientcommande.id_clientcommande";
			
			// Si il s'agit d'une recherche, on modifie la requête en conséquence
			if((recherche!=null && !recherche.equalsIgnoreCase("")) && (typerecherche!=null && !typerecherche.equals("")))
			{
				requeteString+=" AND " + typerecherche + " like ?";
				requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
				
				// Préparation de la requête
				requete=connexion.prepareStatement(requeteString);
				requete.setString(1,"%" + recherche + "%");
			}
			// Dans le cas contraire on liste toutes les commandes
			else
			{
				requeteString+=" ORDER BY " + champTri + " " + typeTri + " LIMIT " + position + "," + maxParPage;
				
				// Préparation de la requête
				requete=connexion.prepareStatement(requeteString);
			}
	
			// Execution de la requête
			resultat=requete.executeQuery();

			// On stocke le resultat dans une liste
			if(resultat!=null)
			{
				while(resultat.next())
				{
					// On effectue le mapping des attributs avec les champs de la table SQL
					commande=commandeAttributsMapper(resultat);
					
					// Ajoute l'objet a la liste des commandes
					listeCommandes.add(commande);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Erreur dans la requete dans la classe ModeleCommande fonction listeCommande");
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleCommande fonction listeCommande");
			}	
		}
		
		// Retourner la liste des commandes
		return listeCommandes;
	}

	
	// Consulter une commande
	public Commande consulterCommande(int idCommande)
	{	
		// Variables
		PreparedStatement requete=null;
		String requeteString=null;
		Commande commande=null;
		
		try
		{			
			// Ouverture d'une connexion
			connexion=datasource.getConnection();
		
			// On créé la requête
			requeteString="SELECT * FROM commande WHERE id_commande = ?";
			
			// On prépare la requête
			requete=connexion.prepareStatement(requeteString);
			requete.setInt(1, idCommande);
				
			// Execution de la requête
			resultat=requete.executeQuery();
			
			// On récupert la commande du client
			if(resultat!=null)
			{
				if(resultat.next())
				{
					commande=commandeAttributsMapper(resultat);
				}
			}
		}
		catch(Exception e)
		{
			commande=null;
			System.out.println("Erreur dans la requete dans la classe ModeleCommande fonction consulterCommande");
		}
		finally
		{
			try
			{
				//* Fermeture de la connexion
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
				System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleCommande fonction consulterCommande");
			}	
		}
		
		// Retourner la commande du client
		return commande;
	}
	
	
	// Réaliser la transformation Relationnel vers Objet
	private Commande commandeAttributsMapper(ResultSet resultat)
	{
		// Variables
		Commande commande=null;
		SimpleDateFormat dateFormat=null;
		ModeleClientCommande modeleClientCommande=null;
		ModeleLigneCommande modeleLigneCommande=null;
		
		//* Initialisation du modèle
		modeleClientCommande=new ModeleClientCommande(this.datasource);
		modeleLigneCommande=new ModeleLigneCommande(this.datasource);
		
		// Initalisation de l'objet commande
		commande=new Commande();
		
		// Initialisation du format de la date
		dateFormat=new SimpleDateFormat("yyyyMMdd");
		
		try
		{	
			// On renseigne l'objet commande
			if(resultat.getString("id_commande")==null)
			{
				commande.setId(0);
			}
			else
			{
				commande.setId(resultat.getInt("id_commande"));
			}
			
			if(resultat.getString("id_client")==null)
			{
				commande.setClient(new Client());
			}
			else
			{
				commande.setClient(modeleClientCommande.getClient(resultat.getInt("id_client")));
			}
			
			if(resultat.getString("datecreationcommande")==null)
			{
				
				commande.setDateCreation(dateFormat.parse(""));
			}
			else
			{
				commande.setDateCreation(dateFormat.parse(resultat.getString("datecreationcommande")));
			}
			
			if(resultat.getString("datemodificationcommande")==null)
			{
				
				commande.setDateModification(dateFormat.parse(""));
			}
			else
			{
				commande.setDateModification(dateFormat.parse(resultat.getString("datemodificationcommande")));
			}
			
			if(resultat.getString("totalcommande")==null)
			{
				
				commande.setTotal(0);
			}
			else
			{
				commande.setTotal(resultat.getDouble("totalcommande"));
			}
			
			if(resultat.getString("totalpoids")==null)
			{
				
				commande.setTotalPoids(0);
			}
			else
			{
				commande.setTotalPoids(resultat.getDouble("totalpoids"));
			}
			
			if(resultat.getString("etatcommande")==null)
			{
				
				commande.setEtat(0);
			}
			else
			{
				commande.setEtat(resultat.getInt("etatcommande"));
			}
			
			// On récupert la liste des lignes de la commande
			commande.setListeLigneCommande(modeleLigneCommande.listerLigneCommande(commande.getId()));
		}
		catch (Exception e) 
		{
			// Si il y a une erreur durant le mapping des attributs avec la base on renvoie un objet null
			commande=null;
			System.out.println("Erreur lors du mappage des attributs d'une commande dans la classe ModeleCommande fonction commandeAttributsMapper");
		}
		
		// On retourne la commande
		return commande;
	}
	
}
