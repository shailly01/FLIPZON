<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Home</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<form:form action="${contextPath}/product/logout" method="post" commandName="product">
<input type="submit" value="Logout">
</form:form>

    <h1>Welcome Admin </h1>

<a href="${contextPath}/admin/userlist" >View all Users</a> <br />
<a href="${contextPath}/admin/inactivelist" >View Pending Sellers</a> <br />
<a href="${contextPath}/admin/rejectlist" >View Reject Sellers</a> <br />
<a href="${contextPath}/admin/productlist" >View All Products</a> <br />


</body>
</html>