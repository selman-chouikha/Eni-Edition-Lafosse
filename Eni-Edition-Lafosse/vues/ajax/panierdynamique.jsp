<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/betaboutique" prefix="b" %>

	<!-- Lorsque le panier est vide -->
	<c:if test="${panier.total == '0' || panier == null}">
		<center>
			<b>Votre panier est vide !!!</b>
		</center>
	</c:if>
	
	<!-- Panier non vide -->
	<c:if test="${panier.total != '0' && panier != null}">
	
		<!-- Nombre d'article dans le panier -->
		<c:set var="nombreObjet" value="0"/>
		<c:forEach items="${panier.listeLigneCommande}" var="ligneCommande">
			<c:set var="nombreObjet" value="${nombreObjet + 1 }"/>
		</c:forEach>
		
		<!-- Affiche le nombre d'objet dans le panier -->
		<table class="entetepanierdynamique" width="400px">
			<tr>
				<td align="left">
					<b>Vous avez <c:out value="${nombreObjet}"></c:out> produit(s) dans votre panier :</b>
				</td>
			</tr>
		</table>
		
		<table class="tableaupanier" width="400px">
		
			<!-- Entete du panier -->
			<tr class="entetepanier" >
				<td width="220px">
					Produit
				</td>
				<td width="80px">
					Quantit&eacute;
				</td>
				<td width="100px">
					Prix total TTC
				</td>
			</tr>
			
			<!-- On définit un index pour la déterminer la classe de la ligne -->
			<c:set value="0" var="index"/>
			
			<c:forEach items="${panier.listeLigneCommande}" var="ligneCommande">
				
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
						<b:prix montant="${ligneCommande.quantite * ligneCommande.prixUnitaire}"/> &euro;
					</td>
				</tr>
				
				<!-- On incrémente l'index -->
				<c:set value="${index + 1 }" var="index" />
				
			</c:forEach>
			
			<!-- Total du panier -->
			<tr class="total_panier">
				<td colspan="2" align="right">
					Total TTC (TVA 19,6%) : &nbsp;&nbsp;
				</td>
				<td >
					<b:prix montant="${panier.total + (panier.totalPoids / 100)}"/> &euro;
				</td>
			</tr>
			
		</table>
		
	</c:if>
