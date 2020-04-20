<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Category Form</title>
<link href="<c:url value="/resources/CSS/mvp.css" />" rel="stylesheet">
</head>
<body>


	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

        <a href="${contextPath}/user/" style = "margin-left: 30px;"><button type ="button">Seller Home</button></a><br/>
 <div style="margin-top: 50px; margin-left: 50px;">
	<h2>Add a New Category</h2>


	<form:form action="${contextPath}/category/add" method="post" commandName="category">

		<table>
			<tr>
				<td>Category Title:</td>
				<td><form:input path="title" size="30" required="required" /> <font color="red"><form:errors
							path="title" /></font></td>
			</tr>

			<tr>
                            <td colspan="2"><button type = "submit" class="btn btn-info" style = "width: 200px;"value="Create Category">Create Category</button></td>
			</tr>
		</table>

	</form:form>
 </div>
</body>
</html>