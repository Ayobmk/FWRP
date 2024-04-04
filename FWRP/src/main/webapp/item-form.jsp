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
				<a href="index.jsp" class="navbar-brand"> Item Management Platform</a>
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
				<c:if test="${item != null}">
					<form action="update" method="post" enctype="multipart/form-data">
				</c:if>
				<c:if test="${item == null}">
					<form action="insert" method="post" enctype="multipart/form-data">
				</c:if>
				
				<caption>
					<h2>
						<c:if test="${item != null}"> Edit Item </c:if>
						<c:if test="${item == null}"> Add Item </c:if>
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
					    <label for="image">Image</label>
					    <input type="file" id="image" name="image" class="form-control" accept="image/*">
					</fieldset>
                    
                    <!-- <fieldset class="form-group">
                        <label>Item Type</label>
                        <input type="text" value="${item != null ? item.itemType : ''}" class="form-control" name="itemType" required="required">
                    </fieldset> -->
                    
					<fieldset class="form-group">
                    <label for="itemType"><i class="zmdi zmdi-nature-people"></i></label>Item Type<br>
						<select name="itemType" id="type"  >
							<option value="" disabled selected>Select your option</option>
							<option value="Vegetables" ${item != null && item.itemType == 'Vegetables' ? 'selected' : ''}>Vegetables</option>
						    <option value="Fruits" ${item != null && item.itemType == 'Fruits' ? 'selected' : ''}>Fruits</option>
						    <option value="Drinks" ${item != null && item.itemType == 'Drinks' ? 'selected' : ''}>Drinks</option>
						</select><br>
                    </fieldset> 
                    
                    <fieldset class="form-group">
                    <label for="reason"><i class="zmdi zmdi-nature-people"></i></label>Reason<br>
						<select name="reason" id="type"  >
							<option value="" disabled selected>Select your reason</option>
							<option value="Sell" ${item != null && item.reason == 'Sell' ? 'selected' : ''}>Sell</option>
						    <option value="Donation" ${item != null && item.reason == 'Donation' ? 'selected' : ''}>Donation</option>
						</select><br>
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