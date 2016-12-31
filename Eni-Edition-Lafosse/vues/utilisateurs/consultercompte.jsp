<!-- Entete de la page -->
<%@ include file="../outils/entete.jspf" %>

	<br/>
	
	<jsp:useBean id="compte" class="gdawj.beans.Client" scope="session"/>
	
	<form action="gestionClient?action=modifier" method="POST">
		<center>
			<table cellpadding="5" cellspacing="0" class="formulaire">
				<tr>
					<td colspan="2" class="titreformulaire">Vos informations</td>
				</tr>
				<tr>
					<td>Identifiant : </td>
					<td>
						<jsp:getProperty name="compte" property="identifiant"/>
					</td>
				<tr> 
				<tr>
					<td>Nom : </td>
					<td>
						<jsp:getProperty name="compte" property="nom"/>
					</td>
				</tr>
				<tr>
					<td>Pr&eacute;nom :  </td>
					<td>
						<jsp:getProperty name="compte" property="prenom"/>
					</td>
				</tr>
				<tr>
					<td>Adresse mail :  </td>
					<td>
						<jsp:getProperty name="compte" property="mail"/>
					</td>
				</tr>
				<tr>
					<td>T&eacute;l&eacute;phone :  </td>
					<td>
						<jsp:getProperty name="compte" property="telephone"/>
					</td>
				</tr>
				<tr>
					<td>Adresse : </td>
					<td>
						<jsp:getProperty name="compte" property="adresse"/>
					</td>
				</tr>
				<tr>
					<td>Code postale : </td>
					<td>
						<jsp:getProperty name="compte" property="codePostal"/>
					</td>
				</tr>
				<tr>
					<td>Ville :  </td>
					<td>
						<jsp:getProperty name="compte" property="ville"/>
					</td>
				</tr>
				<tr>
					<td>Pays :  </td>
					<td>
						<jsp:getProperty name="compte" property="pays"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="image" title="Modifier les informations de votre compte" src="<b:config attribut="urlApplication"/>images/application/modifier.gif"/>
					</td>
				</tr>
			</table>
		</center>
	</form>
	
<!-- Pieds de page -->
<%@ include file="../outils/piedpage.jspf" %>