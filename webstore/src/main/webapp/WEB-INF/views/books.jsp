<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Books</title>
</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Books</h1>
				<p>This page contains all informations about books</p>
				<a href="<c:url value="/j_spring_security_logout" />"
					class="btn btn-warning btn-mini pull-right">logout</a>
			</div>
		</div>
	</section>


	<div class="container">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Book Id</th>
					<th>Title</th>
					<th>Author</th>
					<th>Status</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bookList}" var="book">
					<tr>
						<td>${book.id}</td>
						<td>${book.title}</td>
						<td>${book.authors}</td>
						<td>${book.status}</td>
						<td></td>
						<td>
							<div class="btn-group">
								<a href=" <spring:url value="/books/book?id=${book.id}" /> "
									class="btn btn-primary btn-sm"> <span
									class="glyphicon-info-sign glyphicon" /></span> Details
								</a> <a href="<spring:url value="/books/remove?id=${book.id}"/>"
									class="btn btn-danger btn-sm"> <span
									class="glyphicon glyphicon-remove"></span> Remove

								</a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


	<section class="container">
		<a href="<spring:url value="/" />" class="btn btn-default"> <span
			class="glyphicon-hand-left glyphicon"></span> back
		</a> <a href="<spring:url value="/" />" class="btn btn-default"> <span
			class="glyphicon glyphicon-home"></span> home page
		</a>
	</section>
</body>
</html>
