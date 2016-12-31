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
	
	<form id="formulairerecherche" name="formulairerecherche" method="POST" action="administrationCategories">
		<table cellspacing="4" cellpadding="0" id="tableau" width="520px">
			<tr>
				<td>
					<input type="text" class="input" id="recherche" name="recherche" value="<c:out value="${recherche}"/>"/></td>
				<td>
					<select name="typerecherche" id="typerecherche" onchange="changerTypeRecherche();">
						<option value="categorie.id_categorie" <c:if test="${typerecherche == 'categorie.id_categorie' }">selected="selected"</c:if> >Id</option>
						<option value="categorie.nomcategorie" <c:if test="${typerecherche == 'categorie.nomcategorie' }">selected="selected"</c:if> >Nom</option>
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
		<a href="<b:config attribut="urlApplication"/>administrationCategories?action=lister" title="Afficher la liste complète des catégories">
			<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
			&nbsp;Liste des cat&eacute;gories
		</a>
	</div>

	<p:pagination servlet="administrationCategories" pageActuel="${pageActuel}" totalElement="${totalElement}" maxParPage="${maxParPage}" champTri="${champTri}" typeTri="${typeTri}">
		<p:paramServlet valeur="lister" nom="action"/>
		<p:paramServlet valeur="${recherche}" nom="recherche"/>
		<p:paramServlet valeur="${typerecherche}" nom="typerecherche"/>
		
		<a href="<b:config attribut="urlApplication"/>administrationCategories?action=ajouter" style="font-size:11px">
			Ajouter une catégorie
		</a> 
		
		<table id="tableaubordure" cellspacing="0" cellpadding="0" width="550px">
			<tr style="background-color:white">
				<td colspan="2">
					El&eacute;ments [ <p:premierElement/> - <p:dernierElement/> ] sur <p:totalElement/>
					&nbsp;&nbsp;
					<p:premierePage format="image" cheminImage="images/application/premierepage.gif"/>
					<p:pagePrecedente format="image" cheminImage="images/application/pageprecedente.gif"/>
					&nbsp;( Page <p:pageActuel/> sur <p:dernierePage format="valeur"/> )&nbsp;
					<p:pageSuivante format="image" cheminImage="images/application/pagesuivante.gif"/>
					<p:dernierePage format="image" cheminImage="images/application/dernierepage.gif"/>
				</td>
				<td align="right">
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
				<td width="100px">Id <p:tri champ="id_categorie" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" /></td>
				<td width="300px">Nom <p:tri champ="nomcategorie" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" /></td>
				<td width="150px">Gestion</td>
			</tr>
	
			<c:set value="0" var="index"/>
			<c:forEach items="${listeCategorie}" var="categorie" >
				
				<c:if test="${index % 2 == '0'}">
					<tr align="center" class="lignefonce">
				</c:if>
				<c:if test="${index % 2 == '1'}">
					<tr align="center" class="ligneclair">
				</c:if>
							
					<td>
						<c:out value="${categorie.id}"/>
					</td>
					<td>
						<c:out value="${categorie.nom}"/>
					</td>
					<td>
						<a href="administrationCategories?action=consulter&idCategorie=<c:out value="${categorie.id}"/>" title="Afficher les informations de cette catégorie">
							<img src="<b:config attribut="urlApplication"/>images/application/consulteradmin.png" />
						</a>
						&nbsp;&nbsp;&nbsp;
						<a href="administrationCategories?action=modifier&idCategorie=<c:out value="${categorie.id}"/>" title="Modifier les informations de cette catégorie">
							<img src="<b:config attribut="urlApplication"/>images/application/modifieradmin.png" />
						</a>
						&nbsp;&nbsp;&nbsp;
						<img src="<b:config attribut="urlApplication"/>images/application/supprimeradmin.png" style="cursor: pointer;" title="Supprimer cette catégorie" onclick="confirmerSuppressionCategorie(<c:out value="${categorie.id}"/>);"/>
					</td>
				</tr>
				
				<c:set value="${index + 1 }" var="index" />
			
			</c:forEach>
			
			<tr style="background-color:white">
				<td colspan="2">
					El&eacute;ments [ <p:premierElement/> - <p:dernierElement/> ] sur <p:totalElement/>
					&nbsp;&nbsp;
					<p:premierePage format="image" cheminImage="images/application/premierepage.gif"/>
					<p:pagePrecedente format="image" cheminImage="images/application/pageprecedente.gif"/>
					&nbsp;( Page <p:pageActuel/> sur <p:dernierePage format="valeur"/> )&nbsp;
					<p:pageSuivante format="image" cheminImage="images/application/pagesuivante.gif"/>
					<p:dernierePage format="image" cheminImage="images/application/dernierepage.gif"/>
				</td>
				<td align="right">
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