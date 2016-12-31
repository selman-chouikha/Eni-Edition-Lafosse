/**
 * Fonction permettant d'afficher la note d'un article dans un conteneur (div)
 * ayant pour classe CSS "moyennenote". Ce div comporte une image de fond représentant
 * une liste de note avec étoile de 1 � 10. Pour afficher la note il suffit de décaler
 * la hauteur du fond de x fois 17 px (hauteur d'une étoile).
 */
function afficherNote(note)
{
	/* Variables */
	var moyennenote;
	var decalage;
		
	/* On récupert le conteneur qui affichera la note de l'article sous forme d'�toile */
	moyennenote = $("div.moyennenote:last");
	
	/* Initialise le d�calage en fonction de la note */
	decalage = note * -17;
	
	/* Postionne l'image de fond du div en fonction de la note */
	moyennenote.css({backgroundPosition:"0px "+decalage+"px",display:"block"});
}

/**
 * Fonction permettant d'afficher le nombre de vote d'un article dans un conteneur (div)
 * ayant pour classe CSS "vote."
 */
function afficherVote(vote)
{
	/* Variables */
	var conteneurvote;
		
	/* On r�cupert le conteneur qui affiche le nombre de vote */
	conteneurvote = $("div.vote");

	/* Affichage du nombre de vote */
	if(vote != '0')
	{
		conteneurvote.text("(" + vote + " votes)");
	}	
	else
	{
		conteneurvote.text("(L'article n'a pas encore été noté)");
	}
}

/**
 * Cette fonction permet de d�finir les diff�rentes actions utilis� lors de la notation
 * d'un article : en autre le passage d'une �toile vide � pleine au passage de la souris,
 * ou encore l'action de noter un article, qui ajoute la note en base par une fonction 
 * ajax.
 */
function initialiserChoixNote()
{
	/* Varibales */
	var etoiles = $("div.etoile");
	var choixnote = $("div.choixnote");
	var moyennenote = $("div.moyennenote");
	var conteneurnote = $("div.note");
	
	/* Fonction permettant au passage de la souris sur une �toile vide
	 * de remplir les �toiles d'indice inf�rieur ou �gale � l'indice de l'�toile
	 * survol�e, et de vider les �toiles d'indice sup�rieur */
	var remplirEtoile = function () 
	{
		etoiles.lt(etoiles.index(this) + 1).addClass("etoilepleine");
		etoiles.gt(etoiles.index(this)).removeClass("etoilepleine");
	}
		
	/* Masque le div permettant de noter l'article et d'afficher le div contenant 
	 * la moyenne de l'article */
	var afficheNoteArticle = function()
	{
		choixnote.addClass("invisible");
		moyennenote.removeClass("invisible");
	}
		
	/* Permet de masqu� le div  contenant la moyenne de l'article et d'afficher
	 * le div div permettant de noter l'article */
	var afficheChoixNote = function()
	{
		moyennenote.addClass("invisible");
		choixnote.removeClass("invisible");
	}
		
	/* Fonction permettant de noter l'article */
	var voter= function()
	{
		var note = (etoiles.index(this) + 1) * 2;
		var idArticle = $("#idArticle").val();
		$.ajax(
		{
			type: "GET",
			url: "gestionNotes?action=noter",
			dataType: "xml",
			data: "idArticle="+idArticle+"&note="+note,
			timeout : 8000,
			success: function(xml)
			{
				$("articles>article",xml).each(
					function()
					{				
						/* On r�cupert la nouvelle moyenne de l'article */
						var note = $(this).attr("note");
						
						/* On r�affiche donc la note */
						afficherNote(note);		
						
						/* On refuse le nouveau vote du client */
						autoriserVote('4');					
					}
				);
			}
		});
	}
		
	/* On masque le choix de la note */
	choixnote.addClass("invisible");
		
	/* On applique les diff�rentes fonction d�finit pr�c�dement */
	etoiles.mouseover(remplirEtoile);
	etoiles.click(voter);
	conteneurnote.hover(afficheChoixNote,afficheNoteArticle);
}

/**
 * Cette fonction permet de r�cup�rer la note que l'utilisateur connect�
 * a attribu� � l'article en cours de consultation. Pour cela on utilise une fonction
 * ajax qui retourne un entier compris entre 0 et 4 :
 * - 0 : le client n'a pas not� l'article
 * - 1 : le client a d�j� not� l'article
 * - 3 : le client est un admin et ne peut noter l'article
 * - 4 : le client vient de noter l'article 
 */
function consulterNoteClient()
{
	/* Variables */
	var idArticle;
	
	/* On r�cupert l'id de l'article */
	idArticle = $("#idArticle").val();
	
	$.ajax(
	{
		type: "GET",
		url: "gestionNotes?action=noteclient",
		dataType: "xml",
		data: "idArticle="+idArticle,
		timeout : 8000,
		success: function(xml)
		{
			$("articles>article",xml).each(
				function()
				{				
					/* Variables */
					var autorisation = $(this).attr("autorisation");
					
					/* On autorise ou non le vote du client */
					autoriserVote(autorisation);
				}
			);
		}
	});
}

/**
 * Cette fonction permet suivant la valeur passer en param�tre (valeur retourn� par la 
 * fonction pr�c�dente, d'autoriser ou non le vote d'un article.
 */
function autoriserVote(autorisation)
{
	/* Variables */
	var conteneurmessage = $("div.message");
	var choixnote = $("div.choixnote");
	var moyennenote = $("div.moyennenote");
	var conteneurnote = $("div.note");
	var conteneurvote = $("div.vote");
						
	/* Fonction permettant d'afficher le nombre de vote et
	 * de cacher le message */
	var afficheVote = function()
	{
		conteneurmessage.addClass("invisible");
		conteneurvote.removeClass("invisible");
	}
							
	/* Fonction permettant d'afficher le message et
	 * de cacher le nombre de vote */
	var afficheMessage = function()
	{
		conteneurvote.addClass("invisible");
		conteneurmessage.removeClass("invisible");
	}
						
	/* La variable autorisation indique si l'utilisateur peut noter l'article et dans le 
	 * cas contraire indique la raison : */
	switch (autorisation) 
	{
		/* L'utilisateur n'a pas encore not� l'article : il peut donc choisir sa note*/
		case "0":
						
			/* On initialise donc le choix de la note */
			initialiserChoixNote();
								
			/* On cache le conteneur de message */
			conteneurmessage.addClass("invisible");
									
			/* On d�finit le style du message */
			conteneurmessage.css({color:'#969696'});
									
			/* On charge le message */
			conteneurmessage.text("Cliquer pour noter cet article !");
									
			/* On applique les fonctions sur l'�venement hover (mouseover + mouseout) */
			conteneurnote.hover(afficheMessage,afficheVote);
									
		break;
							
		/* L'utilisateur � d�j� not� l'article */
		case "1":
									
			/* Masque le choix de la note */
			choixnote.addClass("invisible");
									
			/* On applique les fonctions sur l'�venement hover (mouseover + mouseout) */
			conteneurnote.hover(afficheMessage,afficheVote);
									
			/* On cache le conteneur de message */
			conteneurmessage.addClass("invisible");
										
			/* On charge le message */
			conteneurmessage.text("Vous avez déjà noté cet article !");
										
		break;
									
		/* L'utilisateur n'est pas connect� */
		case "2":
						
			/* Masque le choix de la note */
			choixnote.addClass("invisible");
			
			/* Affiche la moyenne de la note */
			moyennenote.removeClass("invisible");
			
			/* Supprime les actions permettant de choisir la note de l'article */
			conteneurnote.unbind('hover').unbind('mouseenter').unbind('mouseleave');				
					
			/* On applique les fonctions sur l'�venement hover (mouseover + mouseout) */
			conteneurnote.hover(afficheMessage,afficheVote);
										
			/* On cache le conteneur de message */
			conteneurmessage.addClass("invisible");
										
			/* On charge le message */
			conteneurmessage.text("Vous devez vous identifier pour noter cet article !");
										
		break;
									
		/* L'utilisateur est un administrateur */
		case "3":
									
			/* Masque le choix de la note */
			choixnote.addClass("invisible");
								
			/* Affiche la moyenne de la note */
			moyennenote.removeClass("invisible");
			
			/* Supprime les actions permettant de choisir la note de l'article */
			conteneurnote.unbind('hover').unbind('mouseenter').unbind('mouseleave');					
			
			/* On applique les fonctions sur l'�venement hover (mouseover + mouseout) */
			conteneurnote.hover(afficheMessage,afficheVote);
										
			/* On cache le conteneur de message */
			conteneurmessage.addClass("invisible");
										
			/* On charge le message */
			conteneurmessage.text("Les administrateurs ne peuvent pas noter un article !");
									
		break;
									
		/* L'utilisateur vient de noter l'article */
		case "4":
									
			/* Masque le choix de la note */
			choixnote.addClass("invisible");
			
			/* Affiche la moyenne de la note */
			moyennenote.removeClass("invisible");
			
			/* Supprime les actions permettant de choisir la note de l'article */
			conteneurnote.unbind('hover').unbind('mouseenter').unbind('mouseleave');
										
			/* On masque le nombre de vote */
			conteneurvote.addClass("invisible");
								
			/* On d�finit le style du message */
			conteneurmessage.css({color:'green'});
										
			/* On charge le message */
			conteneurmessage.text("Merci d'avoir noter cet article.");
										
		break;
	}
}					
	
