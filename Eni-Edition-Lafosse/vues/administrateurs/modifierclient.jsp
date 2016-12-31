<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

<div class="titre">
	<img src="<b:config attribut="urlApplication"/>images/application/clientadmin.jpg" border="0" align="absmiddle"/>
	&nbsp;<b>Modification d'un compte administrateur</b>
</div>

<form action="<b:config attribut="urlApplication"/>administrationClients?action=validermodification" method="POST">

	<!-- Autre informations cachées du client -->
	<input id="id" name="id" type="hidden" value="<c:out value="${client.id}"/><c:out value="${id}"/>" />
	
	<table border="0" id="tableau" cellspacing="5">
		<tr>
			<td>Nom : </td>
			<td>
				<input class="input" type="text" id="nom" name="nom" value="<c:out value="${client.nom}"/><c:out value="${nom}"/>" />
			</td>
		</tr>
		<tr>
			<td>Pr&eacute;nom : </td>
			<td>
				<input class="input" type="text" id="prenom" name="prenom" value="<c:out value="${client.prenom}"/><c:out value="${prenom}"/>" />
			</td>
		</tr>
		<tr>
			<td>Adresse mail : </td>
			<td>
				<input class="input" type="text" id="mail" name="mail" value="<c:out value="${client.mail}"/><c:out value="${mail}"/>" />
			</td>
		</tr>
		<tr>
			<td>T&eacute;l&eacute;phone : </td>
			<td>
				<input class="input" type="text" id="telephone" name="telephone" value="<c:out value="${client.telephone}"/><c:out value="${telephone}"/>" />
			</td>
		</tr>
		<tr>
			<td>Adresse : </td>
			<td>
				<input class="input" type="text" id="adresse" name="adresse" value="<c:out value="${client.adresse}"/><c:out value="${adresse}"/>" />
			</td>
		</tr>
		<tr>
			<td>Code postal : </td>
			<td>
				<input class="input" type="text" id="codePostal" name="codePostal" value="<c:out value="${client.codePostal}"/><c:out value="${codePostal}"/>" />
			</td>
		</tr>
		<tr>
			<td>Ville : </td>
			<td>
				<input class="input" type="text" id="ville" name="ville" value="<c:out value="${client.ville}"/><c:out value="${ville}"/>" />
			</td>
		</tr>
		<tr>
			<td>Pays : </td>
			<td>
				<input class="input" type="text" id="pays" name="pays" value="<c:out value="${client.pays}"/><c:out value="${pays}"/>" />
			</td>
		</tr>
		<tr>
			<td>Mot de passe : </td>
			<td>
				<input class="input" type="password" id="motDePasse" name="motDePasse" value="**********" onclick="this.value = '';">
			</td>
		</tr>
		<tr>
			<td>Confirmation : </td>
			<td>
				<input class="input" type="password" id="confirmation" name="confirmation" value="**********" onclick="this.value = '';">
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