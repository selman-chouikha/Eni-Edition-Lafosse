<%@ include file="../outils/entete.jspf" %>
	
	<br/>
	<center>
		<table cellpadding="5" cellspacing="0" class="formulaire" width="300px">
			<tr>
				<td class="titreformulaire">Comment nous contacter</td>
			</tr>
			<tr>
				<td align="center">
					SARL Betaboutiqe
				</td>
			</tr>
			<tr>
				<td align="center">
					<b:config attribut="adresseContact"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<b:config attribut="villeContact"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					T&eacute;l&eacute;phone : <b:config attribut="telephoneContact"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					mail : 
					<a href="mailto:<b:config attribut="mailContact"/>">
						<b:config attribut="mailContact"/>
					</a>
				</td>
			</tr>
		</table>
		
		<br/>

	</center>
	
<%@ include file="../outils/piedpage.jspf" %>

