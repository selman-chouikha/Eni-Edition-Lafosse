<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

<div class="titre">
	<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
	&nbsp;<b>Ajout d'un nouvel article</b>
</div>

<form action="<b:config attribut="urlApplication"/>administrationArticles?action=validerajout" method="POST">
	<table border="0" id="tableau" cellspacing="5">
		<tr>
			<td>Cat&eacute;gorie : </td>
			<td>
				<select class="input" id="categorie" name="categorie">
					<c:forEach var="categorieArticle" begin="0" items="${categories}">
						<option value="<c:out value="${categorieArticle.id}"/>" <c:if test="${categorie == categorieArticle.id}">selected='selected'</c:if> ><c:out value="${categorieArticle.nom}"/></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>Nom : </td>
			<td>
				<input class="input" type="text" id="nom" name="nom" value="<c:out value="${nom}"/>" />
			</td>
		</tr>
		<tr>
			<td valign="top">Description : </td>
			<td>
				<textarea class="textarea" id="description" name="description"><c:out value="${description}"/></textarea>
			</td>
		</tr>
		<tr>
			<td>Prix TTC (en euro) : </td>
			<td><input class="inputpetit" type="text" id="prix" name="prix" value="<c:out value="${prix}"/>" /> &euro;</td>
		</tr>
		<tr>
			<td>R&eacute;duction : </td>
			<td>
				<select class="inputpetit" id="reduction" name="reduction">
					<option value="0" <c:if test="${reduction == '0'}">selected='selected'</c:if> >0 %</option>
					<option value="5" <c:if test="${reduction == '5'}">selected='selected'</c:if> >5 %</option>
					<option value="10" <c:if test="${reduction == '10'}">selected='selected'</c:if> >10 %</option>
					<option value="15" <c:if test="${reduction == '15'}">selected='selected'</c:if> >15 %</option>
					<option value="20" <c:if test="${reduction == '20'}">selected='selected'</c:if> >20 %</option>
					<option value="25" <c:if test="${reduction == '25'}">selected='selected'</c:if> >25 %</option>
					<option value="30" <c:if test="${reduction == '30'}">selected='selected'</c:if> >30 %</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Stock : </td>
			<td><input class="inputpetit" type="text" id="stock" name="stock" value="<c:out value="${stock}"/>" /></td>
		</tr>
		<tr>
			<td>Poids (en gramme) : </td>
			<td><input class="inputpetit" type="text" id="poids" name="poids" value="<c:out value="${poids}"/>" /></td>
		</tr>
		<tr>
			<td>Etat :</td>
			<td>
				<select class="inputpetit" id="etat" name="etat">
					<option value="1" <c:if test="${etat == '1' }">selected='selected'</c:if> >actif</option>
					<option value="0" <c:if test="${etat == '0' }">selected='selected'</c:if> >inactif</option>
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