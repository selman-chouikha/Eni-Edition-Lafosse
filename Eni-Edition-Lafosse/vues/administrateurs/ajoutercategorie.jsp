<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

<div class="titre">
	<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
	&nbsp;<b>Ajout d'une nouvelle cat&eacute;gorie</b>
</div>

<form action="<b:config attribut="urlApplication"/>administrationCategories?action=validerajout" method="POST">
	<table border="0" id="tableau" cellspacing="5">
		<tr>
			<td>Nom : </td>
			<td>
				<input class="input" type="text" id="nom" name="nom" value="<c:out value="${nom}"/>" />
			</td>
		</tr>
		<tr height="30px">
			<td align="center" colspan="2">
				<input type="image" title="Enregistrer cette catégorie" src="<b:config attribut="urlApplication"/>images/application/enregistrer.gif" />
			</td>
		</tr>
	</table>
</form>

<%@ include file="../outils/adminpiedpage.jspf" %>