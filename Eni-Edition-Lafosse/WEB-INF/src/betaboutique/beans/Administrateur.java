package betaboutique.beans;

/**
 * Classe Administrateur
 * @author Jérôme Lafosse
 * @version 0.01
 */
public class Administrateur 
{	
	// Identifiant unique du compte administrateur
	private int id;
	
	// Nom du propriétaire 
	private String nom;
	
	// Prénom du propriétaire
	private String prenom;
	
	// Adresse de messagerie du propriétaire
	private String mail;
	
	// Identifant du compte administrateur
	private String identifiant;
	
	// Mot de passe haché au format SHA1
	private String motDePasse;
	

	
	// Constructeur par défaut
	public Administrateur()
	{
		super();
	}

	// Fonction permettant de retourner l'id du compte
	public int getId() 
	{
		return id;
	}
	
	// Fonction permettant de définir l'identifiant unique du compte
	public void setId(int id) 
	{
		this.id=id;
	}
	
	// Permet de retourner le mail du propriétaire
	public String getMail() 
	{
		return mail;
	}

	// Permet de définir le mail du propriétaire
	public void setMail(String mail) 
	{
		this.mail=mail;
	}

	// Permet de retourner le nom du propriétaire
	public String getNom() 
	{
		return nom;
	}

	// Permet de définir le nom du propriétaire
	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	// Permet de retourner le prénom du propriétaire
	public String getPrenom() 
	{
		return prenom;
	}

	// Permet de définir le prénom du propriétaire
	public void setPrenom(String prenom) 
	{
		this.prenom=prenom;
	}

	// Permet de retourner le login du compte
	public String getIdentifiant() 
	{
		return identifiant;
	}

	// Permet de définir l'identifiant du compte
	public void setIdentifiant(String identifiant) 
	{
		this.identifiant=identifiant;
	}

	// Permet de retourner le mot de passe du compte haché en SHA1
	public String getMotDePasse() 
	{
		return motDePasse;
	}

	// Permet de définir le mot de passe du compte 
	public void setMotDePasse(String motDePasse) 
	{
		this.motDePasse=motDePasse;
	}
}
