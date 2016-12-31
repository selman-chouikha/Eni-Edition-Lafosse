package betaboutique.beans;


/**
 * Classe Raccourci
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class Raccourci 
{
	// Attributs
	private int id;
	private String url;
	private String nom;
	
	// Constructeur par défaut
	public Raccourci()
	{
		super();
	}

	// Acesseurs
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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url=url;
	}	
	
	
}
