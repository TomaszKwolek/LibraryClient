<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Add Book</title>
</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Book</h1>
				<p>Find book</p>
				<a href="<c:url value="/j_spring_security_logout" />"
				class="btn btn-warning btn-mini pull-right">logout</a>
			</div>
		</div>
	</section>
	<section class="container">
		<form:form method="get" modelAttribute="searchedBook"
			class="form-horizontal" action="findById">
			<fieldset>
				<legend>Find book by ID</legend>

				<!-- Sample template for some fields in Book Entity -->
				<div class="form-group">
					<label class="control-label col-lg-2" for="name">Id</label>
					<div class="col-lg-10">
						<form:input id="id" path="id" type="Long" class="form:input-large" />
					</div>
				</div>
			</fieldset>
			<div class="form-group">
				<div class="col-lg-offset-2 col-lg-10">
					<input type="submit" value="Find" id="btnAdd"
						class="btn btn-primary " value="Find" />
				</div>
			</div>
		</form:form>
	</section>
	<section class="container">
		<form:form method="get" modelAttribute="searchedBook"
			class="form-horizontal" action="findByTitleAndAuthor">
			<fieldset>
				<legend>Find book by title and author</legend>
				<!-- Sample template for some fields in Book Entity -->
				<div class="form-group">
					<label class="control-label col-lg-2" for="name">Title</label>
					<div class="col-lg-10">
						<form:input id="title" path="title" type="text"
							class="form:input-large" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-lg-2" for="name">Author</label>
					<div class="col-lg-10">
						<form:input id="authors" path="authors" type="text"
							class="form:input-large" />
					</div>
				</div>
			</fieldset>
			<div class="form-group">
				<div class="col-lg-offset-2 col-lg-10">
					<input type="submit" value="Find" id="btnAdd"
						class="btn btn-primary " value="Find" />
				</div>
			</div>
		</form:form>
	</section>
	<section class="container">
		<a href="<spring:url value="/" />" class="btn btn-default"> <span
			class="glyphicon-hand-left glyphicon"></span> back
		</a>
		<a href="<spring:url value="/" />" class="btn btn-default"> <span
			class="glyphicon glyphicon-home"></span> home page
		</a>
	</section>
</body>
</html>
