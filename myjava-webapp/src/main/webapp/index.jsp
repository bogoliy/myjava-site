<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<html>
  <head>
    <title>Spring Security + ExtJS Login</title>
    
    <!-- Ext JS files -->
	<link rel="stylesheet" type="text/css" 
		href="/myjava-webapp/scripts/ext-3.0.0/resources/css/ext-all.css" />
	
	<script 
		src="/myjava-webapp/scripts/ext-3.0.0/adapter/ext/ext-base.js">
	</script>
	
	<script 
		src="/myjava-webapp/scripts/ext-3.0.0/ext-all.js">
	</script>
	
	<!-- login form -->
	<script 
		src="/myjava-webapp/scripts/login.js">
	</script>
	
  </head>

  <body>
  <br><br><br><br><br>
    <center>
    	<div id="login"></div>
    </center>
  </body>
</html>
