package betaboutique.beans;

import java.util.List;

/**
 * Classe Categorie
 * @author J�r�me Lafosse
 * @version 0.01
 */ 
public class Categorie 
{
	// Attributs 
	private int id;
	private String nom;
	private List<Article> listeArticle;
	
	// Constructeur par d�faut
	public Categorie()
	{
		super();
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

	public String getNom() 
	{
		return nom;
	}

	public void setNom(String nom) 
	{
		this.nom=nom;
	}

	public List<Article> getListeArticle() 
	{
		return listeArticle;
	}

	public void setListeArticle(List<Article> listeArticle)
	{
		this.listeArticle=listeArticle;
	}

}
