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
                <form action="${contextPath}/cart/update/${c.id}.htm" method="POST"> 
                         <tr>
		<td>${c.title}</td>			
                <td><img height="150" width="150" src="${pageContext.request.contextPath}/${c.filename}"/></td>
                <td>${c.totalprice}</td>
        <%--       <td>${c.quantity}</td> --%>
              <td><input type="number" name="quantity" value= "${c.quantity}"/></td>
                <td>${c.totalprice*c.quantity}</td>
                <td><button value="edit" type="submit">Edit</button></td>
			</tr>
                        	</form>

               </c:forEach>
		
        </table>
</body>
</html>