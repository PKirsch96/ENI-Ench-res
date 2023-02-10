<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="${pageContext.request.contextPath}/CSS/PageDeCo.css" rel="stylesheet">
<title>Connection</title>
</head>
<body>

<h1><a href="${pageContext.request.contextPath}/ServletAccueil">Eni-Enchères</a></h1>
<h2>Connection</h2>
<form class="form card" method="post" action="${pageContext.request.contextPath}/ServletConnection">
  <div class="card_header">
    <svg height="24" width="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
      <path d="M0 0h24v24H0z" fill="none"></path>
      <path d="M4 15h2v5h12V4H6v5H4V3a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v18a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1v-6zm6-4V8l5 4-5 4v-3H2v-2h8z" fill="currentColor"></path>
    </svg>
    <h1 class="form_heading">Connection</h1>
  </div>
  <div class="field">
    <label for="username">Username</label>

    <input id="username" placeholder="Pseudo ou email" type="text" name="id" class="input" value="tom">

  </div>
  <div class="field">
    <label for="password">Password</label>
    <input id="password" placeholder="mot de passe" type="password" name="mdp" class="input" value="Pa$$w0rd">
  </div>
  <div class="field">
    <button class="button">Login</button>
  </div>
</form>
<br>
<br>
<c:if test="${!empty listerreur}">
	<c:forEach var="E" items="${listerreur}">
		<p>${E}</p>
	</c:forEach>
</c:if>
</body>
</html>