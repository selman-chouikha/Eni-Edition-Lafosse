<!-- Entete de la page -->
<%@ include file="../outils/entete.jspf" %>
	
	<!-- Plugin javascript pour le Drag and Drop -->
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/jquery/plugin/ui.mouse.js"></script>
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/jquery/plugin/ui.draggable.js"></script>
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/jquery/plugin/ui.draggable.ext.js"></script>
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/jquery/plugin/ui.droppable.js"></script>
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/jquery/plugin/ui.droppable.ext.js"></script>
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/draganddrop.js"></script>
	
	<!-- Plugin javascript pour la gestion des notes -->
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/note.js"></script>

	<div id="breadcrumbs">
		Cat&eacute;gorie &gt; 
		<a href="<b:config attribut="urlApplication"/>gestionArticles?action=promo">
			Articles en promotion
		</a>
	</div>
	
	<br/>
	
	<table id="conteneurarticle">
		<tr>
			<td>
				<p:pagination servlet="gestionArticles" pageActuel="${pageActuel}" totalElement="${totalElement}" maxParPage="${maxParPage}" champTri="${champTri}" typeTri="${typeTri}">
					<p:paramServlet valeur="promo" nom="action"/>
					<table class="paginationtop">
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
									<option value="<p:tri champ="nomarticle" format="url"/>" <c:if test="${champTri == 'nomarticle' }">selected="selected"</c:if> >Nom</option>
									<option value="<p:tri champ="prixarticle" format="url"/>" <c:if test="${champTri == 'prixarticle' }">selected="selected"</c:if> >Prix</option>
								</select>
							</td>
						</tr>
					</table>	
				</p:pagination>
					
					
				<c:forEach items="${listeArticles}" var="article" >
					<table class="article">
						<tr>
							<td rowspan="6">
								<div id="<c:out value="${article.id}"/>" class="img">
									<img src="<b:config attribut="urlApplication"/>images/articles/<c:out value="${article.vignette}"/>" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="titrearticle">
								<a href="<b:config attribut="urlApplication"/>gestionArticles?action=consulter&idArticle=<c:out value="${article.id}" />">
									<b:resumer longueur="9" texte="${article.nom}" />
								</a>
							</td>
							<td class="disponibilite">
								<c:if test="${article.stock > 0 }"> 
									Disponibilt&eacute; : <img style="vertical-align: text-bottom;" src="<b:config attribut="urlApplication"/>images/application/enstock.bmp" />
								</c:if>
								<c:if test="${article.stock <= 0 }"> 
									Disponibilt&eacute; : <img style="vertical-align: text-bottom;" src="<b:config attribut="urlApplication"/>images/application/rupture.bmp" />
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="notearticle" colspan="2">
								Note : 
								<div class="note">
									<div class="moyennenote"></div>
									
									<!-- On affiche la note de l'article dans le div nommé moyennenote -->
									<script type="text/javascript">
										afficherNote('<c:out value="${article.note}"/>');
									</script>
								</div>
							</td>
						</tr>
						<tr>
							<td class="description" colspan="2">
								<b:resumer longueur="20" texte="${article.description}"/>
							</td>
						</tr>
						<tr>
							<td class="prixarticle" colspan="2">
								Prix : <b:reduction prix="${article.prix}" pourcentage="${article.reduction}"/> &euro;
								<br/>
								<c:if test="${article.reduction != 0 }">
									<div class="paspromoarticle">
										au lieu de <b:prix montant="${article.prix}"/> &euro;
									</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="lien" colspan="2"> 
								<a href="<b:config attribut="urlApplication"/>gestionArticles?action=consulter&idArticle=<c:out value='${article.id}'/>">
									<img title="Consulter la fiche de l'article" style="vertical-align: middle;" src="<b:config attribut="urlApplication"/>images/application/detail.gif"/>
								</a>
								&nbsp;
								&nbsp;
								<c:if test="${compte.class != 'class betaboutique.beans.Administrateur' }">
									<img title="Ajouter le DVD au panier" style="vertical-align: middle;cursor: pointer" src="<b:config attribut="urlApplication"/>images/application/ajouter.gif" onclick="ajouterPanier('<c:out value="${article.id}"/>');"/>
								</c:if>
								<c:if test="${compte.class == 'class betaboutique.beans.Administrateur' }">
									<img title="Ajouter le DVD au panier" style="vertical-align: middle;cursor: pointer" src="<b:config attribut="urlApplication"/>images/application/ajouter.gif" onclick="alert('En tant qu\'administrateur vous ne pouvez effectuer cette action');"/>
								</c:if>
							</td>
						</tr>	
					</table>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td>
				<p:pagination servlet="gestionArticles" pageActuel="${pageActuel}" totalElement="${totalElement}" maxParPage="${maxParPage}" champTri="${champTri}" typeTri="${typeTri}">
					<p:paramServlet valeur="promo" nom="action"/>
					
					<table class="paginationbottom">
						<tr>
							<td>
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
			</td>
		</tr>
	</table>	
	
<!-- Inclure le pieds de page -->		
<%@ include file="../outils/piedpage.jspf" %>