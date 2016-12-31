package betaboutique.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Classe Commande
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class Commande 
{
	// Attributs 
	private int id;
	private Date dateCreation;
	private Date dateModification;
	private int etat;
	private double total;
	private double totalPoids;
	private List<LigneCommande> listeLigneCommande;
	private Client client;
	
	// Constructeur 
	public Commande()
	{
		super();
		
		// Initialisation du poids et du prix
		total=0;
		totalPoids=0;
		etat=0;
		dateCreation=new Date();
		dateModification=new Date();
		
		// On initialise la liste des articles
		listeLigneCommande=new ArrayList<LigneCommande>();
	}

	// Accesseurs 
	public Client getClient() 
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client=client;
	}

	public Date getDateCreation() 
	{
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation)
	{
		this.dateCreation=dateCreation;
	}

	public Date getDateModification() 
	{
		return dateModification;
	}

	public void setDateModification(Date dateModification) 
	{
		this.dateModification=dateModification;
	}

	public int getEtat()
	{
		return etat;
	}

	public void setEtat(int etat)
	{
		this.etat=etat;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id=id;
	}

	public List<LigneCommande> getListeLigneCommande()
	{
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) 
	{
		this.listeLigneCommande=listeLigneCommande;
	}

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total) 
	{
		this.total=total;
	}

	public double getTotalPoids() 
	{
		return totalPoids;
	}

	public void setTotalPoids(double totalPoids)
	{
		this.totalPoids=totalPoids;
	}
	
	// Méthodes publiques
	public void ajouterArticle(Article article)
	{
		// Variables
		LigneCommande ligneCommande=null;
		
		// Initialisation de la nouvelle ligne
		ligneCommande=new LigneCommande(article);
		
		// On vérifie que la ligne (article) n'existe pas déjà
		if(listeLigneCommande.contains(ligneCommande))
		{
			// Si l'article existe on ajoute 1 à la quantitié
			ligneCommande=listeLigneCommande.get(listeLigneCommande.indexOf(ligneCommande));
			ligneCommande.ajouterQuantite();
		}
		else
		{
			// Sinon on ajoute la ligne à la liste
			listeLigneCommande.add(ligneCommande);
		}
		
		// On redéfinit le poids et le prix
		this.total+=ligneCommande.getPrixUnitaire();
		this.totalPoids+=ligneCommande.getArticle().getPoids();
	}
	
	
	public void supprimerUnArticle(Article article)
	{
		// Variables
		LigneCommande ligneCommande=null;
		
		// Initialisation de la nouvelle ligne
		ligneCommande=new LigneCommande(article);
		
		// On vérifie que la ligne (article) existe déjà
		if(listeLigneCommande.contains(ligneCommande))
		{
			// On récupert la ligne de la liste
			ligneCommande=listeLigneCommande.get(listeLigneCommande.indexOf(ligneCommande));

			// Si la quantite est au moins de 2, on enléve 1 à la quantite
			if(ligneCommande.getQuantite() > 1)
			{
				ligneCommande.reduireQuantite();
			}
			// Sinon on supprime la ligne
			else
			{
				listeLigneCommande.remove(ligneCommande);
			}
			// On redéfinit le poids et le prix
			this.total-=ligneCommande.getPrixUnitaire();
			this.totalPoids-=ligneCommande.getArticle().getPoids();
		}
	}
	
	
	public void supprimerArticle(Article article)
	{
		// Variables
		LigneCommande ligneCommande=null;
		
		// Initialisation de la nouvelle ligne
		ligneCommande=new LigneCommande(article);
		
		// On vérifie que la ligne (article) existe déjà
		if(listeLigneCommande.contains(ligneCommande))
		{
			// On récupert la ligne de la liste
			ligneCommande = listeLigneCommande.get(listeLigneCommande.indexOf(ligneCommande));

			// On redéfinit le poids et le prix
			this.total-=ligneCommande.getPrixUnitaire()*ligneCommande.getQuantite();
			this.totalPoids-=ligneCommande.getArticle().getPoids()*ligneCommande.getQuantite();
			
			// On supprime la ligne
			listeLigneCommande.remove(ligneCommande);
		}
	}
	
	
	
	
}
