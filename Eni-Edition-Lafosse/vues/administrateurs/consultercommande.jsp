<!-- Entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>

	<!-- Plugin javascript pour la gestion des commandes -->
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/panier.js"></script>

	<div class="titre">
		<a href="administrationCommandes" title="Afficher la liste complète des commandes">
			<img src="<b:config attribut="urlApplication"/>images/application/clientadmin.jpg" border="0" align="absmiddle"/>
			&nbsp;Liste des commandes
		</a>
	</div>
	
	<center>
			
		<!-- Récapitulatif de la commande -->
		<table>
			<tr>
				<td>
					<table class="recapitulatifcommande" width="485px">
						<tr>
							<td>
								<b>R&eacute;f&eacute;rence de la commande :</b> <c:out value="${commande.id}"/>
							</td>
						</tr>
						<tr>
							<td>
								<b>Commande pass&eacute;e le :</b> <b:formatDate date="${commande.dateCreation}" format="dd/MM/yyyy"/>
							</td>
						</tr>
						<tr>
							<td>
								<b>Mise &agrave; jour le : </b> <b:formatDate date="${commande.dateModification}" format="dd/MM/yyyy"/>
							</td>
						</tr>
						<tr>
							<td>
								<b>Etat de la commande : </b>
								<c:if test="${commande.etat == '0'}">
									<img style="vertical-align: text-bottom" src="<b:config attribut="urlApplication"/>images/application/actiforange.gif" title="En attente du paiement">
									En attente de paiement
								</c:if>
										
								<c:if test="${commande.etat == '1'}">
									<img style="vertical-align: text-bottom" src="<b:config attribut="urlApplication"/>images/application/actif.gif" title="Commande exp&eacute;di&eacute;e">
									Commande expédiée
								</c:if>
										
								<c:if test="${commande.etat == '2'}">
									<img style="vertical-align: text-bottom"  src="<b:config attribut="urlApplication"/>images/application/inactif.gif" title="Commande annul&eacute;e">
									Commande annulée
								</c:if>
							</td>
						</tr>
						<tr>
							<td></td>
						</tr>
					</table>
				</td>
				<td>	
					<table width="200px" class="tableaupanier">
						<tr class="entetepanier"> 
							<td >
								Informations client
							</td>
						</tr>
						<tr>
							<td class="ligne2panier">
								<c:out value="${commande.client.nom}"/> <c:out value="${commande.client.prenom}"/>
							</td>
						</tr>
						<tr>
							<td class="ligne1panier">
								<c:out value="${commande.client.adresse}"/>
							</td>
						</tr>
						<tr> 
							<td class="ligne2panier">
								<c:out value="${commande.client.codePostal}"/>
							</td>
						</tr>
						<tr>
							<td class="ligne1panier">
								<c:out value="${commande.client.ville}"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br/>
		<table border="0" class="tableaupanier" width="700px">
			
			<!-- Entete de la commande -->
			<tr class="entetepanier">
				<td>
					Produit
				</td>
				<td width="80px">
					Quantit&eacute;
				</td>
				<td width="120px">
					Prix unitaire TTC
				</td>
				<td width="80px">
					Disponibilit&eacute;
				</td>
				<td width="174px">
					Prix total TTC
				</td>
			</tr>
				
			<!-- On définit un index pour la déterminer la classe de la ligne -->
			<c:set value="0" var="index"/>
				
			<!-- Chaque objet du panier est ajouté au tableau -->
			<c:forEach items="${commande.listeLigneCommande}" var="ligneCommande">
					
				<!-- Classe de la ligne -->
				<c:if test="${index % 2 == '0'}">
					<tr class="ligne2panier">
				</c:if>
				<c:if test="${index % 2 == '1'}">
					<tr class="ligne1panier">
				</c:if>
					<td>
						<b:resumer longueur="5" texte="${ligneCommande.article.nom}"/>
					</td>
					<td>
						<c:out value="${ligneCommande.quantite}"/>
					</td>
					<td>
						<b:prix montant="${ligneCommande.prixUnitaire}"/> &euro;
					</td>
					<td>
						<c:if test="${ligneCommande.article.stock > 0 }"> 
							<img style="vertical-align: text-bottom;" src="<b:config attribut="urlApplication"/>images/application/enstock.bmp" />
						</c:if>
						<c:if test="${ligneCommande.article.stock <= 0 }"> 
							<img style="vertical-align: text-bottom;" src="<b:config attribut="urlApplication"/>images/application/rupture.bmp" />
						</c:if>
					</td>
					<td>
						<b:prix montant="${ligneCommande.quantite * ligneCommande.prixUnitaire}"/> &euro;
					</td>
				</tr>
					
				<!-- On incrémente l'index -->
				<c:set value="${index + 1 }" var="index" />
				
			</c:forEach>
		</table>
			
		<!-- Total du panier -->
		<table border="0" class="tableautotalcommande" width="390px" align="right">
			<tr class="soustotal_panier">
				<td align="right" >
					Sous total HT : &nbsp;&nbsp;
				</td>
				<td width="174px">
					<b:prix montant="${commande.total * 0.804}"/> &euro;
				</td>
			</tr>
			<tr class="soustotal_panier">
				<td align="right">
					TVA (19,6%) : &nbsp;&nbsp;
				</td>
				<td>
					<b:prix montant="${commande.total * 0.196}"/> &euro;
				</td>
			</tr>
			<tr class="soustotal_panier">
				<td align="right">
					Frais de port (1&euro; pour 100g) : &nbsp;&nbsp;
				</td>
				<td>
					<b:prix montant="${commande.totalPoids / 100}"/> &euro;
				</td>
			</tr>
			<tr class="total_panier">
				<td align="right">
					Total TTC (TVA 19,6%) : &nbsp;&nbsp;
				</td>
				<td>
					<b:prix montant="${commande.total + (commande.totalPoids / 100)}"/> &euro;
				</td>
			</tr>
		</table>
	</center>
		
<!-- Pieds de page -->		
<%@ include file="../outils/adminpiedpage.jspf" %>