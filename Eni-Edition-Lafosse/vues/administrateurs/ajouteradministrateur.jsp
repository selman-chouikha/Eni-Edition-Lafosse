<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

<div class="titre">
	<img src="<b:config attribut="urlApplication"/>images/application/clientadmin.jpg" border="0" align="absmiddle"/>
	&nbsp;<b>Ajout d'un compte administrateur</b>
</div>

<form action="<b:config attribut="urlApplication"/>administrationAdministrateurs?action=validerajout" method="POST">
	<table border="0" id="tableau" cellspacing="5">
		<tr>
			<td>Nom : </td>
			<td>
				<input class="input" type="text" id="nom" name="nom" value="<c:out value="${nom}"/>" />
			</td>
		</tr>
		<tr>
			<td>Pr&eacute;nom : </td>
			<td>
				<input class="input" type="text" id="prenom" name="prenom" value="<c:out value="${prenom}"/>" />
			</td>
		</tr>
		<tr>
			<td>Adresse mail : </td>
			<td>
				<input class="input" type="text" id="mail" name="mail" value="<c:out value="${mail}"/>" />
			</td>
		</tr>
		<tr>
			<td>Identifiant : </td>
			<td>
				<input class="input" type="text" id="identifiant" name="identifiant" value="<c:out value="${identifiant}"/>" />
			</td>
		</tr>
		<tr>
			<td>Mot de passe : </td>
			<td>
				<input class="input" type="password" id="motDePasse" name="motDePasse" value="<c:out value="${motDePasse}"/>" />
			</td>
		</tr>
		<tr>
			<td>Confirmation : </td>
			<td>
				<input class="input" type="password" id="confirmation" name="confirmation" value="<c:out value="${confirmation}"/>" />
			</td>
		</tr>
		<tr height="30px">
			<td align="center" colspan="2">
				<input type="image" title="Finaliser la création de ce compte client" src="<b:config attribut="urlApplication"/>images/application/enregistrer.gif" />
			</td>
		</tr>
	</table>
</form>

<%@ include file="../outils/adminpiedpage.jspf" %>