<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">

<title>Login and Registration form example</title>
<link href="${pageContext.request.contextPath}/CSS/MonProfil.css" rel="stylesheet">
<title>Mon Profil</title>
</head>

<body>
	<h1><a href="${pageContext.request.contextPath}/ServletAccueil">Eni-Encheres</a></h1>
  <h2>Inscription</h2>
 <form class="form card" method="post"action="${pageContext.request.contextPath}/ServletInscription">
	<section>
	<div class="forme1">
	<label for="r1" id="fn">Pseudo:</label>
	<input type="text" name="pseudo" id="r1"  required="required">
	 
	<label  for="r2" id="ln">Prenom:</label>
	<input type="text" name="prenom" id="r2" required="required">
	 
	<label  for="r3" id="un">Telephone:</label>
	<input type="text" name="telephone" id="r3" required="required">
	 
	<label  for="r4" id="pwd">Code postal:</label>
	<input type="text" name="code_postal" id="r4" required="required">
	 
	<label  for="r5" id="em">Mot de passe:</label>
	<input type="password" name="mdp" id="r5" required="required">
	 </div>

	 <div class="forme2">
	<label for="r1" id="fn">Nom:</label>
	<input type="text" name="nom" id="r1" required="required">
	 
	<label  for="r2" id="ln">Email:</label>
	<input type="text" name="email" id="r2" required="required">
	 
	<label  for="r3" id="un">Rue:</label>
	<input type="text" name="rue" id="r3" required="required">
	 
	<label  for="r4" id="pwd">Ville:</label>
	<input type="text" name="ville" id="r4" required="required">
	 
	<label  for="r5" id="em">Confirmation:</label>
	<input type="password" name="mot_de_passe" id="r5" required="required">
	</div>
	
	 <div class="button">
		 <button type="submit" value="Submit" id="button">Créer</button>
	</div>
	</section>
	</form>
<form action="${pageContext.request.contextPath}/ServletAccueil" method="get">
	<button type="submit" value="Submit" id="button">Annuler</button>
</form>
<!-- <br>
<br> -->
<c:if test="${!empty listerreur}">
	<c:forEach var="E" items="${listerreur}">
		<p>${E}</p>
	</c:forEach>
</c:if>
 
</body>
</html>
