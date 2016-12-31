package betaboutique.beans;

/**
 * Classe Administrateur
 * @author J�r�me Lafosse
 * @version 0.01
 */
public class Administrateur 
{	
	// Identifiant unique du compte administrateur
	private int id;
	
	// Nom du propri�taire 
	private String nom;
	
	// Pr�nom du propri�taire
	private String prenom;
	
	// Adresse de messagerie du propri�taire
	private String mail;
	
	// Identifant du compte administrateur
	private String identifiant;
	
	// Mot de passe hach� au format SHA1
	private String motDePasse;
	

	
	// Constructeur par d�faut
	public Administrateur()
	{
		super();
	}

	// Fonction permettant de retourner l'id du compte
	public int getId() 
	{
		return id;
	}
	
	// Fonction permettant de d�finir l'identifiant unique du compte
	public void setId(int id) 
	{
		this.id=id;
	}
	
	// Permet de retourner le mail du propri�taire
	public String getMail() 
	{
		return mail;
	}

	// Permet de d�finir le mail du propri�taire
	public void setMail(String mail) 
	{
		this.mail=mail;
	}

	// Permet de retourner le nom du propri�taire
	public String getNom() 
	{
		return nom;
	}

	// Permet de d�finir le nom du propri�taire
	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	// Permet de retourner le pr�nom du propri�taire
	public String getPrenom() 
	{
		return prenom;
	}

	// Permet de d�finir le pr�nom du propri�taire
	public void setPrenom(String prenom) 
	{
		this.prenom=prenom;
	}

	// Permet de retourner le login du compte
	public String getIdentifiant() 
	{
		return identifiant;
	}

	// Permet de d�finir l'identifiant du compte
	public void setIdentifiant(String identifiant) 
	{
		this.identifiant=identifiant;
	}

	// Permet de retourner le mot de passe du compte hach� en SHA1
	public String getMotDePasse() 
	{
		return motDePasse;
	}

	// Permet de d�finir le mot de passe du compte 
	public void setMotDePasse(String motDePasse) 
	{
		this.motDePasse=motDePasse;
	}
}
