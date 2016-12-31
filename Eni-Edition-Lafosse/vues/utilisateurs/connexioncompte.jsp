<!-- Entete de la page -->
<%@ include file="../outils/entete.jspf" %>

	<br/>

	<form action="<b:config attribut="urlApplication"/>gestionSession?action=validerauthentification" method="POST">
		<center>
			
			<!-- On Stock la page d'appel -->
			<c:if test="${pagePrecedente == '' }">
				<input id="pageprecedente" name="pageprecedente" type="hidden" value="<c:out value="${header.referer}"/>"/>
			</c:if>
			<c:if test="${pagePrecedente != '' }">
				<input id="pageprecedente" name="pageprecedente" type="hidden" value="<c:out value="${pagePrecedente}"/>"/>
			</c:if>
		
			<table cellpadding="5" cellspacing="0" class="formulaire">
				<tr>
					<td colspan="2" class="titreformulaire">Connexion &agrave; votre compte</td>
				</tr>
				
				<tr>
					<td>Identifiant : </td>
					<td><input class="input" type="text" name="identifiant" id="identifiant" value="<c:out value="${identifiant}"/>"/></td>
				</tr>
				<tr>
					<td>Mot de passe : </td>
					<td><input class="input" type="password" name="motDePasse" id="motDePasse" /></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<input type="image" title="Se connecter" src="<b:config attribut="urlApplication"/>images/application/valider.gif"/>
					</td>
				</tr>
			</table>
			<br>
			<div class="texte">
				Si vous ne disposez pas encore de compte, vous pouvez vous inscrire sur ce <a href="<b:config attribut="urlApplication"/>gestionClient?action=ajouter" title="S'inscrire">lien</a>
			</div>
		</center>
	</form>

<!-- Pieds de page -->
<%@ include file="../outils/piedpage.jspf" %>