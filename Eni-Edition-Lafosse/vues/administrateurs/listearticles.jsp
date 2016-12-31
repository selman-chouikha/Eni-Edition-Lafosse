<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>
	
	<!-- Plugin javascript pour la confirmation de la suppression -->
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/confirmation.js"></script>
	
	<!-- Plugin javascript pour les recherches -->
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/recherche.administrateur.js"></script>

	<!--  Plugin javascript pour l'autocompletion  -->
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/jquery/plugin/dimensions.js"></script>
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/jquery/plugin/jquery.bgiframe.min.js"></script>
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/jquery/plugin/jquery.autocomplete.js"></script>

	<!-- Formulaire de recherche -->
	<div id="rechercher">
		<a href="javascript:afficherRecherche();">
			<img src="<b:config attribut="urlApplication"/>images/application/louperecherche.png" title="Cliquer pour afficher le formulaire de recherche" alt="Rechercher" align="absmiddle"border="0"/>
		</a>
	</div>
	
	<form id="formulairerecherche" name="formulairerecherche" method="POST" action="administrationArticles">
		<table cellspacing="4" cellpadding="0" id="tableau" width="520px">
			<tr>
				<td>
					<input type="text" class="input" id="recherche" name="recherche" value="<c:out value="${recherche}"/>"/></td>
				<td>
					<select name="typerecherche" id="typerecherche" onchange="changerTypeRecherche();">
						<option value="article.id_article" <c:if test="${typerecherche == 'article.id_article' }">selected="selected"</c:if> >Id</option>
						<option value="article.nomarticle" <c:if test="${typerecherche == 'article.nomarticle' }">selected="selected"</c:if> >Nom</option>
						<option value="article.prixarticle" <c:if test="${typerecherche == 'article.prixarticle' }">selected="selected"</c:if> >Prix</option>
					</select>
				</td>
				<td>
					<input type="image" src="<b:config attribut="urlApplication"/>images/application/rechercher.gif" align="absmiddle"/>
				</td>
			</tr>
		</table>
	</form>
	
	<br/>
	
	<div class="titre">
		<a href="<b:config attribut="urlApplication"/>administrationArticles?action=lister" title="Afficher la liste complète des articles">
			<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
			&nbsp;Liste des articles
		</a>
	</div>

	<p:pagination servlet="administrationArticles" pageActuel="${pageActuel}" totalElement="${totalElement}" maxParPage="${maxParPage}" champTri="${champTri}" typeTri="${typeTri}">
		<p:paramServlet valeur="lister" nom="action"/>
		<p:paramServlet valeur="${recherche}" nom="recherche"/>
		<p:paramServlet valeur="${typerecherche}" nom="typerecherche"/>
		
		<a href="<b:config attribut="urlApplication"/>administrationArticles?action=ajouter" style="font-size:11px">
			Ajouter un article
		</a> 
		
		<table id="tableaubordure" cellspacing="0" cellpadding="0" width="750px">
			<tr style="background-color:white">
				<td colspan="5">
					El&eacute;ments [ <p:premierElement/> - <p:dernierElement/> ] sur <p:totalElement/>
					&nbsp;&nbsp;
					<p:premierePage format="image" cheminImage="images/application/premierepage.gif"/>
					<p:pagePrecedente format="image" cheminImage="images/application/pageprecedente.gif"/>
					&nbsp;( Page <p:pageActuel/> sur <p:dernierePage format="valeur"/> )&nbsp;
					<p:pageSuivante format="image" cheminImage="images/application/pagesuivante.gif"/>
					<p:dernierePage format="image" cheminImage="images/application/dernierepage.gif"/>
				</td>
				<td colspan="3" align="right">
					Afficher : 
					<select onchange="document.location = this.value;">
						<option value="<p:maxParPage valeur="5" format="url"/>" <c:if test="${maxParPage == '5' }">selected="selected"</c:if> >5 produits</option>
						<option value="<p:maxParPage valeur="10" format="url"/>" <c:if test="${maxParPage == '10' }">selected="selected"</c:if> >10 produits</option>
						<option value="<p:maxParPage valeur="15" format="url"/>" <c:if test="${maxParPage == '15' }">selected="selected"</c:if> >15 produits</option>
						<option value="<p:maxParPage valeur="20" format="url"/>" <c:if test="${maxParPage == '20' }">selected="selected"</c:if> >20 produits</option>
					</select>
				</td>
			</tr>
			
			<tr align="center" class="entetetableau">
				<td width="35px">Id <p:tri champ="id_article" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" /></td>
				<td>Nom <p:tri champ="nomarticle" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" /></td>
				<td width="50px">Prix <p:tri champ="prixarticle" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" /></td>
				<td width="70px">Date <p:tri champ="datearticle" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" /></td>
				<td width="70px">Vignette</td>
				<td width="140px">Cat&eacute;gorie <p:tri champ="id_categorie" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			</td>
				<td width="40px">Etat <p:tri champ="etatarticle" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			</td>
				<td width="100px">Gestion</td>
			</tr>
	
			<c:set value="0" var="index"/>
			<c:forEach items="${listeArticles}" var="article" >
				
				<c:if test="${index % 2 == '0'}">
					<tr align="center" class="lignefonce">
				</c:if>
				<c:if test="${index % 2 == '1'}">
					<tr align="center" class="ligneclair">
				</c:if>
							
					<td>
						<c:out value="${article.id}"/>
					</td>
					<td>
						<c:out value="${article.nom}"/>
					</td>
					<td>
						<b:prix montant="${article.prix}"/> &euro;
					</td>
					<td>
						<b:formatDate date="${article.date}" format="dd/MM/yyyy"/>
					</td>
					<td>
						<img src="<b:config attribut="urlApplication"/>images/articles/<c:out value="${article.vignette}"/>"  height="60px" />
					</td>
					<td>
						<c:out value="${article.categorie.nom}"/>
					</td>
					<td>
						<c:if test="${article.etat == '1' }">
							<img src="<b:config attribut="urlApplication"/>images/application/actif.gif" />
						</c:if>
						<c:if test="${article.etat == '0' }">
							<img src="<b:config attribut="urlApplication"/>images/application/inactif.gif" />
						</c:if>
					</td>
					<td>
						<a href="administrationArticles?action=consulter&idArticle=<c:out value="${article.id}"/>" title="Afficher les informations de cet article">
							<img src="<b:config attribut="urlApplication"/>images/application/consulteradmin.png" />
						</a>
						&nbsp;
						<a href="administrationArticles?action=modifier&idArticle=<c:out value="${article.id}"/>" title="Modifier les informations de cet article">
							<img src="<b:config attribut="urlApplication"/>images/application/modifieradmin.png" />
						</a>
						&nbsp;
						<img src="<b:config attribut="urlApplication"/>images/application/supprimeradmin.png" style="cursor: pointer;" title="Supprimer cet article" onclick="confirmerSuppressionArticle(<c:out value="${article.id}"/>);"/>
					</td>
				</tr>
				
				<c:set value="${index + 1 }" var="index" />
			
			</c:forEach>
			
			<tr style="background-color:white">
				<td colspan="5">
					El&eacute;ments [ <p:premierElement/> - <p:dernierElement/> ] sur <p:totalElement/>
					&nbsp;&nbsp;
					<p:premierePage format="image" cheminImage="images/application/premierepage.gif"/>
					<p:pagePrecedente format="image" cheminImage="images/application/pageprecedente.gif"/>
					&nbsp;( Page <p:pageActuel/> sur <p:dernierePage format="valeur"/> )&nbsp;
					<p:pageSuivante format="image" cheminImage="images/application/pagesuivante.gif"/>
					<p:dernierePage format="image" cheminImage="images/application/dernierepage.gif"/>
				</td>
				<td colspan="5" align="right">
					Afficher : 
					<select onchange="document.location = this.value;">
						<option value="<p:maxParPage valeur="5" format="url"/>" <c:if test="${maxParPage == '5' }">selected="selected"</c:if> >5 produits</option>
						<option value="<p:maxParPage valeur="10" format="url"/>" <c:if test="${maxParPage == '10' }">selected="selected"</c:if> >10 produits</option>
						<option value="<p:maxParPage valeur="15" format="url"/>" <c:if test="${maxParPage == '15' }">selected="selected"</c:if> >15 produits</option>
						<option value="<p:maxParPage valeur="20" format="url"/>" <c:if test="${maxParPage == '20' }">selected="selected"</c:if> >20 produits</option>
					</select>
				</td>
			</tr>
		</table>
	</p:pagination>
	<br/>

<%@ include file="../outils/adminpiedpage.jspf" %>