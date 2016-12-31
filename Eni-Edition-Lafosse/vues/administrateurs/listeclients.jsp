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
	
	<div id="rechercher">
		<a href="javascript:afficherRecherche();">
			<img src="<b:config attribut="urlApplication"/>images/application/louperecherche.png" title="Cliquer pour afficher le formulaire de recherche" alt="Rechercher" align="absmiddle"border="0"/>
		</a>
	</div>

	<form id="formulairerecherche" name="formulairerecherche" method="POST" action="administrationClients">
		<table cellspacing="4" cellpadding="0" id="tableau" width="520px">
			<tr>
				<td>
					<input type="text" class="input" id="recherche" name="recherche" value="<c:out value="${recherche}"/>"/></td>
				<td>
					<select name="typerecherche" id="typerecherche" onchange="changerTypeRecherche();">
						<option value="client.id_client" <c:if test="${typerecherche == 'client.id_client' }">selected="selected"</c:if> >Id</option>
						<option value="client.nomclient" <c:if test="${typerecherche == 'client.nomclient' }">selected="selected"</c:if> >Nom</option>
						<option value="client.prenomclient" <c:if test="${typerecherche == 'client.prenomclient' }">selected="selected"</c:if> >Prénom</option>
						<option value="client.identifiantclient" <c:if test="${typerecherche == 'client.identifiantclient' }">selected="selected"</c:if> >Identifiant</option>
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
		<a href="<b:config attribut="urlApplication"/>administrationClients?action=lister" title="Afficher la liste complète des clients">
			<img src="<b:config attribut="urlApplication"/>images/application/clientadmin.jpg" border="0" align="absmiddle"/>
			&nbsp;Liste des clients
		</a>
	</div>
	
	<a href="<b:config attribut="urlApplication"/>administrationClients?action=ajouter" style="font-size:11px">
		Ajouter un client
	</a> 

	<p:pagination servlet="administrationClients" pageActuel="${pageActuel}" totalElement="${totalElement}" maxParPage="${maxParPage}" champTri="${champTri}" typeTri="${typeTri}">
		<p:paramServlet valeur="lister" nom="action"/>
		<p:paramServlet valeur="${recherche}" nom="recherche"/>
		<p:paramServlet valeur="${typerecherche}" nom="typerecherche"/>
		
		<table id="tableaubordure" cellspacing="0" cellpadding="0">
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
					Id <p:tri champ="id_client" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">
					Nom <p:tri champ="nomclient" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">
					Pr&eacute;nom <p:tri champ="prenomclient" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">
					Identifiant <p:tri champ="identifiantclient" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="200px">
					Adresse mail <p:tri champ="emailclient" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">Gestion</td>
			</tr>
	
			<c:set value="0" var="index"/>
			<c:forEach items="${listeClients}" var="client" >
				
				<c:if test="${index % 2 == '0'}">
					<tr align="center" class="lignefonce">
				</c:if>
				<c:if test="${index % 2 == '1'}">
					<tr align="center" class="ligneclair">
				</c:if>
							
					<td>
						<c:out value="${client.id}"/>
					</td>
					<td>
						<c:out value="${client.nom}"/>
					</td>
					<td>
						<c:out value="${client.prenom}"/>
					</td>						
					<td>
						<c:out value="${client.identifiant}"/>
					</td>
					<td>
						<c:out value="${client.mail}"/>
					</td>
					<td>
						<a href="administrationClients?action=consulter&idClient=<c:out value="${client.id}"/>" title="Afficher les informations de ce client">
							<img src="<b:config attribut="urlApplication"/>images/application/consulteradmin.png" />
						</a>
						&nbsp;
						<a href="administrationClients?action=modifier&idClient=<c:out value="${client.id}"/>" title="Modifier les informations de ce client">
							<img src="<b:config attribut="urlApplication"/>images/application/modifieradmin.png" />
						</a>
						&nbsp;
						<img src="<b:config attribut="urlApplication"/>images/application/supprimeradmin.png" style="cursor: pointer;" title="Supprimer ce client" onclick="confirmerSuppressionClient(<c:out value="${client.id}"/>);"/>
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