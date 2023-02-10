<%@page import="fr.eni.eniD2WM147.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<link href="${pageContext.request.contextPath}/CSS/AfficherUnProfile.css" rel="stylesheet">

<title>Afficher un Profil</title>

</head>
<body>
	<article class="navbar">
		<div class="div1">
			
				<h1><a href="${pageContext.request.contextPath}/ServletAccueil">ENI-Enchères</a></h1>
			

			<div class="sdiv3">
				<div class ="insc">
				<form method="get" action="${pageContext.request.contextPath}/ServletInscription">
					<input type="submit" value="s'inscrire">
				</form>
				</div>
				<c:choose>
					<c:when test="${empty UtilisateurCo}">
						<form method="get"
							action="${pageContext.request.contextPath}/ServletConnection">
							<input type="submit" value="Connection">
						</form>
					</c:when>
					<c:otherwise>
						<form method="post"
							action="${pageContext.request.contextPath}/ServletDeconnection">
							<input type="submit" value="Deconnection">
						</form>
						<form method="get"
							action="${pageContext.request.contextPath}/ServletAfficherUnProfil">
							<input type="submit" value="Profil">
						</form>
						<form method="get"
							action="${pageContext.request.contextPath}/ServletNouvelleVente">
							<input type="submit" value="Vendre">
						</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div> 
		
		</article>
	
	
	<h1>Fiche Client</h1>

	<c:if test="${!empty client}">
	
		<ul>
			<li>
				Pseudo : ${requestScope.client.pseudo}
			</li>
			<br>
			<li>
				Nom : ${requestScope.client.nom}
			</li>
			<br>
			<li>
				Prenom : ${requestScope.client.prenom} 
			</li>
			<br>
			<li>
				Email : ${requestScope.client.email}
			</li>
			<br>
			<li>
				Telephone : ${requestScope.client.telephone} 
			</li>
			<br>
			<li>
				Rue : ${requestScope.client.rue}
			</li>
			<br>
			<li>
				Code Postal : ${requestScope.client.codePostal}
			</li>
			<br>
			<li>
				Ville : ${requestScope.client.ville}
			</li>
			<br>
		</ul>
	</c:if>


<form method="get" action="${pageContext.request.contextPath}/ServletModifUtilisateur">

			<div class="button">
			<button type="submit" value="Submit" id="button">Modifier</button>
			</div>
			
</form>

</body>
</html>