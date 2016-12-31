var url=null;

/* Charge l'autocomplétion pour la recherche d'un article
 *  en mode utilisateur
 */ 
function chargerAutoCompleteUser()
{
	 /* Mise en forme de l'url */
	 url = "autoComplete";
	 
	 /* Activation de l'autocompl�tion  */
	 $("#recherche").autocomplete(url, {
	                delay: 400,
	                width:400,
	                cacheLength:1,
	                matchSubset:false,
	                mustMatch : true,
	                minChars:1,
	                autoFill: false
	  });
}

