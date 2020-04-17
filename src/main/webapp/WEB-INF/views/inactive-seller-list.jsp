<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of Users</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

 	<a href="${contextPath}/"><input type="submit" value="Logout"></a><br/><br> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<a href="${contextPath}/admin/">Admin Home</a><br/>
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
                        <td><b>Option</b></td>
			
		</tr>
                               
                
                
		<c:forEach var="user" items="${users}">
                    <form action="${contextPath}/admin/approve/${user.personID}.htm" method="POST">
			<tr>
                            
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.username}</td>
                            <td>${user.email.emailAddress}</td>
                            <td>${user.usertype}</td>
                            <td>${user.active}</td>
                            <td><button value="remove" type="submit">Approve</button></td> 
                            <td><a href="${contextPath}/admin/reject/${user.personID}.htm"><button type="button">Reject</button></a></td> 
			</tr>
                        </form>

		</c:forEach>
	</table>
	
</body>
</html>

