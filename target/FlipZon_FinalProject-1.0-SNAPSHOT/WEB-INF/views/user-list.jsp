<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of Users</title>
<link href="<c:url value="/resources/CSS/mvp.css" />" rel="stylesheet">
</head>
<body>
   
    <div style="margin-top: 10px; margin-left: 1200px;">	
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
 <a href="${contextPath}/"><button type="submit" class="btn btn-info" value="Logout">Logout</button></a><br/><br> 
</div>
  
<div style="margin-top: 10px; margin-left: 50px;">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<a href="${contextPath}/admin/"><button type = "button" style = "width:200px;">Admin Home</button></a><br/>
	<br>

	
	<table border="1" cellpadding="2" cellspacing="2">
		<tr>
			<td><b>First Name</b></td>
			<td><b>Last Name</b></td>
			<td><b>UserName</b></td>
			<td><b>Email</b></td>
			<td><b>Type</b></td>
			<td><b>Status</b></td>
                        <td><b>Option</b></td>
			
		</tr>
                               
                
                
		<c:forEach var="user" items="${users}">
                    <form action="${contextPath}/admin/remove/${user.personID}.htm" method="GET">
			<tr>
                            
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.username}</td>
                            <td>${user.email.emailAddress}</td>
                            <td>${user.usertype}</td>
                            <td>${user.active}</td>
                            <td><button value="remove" type="submit">Remove</button></td>                                                   
			</tr>
                        </form>

		</c:forEach>
	</table>
</div>	
</body>
</html>