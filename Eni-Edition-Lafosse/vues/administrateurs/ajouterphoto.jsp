<!-- inclure l'entete de la page -->
<%@ include file="../outils/adminentete.jspf" %>
	
	<!-- Plugin javascript pour l'upload -->
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/prototype/prototype.js"></script>
	<script type="text/javascript" src="<b:config attribut="urlApplication"/>javascript/upload.js"></script>
	
	<div class="titre">
		<img src="<b:config attribut="urlApplication"/>images/application/articleadmin.png" border="0" align="absmiddle"/>
		&nbsp;<b>Importer une vignette</b>
	</div>

	<!-- Cet IFRAME est utilisé comme cible pour le renvoi d'informations lors la récupération du status de l'upload -->
	<iframe id="target_upload" name="target_upload" style="display:none"></iframe>

	<!-- Formulaire d'upload de fichier -->
	<form enctype="multipart/form-data" name="form" method="POST" action="<b:config attribut="urlApplication"/>upload" onsubmit="getStatus('<b:config attribut="urlApplication"/>');" target="target_upload">
		<table cellspacing="4" cellpadding="0" id="tableau" width="520px">
			<tr>
				<td width="130px">
					Chemin du fichier : 
				</td>
				<td>
					<input type="file" id="importFile" class="input" name="importFile" />
				</td>
				<td>
					<input type="image" id="charger" title="Charger cette vignette" src="<b:config attribut="urlApplication"/>images/application/charger.gif" />
				</td>
			</tr>
		</table>
	</form>

	<div id="status">
		<table id="tableau" width="520px" cellspacing="4" cellpadding="4">
			<tr>
				<td align="right" width="160px">Progression : </td>
				<td align="left"><div id="progressborder"><div id="progressbar" style="width:0%"></div></div><div id="pourcentage">0 %</td>
			</tr>
				<td colspan="2" align="center">
					<div id="detailupload">
						Chargement de 0 Mo sur 0 Mo - environ 0 secondes restante(s) 
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<br>
	
	<form action="<b:config attribut="urlApplication"/>administrationArticles?action=finaliserajout" method="POST">
		
		<!-- On place tous les attributs de l'article précédement ajouter dans le formulaire pour l'ajout en base -->
		<input id="nom" name="nom" type="hidden" value="<c:out value="${nom}"/>" />
		<input id="description" name="description" type="hidden" value="<c:out value="${description}"/>" />
		<input id="categorie" name="categorie" type="hidden" value="<c:out value="${categorie}"/>" />
		<input id="prix" name="prix" type="hidden" value="<c:out value="${prix}"/>" />
		<input id="reduction" name="reduction" type="hidden" value="<c:out value="${reduction}"/>" />
		<input id="poids" name="poids" type="hidden" value="<c:out value="${poids}"/>" />
		<input id="stock" name="stock" type="hidden" value="<c:out value="${stock}"/>" />
		<input id="etat" name="etat" type="hidden" value="<c:out value="${etat}"/>" />
		
		<!-- Nouvelle information de ce formulaire : nom de la vignette et de la photo -->
		<input id="vignettearticle" name="vignettearticle" type="hidden" value="<b:config attribut="vignetteParDefaut"/>" />
		<input id="photoarticle" name="photoarticle" type="hidden" value="<b:config attribut="photoParDefaut"/>" />
		
		<div id="image_article">
			<table cellspacing="4" cellpadding="0" id="tableau" width="520px">
				<tr>
					<td align="center" width="260px">
						Photo 
					</td>
					<td align="center" width="260px">
						Vignette
					</td>
				</tr>
				<tr>
					<td align="center" width="260px">
						<div id="image">
							<img src="<b:config attribut="urlApplication"/>images/articles/<b:config attribut="photoParDefaut"/>" />
						</div>
					</td>
					<td align="center" width="260px">
						<div id="vignette"> 
							<img src="<b:config attribut="urlApplication"/>images/articles/<b:config attribut="vignetteParDefaut"/>" />
						</div>
					</td>
				</tr>
				<tr height="30px">
					<td colspan="2" align="center" >
						<input type="image" title="Enregistrer cet article" src="<b:config attribut="urlApplication"/>images/application/enregistrer.gif" />
					</td>
				</tr>
			</table>
		</div>
	</form>

<%@ include file="../outils/adminpiedpage.jspf" %>