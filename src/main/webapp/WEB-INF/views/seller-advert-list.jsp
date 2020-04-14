<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Adverts</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

 	<a href="${contextPath}/"><input type="submit" value="Logout"></a><br/><br> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<a href="${contextPath}/user/">Seller Home</a><br/>
	<br>

	
	<table border="1" cellpadding="2" cellspacing="2">
		<tr>
			<td><b>Product Name</b></td>
			<td><b>Product Description</b></td>
			<td><b>Seller Name</b></td>
			<td><b>Category</b></td>
			<td><b>Image</b></td>
			<td><b>Price</b></td>
                        <td><b>Option</b></td>
                        <td><b>Option</b></td>
			
		</tr>
                               
                
                
		<c:forEach var="adv" items="${adverts}">
                    <form action="${contextPath}/advert/edit" method="get">
			<tr>
                            
                            <td>${adv.title}</td>
                            <td>${adv.message}</td>
                            <td>${adv.user.username}</td>
                            <td><c:forEach var="categ" items="${adv.categories}">
                    	    ${categ}
                            </c:forEach></td>
<!--                            <td><img height="150" width="150" src="${adv.filename}" /></td>-->
                            
                            <c:choose>
				<c:when test="${adv.filename == null}">
				</c:when>
				<c:otherwise>
				<td><img height="150" width="150"
                                         src="${pageContext.request.contextPath}/${adv.filename}" /></td>
					
				</c:otherwise>
			</c:choose>
                            
                            
                            
                            
                            
                            
                            
                            <td>${adv.price}</td>
                            <td><button value="edit" type="submit">Edit</button></td>
                            <td><a href="${contextPath}/advert/remove/${adv.id}.htm" ><button type="button">Remove</button></a></td>                        
			</tr>
                        </form>

		</c:forEach>
	</table>
	
</body>
</html>