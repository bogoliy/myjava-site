<%@ page
	import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page
	import="org.springframework.security.ui.AbstractProcessingFilter"%>
<%@ page import="org.springframework.security.AuthenticationException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<title>Login</title> <!-- change to whatever you want as the title for your website -->
<meta name="description" content=" " /> <!-- enter a description for your website inside the " " -->
<meta name="keywords" content=" " /> <!-- enter a a string of keywords that relate to your website inside the " " -->
<meta http-equiv="Content-Language" content="ru-ru" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<div id="login">
<form action="j_spring_security_check"><label for="j_username">Username</label>
<input type="text" name="j_username" id="j_username"
	<c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>'</c:if> />
<br />
<label for="j_password">Password</label> <input type="password"
	name="j_password" id="j_password" /> <br />
<input type='checkbox' name='_spring_security_remember_me' /> Remember
me <br />
<input type="submit" value="Login" /></form>
</div>