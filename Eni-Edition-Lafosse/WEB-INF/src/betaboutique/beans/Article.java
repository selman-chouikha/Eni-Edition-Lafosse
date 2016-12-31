package betaboutique.beans;

import java.util.Date;

/**
 * Classe Article
 * @author Jérôme Lafosse
 * @version 0.01
 */
public class Article 
{
	
	// Identifiant unique de l'article
	private int id;
	
	// Référence de l'article (utilisé pour la liaison avec la table articlecommande) 
	private int reference;
	
	// Nom de l'article
	private String nom;
	
	// Description de l'article
	private String description;
	
	// Prix TTC de l'article
	private double prix;
	
	// Pourcentage de reduction sur l'article
	private int reduction;
	
	// Date de création de l'article
	private Date date;
	
	// Chemin de la photo de l'article
	private String photo;
	
	// Chemin de la vignette de l'article
	private String vignette;
	
	// Etat de l'article (0 : inactif, 1 : actif)
	private int etat;
	
	// Poids en gramme de l'article 
	private int poids;
	
	// Moyenne des notes attribués à l'article
	private double note;
	
	// Nombre de votes réalisés sur l'article
	private int vote;
	
	// Nombre de cet article en stock
	private int stock;
	
	// Catégorie de l'article
	private Categorie categorie;
	
	
	// Constructeur par défaut
	public Article()
	{
		super();
	}
	
	// Permet de retourner la date de création de l'article
	public Date getDate() 
	{
		return date;
	}

	// Permet de définir la date de création de l'article
	public void setDate(Date date) 
	{
		this.date=date;
	}

	// Permet de retourner la description de l'article
	public String getDescription() 
	{
		return description;
	}

	// Permet de définir la description de l'article
	public void setDescription(String description) 
	{
		this.description=description;
	}

	// Permet de retourner l'état de l'article (0: inactif, 1: actif)
	public int getEtat() 
	{
		return etat;
	}

	// Permet de définir l'état de l'article
	public void setEtat(int etat)
	{
		this.etat=etat;
	}

	// Permet de retourner l'identifiant unique de l'article
	public int getId() 
	{
		return id;
	}

	// Permet de définir l'identifiant unique de l'article
	public void setId(int id) 
	{
		this.id=id;
	}

	// Permet de retourner le nom de l'article
	public String getNom() 
	{
		return nom;
	}

	// Permet de définir le nom de l'article
	public void setNom(String nom) 
	{
		this.nom=nom;
	}

	// Permet de retourner la moyenne des notes de l'article
	public double getNote() 
	{
		return note;
	}

	// Permet de définir la moyenne des notes de l'article
	public void setNote(double note) 
	{
		this.note=note;
	}

	// Permet de retourner le nombre de votes de cet article
	public int getVote() 
	{
		return vote;
	}

	// Permet de définir le nombre de votes de cet article
	public void setVote(int vote) 
	{
		this.vote=vote;
	}

	// Permet de retourner le chemin de la photo de l'article
	public String getPhoto() 
	{
		return photo;
	}

	// Permet de définir le chemin de la photo de l'article
	public void setPhoto(String photo)
	{
		this.photo=photo;
	}

	// Permet de retourner le poids en grammes de l'article
	public int getPoids() 
	{
		return poids;
	}

	// Permet de définir le poids en grammes de d'article
	public void setPoids(int poids)
	{
		this.poids=poids;
	}

	// Permet de retourner le prix de l'article
	public double getPrix() 
	{
		return prix;
	}

	// Permet de définir le prix TTC de l'article
	public void setPrix(double prix) 
	{
		this.prix=prix;
	}

	// Permet de retourner le stock de cet article
	public int getStock()
	{
		return stock;
	}

	// Permet de définir le stock de cet article
	public void setStock(int stock)
	{
		this.stock=stock;
	}

	// Permet de retourner le chemin de la vingette de cet artice
	public String getVignette() 
	{
		return vignette;
	}

	// Permet de définir le chemin de la vignette de cette article
	public void setVignette(String vignette) 
	{
		this.vignette=vignette;
	}

	// Permet de retourner la catégorie de l'article
	public Categorie getCategorie() 
	{
		return categorie;
	}

	// Permet de définir la catégorie de l'article
	public void setCategorie(Categorie categorie) 
	{
		this.categorie =categorie;
	}

	// Permet de retourner la redunction appliquée a cet article
	public int getReduction() 
	{
		return reduction;
	}

	// Permet de définir la reduction appliqué a cet article
	public void setReduction(int reduction) 
	{
		this.reduction=reduction;
	}
	
	// Permet de retourner la référence de l'article (référence table articlecommande - table article)
	public int getReference() 
	{
		return reference;
	}

	// Permet de définir la référence de l'article
	public void setReference(int reference) 
	{
		this.reference=reference;
	}

	// Cette méthode est utilisée dans la comparaison de 2 objet article.
	@Override
	public int hashCode() 
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((date == null) ? 0 : date.hashCode());
		result = PRIME * result + id;
		result = PRIME * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	//Cette méthode est utilisé dans la comparaison de 2 objet article.
	@Override
	public boolean equals(Object obj)
	{
		if (this==obj)
		{
			return true;
		}	
		if (obj==null)
		{
			return false;
		}
		if (getClass()!=obj.getClass())
		{
			return false;
		}
			
		final Article other=(Article)obj;
		
		if (date==null) 
		{
			if (other.date!=null)
			{
				return false;
			}
				
		} 
		else if (!date.equals(other.date))
		{
			return false;
		}	
		if (id!=other.id)
		{
			return false;
		}
		if (nom==null) 
		{
			if (other.nom != null)
			{
				return false;
			}
				
		} 
		else if (!nom.equals(other.nom))
		{
			return false;
		}	
		return true;
	}
	
	
}
