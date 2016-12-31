package betaboutique.beans;

import java.util.List;


/**
 * Classe Client
 * @author Jérôme Lafosse
 * @version 0.01
 */ 
public class Client 
{
	// Attributs 
	private int id;
	private String nom;
	private String prenom;
	private String mail;
	private String telephone;
	private String adresse;
	private String codePostal;
	private String ville;
	private String pays;
	private String identifiant;
	private String motDePasse;
	private List<Raccourci> listeFavoris;
	private List<Commande> listeCommande;
	
	// Constructeur par défaut
	public Client()
	{
		super();
	}

	// Accesseurs 
	public String getAdresse()
	{
		return adresse;
	}

	public void setAdresse(String adresse) 
	{
		this.adresse=adresse;
	}

	public String getCodePostal() 
	{
		return codePostal;
	}

	public void setCodePostal(String codePostal) 
	{
		this.codePostal=codePostal;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id=id;
	}

	public String getIdentifiant()
	{
		return identifiant;
	}

	public void setIdentifiant(String identifiant)
	{
		this.identifiant=identifiant;
	}

	public String getMail() 
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail=mail;
	}

	public String getMotDePasse()
	{
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse)
	{
		this.motDePasse=motDePasse;
	}

	public String getNom() 
	{
		return nom;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	public String getPays()
	{
		return pays;
	}

	public void setPays(String pays)
	{
		this.pays = pays;
	}

	public String getPrenom()
	{
		return prenom;
	}

	public void setPrenom(String prenom) 
	{
		this.prenom=prenom;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone) 
	{
		this.telephone=telephone;
	}

	public String getVille() 
	{
		return ville;
	}

	public void setVille(String ville) 
	{
		this.ville=ville;
	}
	
	public List<Raccourci> getListeFavoris()
	{
		return listeFavoris;
	}
	
	public void setListeFavoris(List<Raccourci> listeFavoris)
	{
		this.listeFavoris=listeFavoris;
	}

	public List<Commande> getListeCommande() 
	{
		return listeCommande;
	}

	public void setListeCommande(List<Commande> listeCommande) 
	{
		this.listeCommande=listeCommande;
	}

	
	
	
}
