<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Home</title>
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
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<form:form action="${contextPath}/product/logout" method="post" commandName="product">
    <div style="margin-top: 50px; margin-left: 1200px;">
    <button type="submit" class="btn btn-info" value="Logout">Logout</button>
    </div>
<br/><br/>
</form:form>
 <div style="margin-top: 50px; margin-left: 50px;">
	 <h1>Welcome Seller, ${user.firstName} </h1>
	 <br/><br/>

            <div class="row justify-content-md-center container ">
				

					<div class="col-sm-12">
                                            
						<a href="${contextPath}/category/add" ><button type = "button" class="btn btn-info" style = "width: 250px;">Add a Product Category</button></a> <br />
					</div>
                                        
                                        <br/><br/><br/><br/>
                                        
                                        
                                        
                                        <div class="col-sm-12">
                                            
						<a href="${contextPath}/product/add" ><button type = "button" class="btn btn-info btn-block" style = "width: 250px;">Add the Product Description</button></a> <br />
                                        </div>
                                        <br/><br/><br/><br/>

                                        
                                         <div class="col-sm-12">
                                            
						<a href="${contextPath}/product/sellerlist" ><button type = "button" class="btn btn-info btn-block" style = "width: 250px;">View All Products</button></a> <br />
                                        </div>
                                        
                                        
                                        
				</div>

 </div>


</body>
</html>