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

	<form id="formulairerecherche" name="formulairerecherche" method="POST" action="administrationCommandes">
		<table cellspacing="4" cellpadding="0" id="tableau" width="520px">
			<tr>
				<td>
					<input type="text" class="input" id="recherche" name="recherche" value="<c:out value="${recherche}"/>"/></td>
				<td>
					<select name="typerecherche" id="typerecherche" onchange="changerTypeRecherche();">
						<option value="commande.id_commande" <c:if test="${typerecherche == 'commande.id_commande' }">selected="selected"</c:if> >Id</option>
						<option value="clientcommande.identifiantclient" <c:if test="${typerecherche == 'clientcommande.identifiantclient' }">selected="selected"</c:if> >Client</option>
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
		<a href="<b:config attribut="urlApplication"/>administrationCommandes?action=lister" title="Afficher la liste complète des commandes">
			<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
			&nbsp;Liste des commandes
		</a>
	</div>

	<!-- Pagination -->
	<p:pagination servlet="administrationCommandes" pageActuel="${pageActuel}" totalElement="${totalElement}" maxParPage="${maxParPage}" champTri="${champTri}" typeTri="${typeTri}">
		<p:paramServlet valeur="lister" nom="action"/>
		<p:paramServlet valeur="${recherche}" nom="recherche"/>
		<p:paramServlet valeur="${typerecherche}" nom="typerecherche"/>
		
		<table id="tableaubordure" cellspacing="0" cellpadding="0" >
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
				<td width="70px">
					R&eacute;f&eacute;rence <p:tri champ="id_commande" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="150px">
					Client <p:tri champ="identifiantclient" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">
					Passée le <p:tri champ="datecreationcommande" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="50px">
					Etat <p:tri champ="etatcommande" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="80px">
					Poids <p:tri champ="totalpoids" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">
					Prix TTC <p:tri champ="totalcommande" format="image" imageCroissant="images/application/asc.gif" imageDecroissant="images/application/desc.gif" />			
				</td>
				<td width="100px">Gestion</td>
			</tr>
	
			<c:set value="0" var="index"/>
			<c:forEach items="${listeCommandes}" var="commande" >
				
				<c:if test="${index % 2 == '0'}">
					<tr align="center" class="lignefonce">
				</c:if>
				<c:if test="${index % 2 == '1'}">
					<tr align="center" class="ligneclair">
				</c:if>
							
					<td>
						<c:out value="${commande.id}"/>
					</td>
					<td>
						<c:out value="${commande.client.identifiant}"/>
					</td>
					<td>
						<b:formatDate date="${commande.dateCreation}" format="dd/MM/yyyy"/>
					</td>						
					<td>
						<c:if test="${commande.etat == '0'}">
							<img src="<b:config attribut="urlApplication"/>images/application/actiforange.gif" title="En attente du paiement">
						</c:if>
							
						<c:if test="${commande.etat == '1'}">
							<img src="<b:config attribut="urlApplication"/>images/application/actif.gif" title="Commande expédiée">
						</c:if>
							
						<c:if test="${commande.etat == '2'}">
							<img src="<b:config attribut="urlApplication"/>images/application/inactif.gif" title="Commande annulée">
						</c:if>
					</td>
					<td>
						<b:prix montant="${commande.totalPoids}" /> g
					</td>
					<td>
						<b:prix montant="${commande.total + (commande.totalPoids * 0.01)}" /> &euro;
					</td>
					<td>
						<a href="administrationCommandes?action=consulter&idCommande=<c:out value="${commande.id}"/>" title="Afficher la factrure de cette commande">
							<img src="<b:config attribut="urlApplication"/>images/application/consulteradmin.png" />
						</a>
						&nbsp;
						<a href="administrationCommandes?action=modifier&idCommande=<c:out value="${commande.id}"/>" title="Modifier les informations de cette commande">
							<img src="<b:config attribut="urlApplication"/>images/application/modifieradmin.png" />
						</a>
						&nbsp;
						<img src="<b:config attribut="urlApplication"/>images/application/supprimeradmin.png" title="Supprimer cette commande" style="cursor: pointer;" onclick="confirmerSuppressionCommande();"/>
					</td>
				<tr>
			
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