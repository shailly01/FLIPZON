<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    
    
    	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

 	<a href="${contextPath}/"><input type="submit" value="Logout"></a><br/><br> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
        
        
        <a href="${contextPath}/user/buyer">Customer Home</a><br/>
        <br>
        
 <form:form action="${contextPath}/cart/checkout" method="post" commandName="cart">       
      	<table border="1" cellpadding="5" cellspacing="5">
		<tr>
			<td><b>Product Name</b></td>
			<td><b>Image</b></td>
			<td><b>Price</b></td>
                        <td><b>Quantity</b></td>
                        <td><b>Final Price</b></td>
                        <td><b>Option</b></td>
			
		</tr>  
	
            <c:forEach var="c" items="${carts}">  
                         <tr>
		<td>${c.title}</td>			
                <td><img height="150" width="150" src="${c.filename}" /></td>
                <td>${c.totalprice}</td>
                <td>${c.quantity}</td>
                <td>${c.finalPrice}</td>
                
                <td><a href="${contextPath}/cart/remove/${c.id}.htm" >Remove</a></td> 
			</tr>
               </c:forEach>
		
	</table>
	<input type="submit" value="Checkout Items" />
	</form:form>
</body>
</html>