package betaboutique.servlets.administrateur;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.missiondata.fileupload.MonitoredDiskFileItemFactory;
import betaboutique.outils.FileUploadListener;
import betaboutique.outils.GestionDroit;

/**
 * Classe ServletUpload
 * @author J�r�me Lafosse
 * @version 0.01
 */
@SuppressWarnings("serial")
public class ServletUpload extends HttpServlet 
{	
	// Variables
	private GestionDroit gestionDroit=null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{ 
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// Initialisation de la gestion des droits
		gestionDroit=new GestionDroit(request);
			
		// On autorise uniquement les administrateurs
		if(!gestionDroit.estAutorise(true,false,false))
		{	
			getServletContext().getRequestDispatcher("/pageFixe?action=erreur&erreurs=Vous n'�tes pas autoris� � afficher cette page!!").forward(request, response);
		}
		else
		{
			// On r�cupert l'action en cours 
			String action=request.getParameter("action");		
			if(action==null)
			{
				action="upload";
			}
			
			// On r�cupert le status du chargement 
			if (action.equals("status")) 
			{
				doStatus(request, response);	
			} 
	
			// Chargement du fichier 
			if(action.equals("upload"))
			{
				doFileUpload(request, response);
			}
		}
	}

	
	// M�thode de chargement
	private void doFileUpload(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		// Variables
		List<String> erreurs=null;
		HttpSession session=null;
		FileUploadListener ecouteur=null;
		FileItemFactory factory=null;
		ServletFileUpload upload=null;
		List<File> fichiers=null;
		FileItem fichier=null;
		File fichierCharger=null;
		File imageTemporaire=null;
		File image=null;
		File vignette=null;
		String dossierImage=null;
		int tailleMax=0;
		
		// On r�cupert les attributs du context de l'application 
		tailleMax=Integer.parseInt(getServletContext().getInitParameter("uploadTailleMax"));
		dossierImage=getServletContext().getInitParameter("dossierImage");
		
		// On r�cupert la session de l'utilisateur 
		session=request.getSession();
		
		// Initialisation des message d'erreur 
		erreurs=new ArrayList<String>();
		
		try 
		{	
			// On d�finit un listeneur qui permet de r�cup�rer les statistiques de chargement du fichier 
			ecouteur=new FileUploadListener(request.getContentLength());
			
			// On le place dans la session de l'utilisateur
			session.setAttribute("FILE_UPLOAD_STATS", ecouteur.getFileUploadStats());
			
			// On cr�e l'objet permettant d'uploader le fichier 
			factory=new MonitoredDiskFileItemFactory(ecouteur);
			upload=new ServletFileUpload(factory);
			
			// On r�cupert la liste des fichiers depuis la requ�te 
			fichiers=upload.parseRequest(request);
			
			// Pour chacun des fichiers on l'�crit sur le disque 
			for (Iterator i=fichiers.iterator(); i.hasNext();) 
			{
				fichier=(FileItem)i.next();
				if (!fichier.isFormField()) 
				{
					// On teste la taille du fichier 
					if(fichier.getSize()>tailleMax )
					{
						// Si le fichier est trop grand informer l'utilisateur 
						erreurs.add("La taille du fichier ne doit pas d�passer " + formaterTaille(tailleMax));
					}
					
					// On cr�e un fichier au format java.io.File pour r�cup�rer facilement le nom du fichier 
					fichierCharger=new File(fichier.getName());
					
					// On teste son extensions : GIF | JPEG | BMP | PNG 
					if(!extension(fichierCharger).equals("bmp") && !extension(fichierCharger).equals("jpeg") && !extension(fichierCharger).equals("png") && !extension(fichierCharger).equals("jpg") && !extension(fichierCharger).equals("gif"))
					{
						// Si l'extension est incorrecte on affiche un message d'erreur 
						erreurs.add("Extension du fichier non valide (BMP, GIF, JPEG, JPG ou PNG)");
					}
					
					// On envoie la liste d'erreurs dans la requ�te 
					if(erreurs.size()>0)
					{
						request.setAttribute("erreurs", erreurs);
					}
					else
					{
						// Si il n'y a aucune erreur on �crit l'image sur le disque 
						imageTemporaire=new File( dossierImage + fichierCharger.getName());
						fichier.write(imageTemporaire);
						
						// Puis on cr�e la photo de l'article 
						image=new File( dossierImage + "grande" + fichierCharger.getName());
						creerImage(imageTemporaire, image);
						
						// Puis la vignette 
						vignette=new File( dossierImage + fichierCharger.getName());
						creerVignette(imageTemporaire, vignette);
						
						// On passe les informations dans la requ�te 
						request.setAttribute("image", image.getName());
						request.setAttribute("vignette", vignette.getName());
					}
					
					// On supprime le fichier de la liste de fichiers charg�e
					fichier.delete();
				}
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		finally
		{
			// On informe la page JSP qu'il s'agit des informations de l'upload 
			request.setAttribute("type", "upload");
			
			// On renvoie la page JSP 
			getServletContext().getRequestDispatcher("/vues/ajax/upload.jsp").forward(request,response);
		}
	}

	
	// Afficher l'�tat du chargement montant
	private void doStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// Variables 
		HttpSession session=null;
		FileUploadListener.FileUploadStats status;
		long octetLu=0;
		long tailleTotal=0;
		long pourcentageEffectue=0;
		long tempEffectue=0;
		double vitesseChargement=0;
		double tempTotal=0;
		
		// On r�cupert la session de l'utilisateur 
		session=request.getSession();

		// On r�cupert l'�couteur plac� dans la session 
		status=(FileUploadListener.FileUploadStats) session.getAttribute("FILE_UPLOAD_STATS");
		
		// Supprime le cache dans la r�ponse 
		response.addHeader("Expires", "0");
	    response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	    response.addHeader("Pragma", "no-cache");
		
		if (status!=null) 
		{
			// On r�cupert depuis l'�couteur le status du chargement 
			octetLu=status.getBytesRead();
			tailleTotal=status.getTotalSize();
			pourcentageEffectue=(long)Math.floor(((double) octetLu / (double) tailleTotal)*100.0);
			tempEffectue=status.getElapsedTimeInSeconds();
			vitesseChargement=octetLu/(tempEffectue+0.00001);
			tempTotal=tailleTotal/(vitesseChargement+0.00001);
			
			// On passe ces variables dans la requ�te 		
			request.setAttribute("type", "status");
			request.setAttribute("pourcentageEffectue", pourcentageEffectue);
			request.setAttribute("tailleTotal", formaterTaille(tailleTotal));
			request.setAttribute("octetLu", formaterTaille(octetLu));
			request.setAttribute("tempRestant", formaterTemp(tempTotal - tempEffectue));

			// On renvoie la page JSP 
			getServletContext().getRequestDispatcher("/vues/ajax/upload.jsp").forward(request,response);
		}
	}
	
	
	
	// Cr�er une image
	private void creerImage(File source, File destination)
	{
		// Variables
		BufferedImage image=null;
		double largeur=0;
		double hauteur=0;
		
		try
		{
			// On r�cupert l'image d'origine et ses dimensions 
			image=ImageIO.read(source);
			largeur=image.getWidth();
			hauteur=image.getHeight();
			
			// On r�duit les dimensions de l'image de 1% jusqu'a obtenir une image de 240*170 pixels tout en gardant le rapport largeur hauteur 
			while(largeur>170 || hauteur>240)
			{
				largeur=(double)largeur*0.99;
				hauteur=(double)hauteur*0.99;
			}
			
			// R�duction de l'image 
			redimensionner(image, destination, (int)largeur, (int)hauteur);
		}
		catch (Exception e) 
		{
			
		}
	}
	
	
	
	// Cr�er une vignette de l'image associ�e
	private void creerVignette(File source, File destination)
	{
		// Variables 
		BufferedImage image=null;
		double largeur=0;
		double hauteur=0;
		
		try
		{
			// On r�cupert l'image d'origine et ses dimensions 
			image=ImageIO.read(source);
			largeur=image.getWidth();
			hauteur=image.getHeight();
			
			// On r�duit les dimensions de l'image de 1% jusqu'a obtenir une image de 240*170 pixels tout en gardant le rapport largeur hauteur 
			while(largeur>110 || hauteur>160)
			{
				largeur=(double)largeur*0.99;
				hauteur=(double)hauteur*0.99;
			}
			
			// R�duction de l'image 
			redimensionner(image, destination, (int)largeur, (int)hauteur);
		}
		catch (Exception e) 
		{
			
		}
	}
	
	// Redimensionner l'image associ�e
	private void redimensionner(BufferedImage source, File destination, int width, int height)
	{
		// Variables 
		BufferedImage buffer=null;
		Graphics2D graphique=null;
		
		try
		{
			// On cr�e une nouvelle image aux bonnes dimensions
			buffer=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			// On dessine sur le Graphics de l'image bufferis�e
		    graphique=buffer.createGraphics();
		    graphique.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    graphique.drawImage(source, 0, 0, width, height, null);
		    graphique.dispose();
		    
		    // Ecriture de l'image au format JPEG 
		    ImageIO.write(buffer, "png", destination);
		} 
		catch (Exception e) 
		{
	    	System.out.println(e);
	    } 
	}
	
	
	
	// V�rifier l'extension d'un fichier
	private String extension(File fichier)
	{
		return fichier.getName().split("\\.")[fichier.getName().split("\\.").length-1];
	}
	

	
	// Formater les secondes
	private String formaterTemp(double secondes) 
	{
		// On transforme les secondes en heure - minutes - secondes 
		long seconde=(long) Math.floor(secondes);
		long minute=(long) Math.floor(secondes/60.0);
		long heure=(long) Math.floor(minute/60.0);

		// On applique le suffixe 
		if (heure!=0) 
		{
			return heure + "heures " + (minute%60) + " minutes " + (seconde%60) + " secondes";
		} 
		else if (minute%60!=0) 
		{
			return (minute%60) + " minutes " + (seconde%60) + " secondes";
		} 
		else 
		{
			return (seconde%60) + " secondes";
		}
	}

	
	// Formater la taille
	private String formaterTaille(long taille) 
	{
		// Variables
		String[] suffixes=null;
		double tailleTemporaire=0;
		int compteur=0;
		
		// On d�finit tous les suffixes de taille 
		suffixes=new String[] { "octets", "Ko", "Mo", "Go", "To" };

		// On cast la taille en double 
		tailleTemporaire=taille;
		
		// On cherche le suffixe � appliquer 
		while (tailleTemporaire>=1024) 
		{
			tailleTemporaire/=1024.0;
			compteur++;
		}

		// On fait un arrondi � 10-2 pr�s 
		tailleTemporaire*=100;
		tailleTemporaire=(int)(tailleTemporaire+0.5);
		tailleTemporaire/=100;

		// On renvoie la taille 
		return tailleTemporaire + " " + suffixes[compteur];
	}
	
	
	
}