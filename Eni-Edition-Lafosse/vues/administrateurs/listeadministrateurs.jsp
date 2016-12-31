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

	<form id="formulairerecherche" name="formulairerecherche" method="POST" action="administrationAdministrateurs">
		<table cellspacing="4" cellpadding="0" id="tableau" width="520px">
			<tr>
				<td>
					<input type="text" class="input" id="recherche" name="recherche" value="<c:out value="${recherche}"/>"/></td>
				<td>
					<select name="typerecherche" id="typerecherche" onchange="changerTypeRecherche();">
						<option value="administrateur.id_administrateur" <c:if test="${typerecherche == 'administrateur.id_administrateur' }">selected="selected"</c:if> >Id</option>
						<option value="administrateur.nomadministrateur" <c:if test="${typerecherche == 'administrateur.nomadministrateur' }">selected="selected"</c:if> >Nom</option>
						<option value="administrateur.prenomadministrateur" <c:if test="${typerecherche == 'administrateur.prenomadministrateur' }">selected="selected"</c:if> >Prénom</option>
						<option value="administrateur.identifiantadministrateur" <c:if test="${typerecherche == 'administrateur.identifiantadministrateur' }">selected="selected"</c:if> >Identifiant</option>
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
		<a href="<b:config attribut="urlApplication"/>administrationAdministrateurs?action=lister" title="Afficher la liste complète des administrateurs">
			<img src="<b:config attribut="urlApplication"/>images/application/clientadmin.jpg" border="0" align="absmiddle"/>
			&nbsp;Liste des administrateurs
		</a>
	</div>

	<!-- Pagination -->
	<p:pagination servlet="administrationAdministrateurs" pageActuel="${pageActuel}" totalElement="${totalElement}" maxParPage="${maxParPage}" champTri="${champTri}" typeTri="${typeTri}">
		<p:paramServlet valeur="lister" nom="action"/>
		<p:paramServlet valeur="${recherche}" nom="recherche"/>
		<p:paramServlet valeur="${typerecherche}" nom="typerecherche"/>
		
		<a href="<b:config attribut="urlApplication"/>administrationAdministrateurs?action=ajouter" style="font-size:11px">
			Ajouter un administrateur
		</a> 
		
		<table id="tableaubordure" cellspacing="0" cellpadding="0" >
			<tr style="background-color:white">
				<td colspan="4">
					El&eacute;ments [ <p:premierElement/> - <p:dernierElement/> ] sur <p:totalElement/>
					&nbsp;&nbsp;
					<p:premierePage format="image" cheminImage="images/application/premierepage.gif"/>
					<p:pagePrecedente format="image" cheminImage="images/application/pageprecedente.gif"/>
					&nbsp;( Page <p:pageActuel/> sur <p:dernierePage format="valeur"/> )&nbsp;
					<p:pageSuivante format="image" cheminImage="images/application/pagesuivante.gif"/>
					<p:dernierePage format="image" cheminImage="images/application/dernierepage.gif"/>
				</td>
				<td colspan="2" align="right">
					Afficher : 
					<select onchange="document.location = this.value;">
						<option value="<p:maxParPage valeur="5" format="url"/>" <c:if test="${maxParPage == '5' }">selected="selected"</c:if> >5 &eacute;l&eacute;ments</option>
						<option value="<p:maxParPage valeur="10" format="url"/>" <c:if test="${maxParPage == '10' }">selected="selected"</c:if> >10 &eacute;l&eacute;ments</option>
						<option value="<p:maxParPage valeur="15" format="url"/>" <c:if test="${maxParPage == '15' }">selected="selected"</c:if> >15 &eacute;l&eacute;ments</option>
						<option value="<p:maxParPage valeur="20" format="url"/>" <c:if test="${maxParPage == '20' }">selected="selected"</c:if> >20 &eacute;l&eacute;ments</option>
					</select>
				</td>
			</tr>
			
			<tr align="center" class="entetetableau">
				<td width="50px">
					Id <p:tri champ="id_administrateur" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">
					Nom <p:tri champ="nomadministrateur" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">
					Pr&eacute;nom <p:tri champ="prenomadministrateur" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">
					Identifiant <p:tri champ="identifiantadministrateur" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="200px">
					Adresse mail <p:tri champ="mailadministrateur" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">Gestion</td>
			</tr>
	
			<c:set value="0" var="index"/>
			<c:forEach items="${listeAdministrateurs}" var="administrateur" >
				
				<c:if test="${index % 2 == '0'}">
					<tr align="center" class="lignefonce">
				</c:if>
				<c:if test="${index % 2 == '1'}">
					<tr align="center" class="ligneclair">
				</c:if>
							
					<td>
						<c:out value="${administrateur.id}"/>
					</td>
					<td>
						<c:out value="${administrateur.nom}"/>
					</td>
					<td>
						<c:out value="${administrateur.prenom}"/>
					</td>						
					<td>
						<c:out value="${administrateur.identifiant}"/>
					</td>
					<td>
						<c:out value="${administrateur.mail}"/>
					</td>
					<td>
						<a href="administrationAdministrateurs?action=consulter&idAdministrateur=<c:out value="${administrateur.id}"/>" title="Afficher les informations de ce compte administrateur">
							<img src="<b:config attribut="urlApplication"/>images/application/consulteradmin.png" />
						</a>
						&nbsp;
						<a href="administrationAdministrateurs?action=modifier&idAdministrateur=<c:out value="${administrateur.id}"/>" title="Modifier les informations de ce compte administrateur">
							<img src="<b:config attribut="urlApplication"/>images/application/modifieradmin.png" />
						</a>
						&nbsp;
						<img src="<b:config attribut="urlApplication"/>images/application/supprimeradmin.png" title="Supprimer ce compte administrateur" style="cursor: pointer;" onclick="confirmerSuppressionAdministrateur(<c:out value="${administrateur.id}"/>);"/>
					</td>
				<tr>
			
				<c:set value="${index + 1 }" var="index" />
			
			</c:forEach>
			
			<tr style="background-color:white">
				<td colspan="4">
					El&eacute;ments [ <p:premierElement/> - <p:dernierElement/> ] sur <p:totalElement/>
					&nbsp;&nbsp;
					<p:premierePage format="image" cheminImage="images/application/premierepage.gif"/>
					<p:pagePrecedente format="image" cheminImage="images/application/pageprecedente.gif"/>
					&nbsp;( Page <p:pageActuel/> sur <p:dernierePage format="valeur"/> )&nbsp;
					<p:pageSuivante format="image" cheminImage="images/application/pagesuivante.gif"/>
					<p:dernierePage format="image" cheminImage="images/application/dernierepage.gif"/>
				</td>
				<td colspan="2" align="right">
					Afficher : 
					<select onchange="document.location = this.value;">
						<option value="<p:maxParPage valeur="5" format="url"/>" <c:if test="${maxParPage == '5' }">selected="selected"</c:if> >5 &eacute;l&eacute;ments</option>
						<option value="<p:maxParPage valeur="10" format="url"/>" <c:if test="${maxParPage == '10' }">selected="selected"</c:if> >10 &eacute;l&eacute;ments</option>
						<option value="<p:maxParPage valeur="15" format="url"/>" <c:if test="${maxParPage == '15' }">selected="selected"</c:if> >15 &eacute;l&eacute;ments</option>
						<option value="<p:maxParPage valeur="20" format="url"/>" <c:if test="${maxParPage == '20' }">selected="selected"</c:if> >20 &eacute;l&eacute;ments</option>
					</select>
				</td>
			</tr>
		</table>
	</p:pagination>
	<br/>

<%@ include file="../outils/adminpiedpage.jspf" %>