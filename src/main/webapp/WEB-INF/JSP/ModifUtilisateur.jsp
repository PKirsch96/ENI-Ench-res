<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<link rel="preconnect" href="https://fonts.googleapis.com">
<!-- <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin> -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@200;400&family=Roboto&family=Syncopate:wght@700&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<!-- <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin> -->
<link href="https://fonts.googleapis.com/css2?family=Roboto&family=Syncopate&display=swap" rel="stylesheet">
<link href="${pageContext.request.contextPath}/CSS/MonProfil.css" rel="stylesheet">
<title>Modif_Utilisateur</title>
</head>

<body>
	<h1><a href="${pageContext.request.contextPath}/ServletAccueil">Eni-Enchères</a></h1>
  <h1>Modification de Compte</h1>
 <form class="form card" method="post"action="${pageContext.request.contextPath}/ServletModifUtilisateur">
<section>
<div class="forme1">
<label for="r1" id="fn">Pseudo:</label>
<input type="text" name="pseudo" id="r1" value="${pseudo}">
 
<label  for="r2" id="ln">Prenom:</label>
<input type="text" name="prenom" id="r2" value="${prenom}">
 
<label  for="r3" id="un">Telephone:</label>
<input type="text" name="telephone" id="r3" value="${telephone}">

<label  for="r4" id="pwd">Code postal:</label>
<input type="text" name="code_postal" id="r4" value="${code_postal}">
 
<label  for="r5" id="em">Mot de passe actuel:</label>
<input type="password" name="mdp" id="r5" >
 
<label  for="r6" id="em">Nouveau mot de passe</label>
<input type="password" name="new_mdp" id="r5">
 </div>
 
 <div class="forme2">
<label for="r1" id="fn">Nom:</label>
<input type="text" name="nom" id="r1" value="${nom}">
 
<label  for="r2" id="ln">Email:</label>
<input type="text" name="email" id="r2" value="${email}">
 
<label  for="r3" id="un">Rue:</label>
<input type="text" name="rue" id="r3" value="${rue}">
 
<label  for="r4" id="pwd">Ville:</label>
<input type="text" name="ville" id="r4" value="${ville}">
 
<label  for="r5" id="em">Confirmation:</label>
<input type="password" name="conf_mdp" id="r5">
</div>
 <div class="button">
	 <button type="submit" value="Submit" id="button">Enregistrer</button>
</div>
</section>
</form>
 <form action = "${pageContext.request.contextPath}/ServletDeleteUtilisateur" method="post">
	 <button type="submit" value="Submit" id="button">Supprimer mon compte</button>	 
</form>

<c:if test="${!empty listerreur}">
	<c:forEach var="E" items="${listerreur}">
		<p>${E}</p>
	</c:forEach>
</c:if>
 
</body>
</html>
