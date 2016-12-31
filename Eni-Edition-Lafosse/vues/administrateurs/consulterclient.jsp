<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

	<div class="titre">
		<a href="administrationClients" title="Afficher la liste compl&egrave;te des clients">
			<img src="<b:config attribut="urlApplication"/>images/application/clientadmin.jpg" border="0" align="absmiddle"/>
			&nbsp;Liste des clients
		</a>
	</div>

	<table border="0" id="tableau" cellspacing="5" width="500px">
		<tr>
			<td width="150px">Id : </td>
			<td class="infoarticle">
				<c:out value="${client.id}"/>
			</td>
		</tr>
		<tr>
			<td>Nom : </td>
			<td class="infoarticle">
				<c:out value="${client.nom}"/>
			</td>
		</tr>
		<tr>
			<td>Pr&eacute;nom : </td>
			<td class="infoarticle">
				<c:out value="${client.prenom}"/>
			</td>
		</tr>
		<tr>
			<td>Adresse mail : </td>
			<td class="infoarticle">
				<c:out value="${client.mail}"/>
			</td>
		</tr>
		<tr>
			<td>T&eacute;l&eacute;phone : </td>
			<td class="infoarticle">
				<c:out value="${client.telephone}"/>
			</td>
		</tr>
		<tr>
			<td>Adresse : </td>
			<td class="infoarticle">
				<c:out value="${client.adresse}"/>
			</td>
		</tr>
		<tr>
			<td>Code postal : </td>
			<td class="infoarticle">
				<c:out value="${client.codePostal}"/>
			</td>
		</tr>
		<tr>
			<td>Ville : </td>
			<td class="infoarticle">
				<c:out value="${client.ville}"/>
			</td>
		</tr>
		<tr>
			<td>Pays : </td>
			<td class="infoarticle">
				<c:out value="${client.pays}"/>
			</td>
		</tr>
		<tr>
			<td>Identifiant : </td>
			<td class="infoarticle">
				<c:out value="${client.identifiant}"/>
			</td>
		</tr>
	</table>
	<br/>

<%@ include file="../outils/adminpiedpage.jspf" %>