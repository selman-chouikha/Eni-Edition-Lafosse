$(document).ready(function()
{
	if($("#typerecherche") != null)
	{
		/* Déclenchement de l'action pour initialiser l'autocomplétion */
		changerTypeRecherche();
		
		var recherche=$('#recherche').val();
		
		if(recherche == null || recherche == '')
		{
			$("#formulairerecherche").hide();
		}
		else
		{
			$("#formulairerecherche").show();
		}
		
	}
});


function changerTypeRecherche()
{
	var attribut=$('#typerecherche').val();
	
	if(attribut!=null)
	{
		var tab=attribut.split('.');
		table=tab[0];
		attribut=tab[1];
		
		table=jQuery.trim(table);
		attribut=jQuery.trim(attribut);
	
		if(attribut!='' && table!='')
		{
			url="administrationAutoComplete?attribut="+attribut+"&table="+table;
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
 	}
}

function afficherRecherche()
{
	if($("#formulairerecherche").is(":hidden"))
 	{
    	$("#formulairerecherche").slideDown("fast");
 	}
    else
	{
	   $("#formulairerecherche").slideUp("fast");
	}
}
 

