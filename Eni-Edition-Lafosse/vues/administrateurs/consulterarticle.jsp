<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

	<div class="titre">
		<a href="administrationArticles" title="Afficher la liste complète des articles">
			<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
			&nbsp;Liste des articles
		</a>
	</div>

	<table border="0" id="tableau" cellspacing="5" width="520px">
		<tr>
			<td width="150px">Id : </td>
			<td class="infoarticle">
				<c:out value="${article.id}"/>
			</td>
		</tr>
		<tr>
			<td>Nom : </td>
			<td class="infoarticle">
				<c:out value="${article.nom}"/>
			</td>
		</tr>
		<tr>
			<td>Description : </td>
			<td class="infoarticle">
				<c:out value="${article.description}"/>
			</td>
		</tr>
		<tr>
			<td>Prix : </td>
			<td class="infoarticle">
				<b:prix montant="${article.prix}"/> &euro;
			</td>
		</tr>
		<tr>
			<td>R&eacute;duction : </td>
			<td class="infoarticle">
				<c:out value="${article.reduction}"/> %
			</td>
		</tr>
		<tr>
			<td>Stock : </td>
			<td class="infoarticle">
				<c:out value="${article.stock}"/> articles
			</td>
		</tr>
		<tr>
			<td>Date de création : </td>
			<td class="infoarticle">
				<b:formatDate date="${article.date}" format="dd/MM/yyyy"/>
			</td>
		</tr>
		<tr>
			<td>Etat :</td>
			<td>
				<c:if test="${article.etat == '1' }">
					<img src="<b:config attribut="urlApplication"/>images/application/actif.gif" />
				</c:if>
				<c:if test="${article.etat == '0' }">
					<img src="<b:config attribut="urlApplication"/>images/application/inactif.gif" />
				</c:if>	
			<td>
		</tr>
		<tr>
			<td>Cat&eacute;gorie : </td>
			<td class="infoarticle">
				<c:out value="${article.categorie.nom}"/>
			</td>
		</tr>
	</table>
	<br/>
	<table cellspacing="4" cellpadding="0" id="tableau" width="520px">
		<tr>
			<td align="center" width="260px">
				Photo 
			</td>
			<td align="center" width="260px">
				Vignette
			</td>
		</tr>
		<tr>
			<td align="center" width="260px">
				<img src="<b:config attribut="urlApplication"/>images/articles/<c:out value="${article.photo}"/>" />
			</td>
			<td align="center" width="260px"> 
				<img src="<b:config attribut="urlApplication"/>images/articles/<c:out value="${article.vignette}"/>" />
			</td>
		</tr>
	</table>
	<br/>

<%@ include file="../outils/adminpiedpage.jspf" %>