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
<!--Allert Message-->
 <% if(request.getParameter("message") != null) { %>
    <script>alert('<%= request.getParameter("message") %>');</script>
<% } %>
<% if(request.getParameter("error") != null) { %>
    <script>alert('<%= request.getParameter("error") %>');</script>
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
			<h2 class="title2">Items Expiring Soon</h2>
		</div>
		

		
		<h2 class="title2">Surplus Items</h2>
		<table border="1">
		    <tr>
		        <th>ID</th>
		        <th>Image</th>
		        <th>Item Name</th>
		        <th>Item Type</th>
		        <th>Item Description</th>
		        <th>Reason</th>
		        <th>Expiration Date</th>
		        <th>Price</th>
		        <th>Discounted Price</th>
		    </tr>
		    <c:forEach var="order" items="${userOrders}">
		        <tr>
		            <td>${order.id}</td>
		            <td>
			            <c:if test="${order.image != null}">
			                <img src="data:image/png;base64,${Base64.getEncoder().encodeToString(order.image)}" height="100" alt="Order Image"/>
			            </c:if>
			            <c:if test="${order.image == null}">
			                <img src="" alt="" height="100"/>
			            </c:if>
				    </td>
		            <td>${order.itemName}</td>
		            <td>${order.itemType}</td>
		            <td>${order.itemDescription}</td>
		            <td>${order.reason}</td>
		            <td>${order.expDate}</td>
		            <td>${order.price}</td>
		            <td><c:out value="${discountedPrices[item.id]}" default="N/A"/></td>
		        </tr>
		    </c:forEach>
		</table>
		
		


		
	</section>

</body>

</html>