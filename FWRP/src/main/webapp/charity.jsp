<%
if(session.getAttribute("name")==null){
	response.sendRedirect("login.jsp");
}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.Base64" %> <!-- i added 2 lines for convert the byte array into a Base64 string -->
<%@ page import="project.item.management.model.Items" %>    

<!-- Add the alert check here -->
<% if("true".equals(request.getParameter("claimSuccess"))) { %>
    <script>alert('You have successfully claimed the item!');</script>
<% } %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
	crossorigin="anonymous">
	
<title>Customer Dash board</title>
</head>
<body id="page-top">
	<!-- Navigation-->
	
		<nav class="navbar navbar-expand-md navbar-dark" style="background-color: black">
		<div> <a class="navbar-brand" href="index.jsp">FOOD WASTE REDUCTION PLATFORM</a></div>
		<ul class="navbar-nav">
			<!-- check the user type -->
        <%String userType = (String) session.getAttribute("userType");
        if("Retailer".equals(userType)) {
        %>
            <li class="nav-link"><a href="<%=request.getContextPath()%>/list">DASHBOARD</a></li>
        <% } else if("Customer".equals(userType)) { %>
            <li class="nav-link"><a href="<%=request.getContextPath()%>/customerStatus">CUSTOMER STATUS</a></li>
        <% } else if("Charity".equals(userType)) { %>
            <li class="nav-link"><a href="<%=request.getContextPath()%>/customerStatus">CHARITY STATUS</a></li>
        <% } %>
			<li class="nav-link"><a href="LogoutServlet">LOGOUT</a></li>
			<li class="nav-link"><a href="LogoutServlet"><%=session.getAttribute("name") %></a></li>
		</ul>
	</nav>
	
	<section class="page-section-products" id="products">
		<div class="containerTitleProduct">
			<h2 class="title2">Items To Claim</h2>
		</div>
		
		<!--<div class="ContainerAllItems">
			<div class="containerItem">
				<img alt="this is image" src="">
				<p class="itemDesc">Product Name</p>
				<p class="itemDesc">Product Type</p>
				<p class="itemDesc">Product Description</p>
				<p class="itemDesc">Product Price</p>
				<Input type="button" value="Order Now">
			</div>
		</div>-->
		<div class="ContainerAllItems">
			<div class="containerItem">
		    <table border="1">
		        <tr>
		            <th>ID</th>
		            <th>Image</th>
		            <th>Item Name</th>
		            <th>Item Type</th>
		            <th>Item Description</th>
		            <th>Reason</th>
		            <th>Expiration Date</th>
		            <th>Action</th>
		        </tr>
		        <c:forEach var="item" items="${donationItems}">
		            <tr>
		                <td>${item.id}</td>
                        <td>
				            <c:if test="${item.image != null}">
								<img src="data:image/png;base64,${Base64.getEncoder().encodeToString(item.image)}" height="100" alt="Item Image"/>
				            </c:if>
				        </td>		                
		                <td>${item.itemName}</td>
		                <td>${item.itemType}</td>
		                <td>${item.itemDescription}</td>
		                <td>${item.reason}</td>
		                <td>${item.expDate}</td>
				        <td>
				            <form action="ClaimItemServlet" method="post">
				                <input type="hidden" name="itemId" value="${item.id}" />
				                <input type="submit" value="Claim" />
				            </form>
				        </td>
		                
		            </tr>
		        </c:forEach>
		    </table>
		    <c:if test="${not empty error}">
		        <p>Error: ${error}</p>
		    </c:if>
 			</div>
		</div>
		

		
	</section>

</body>
</html>