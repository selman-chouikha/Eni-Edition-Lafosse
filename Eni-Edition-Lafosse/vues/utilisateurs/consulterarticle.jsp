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
		<a href="<b:config attribut="urlApplication"/>gestionArticles?action=lister&idCategorie=<c:out value="${article.categorie.id}"/>">
			<c:out value="${article.categorie.nom}"/>
		</a>
		&gt;
		<a href="<b:config attribut="urlApplication"/>gestionArticles?action=consulter&idArticle=<c:out value="${article.id}"/>">
			<c:out value="${article.nom}"/>
		</a>
	</div>
	
	<br/>
	
	<div id="conteneurarticle">
		<table class="article" cellspacing="5">
			<tr>
				<td rowspan="7" >
					<div id="<c:out value="${article.id}"/>" class="img">
						<img src="<b:config attribut="urlApplication"/>images/articles/<c:out value='${article.photo}'/>" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="titrearticle">
					<a href="<b:config attribut="urlApplication"/>gestionArticles?action=consulter&idArticle=<c:out value='${article.id}'/>">
						<c:out value='${article.nom}'/>
					</a>
				</td>
			</tr>
			<tr>
				<td class="notearticle">
					Note : 
					<div class="note">
					
						<!-- ID de l'article à noter -->
						<input id="idArticle" type="hidden" value="<c:out value='${article.id}'/>"/> 
						
						<!-- Note globale de l'article -->
						<div class="moyennenote"></div>
									
						<!-- On affiche la note de l'article dans le div nommé moyennenote -->
						<script type="text/javascript">
								afficherNote('<c:out value="${article.note}"/>');
						</script>
						
						<!-- Permet de noter l'article -->
						<div class="choixnote">
							<div class="etoile" title="Donner votre avis sur cet article !"></div>
							<div class="etoile" title="Donner votre avis sur cet article !"></div>
							<div class="etoile" title="Donner votre avis sur cet article !"></div>
							<div class="etoile" title="Donner votre avis sur cet article !"></div>
							<div class="etoile" title="Donner votre avis sur cet article !"></div>
						</div>
						
						<!-- Paramétrage et initialisation des actions pour le vote de l'article -->
						<script type="text/javascript">
								consulterNoteClient();
						</script>
						
					</div>
					
					<!-- Indique le nombre de vote -->
					<div class="vote"></div>
					
					<!-- On affiche le nombre de vote de l'article dans le div nommé vote -->
					<script type="text/javascript">
							afficherVote('<c:out value="${article.vote}"/>');
					</script>
					
					<!-- Message lorsque l'utilisateur ne peut noté l'article -->
					<div class="message"></div>
				</td>
			</tr>
			<tr >
				<td class="notearticle">
					<c:if test="${article.stock > 0 }"> 
						Disponibilt&eacute; : <img style="vertical-align: text-bottom;" src="<b:config attribut="urlApplication"/>images/application/enstock.bmp" />
					</c:if>
					<c:if test="${article.stock <= 0 }"> 
						Disponibilt&eacute; : <img style="vertical-align: text-bottom;" src="<b:config attribut="urlApplication"/>images/application/rupture.bmp" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="description">
					<c:out value='${article.description}'/>
				</td>
			</tr>
			<tr>
				<td class="prixarticle">
					Prix : <b:reduction pourcentage="${article.reduction}" prix="${article.prix}" /> &euro;
					<br/>
					<c:if test="${article.reduction != 0 }">
						<div class="paspromoarticle"> 
							au lieu de <b:prix montant="${article.prix}" /> &euro;
						</div>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="lien">
					<c:if test="${compte.class != 'class betaboutique.beans.Administrateur' }">
						<img title="Ajouter l'article au panier" style="vertical-align: middle;cursor: pointer" src="<b:config attribut="urlApplication"/>images/application/ajouter.gif" onclick="ajouterPanier('<c:out value="${article.id}"/>');"/>
					</c:if>
					<c:if test="${compte.class == 'class betaboutique.beans.Administrateur' }">
						<img title="Ajouter l'article au panier" style="vertical-align: middle;cursor: pointer" src="<b:config attribut="urlApplication"/>images/application/ajouter.gif" onclick="alert('En tant qu\'administrateur vous ne pouvez effectuer cette action');"/>
					</c:if>	
					&nbsp;
					&nbsp;
					<a href="<c:out value="${header.referer}"/>">
						<img title="Retourner au catalogue" style="vertical-align: middle;cursor: " src="<b:config attribut="urlApplication"/>images/application/retour.gif"/>
					</a>
				</td>
			</tr>	
		</table>
	</div>
	
<!-- Pieds de page -->		
<%@ include file="../outils/piedpage.jspf" %>