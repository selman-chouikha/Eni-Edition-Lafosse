/**
 * Fonction s'executant au lancement du script : positionne le tableau contenant 
 * le total du panier avec un d�calage de 1px vers la droite (pour Internet Explorer).
 * Elle initialise aussi les fonctions de style au passage sur une ligne du tableau.
 */
$(document).ready(function()
{
	/* Change le style de la ligne au passage de la souris */
	var over = function()
	{
		$(this).removeClass("ligne2panier");
		$(this).addClass("ligne2panierover");
	}
	
	/* Repostitionne le style d'origine de la ligne */
	var out = function()
	{
		
		$(this).removeClass("ligne2panierover");
		$(this).addClass("ligne2panier");
	}
	
	/* Applique les fonctions sur le tableau */
	$("tr.ligne2panier").hover(over,out);
	
	/* Change le style de la ligne au passage de la souris */
	var over = function()
	{
		$(this).removeClass("ligne1panier");
		$(this).addClass("ligne1panierover");
	}
	
	/* Repostitionne le style d'origine de la ligne */
	var out = function()
	{
		$(this).removeClass("ligne1panierover");
		$(this).addClass("ligne1panier");
	}
	
	/* Applique les fonctions sur le tableau */
	$("tr.ligne1panier").hover(over,out);
	
	/* Applique un d�calage du tableau pour IE */
	if (navigator.appName=="Microsoft Internet Explorer")
	{
		$("table.tableautotalpanier").addClass("tableautotalpanierie");
	}
});

/**
 * Fonction ajax permettant d'ajouter un article au panier
 */
function ajouterPanier(idArticle)
{
	$.ajax(
	{
		type: "GET",
		url: "gestionPanier?action=ajouter",
		dataType: "xml",
		data: "idArticle="+idArticle+"&methode=ajax",
		timeout : 8000,
		success: function(xml)
		{
			/* On r�cupert le code d'erreur */
			codeErreur = $("raccourcis>code",xml).attr("valeur");
			
			/* Si l'article a �t� ajout� avec succ�s on propose d'acc�der au panier */
			if(codeErreur == 0)
			{
				if(confirm("L'article a été ajouté à votre panier. Voulez vous accéder à votre panier?"))
			   	{
			   		
			    	document.location.href="gestionPanier?action=lister";
			   	}
			}
			else
			{
				alert("Une erreur s'est produite durant l'ajout de cet article au panier.");
			}
		}
	});
}

function confirmerValidationPanier()
{   
	if(confirm("Veuillez confirmer l'ajout de cette commande?"))
	{
	 	chemin="gestionCommandes?action=ajouter";
	    document.location.href=chemin;
	}
	else
	{
	 	return;
	}
}