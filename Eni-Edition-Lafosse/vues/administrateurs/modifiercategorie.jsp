<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

<div class="titre">
	<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
	&nbsp;<b>Modification d'une cat&eacute;gorie</b>
</div>

<form action="<b:config attribut="urlApplication"/>administrationCategories?action=validermodification" method="POST">

	<!-- Autre informations de l'article caché -->
	<input id="id" name="id" type="hidden" value="<c:out value="${categorie.id}"/><c:out value="${id}"/>" />

	<table border="0" id="tableau" cellspacing="5">
		<tr>
			<td>Nom : </td>
			<td>
				<input class="input" type="text" id="nom" name="nom" value="<c:out value="${categorie.nom}"/><c:out value="${nom}"/>" />
			</td>
		</tr>
		<tr height="30px">
			<td align="center" colspan="2">
				<input type="image" title="Enregistrer les modifications" src="<b:config attribut="urlApplication"/>images/application/enregistrer.gif" />
			</td>
		</tr>
	</table>
</form>

<%@ include file="../outils/adminpiedpage.jspf" %>