<!-- Entete de la page -->
<%@ include file="../outils/entete.jspf" %>

	<br>
	
	<center>
		<table border="0" class="tableaupanier" width="720px">
			<tr class="titreformulaire" align="center">
				<td colspan="6"> 
					Mes commandes
				</td>
			</tr>
		</table>
		
		<br/>
	
		<p:pagination servlet="gestionCommandes" pageActuel="${pageActuel}" totalElement="${totalElement}" maxParPage="${maxParPage}" champTri="${champTri}" typeTri="${typeTri}">
			<p:paramServlet valeur="lister" nom="action"/>
				
			<table class="paginationpanier">
				<tr>
					<td>
						El&eacute;ments [ <p:premierElement/> - <p:dernierElement/> ] sur <p:totalElement/>
						&nbsp;&nbsp;
						<p:premierePage format="image" cheminImage="images/application/premierepage.gif"/>
						<p:pagePrecedente format="image" cheminImage="images/application/pageprecedente.gif"/>
						( Page <p:pageActuel/> sur <p:dernierePage format="valeur"/> )
						<p:pageSuivante format="image" cheminImage="images/application/pagesuivante.gif"/>
						<p:dernierePage format="image" cheminImage="images/application/dernierepage.gif"/>
					</td>
					<td align="right">
						Trier par : 
						<select onchange="document.location = this.value;">
							<option value="<p:tri champ="id_commande" format="url"/>" <c:if test="${champTri == 'id_commande' }">selected="selected"</c:if> >R&eacute;f&eacute;rence</option>
							<option value="<p:tri champ="totalcommande" format="url"/>" <c:if test="${champTri == 'totalcommande' }">selected="selected"</c:if> >Prix</option>
							<option value="<p:tri champ="datecreationcommande" format="url"/>" <c:if test="${champTri == 'datecreationcommande' }">selected="selected"</c:if> >Date de commande</option>
							<option value="<p:tri champ="etatcommande" format="url"/>" <c:if test="${champTri == 'etatcommande' }">selected="selected"</c:if> >Etat</option>
						</select>
					</td>
				</tr>
			</table>	
		</p:pagination>
	
		<table border="0" class="tableaupanier" width="720px">
				
			<!-- Entete du panier -->
			<tr class="entetepanier">
				<td width="150px">
					Num&eacute;ro de commande
				</td>
				<td width="150">
					Commande pass&eacute;e le
				</td>
				<td width="150px">
					Derni&egrave;re modification
				</td>
				<td>
					Etat
				</td>
				<td width="100px">
					Prix total TTC
				</td>
				<td width="70px">
					Options
				</td>
			</tr>
				
			<!-- On définit un index pour la déterminer la classe de la ligne -->
			<c:set value="0" var="index"/>
				
			<!-- Chaque objet du panier est ajouté au tableau -->
			<c:forEach items="${listeCommandes}" var="commande">
					
				<!-- Classe de la ligne -->
				<c:if test="${index % 2 == '0'}">
					<tr class="ligne2panier">
				</c:if>
				<c:if test="${index % 2 == '1'}">
					<tr class="ligne1panier">
				</c:if>
					<td>
						<c:out value="${commande.id}"/>
					</td>
					<td>
						<b:formatDate date="${commande.dateCreation}" format="dd/MM/yyyy"/>
					</td>
					<td>
						<b:formatDate date="${commande.dateModification}" format="dd/MM/yyyy"/>
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
						<b:prix montant="${commande.total + (commande.totalPoids * 0.01)}" /> &euro;
					</td>
					<td class="lien">
						<a href="<b:config attribut="urlApplication"/>gestionCommandes?action=consulter&idCommande=<c:out value="${commande.id}"/>">
							<img src="<b:config attribut="urlApplication"/>images/application/consulteradmin.png">
						</a>
					</td>
				</tr>
					
				<!-- On incrémente l'index -->
				<c:set value="${index + 1 }" var="index" />
					
			</c:forEach>
		</table>
		
		<p:pagination servlet="gestionCommandes" pageActuel="${pageActuel}" totalElement="${totalElement}" maxParPage="${maxParPage}" champTri="${champTri}" typeTri="${typeTri}">
			<p:paramServlet valeur="lister" nom="action"/>
					
			<table class="paginationbottom">
				<tr>
					<td>
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
			
	</center>

<!-- Inclure le pieds de page -->		
<%@ include file="../outils/piedpage.jspf" %>