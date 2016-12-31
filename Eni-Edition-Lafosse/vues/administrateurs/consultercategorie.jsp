<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

	<div class="titre">
		<a href="administrationCategories" title="Afficher la liste compl&egrave;te des cat&eacute;gories">
			<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
			&nbsp;Liste des cat&eacute;gories
		</a>
	</div>

	<table border="0" id="tableau" cellspacing="5" width="400px">
		<tr>
			<td width="150px">Id : </td>
			<td class="infoarticle">
				<c:out value="${categorie.id}"/>
			</td>
		</tr>
		<tr>
			<td>Nom : </td>
			<td class="infoarticle">
				<c:out value="${categorie.nom}"/>
			</td>
		</tr>
	</table>
	
	<br/>

<%@ include file="../outils/adminpiedpage.jspf" %>