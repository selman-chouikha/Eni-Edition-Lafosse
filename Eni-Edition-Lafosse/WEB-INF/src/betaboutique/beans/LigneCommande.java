package betaboutique.beans;


/**
 * Classe LigneCommande
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class LigneCommande 
{
	// Attributs
	private int id;
	private int quantite;
	private double prixUnitaire;
	private Article article;
	
	// Constructeur par défaut
	public LigneCommande()
	{
		super();
	}
	
	public LigneCommande(Article article)
	{
		this.article=article;
		this.quantite=1;
		
		if(article.getReduction()==0)
		{
			this.prixUnitaire=article.getPrix();
		}
		else
		{
			this.prixUnitaire=article.getPrix() * (100 - article.getReduction()) / 100;
		}
	}

	// Accesseurs
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id=id;
	}

	public double getPrixUnitaire() 
	{
		return prixUnitaire;
	}

	public void setPrixUnitaire(double prixUnitaire) 
	{
		this.prixUnitaire=prixUnitaire;
	}

	public int getQuantite() 
	{
		return quantite;
	}

	public void setQuantite(int quantite) 
	{
		this.quantite=quantite;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) 
	{
		this.article=article;
	}
	
	public void ajouterQuantite()
	{
		this.quantite++;
	}
	
	public void reduireQuantite()
	{
		this.quantite--;
	}
	
	// Redéfinition des méthodes
	@Override
	public int hashCode() 
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((article == null) ? 0 : article.hashCode());
		return result;
	}

	// Egalité
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
			
		final LigneCommande other=(LigneCommande) obj;
		if (article == null) 
		{
			if (other.article!=null)
			{
				return false;
			}
				
		} 
		else if (!article.equals(other.article))
		{
			return false;
		}
			
		return true;
	}
	
	
	
}
