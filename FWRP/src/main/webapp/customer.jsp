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
		            <th>Price</th>
		            <th>Discounted Price</th>
		            <th>Action</th>
		        </tr>
		        <c:forEach var="item" items="${listItemsExpiringSoon}">
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
		                <td>${item.price}</td>
		                <td><c:out value="${discountedPrices[item.id]}" default="N/A"/></td>
                        <td>
                            <form action="BuyItemServlet" method="post">
                                <input type="hidden" name="itemId" value="${item.id}" />
                                <input type="submit" value="Buy" class="btn btn-primary"/>
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
		        <th>Action</th>
		    </tr>
		    <c:forEach var="item" items="${surplusItems}">
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
		            <td>${item.price}</td>
		            <td><c:out value="${discountedPrices[item.id]}" default="N/A"/></td>
                    <td>
                        <form action="BuyItemServlet" method="post">
                            <input type="hidden" name="itemId" value="${item.id}" />
                            <input type="submit" value="Buy" class="btn btn-primary"/>
                        </form>
                    </td>
		        </tr>
		    </c:forEach>
		</table>
		
		
		<h2 class="title2">Our Items</h2>
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
		                <th>Price</th>
		                <th>Discounted Price</th>
		                <th>Action</th>
		            </tr>
		            <c:forEach var="item" items="${otherSellItems}">
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
				        <td>${item.price}</td>
				        <td>
				            <c:choose>
				                <c:when test="${discountedPrices[item.id] != -1}">
				                    <c:out value="${discountedPrices[item.id]}" />
				                </c:when>
				                <c:otherwise>
				                    N/A
				                </c:otherwise>
				            </c:choose>
				        </td>
                        <td>
                            <form action="BuyItemServlet" method="post">
                                <input type="hidden" name="itemId" value="${item.id}" />
                                <input type="submit" value="Buy" class="btn btn-primary"/>
                            </form>
                        </td>
   	               </tr>
		            </c:forEach>
		        </table>
		    </div>
		</div>

		
	</section>

</body>

</html>