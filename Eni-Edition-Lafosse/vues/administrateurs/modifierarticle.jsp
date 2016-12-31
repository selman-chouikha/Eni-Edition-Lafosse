<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

<div class="titre">
	<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
	&nbsp;<b>Modification d'un article</b>
</div>

<form action="<b:config attribut="urlApplication"/>administrationArticles?action=validermodification" method="POST">

	<!-- Autre informations de l'article caché -->
	<input id="id" name="id" type="hidden" value="<c:out value="${article.id}"/><c:out value="${id}"/>" />
	<input id="vignette" name="vignette" type="hidden" value="<c:out value="${article.vignette}"/><c:out value="${vignette}"/>" />
	<input id="photo" name="photo" type="hidden" value="<c:out value="${article.photo}"/><c:out value="${photo}"/>" />
	
	<table border="0" id="tableau" cellspacing="5">
		<tr>
			<td>Cat&eacute;gorie : </td>
			<td>
				<select class="input" id="categorie" name="categorie">
					<c:forEach var="categorieArticle" begin="0" items="${categories}">
						<option value="<c:out value="${categorieArticle.id}"/>" <c:if test="${article.categorie.id == categorieArticle.id || categorie == categorieArticle.id}">selected='selected'</c:if> ><c:out value="${categorieArticle.nom}"/></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>Nom : </td>
			<td>
				<input class="input" type="text" id="nom" name="nom" value="<c:out value="${article.nom}"/><c:out value="${nom}"/>" />
			</td>
		</tr>
		<tr>
			<td valign="top">Description : </td>
			<td>
				<textarea class="textarea" id="description" name="description"><c:out value="${article.description}"/><c:out value="${description}"/></textarea>
			</td>
		</tr>
		<tr>
			<td>Prix TTC (en euro) : </td>
			<td><input class="inputpetit" type="text" id="prix" name="prix" value="<c:out value="${article.prix}"/><c:out value="${prix}"/>" /> &euro;</td>
		</tr>
		<tr>
			<td>R&eacute;duction : </td>
			<td>
				<select class="inputpetit" id="reduction" name="reduction">
					<option value="0" <c:if test="${article.reduction == '0' || reduction == '0'}">selected='selected'</c:if> >0 %</option>
					<option value="5" <c:if test="${article.reduction == '5' || reduction == '5'}">selected='selected'</c:if> >5 %</option>
					<option value="10" <c:if test="${article.reduction == '10' || reduction == '10'}">selected='selected'</c:if> >10 %</option>
					<option value="15" <c:if test="${article.reduction == '15' || reduction == '15'}">selected='selected'</c:if> >15 %</option>
					<option value="20" <c:if test="${article.reduction == '20' || reduction == '20'}">selected='selected'</c:if> >20 %</option>
					<option value="25" <c:if test="${article.reduction == '25' || reduction == '25'}">selected='selected'</c:if> >25 %</option>
					<option value="30" <c:if test="${article.reduction == '30' || reduction == '30'}">selected='selected'</c:if> >30 %</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Stock : </td>
			<td><input class="inputpetit" type="text" id="stock" name="stock" value="<c:out value="${article.stock}"/><c:out value="${stock}"/>" /></td>
		</tr>
		<tr>
			<td>Poids (en gramme) : </td>
			<td><input class="inputpetit" type="text" id="poids" name="poids" value="<c:out value="${article.poids}"/><c:out value="${poids}"/>" /></td>
		</tr>
		<tr>
			<td>Etat :</td>
			<td>
				<select class="inputpetit" id="etat" name="etat">
					<option value="1" <c:if test="${article.etat == '1' || etat == '1'}">selected='selected'</c:if> >actif</option>
					<option value="0" <c:if test="${article.etat == '0' || etat == '0'}">selected='selected'</c:if> >inactif</option>
				</select>
			<td>
		</tr>
		<tr height="30px">
			<td align="center" colspan="2">
				<input type="image" title="Accéder au choix de la vignette" src="<b:config attribut="urlApplication"/>images/application/continuer.gif" />
			</td>
		</tr>
	</table>
</form>

<%@ include file="../outils/adminpiedpage.jspf" %>