<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

<div class="titre">
	<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
	&nbsp;<b>Modification des informations d'une commande</b>
</div>

<form action="<b:config attribut="urlApplication"/>administrationCommandes?action=validermodification" method="POST">

	<!-- Autre informations cachées de l'administrateur -->
	<input id="idCommande" name="idCommande" type="hidden" value="<c:out value="${commande.id}"/><c:out value="${idCommande}"/>" />
	<input id="idClient" name="idClient" type="hidden" value="<c:out value="${commande.client.id}"/><c:out value="${idClient}"/>" />
	
	<table border="0" id="tableau" cellspacing="5" width="400px">
		<tr>
			<td>Etat  : </td>
			<td>
				<select class="input" id="etat" name="etat">
					<option value="0" <c:if test="${commande.etat == '0' || etat == '0'}">selected='selected'</c:if> >En attente de paiement</option>
					<option value="1" <c:if test="${commande.etat == '1' || etat == '1'}">selected='selected'</c:if> >Commande exp&eacute;di&eacute;e</option>
					<option value="2" <c:if test="${commande.etat == '2' || etat == '2'}">selected='selected'</c:if> >Commande annul&eacute;e</option>
				</select>
			</td>
		</tr>
	</table>
	
	<br/>
	
	<div class="titre">
		<img src="<b:config attribut="urlApplication"/>images/application/clientadmin.jpg" border="0" align="absmiddle"/>
		&nbsp;<b>Modifier l'adresse de livraison</b>
	</div>
	
	<table border="0" id="tableau" cellspacing="5" width="400px">
		<tr>
			<td>Nom :</td>
			<td>
				<input class="input" type="text" id="nom" name="nom" value="<c:out value="${commande.client.nom}"/><c:out value="${nom}"/>" />
			</td>
		</tr>
		<tr>
			<td>Pr&eacute;nom :</td>
			<td>
				<input class="input" type="text" id="prenom" name="prenom" value="<c:out value="${commande.client.prenom}"/><c:out value="${prenom}"/>" />
			</td>
		</tr>
		<tr>
			<td>Adresse :</td>
			<td>
				<input class="input" type="text" id="adresse" name="adresse" value="<c:out value="${commande.client.adresse}"/><c:out value="${adresse}"/>" />
			</td>
		</tr>
		<tr>
			<td>Code postal :</td>
			<td>
				<input class="input" type="text" id="codePostal" name="codePostal" value="<c:out value="${commande.client.codePostal}"/><c:out value="${codePostal}"/>" />
			</td>
		</tr>
		<tr>
			<td>Ville :</td>
			<td>
				<input class="input" type="text" id="ville" name="ville" value="<c:out value="${commande.client.ville}"/><c:out value="${ville}"/>" />
			</td>
		</tr>
		<tr height="30px">
			<td align="center" colspan="2">
				<input type="image" title="Appliquer les modifications" src="<b:config attribut="urlApplication"/>images/application/enregistrer.gif" />
			</td>
		</tr>
	</table>
</form>

<%@ include file="../outils/adminpiedpage.jspf" %>