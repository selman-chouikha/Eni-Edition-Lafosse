/**
 * Fonction permettant d'afficher ou de masquer le formulaire d'ajout
 * d'un raccourcis au clic sur le lien "mes raccourcis" du client
 */
function afficherRaccourci()
{
	if ($("#conteneurraccourci").is(":hidden")) 
	{
		$("#conteneurraccourci").show("slow");
	} 
	else 
	{
      fermerRaccourci();
    }
}

/**
 * Fonction permettant de masquer le formulaire d'ajout d'un raccourci
 */
function fermerRaccourci()
{
	$("#conteneurraccourci").hide("slow");
}

/**
 * Fonction permettant r�cup�rer en ajax les raccourcis d'un utilisateur
 * connect� en tant que client. Les donn�es sont r�cup�r�s au format XML
 * et parser par la fonction "afficherListeRaccourci"
 */
$(document).ready(function()
{
	$.ajax(
	{
		type: "GET",
		url: "gestionRaccourci?action=lister",
		dataType: "xml",
		timeout : 8000,
		success: function(xml)
		{
			/* On affiche tous les raccourcis de l'utilisateur */
			afficherListeRaccourci(xml);
		}
	});
});

/**
 * Fonction permettant de parser le r�sultat (XML) de la fonction "initialiserRaccourci".
 * Chaque raccourci est alors ajout� au tableau "listeraccourci".
 */
function afficherListeRaccourci(xml)
{
	/* On r�cupert le tableau qui contiendra la liste des raccourcis */
	var listeRaccourci = document.getElementById("listeraccourci");
	
	/* On supprime les �l�ments du tableau */
	while (listeRaccourci.childNodes.length > 0) 
	{
		listeRaccourci.removeChild(listeRaccourci.firstChild);
	}
	
	/* Si la liste des raccourcis contenu dans le fichier xml est sup�rieur � 0 
	 * on affiche l'entete du tableau 
	 */
	if($("raccourcis>raccourci",xml).size() > 0)
	{
		/* On cr�� un tbody pour ie */
		var tbody = document.createElement("tbody");
		listeRaccourci.appendChild(tbody);
	
		/* On cr�� une nouvelle colonne et une nouvelle ligne */
		tr = document.createElement("tr");
	    td = document.createElement("td");
	    
	    /* On d�finit le style de la ligne */
	    td.colSpan = '2';
		td.innerHTML = '<b>Vos favoris : </b>';
		
		/* On ajoute les �l�ments au tableau */
		tr.appendChild(td);
		tbody.appendChild(tr);	
	}
	
	/* On ajoute chaque raccourci � la listr */
	$("raccourcis>raccourci",xml).each
	(
		function()
		{				
			/* On r�cupert le nom et l'url */
			var nom = $(this).attr("nom");
			var url = $(this).attr("url");
			var id = $(this).attr("id");
			
			/* On cr�� une nouvelle colonne et une nouvelle ligne */
			tr = document.createElement("tr");
	    	td = document.createElement("td");
	    
	    	/* On ajoute une colonne contenant le lien pour supprimer le raccourci */
	    	td.className  = "lienraccourci";
	    	td.width = '20';
	    	td.innerHTML = '&nbsp;&nbsp;<img src="images/application/supprimer.png" title="Supprimer ce favori" style="cursor:pointer" onclick="supprimerRaccourci('+id+');"/>';

	    	/* On ajoute la colonne � la ligne */
	    	tr.appendChild(td);
	    	
	    	/* On ajoute une colonne contenant le lien vers ce raccourci */
	    	td = document.createElement("td");
	    	td.className  = "lienraccourci";
	    	td.innerHTML = '<a href="' + url + '">' + nom + '</a>';

			/* On ajoute la colonne � la ligne */
	    	tr.appendChild(td);
	    	
	    	/* On ajoute la ligne au tbody */
	    	tbody.appendChild(tr);
		}
	);
}

/**
 * Fonction permettant d'ajouter un raccourci en ajax. Apr�s l'appel de la servlet
 * on r�cupert un code de retour indiquant si l'ajout s'est effectu� avec succ�s.
 * en cas d'echec, on affiche un message d'erreur et en cas de succ�s on r�affiche
 * la liste compl�te des raccourcis utilisateur.
 */
function ajouterRaccourci()
{
	/* On r�cupert le nom du raccouci */
	var nom = $("#nomraccourci").val();
	
	/* On vide le champ nom */
	$("#nomraccourci").val("");

	/* On r�cupert le le conteneur de message */
	var messageraccourci = $("#messageraccourci");

	/* On test si le nom renseign� est vide ou non */
	if(nom == '')
	{
		/* On ajoute un message d'erreur */
		messageraccourci.text("Veuillez renseigner le nom");
	}
	else
	{
		$.ajax(
		{
			type: "GET",
			url: "gestionRaccourci?action=ajouter",
			dataType: "xml",
			data: "nom=" + nom,
			timeout : 8000,
			success: function(xml)
			{
				/* On r�cupert le code d'erreur */
				codeErreur = $("raccourcis>code",xml).attr("valeur");
				
				/* La variable codeErreur indique si le raccourci a �t� ajout� et dans le
				 * cas contraire indique la raison 
				 */
				switch(codeErreur)
				{
					/* 0 : Ajout avec succ�s */
					case "0":
					
						/* On supprime le message �ventuel */
						messageraccourci.text("");
						
						/* On r�affiche la liste des raccourcis */
						afficherListeRaccourci(xml);
					
					break;
					
					/* 1 : Maximum de raccourcis */
					case "1":
					
						/* On ajoute un message d'erreur */
						messageraccourci.text("Limite de favoris atteinte");
					
					break;
					
					/* 2 : Le raccourci existe */
					case "2":
					
						/* On ajoute un message d'erreur */
						messageraccourci.text("Ce favoris existe déjà");
					
					break;
				}
			}
		});
	}
}

/**
 * Fonction permettant de supprimer un raccourci en ajax. Apr�s l'appel de la servlet
 * on r�cupert un code de retour indiquant si la suppression s'est effectu� avec succ�s.
 * En cas de succ�s on r�affiche la liste compl�te des raccourcis utilisateur.
 */
function supprimerRaccourci(idRaccourci)
{
	$.ajax(
	{
		type: "GET",
		url: "gestionRaccourci?action=supprimer",
		dataType: "xml",
		data: "idRaccourci=" + idRaccourci,
		timeout : 8000,
		success: function(xml)
		{
			/* On r�cupert le code d'erreur */
			codeErreur = $("raccourcis>code",xml).attr("valeur");
			
			/* Si l'article a �t� supprim� on r�affiche la liste des raccourcis */
			if(codeErreur == 0)
			{
				afficherListeRaccourci(xml);
			}
		}
	});
}
