<!-- Entete de la page -->
<%@ include file="../outils/entete.jspf" %>

	<br/>

	<form action="<b:config attribut="urlApplication"/>gestionClient?action=validermodification" method="POST">
		<center>
		
			<!-- ID caché -->
			<input type="hidden" value="<c:out value="${client.id}"/><c:out value="${id}"/>" id="id" name="id"/>
			<table cellpadding="5" cellspacing="0" class="formulaire">
				<tr>
					<td colspan="2" class="titreformulaire">Modification de votre compte</td>
				</tr>
				<tr>
					<td>Nom : </td>
					<td>
						<input class="input" type="text" name="nom" value="<c:out value="${client.nom}"/><c:out value="${nom}"/>">
					</td>
				</tr>
				<tr>
					<td>Pr&eacute;nom : </td>
					<td>
						<input class="input" type="text" name="prenom" value="<c:out value="${client.prenom}"/><c:out value="${prenom}"/>">
					</td>
				</tr>
				<tr>
					<td>Adresse mail : </td>
					<td>
						<input class="input" type="text" name="mail" value="<c:out value="${client.mail}"/><c:out value="${mail}"/>">
					</td>
				</tr>
				<tr>
					<td>T&eacute;l&eacute;phone : </td>
					<td>
						<input class="input" type="text" name="telephone" value="<c:out value="${client.telephone}"/><c:out value="${telephone}"/>">
					</td>
				</tr>
				<tr>
					<td>Adresse : </td>
					<td>
						<input class="input" type="text" name="adresse"  value="<c:out value="${client.adresse}"/><c:out value="${adresse}"/>">
					</td>
				</tr>
				<tr>
					<td>Code postal : </td>
					<td>
						<input class="input" type="text" name="codePostal" value="<c:out value="${client.codePostal}"/><c:out value="${codePostal}"/>"> 
					</td>
				</tr>
				<tr>
					<td>Ville : </td>
					<td>
						<input class="input" type="text" name="ville"  value="<c:out value="${client.ville}"/><c:out value="${ville}"/>">
					</td>
				</tr>
				<tr>
					<td>Pays : </td>
					<td>
						<input class="input" type="text" name="pays" value="<c:out value="${client.pays}"/><c:out value="${pays}"/>">
					</td>
				</tr>
				<tr>
					<td>Mot de passe : </td>
					<td>
						<input class="input" type="password" name="motDePasse" value="**********" onclick="this.value = '';">
					</td>
				</tr>
				<tr>
					<td>Confirmation : </td>
					<td>
						<input class="input" type="password" name="confirmation" value="**********" onclick="this.value = '';">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="image" title="Appliquer les modifications" src="<b:config attribut="urlApplication"/>images/application/enregistrer.gif"/> 
					</td>
				</tr>
			</table>
		</center>
	</form>

<!-- Pieds de page -->
<%@ include file="../outils/piedpage.jspf" %>