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
<link
			href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
			rel="stylesheet"
			integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
			crossorigin="anonymous"
		/>

		<script
			src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
			integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
			crossorigin="anonymous"
		></script>
</head>
<body>
    <div style="margin-top: 50px; margin-left: 50px;">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
        
        <h2>Product Added to Cart Successfully</h2><br/><br/>
	<a href="../../product/list"><button type = "button" class="btn btn-info" style = "width: 150px;">Back</button></a><br/><br/><br/><br/>
        <a href="${contextPath}/cart/customerlist" ><button type = "button" class="btn btn-info" style = "width: 150px;">View Cart</button></a> <br/>
    </div>
</body>
</html>