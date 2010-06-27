<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Login</title> <!-- change to whatever you want as the title for your website -->
	<meta name="description" content=" " /> <!-- enter a description for your website inside the " " -->
	<meta name="keywords" content=" " /> <!-- enter a a string of keywords that relate to your website inside the " " -->
	<meta http-equiv="Content-Language" content="ru-ru" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="/myjava-webapp/css/style.css" />
</head>

<body>
<div id="header">
<%-- End of Ch 3 --%>
	<div class="username">
		<%-- Welcome, <strong><sec:authentication property="principal.username"/></strong> --%>
	</div>
<ul>
	<c:url value="/home.do" var="homeUrl"/>
	<li><a href="${homeUrl}">Home</a></li>
	<c:url value="/logout" var="logoutUrl"/>
	<li><a href="${logoutUrl}">Log Out</a></li>
	<c:url value="/account/home.do" var="accountUrl"/>
	<li><a href="${accountUrl}">My Account</a></li>
	<c:url value="/wishlist/home.do" var="wishlistUrl"/>
	<li><a href="${wishlistUrl}">My Wishlist</a></li>
	
</ul>
<br />
</div>

