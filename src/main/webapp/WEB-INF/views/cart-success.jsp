<%-- 
    Document   : cart-success
    Created on : Apr 3, 2020, 8:51:49 PM
    Author     : Shailly
--%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product Added to Cart Successfully</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
        
        <h2>Product Added to Cart Successfully</h2>
	<a href="../../product/list">Back</a><br/>
        <a href="${contextPath}/cart/customerlist" >View Cart</a> <br/>

</body>
</html>