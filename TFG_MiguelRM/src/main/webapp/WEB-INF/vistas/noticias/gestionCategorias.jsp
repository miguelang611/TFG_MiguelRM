<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="es">

<head>
<%@ include file="../common/head.jsp"%>
<c:if test="${mensaje != null}">
	<title>Error</title>
</c:if>
<title>categorias ${categoria}</title>
</head>

<body>
	<%@ include file="../common/scripts.jsp"%>
	<%@ include file="../common/nav.jsp"%>


	<main class="blog-post-single" style="padding: 50px">
		<div class="container">
			<div class="page-header wow fadeInUp text-center">
				<h1 class="page-title">Categorias</h1>
				<p class="result-count">Mostrando ${listaCategorias.size()} categorias</p>
				<a href="/gestion/categorias/create${origen}"
					class="btn btn-primary btn-sm">Nuevo Categoria</a>
			</div>
			<br />

			<c:if test="${mensaje != null && mensaje.contains('Error')}">

				<main class="blog-post-single">
					<div class="container">
						<h5 class=" text-warning text-center page-title">${mensaje}</h5>

					</div>
					<br />
				</main>
			</c:if>

			<c:if test="${mensaje != null && !mensaje.contains('Error')}">

				<main class="blog-post-single">
					<div class="container">
						<h5 class=" text-success text-center page-title">${mensaje}</h5>

						<br />
				</main>
			</c:if>

			<c:if test="${mensajeError != null}">
				<br>
				<h2 class="text-primary text-warning text-center">${mensajeError}</h2>
			</c:if>
			<c:if test="${listaCategoriasFull.size() != 0}">
				<table class="table table-striped table-sm fadeInUp wow">
					<tr>
						<th>Id</th>
						<th>Nombre</th>
						<th>Descripción</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach var="categoria" items="${listaCategorias}">
						<tr>
							<td>${categoria.idCategoria}</td>
							<td>${categoria.nombre}</td>
							<td>${categoria.descripcion}</td>


							<td><a
								href="/gestion/categorias/editar/${categoria.idCategoria}${origen}"
								class="btn btn-info btn-sm">Editar</a></td>
							<td><a
								href="/gestion/categorias/eliminar/${categoria.idCategoria}${origen}"
								class="btn btn-danger btn-sm">Eliminar</a></td>

						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
		<br />
		<div class="col-md-8 blog-post-wrapper">
			<div class="post-navigation wow fadeInUp">
				<a class="btn prev-post" href="/gestion/categorias/">Volver</a>
			</div>
		</div>

	</main>


	<%@ include file="../common/footer.jsp"%>

</body>

</html>