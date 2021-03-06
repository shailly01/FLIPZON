<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Products</title>
<link href="<c:url value="/resources/CSS/mvp.css" />" rel="stylesheet">
</head>
<body>
    <div style="margin-top: 10px; margin-left: 1200px;">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

 	<a href="${contextPath}/"><button type="submit" class="btn btn-info" value="Logout">Logout</button></a><br/><br> 
        </div>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

 <div style="margin-top: 10px; margin-left: 50px;">
     <a href="${contextPath}/admin/"><button type = "button" class="btn btn-info" style = "width: 200px;">Admin Home</button></a><br/>
	<br>

	
	<table border="1" cellpadding="2" cellspacing="2">
		<tr>
			<td><b>Product Name</b></td>
			<td><b>Product Description</b></td>
			<td><b>Seller Name</b></td>
			<td><b>Category</b></td>
			<td><b>Image</b></td>
			<td><b>Price</b></td>
			
		</tr>
                               
                
                
		<c:forEach var="adv" items="${products}">
                   
			<tr>
                            
                            <td>${adv.title}</td>
                            <td>${adv.message}</td>
                            <td>${adv.user.username}</td>
                            <td><c:forEach var="categ" items="${adv.categories}">
                    	    ${categ}
                            </c:forEach></td>
                            <td><img height="150" width="150" src="${pageContext.request.contextPath}/${adv.filename}"/></td>
                            <td>${adv.price}</td>
                            </tr>
                        </c:forEach>
	</table>
 </div>	
</body>
</html>