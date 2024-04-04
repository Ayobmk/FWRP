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
                        <th>Item Name</th>
                        <th>Item Type</th>
                        <th>Item Description</th>
                        <th>Reason</th>
                        <th>Expiration Date</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listItems}">
                        <tr>
                            <td><c:out value="${item.id}"/></td>
                            <td><c:out value="${item.itemName}"/></td>
                            <td><c:out value="${item.itemType}"/></td>
                            <td><c:out value="${item.itemDescription}"/></td>
                            <td><c:out value="${item.reason}"/></td>
                            <td><c:out value="${item.expDate}"/></td>
                            <td><c:out value="${item.price}"/></td>
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
	</div>
</body>
</html>