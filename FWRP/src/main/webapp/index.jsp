<%
if(session.getAttribute("name")==null){
	response.sendRedirect("login.jsp");
}
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Food Waste Reduction Platform</title>

<!-- Core theme CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
	crossorigin="anonymous"></head>


<body id="page-top">
	<!-- Navigation-->
	<nav class="navbar navbar-expand-md navbar-dark" style="background-color: black">
		<div> <a class="navbar-brand" href="#page-top">FOOD WASTE REDUCTION PLATFORM</a></div>
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
	
	<!-- Title-->
	<header class="containerHeader">
		<div class="containerTitlePage">
			<h1 class="title1">Welcome To Our Food Waste Reduction Platform </h1>
		</div>
	</header>
	
	<!-- Product Section-->
	<section class="page-section-products" id="products">
		<div class="containerTitleProduct">
			<h2 class="title2">Start Shopping now</h2>
		</div>
	</section>
	
			<!--<div> <a class="navbar-brand" href="customer.jsp">CUSTOMER</a></div>-->
	
	<ul class="navbar-nav">
        <%String SHOP = (String) session.getAttribute("userType");
        if("Retailer".equals(SHOP)) {
        %>
            <li class="Shop-link"><a href="<%=request.getContextPath()%>/customer">CUSTOMER</a></li>
            <li class="Shop-link"><a href="<%=request.getContextPath()%>/charity">CHARITY</a></li>
        <% } else if("Customer".equals(SHOP)) { %>
            <li class="Shop-link"><a href="<%=request.getContextPath()%>/customer">CUSTOMER</a></li>
        <% } else if("Charity".equals(SHOP)) { %>
            <li class="nav-link"><a href="<%=request.getContextPath()%>/charity">CHARITY STATUS</a></li>
        <% } %>	                
     </ul>
	<br>
	<br>
	<div class="containerTitleProduct">
		<h2 class="title2">Subscribe now</h2>
	</div>
	<br>

	<div>
		<form action="SubscribeServlet" method="POST">
		    <input type="hidden" name="userEmail" value="${sessionScope.name}"/> 
		    <select name="foodPref">
		        <option value="Drinks">Drinks</option>
		        <option value="Vegetables">Vegetables</option>
		        <option value="Fruits">Fruits</option>
		    </select>
		    <input type="hidden" name="userProvince" value="${sessionScope.userProvince}"/> 
		    <button type="submit">Subscribe</button>
		</form>
	</div>

</body>
</html>
<% if(session.getAttribute("subscriptionMessage") != null) { %>
    <script>alert('<%= session.getAttribute("subscriptionMessage") %>');</script>
    <% session.removeAttribute("subscriptionMessage"); %>
<% } %>