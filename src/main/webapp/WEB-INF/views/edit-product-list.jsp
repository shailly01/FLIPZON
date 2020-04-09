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
			
		</tr>
                               
                
                
		<c:forEach var="adv" items="${adverts}">
                    <form action="${contextPath}/advert/update/${adv.id}.htm" method="POST">
			<tr>
                            
                            <td><input type="text" name="title" value="${adv.title}"/></td>
                            <td><input type="text" name="description" value="${adv.message}"/></td>
                            <td>${adv.user.username}</td>
                            <td><c:forEach var="categ" items="${adv.categories}">
                    	    ${categ}
                            </c:forEach></td>
                            <td><img height="150" width="150" src="${adv.filename}" /></td>
                            <td><input type="text" name="price" value= "${adv.price}"/></td>
                            <td><button value="edit" type="submit">Edit</button></td>
                            </tr>
                        </form>

		</c:forEach>
	</table>
	
</body>
</html>