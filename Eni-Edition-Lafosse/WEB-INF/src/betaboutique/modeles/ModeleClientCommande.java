package betaboutique.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import betaboutique.outils.GestionBaseDeDonnees;
import betaboutique.beans.Client;

/**
 * Classe ModeleClientCommande
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class ModeleClientCommande 
{
	// Variables
	DataSource datasource=null;
	Connection connexion=null;
	ResultSet resultat=null;
	
	// Constructeur
	public ModeleClientCommande (DataSource datasource)
	{
		this.datasource=datasource;
	}
    
	// Récupérer le client
    public Client getClient(int idClient)
    {
    	// Variables
    	PreparedStatement requete=null;
    	Client client=null;
    	String requeteString=null;
    
    	try
    	{
    		// Ouverture d'une connexion
    		connexion=datasource.getConnection();
    		
    		// Création de la requête
    		requeteString="SELECT * FROM clientcommande where id_clientcommande = ?";
    		
    		// Preparer la requete
    		requete=connexion.prepareStatement(requeteString);
    		requete.setInt(1,idClient);
    			
    		// Execution de la requête
    		resultat=requete.executeQuery();
    		
    		// On stocke le resultat dans l'objet client
    		if(resultat != null)
    		{
    			if(resultat.next())
    			{
    				// On réalise le mapping des attribus du client dans la classe avec la base
    				client=clientAttribusMapper(resultat); 				
    			}
    		}
       	}
    	catch(Exception e)
    	{
    		// Si l'identifant du client n'existe pas, on initialise l'objet client à null
			client=null;
    		System.out.println("Erreur dans la requête dans la classe ModeleClientCommande.java fonction getClient");    		
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
    			System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleClientCommande.java fonction getClient");
    		}	
    	}
    	
    	// Retourner un client
    	return client;
    }
    
    
    // Modifier une adresse de livraison pour le client
    public int modifierAdresseLivraison(Client client)
    {
    	// Variables
    	int codeErreur=0;
    	PreparedStatement requete=null;
    	String requeteString=null;
    
    	try
    	{
    		// Ouverture d'une connexion
    		connexion=datasource.getConnection();
    		
    		// Annulation de l'autocommit
    		connexion.setAutoCommit(false);
    		
    		// Création de la requête
    		requeteString="UPDATE clientcommande SET nomclient = ? , prenomclient = ? , adresseclient = ? , codepostalclient = ?, villeclient = ? WHERE id_clientcommande = ?";
    		
    		// Preparer la requete
    		requete=connexion.prepareStatement(requeteString);
    		requete.setString(1,client.getNom());
			requete.setString(2,client.getPrenom());
			requete.setString(3,client.getAdresse());
			requete.setString(4,client.getCodePostal());
			requete.setString(5,client.getVille());
			requete.setInt(6,client.getId());
    			
    		// Execution de la requête
    		codeErreur=requete.executeUpdate();
    		
    		// Si il y a une erreur durant la modification de l'adresse de livraison, on fait un rollback
			if(codeErreur!=1)
			{
				GestionBaseDeDonnees.rollback(connexion);
			}
			else
			{
				GestionBaseDeDonnees.commit(connexion);
			}
			
			// On vide le client par sécurité
			client=null;
       	}
    	catch(Exception e)
    	{
			codeErreur=0;
			System.out.println(e);
			GestionBaseDeDonnees.rollback(connexion);
    		System.out.println("Erreur dans la requête dans la classe ModeleClientCommande.java fonction modifierAdresseLivraison");    		
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
    			System.out.println("Erreur lors de la fermeture de la connexion avec la base de données dans la classe ModeleClientCommande.java fonction modifierAdresseLivraison");
    		}	
    	}
    	
    	// Retourner un client
    	return codeErreur;
    }
   
    
    
    
    // Réaliser la transformation Relationnel vers Objet
	public Client clientAttribusMapper (ResultSet resultat)
	{
		// Variables
		Client client=null;
		
		// Initialisation du client
		client=new Client();
		
		try 
		{	
			if (resultat.getString("id_clientcommande")==null)
			{
				client.setId(0);	
			}
			else 
			{
				client.setId(resultat.getInt("id_clientcommande"));	
			}
			
			if (resultat.getString("nomclient")==null)
			{
				client.setNom("");
			}
			else 
			{
				client.setNom(resultat.getString("nomclient"));	
			}
			
			if (resultat.getString("prenomclient")==null)
			{
				client.setPrenom("");	
			}
			else 
			{
				client.setPrenom(resultat.getString("prenomclient"));	
			}	
			
			if (resultat.getString("emailclient")==null)
			{
				client.setMail("");
			}
			else 
			{
				client.setMail(resultat.getString("emailclient"));	
			}
			
			if (resultat.getString("telephoneclient")==null)
			{
				client.setTelephone("");
			}
			else 
			{
				client.setTelephone(resultat.getString("telephoneclient"));	
			}
			
			if (resultat.getString("adresseclient")==null)
			{
				client.setAdresse("");
			}
			else 
			{
				client.setAdresse(resultat.getString("adresseclient"));	
			}
			
			if (resultat.getString("telephoneclient")==null)
			{
				client.setTelephone("");
			}
			else 
			{
				client.setTelephone(resultat.getString("telephoneclient"));	
			}
			
			if (resultat.getString("villeclient")==null)
			{
				client.setVille("");
			}
			else 
			{
				client.setVille(resultat.getString("villeclient"));	
			}
			
			if (resultat.getString("codepostalclient")==null)
			{
				client.setCodePostal("");
			}
			else 
			{
				client.setCodePostal(resultat.getString("codepostalclient"));	
			}
			
			if (resultat.getString("paysclient")==null)
			{
				client.setPays("");
			}
			else 
			{
				client.setPays(resultat.getString("paysclient"));	
			}
			
			if (resultat.getString("identifiantclient")==null)
			{
				client.setIdentifiant("");
			}
			else 
			{
				client.setIdentifiant(resultat.getString("identifiantclient"));	
			}
			
			if (resultat.getString("motdepasseclient")==null)
			{
				client.setMotDePasse("");
			}
			else 
			{
				client.setMotDePasse(resultat.getString("motdepasseclient"));	
			}
		}
	    catch (Exception e)
	    {
	    	// Si il y a une erreur durant le mapping des attributs avec la base on renvoi un objet null
	    	client=null;
	    	System.out.println("Erreur lors du mappage des attributs d'un client dans la class ModeleClientCommande, fonction clientAttribusMapper");
	    }
	    
	    // On retourne le client
	    return client;
	}
	
	
	

	
}