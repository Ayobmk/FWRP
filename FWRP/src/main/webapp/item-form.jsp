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
		<nav class="navbar navbar-expand-md navbar-dark"
		style="background-color: Black">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand"> Item Management Platform</a>
			</div>
			
			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list" class="nav-link"> Items</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${items != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${items == null}">
					<form action="insert" method="post">
				</c:if>
				
				<caption>
					<h2>
						<c:if test="${items != null}">
						Edit Item
						</c:if>
						<c:if test="${items == null}">
						Add Item
						</c:if>
					</h2>
				</caption>
				
                    <c:if test="${item != null}">
                        <input type="hidden" name="id" value="${item.id}" />
                    </c:if>
                    
                    <fieldset class="form-group">
                        <label>Item Name</label>
                        <input type="text" value="${item != null ? item.itemName : ''}" class="form-control" name="itemName" required="required">
                    </fieldset>
                    
                    <fieldset class="form-group">
                        <label>Item Type</label>
                        <input type="text" value="${item != null ? item.itemType : ''}" class="form-control" name="itemType" required="required">
                    </fieldset>
                    
                    <fieldset class="form-group">
                        <label>Item Description</label>
                        <input type="text" value="${item != null ? item.itemDescription : ''}" class="form-control" name="itemDescription">
                    </fieldset>
                    
                    <fieldset class="form-group">
                        <label>Expiration Date</label>
                        <input type="text" value="${item != null ? item.expDate : ''}" class="form-control" name="expDate" required="required">
                    </fieldset>
                    
                    <fieldset class="form-group">
                        <label>Price</label>
                        <input type="text" value="${item != null ? item.price : ''}" class="form-control" name="price" required="required">
                    </fieldset>
				
				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>