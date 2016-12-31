<!-- Entete de la page -->
<%@ include file="../outils/entete.jspf" %>

	<br/>
	
	<!-- Lorsque le panier est vide -->
	<c:if test="${panier.total == '0' || panier == null}">
		<center>		
			<div class="texte">
				<b>Votre panier est vide !!!</b>
			</div>
		</center>
	</c:if>
	
	<!-- Panier non vide -->
	<c:if test="${panier.total != '0' && panier != null}">
		<center>
			<table border="0" class="tableaupanier" width="700px">
				<tr class="titreformulaire" align="center">
					<td colspan="6"> 
						Mon panier
					</td>
				</tr>
			</table>
			<br/>
			<table border="0" class="tableaupanier" width="700px">
				
				<!-- Entete du panier -->
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
					<td width="100px">
						Prix total TTC
					</td>
					<td width="70px">
						Supprimer
					</td>
				</tr>
				
				<!-- On définit un index pour la déterminer la classe de la ligne -->
				<c:set value="0" var="index"/>
				
				<!-- Chaque objet du panier est ajouté au tableau -->
				<c:forEach items="${panier.listeLigneCommande}" var="ligneCommande">
					
					<!-- Classe de la ligne -->
					<c:if test="${index % 2 == '0'}">
						<tr class="ligne2panier">
					</c:if>
					<c:if test="${index % 2 == '1'}">
						<tr class="ligne1panier">
					</c:if>
						<td>
							<a href="<b:config attribut="urlApplication"/>gestionArticles?action=consulter&idArticle=<c:out value="${ligneCommande.article.id}"/>">
								<b:resumer longueur="5" texte="${ligneCommande.article.nom}"/>
							</a>
						</td>
						<td>
							<a href="<b:config attribut="urlApplication"/>gestionPanier?action=supprimer&idArticle=<c:out value="${ligneCommande.article.id}"/>">
								<img style="vertical-align:text-bottom;" src="<b:config attribut="urlApplication"/>images/application/reduire.bmp"/>
							</a>
							<c:out value="${ligneCommande.quantite}"/>
							<a href="<b:config attribut="urlApplication"/>gestionPanier?action=ajouter&idArticle=<c:out value="${ligneCommande.article.id}"/>">
								<img style="vertical-align:text-bottom;" src="<b:config attribut="urlApplication"/>images/application/ajouter.bmp"/>
							</a>
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
						<td>
							<a href="<b:config attribut="urlApplication"/>gestionPanier?action=supprimerdefinitif&idArticle=<c:out value="${ligneCommande.article.id}"/>">
								<img style="vertical-align:text-bottom;" src="<b:config attribut="urlApplication"/>images/application/trash.png"/>
							</a>
						</td>
					</tr>
					
					<!-- On incrémente l'index -->
					<c:set value="${index + 1 }" var="index" />
					
				</c:forEach>
			</table>
			
			<!-- Total du panier -->
			<table border="0" class="tableautotalpanier" width="390px" align="right">
				<tr class="soustotal_panier">
					<td align="right" >
						Sous total HT : &nbsp;&nbsp;
					</td>
					<td width="174px">
						<b:prix montant="${panier.total * 0.804}"/> &euro;
					</td>
				</tr>
				<tr class="soustotal_panier">
					<td align="right">
						TVA (19,6%) : &nbsp;&nbsp;
					</td>
					<td>
						<b:prix montant="${panier.total * 0.196}"/> &euro;
					</td>
				</tr>
				<tr class="soustotal_panier">
					<td align="right">
						Frais de port (1&euro; pour 100g) : &nbsp;&nbsp;
					</td>
					<td>
						<b:prix montant="${panier.totalPoids / 100}"/> &euro;
					</td>
				</tr>
				<tr class="total_panier">
					<td align="right">
						Total TTC (TVA 19,6%) : &nbsp;&nbsp;
					</td>
					<td>
						<b:prix montant="${panier.total + (panier.totalPoids / 100)}"/> &euro;
					</td>
				</tr>
				<tr height="30px">
					<td colspan="2" align="center" class="lien">
						<c:if test="${compte.class == 'class betaboutique.beans.Client' }">
							<img style="vertical-align:middle;cursor:pointer;" src="<b:config attribut="urlApplication"/>images/application/valider.gif" onclick="confirmerValidationPanier();"/>
						</c:if>
						<c:if test="${compte.class != 'class betaboutique.beans.Client' }">
							<img style="vertical-align:middle;cursor:pointer;" src="<b:config attribut="urlApplication"/>images/application/valider.gif" onclick="document.location = 'gestionSession';"/>
						</c:if>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="<c:out value="${header.referer}"/>">
							<img title="Retourner au catalogue" style="vertical-align: middle;cursor: " src="<b:config attribut="urlApplication"/>images/application/retour.gif"/>
						</a>
					</td>
				</tr>
			</table>
		</center>
	</c:if>


<!-- Pieds de page -->		
<%@ include file="../outils/piedpage.jspf" %>