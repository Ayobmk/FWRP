<%
if(session.getAttribute("name")==null){
	response.sendRedirect("login.jsp");
}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
<title>Items Management Platform</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
	crossorigin="anonymous">
</head>
<body>
	<header>
	<nav class="navbar navbar-expand-md navbar-dark" style="background-color: black">
		<div> <a class="navbar-brand" href="index.jsp">FOOD WASTE REDUCTION PLATFORM</a></div>
		<ul class="navbar-nav">
			<li class="nav-link"><a href="<%=request.getContextPath()%>/list">DASHBOARD</a></li>
			<li class="nav-link"><a href="<%=request.getContextPath()%>/list">ACCOUNT STATUS</a></li>
			<li class="nav-link"><a href="LogoutServlet">LOGOUT</a></li>
			<li class="nav-link"><a href="LogoutServlet"><%=session.getAttribute("name") %></a></li>
		</ul>
	</nav>
	</header>
	<br>
	<div class="row">
		<div class="container">
			<h3 class="text-center">List of Items</h3>
			<hr>
			<div class="container text-left">
				<a href="<%=request.getContextPath()%>/new" class="btn tbn-success" style="background-color: green">Add New Item</a>
			</div>
			<br>
            <table class="table table-bordered">
                <thead>
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
                        <th>Surplus</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listItems}">
                        <tr>
                            <td><c:out value="${item.id}"/></td>
					        <td>
					            <c:if test="${not empty item.base64Image}">
					                <img src="data:image/png;base64,${item.base64Image}" alt="Item Image" height="100"/>
					            </c:if>
					        </td>                            <td><c:out value="${item.itemName}"/></td>
                            <td><c:out value="${item.itemType}"/></td>
                            <td><c:out value="${item.itemDescription}"/></td>
                            <td><c:out value="${item.reason}"/></td>
                            <td><c:out value="${item.expDate}"/></td>
                            <td><c:out value="${item.price}"/></td>
			                <td>
				                <c:choose>
				                    <c:when test="${discountedPrices[item.id] ne -1}">
				                        <c:out value="${discountedPrices[item.id]}"/>
				                    </c:when>
				                    <c:otherwise>
				                        N/A
				                    </c:otherwise>
				                </c:choose>	
             		      	</td>                            
                            <td><c:out value="${item.surplus ? 'Yes' : 'No'}"/></td> <!-- Displaying Surplus status -->
                            <td>
                                <a href="edit?id=<c:out value='${item.id}'/>">Edit</a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="delete?id=<c:out value='${item.id}'/>">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
           
		</div>
		<div class="container">
			<h3 class="text-center">Current Orders</h3>
			<hr>
			<br>
            <table class="table table-bordered">
                <thead>
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
                        <th>User Mail</th>
                        <th>User Type</th>
                    </tr>
                </thead>
                <tbody>
		            <c:forEach var="order" items="${listItemsOrders}">
		                <tr>
		                    <td>${order.id}</td>
		                    <td>
		                        <c:if test="${order.image != null}">
		                            <img src="data:image/png;base64,${order.base64Image}" height="100" alt="Item Image"/>
		                        </c:if>
		                    </td>
		                    <td>${order.itemName}</td>
		                    <td>${order.itemType}</td>
		                    <td>${order.itemDescription}</td>
		                    <td>${order.reason}</td>
		                    <td>${order.expDate}</td>
		                    <td>${order.price}</td>
		                    <td>${order.discountedPrice}</td>
		                    <td>${order.userName}</td>
		                    <td>${order.userType}</td>
		                </tr>
		            </c:forEach>
                </tbody>
            </table>
            
           
		</div>
		
	</div>
</body>
</html>