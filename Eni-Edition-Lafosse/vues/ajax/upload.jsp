<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>

<c:if test="${type == 'upload'}">

	<script type='text/javascript'>
		window.parent.killUpdate('');
		
		<c:forEach items="${erreurs}" var="erreur">
			window.parent.reportError('<c:out value="${erreur}"/>');
		</c:forEach>
		
		<c:if test="${vignette != null && image != null}">
			window.parent.afficherVignette('<c:out value="${image}"/>','<c:out value="${vignette}"/>');
		</c:if>
	</script>
	
</c:if>

<c:if test="${type == 'status'}">

	<table id="tableau" width="520px" cellspacing="4" cellpadding="4">
		<tr>
			<td align="right" width="160px">Progression : </td>
			<td align="left"><div id="progressborder"><div id="progressbar" style="width:<c:out value='${pourcentageEffectue}'/>%"></div></div><div id="pourcentage"> <c:out value='${pourcentageEffectue}'/> %</td>
		</tr>
			<td colspan="2" align="center">
				<div id="detailupload">
					Chargement de <c:out value='${octetLu}'/> sur <c:out value='${tailleTotal}'/> - environ <c:out value='${tempRestant}'/> restante(s)
				</div>
			</td>
		</tr>
	</table>
		
</c:if>