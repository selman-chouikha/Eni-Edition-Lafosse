<%@ include file="../outils/entete.jspf" %>
	
	<div class="texte">
		<img src="<b:config attribut="urlApplication"/>images/application/clientadmin.jpg" border="0" align="absmiddle"/>
		&nbsp;<b>Bienvenue sur Betaboutique, la boutique de DVD</b>
	</div>

	<br/>
	
	<div class="texte">
		&nbsp;&nbsp;&nbsp;&nbsp;La boutique de DVD BetaBoutique vous propose une s&eacute;lection de films et documentaires &agrave; petit prix <br/>
		et vous invite &agrave; retrouver l'actualit&eacute; des DVD et du cin&eacute;ma. 
		<br>
		<br>
		Nous vous souhaitons une tr&egrave;s bonne visite.
	</div>
	
	<br/>
	<br/>
	
	<div class="texte">
		&nbsp;<b>Nos promotions</b>
	</div>
	
	<br/>
	
	<table class="tableauindex" cellspacing="10" style="background: url('<b:config attribut="urlApplication"/>images/application/degrade_tableau.gif') repeat-x;"> 
		<tr>	
			<c:forEach items="${promotion}" var="article">			
				<td class="ligneimagearticle">
					<a href="<b:config attribut="urlApplication"/>gestionArticles?action=consulter&idArticle=<c:out value="${article.id}"/>">
						<img src="<b:config attribut="urlApplication"/>images/articles/<c:out value="${article.vignette}"/>" height="150px"/>
					</a>
					<br/>
					<b:resumer longueur="3" texte="${article.nom}"/>
					<br/>
					<br/>
					<div class="prixarticle">
						Prix : <b:reduction prix="${article.prix}" pourcentage="${article.reduction}"/> &euro;
						<br/>
						au lieu de <b:prix montant="${article.prix}"/> &euro;
					</div>
				</td>
			</c:forEach>
		</tr>
	</table>
	
	<br/>
	
	<div class="texte">
		&nbsp;<b>Nos nouveaut&eacute;s</b>
	</div>
	
	<br/>
	
	<table class="tableauindex" cellspacing="10" style="background: url('<b:config attribut="urlApplication"/>images/application/degrade_tableau.gif') repeat-x;"> 
		<tr>	
			<c:forEach items="${nouveaute}" var="article">			
				<td class="ligneimagearticle">
					<a href="<b:config attribut="urlApplication"/>gestionArticles?action=consulter&idArticle=<c:out value="${article.id}"/>">
						<img src="<b:config attribut="urlApplication"/>images/articles/<c:out value="${article.vignette}"/>" height="150px"/>
					</a>
					<br/>
					<b:resumer longueur="3" texte="${article.nom}"/>
					<br/>
					<br/>
					<div class="prixarticle">
						Prix : <b:reduction prix="${article.prix}" pourcentage="${article.reduction}"/> &euro;
					</div>
				</td>
			</c:forEach>
		</tr>
	</table>

<%@ include file="../outils/piedpage.jspf" %>

