package betaboutique.outils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 * Classe Hachage : cette classe permet de g�n�rer un hash MD5 ou SHA1 d'une cha�ne de
 * caract�res. Cette classe ne comprant que des m�thodes dites static. Il n'est pas donc pas n�cessaire
 * d'instancier un objet de cette classe pour utiliser ses fonctionnalit�s.
 * <br><br>
 * @author LAFOSSE J�r�me
 * @version 0.01
 */
public class Hachage 
{

	// Fonction permet de g�n�rer un Hash MD5 d'une cha�ne de caract�res
	public static String MD5(final String texteClair) 
	{
		return hash("MD5", texteClair);
	}
	
	// Fonction permet de g�n�rer un Hash SHA1 d'une cha�ne de caract�res
	public static String SHA1(final String texteClair) 
	{
		return hash("SHA1", texteClair);
	}
	
	// Fonction permettant de g�n�rer le hachage
	private static String hash(final String algorythme, final String texteClair)
	{
		// D�finition des variables
		BASE64Encoder encodeur=null;
		byte [] hash=null;
		MessageDigest fonctionDeHachage=null;
	
		// Choix de la fonction de hachage
		if (algorythme.equals("MD5") || algorythme.equals("SHA1"))
		{
			try
			{
				// Chargement de la fonction de hachage
				fonctionDeHachage=MessageDigest.getInstance(algorythme);
				
				// On applique la fonction de hachage sur le mot de passe en clair
				hash=fonctionDeHachage.digest(texteClair.getBytes());
				
				// On encode en base64
				encodeur=new BASE64Encoder();
				return encodeur.encode(hash);
			}
			catch (NoSuchAlgorithmException e)
			{
				// Renvoyer une cha�ne null
				return null;
			}
		}

		// Renvoyer une cha�ne null
		return null;
	}
	
	
}
