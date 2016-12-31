<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

	<div class="titre">
		<a href="administrationAdministrateurs" title="Afficher la liste compl&egrave;te des administrateurs">
			<img src="<b:config attribut="urlApplication"/>images/application/clientadmin.jpg" border="0" align="absmiddle"/>
			&nbsp;Liste des administrateurs
		</a>
	</div>

	<table border="0" id="tableau" cellspacing="5" width="500px">
		<tr>
			<td width="150px">Id : </td>
			<td class="infoarticle">
				<c:out value="${administrateur.id}"/>
			</td>
		</tr>
		<tr>
			<td>Nom : </td>
			<td class="infoarticle">
				<c:out value="${administrateur.nom}"/>
			</td>
		</tr>
		<tr>
			<td>Pr&eacute;nom : </td>
			<td class="infoarticle">
				<c:out value="${administrateur.prenom}"/>
			</td>
		</tr>
		<tr>
			<td>Adresse mail : </td>
			<td class="infoarticle">
				<c:out value="${administrateur.mail}"/>
			</td>
		</tr>
		<tr>
			<td>Identifiant : </td>
			<td class="infoarticle">
				<c:out value="${administrateur.identifiant}"/>
			</td>
		</tr>
	</table>
	<br/>

<%@ include file="../outils/adminpiedpage.jspf" %>