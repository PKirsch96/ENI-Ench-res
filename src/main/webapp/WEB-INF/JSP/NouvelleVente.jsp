<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Krona+One&family=Poppins&family=Roboto&family=Syncopate:wght@700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<link href="${pageContext.request.contextPath}/CSS/NewVente.css"rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<nav class="navbar">
		<div class="eni">
			<h1><a href="${pageContext.request.contextPath}/ServletAccueil">ENI-Enchères</a></h1>
		</div>
		<div class="sdiv btn-group btn-group-toggle3">
			<form class="inscription btn btn-secondary" method="get"
				action="${pageContext.request.contextPath}/ServletInscription">
				<input type="submit" value="s'inscrire" >
			</form>
			
			<c:choose>
				<c:when test="${empty UtilisateurCo}">
					<form class="connection btn btn-secondary" method="get"
						action="${pageContext.request.contextPath}/ServletConnection">
						<input type="submit" value="Connection">
					</form>
					
				</c:when>
					<c:otherwise>
					
					<form class ="deconnection btn btn-secondary" method="post"
						action="${pageContext.request.contextPath}/ServletDeconnection">
						<input type="submit" value="Deconnection">
					</form>
					
					<form class="profil btn btn-secondary" method="get"
						action="${pageContext.request.contextPath}/ServletAfficherUnProfil">
						<input type="submit" value="Profil">
					</form>
					
					<form class="vendre btn btn-secondary" method="get"
						action="${pageContext.request.contextPath}/ServletNouvelleVente">
						<input type="submit" value="Vendre">
					</form>
				</c:otherwise>	
				
			</c:choose>
		</div>
	</nav> 
	
	
	<img alt="" src="">
	
	<div class="content">
	<h2 class="titre">Nouvelle vente</h2><br>
	<form action="" method="post" class="form">
		 <div class="form1">
		   <label for="name">Article : </label>
		   <input type="text" name="nomArticle" id="nomArticle" required>
		 </div><br>
		  
		 <label for="description">Description :</label>
			<textarea  name="description" id="description"
          rows="3" cols="28"required>coucou</textarea><br>
		
		<label for="cate">Catégorie:</label><br>
		<select name="cate" id="cate">
					<option value="0">Catégories</option>
					<c:forEach var="LIB" items="${libelles}">
						<option value="${LIB.no_categorie}">${LIB.libelle}</option>
					</c:forEach>
		</select><br><br>
		
		<label for="photo">Photo de l'article</label>
		
		<input 
		  type="file"
		  id="photo" 
		  name="photo"
		  accept="image/*"><br><br>
		  
		  <div class="form2">
		    <label for="prix">Mise à prix : </label>
		    <input type="number" name="miseAPrix" id="miseAPrix" required>
		  </div><br><br>
		  
		  <label>
		    Début de l'enchère :
		    <input type="datetime-local" name="dateDebutEncheres" id="dateDebutEncheres">
		  </label><br><br>
		  
		   <label>
		    Fin de l'enchère :
		    <input type="datetime-local" name="dateFinEncheres" id="dateFinEncheres">
		  </label><br><br>
		  
		  
		  <div class="retrait">
		  <h2 class="blaze">Retrait</h2><br>
		  <label>
		    Rue :
		    <input type="text" name="rue" id="rue">
		  </label><br><br>
		   <label>
		    Code postal :
		    <input type="number" name="codePostal" id="codePostal">
		  </label><br><br>
		   <label>
		    Ville :
		    <input type="text" name="ville" id="ville">
		  </label><br><br>
		  </div>
		  
		  
		  <input action="${pageContext.request.contextPath}/ServletDeconnection" type="submit" value="Enregistrer" id="button">
		  
		 
	</form>
	
	<form action="${pageContext.request.contextPath}/ServletAccueil">
	<input type="submit" value="Annuler">
	</form>
	<c:if test="${!empty listerreur}">
	<c:forEach var="E" items="${listerreur}">
		<p>${E}</p>
	</c:forEach>
	</c:if>
	</div>
</body>
</html>