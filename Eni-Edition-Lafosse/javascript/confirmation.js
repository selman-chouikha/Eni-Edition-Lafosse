function confirmerSuppressionArticle(idArticle)
{   
   if(confirm("Voulez-vous supprimer cet article ?"))
   {
   		chemin="administrationArticles?action=supprimer&idArticle="+idArticle;
    	document.location.href=chemin;
   }
   else
   {
   		return;
   }
}

function confirmerSuppressionAdministrateur(idAdministrateur)
{   
   if(confirm("Voulez-vous supprimer ce compte administrateur ?"))
   {
   		chemin="administrationAdministrateurs?action=supprimer&idAdministrateur="+idAdministrateur;
    	document.location.href=chemin;
   }
   else
   {
   		return;
   }
}

function confirmerSuppressionClient(idClient)
{   
   if(confirm("Voulez-vous supprimer ce compte client ?"))
   {
   		chemin="administrationClients?action=supprimer&idClient="+idClient;
    	document.location.href=chemin;
   }
   else
   {
   		return;
   }
}

function confirmerSuppressionCategorie(idCategorie)
{   
	if(idCategorie == '1')
	{
		alert("Pour des raisons administrative, cette catégorie ne peut être supprimée")
	}
	else
	{
		if(confirm("Voulez-vous supprimer cette catégorie ? \n Les articles de cette catégorie seront déplacées dans la catégorie \"Autre\"."))
	   	{
	   		chemin="administrationCategories?action=supprimer&idCategorie="+idCategorie;
	    	document.location.href=chemin;
	   }
	   else
	   {
	   		return;
	   }
	}
}

function confirmerSuppressionCommande()
{   
	alert("Pour des raisons d'historisation, les commandes ne peuvent et ne doivent pas être supprimées.");
}



