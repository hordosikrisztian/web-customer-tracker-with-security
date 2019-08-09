<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html>
	<head>
		<title>List Customers</title>
		
		<!-- EL part gives us the proper app name. -->
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
	</head>
	
	<body>
		<div id="wrapper">
			<div id="header">
				<h2>CRM - Customer Relationship Manager</h2>
			</div>
		</div>
		
		<div id="container">
			<div id="content">
				<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
					<!-- Put new button: Add customer. -->
					<input type="button" value="Add Customer" 
						   onclick="window.location.href = 'showFormForAdd'; return false;"
						   class="add-button"/>
				</security:authorize>
					   
				<form:form action="search" method="GET">
					Search customer: <input type="text" name="searchName"/>
					
					<input type="submit" value="Search" class="add-button"/>
				</form:form>
				
				<!-- Add our HTML table here. -->
				<table>
					<tr>
						<th>First name</th>
						<th>Last name</th>
						<th>E-mail</th>
						<th>Action</th>
					</tr>
					
					<!-- Loop over and print our customers (model attribute). -->
					<c:forEach var="customer" items="${customers}">
						<!-- Construct an "update" link with customer id -->
						<c:url var="updateLink" value="/customer/showFormForUpdate">
							<c:param name="customerId" value="${customer.id}"/>
						</c:url>
						
						<!-- Construct an "delete" link with customer id -->
						<c:url var="deleteLink" value="/customer/delete">
							<c:param name="customerId" value="${customer.id}"></c:param>
						</c:url>
						
						<tr>
							<td>${customer.firstName}</td>
							<td>${customer.lastName}</td>
							<td>${customer.email}</td>
							
							<td>
								<!-- Display the update link. -->
								<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
									<a href="${updateLink}">Update</a>
								</security:authorize>
								
								<security:authorize access="hasAnyRole('ADMIN')">
									<a href="${deleteLink}" onclick="if (!confirm('Are you sure you want to delete this customer?')) return false;">Delete</a>
								</security:authorize>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<br><br>
				
				<a href="${pageContext.request.contextPath}/showMyLoginPage">Log out</a>				
			</div>
		</div>
	</body>
</html>