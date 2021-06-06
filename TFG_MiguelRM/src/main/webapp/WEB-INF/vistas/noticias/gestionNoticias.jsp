<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<!DOCTYPE html>
<html lang="es">

<head>
<%@ include file="../common/head.jsp"%>
<c:if test="${mensaje != null}">
	<title>Error</title>
</c:if>
<title>Noticias ${categoria}</title>
</head>

<body>
	<%@ include file="../common/scripts.jsp"%>
	<%@ include file="../common/nav.jsp"%>
	
	
<main class="blog-post-single" style="padding: 50px">
<div class="container">
<div class="page-header wow fadeInUp text-center">
					<h1 class="page-title">Noticias ${categoria}</h1>
					<p class="result-count">Mostrando ${miListaNoticias.size()}
						noticias</p>
			<a href="/gestion/noticias/create${origen}" class="btn btn-primary btn-sm">Nuevo
				Noticia</a>
				</div>
				<br/>

<c:if test="${mensaje != null && mensaje.contains('Error')}">

		<main class="blog-post-single">
			<div class="container">
				<h5 class=" text-warning text-center page-title">${mensaje}</h5>
				
			</div>
			<br/>
		</main>
	</c:if>
	
<c:if test="${mensaje != null && !mensaje.contains('Error')}">

		<main class="blog-post-single">
			<div class="container">
				<h5 class=" text-success text-center page-title">${mensaje}</h5>
			
			<br/>
		</main>
	</c:if>

		<c:if test="${mensajeError != null}">
				<br>
			<h2 class="text-primary text-warning text-center">${mensajeError}</h2>
		</c:if>
<c:if test="${miListaNoticias.size() != 0}">
			<table class="table table-striped table-sm fadeInUp wow">
				<th>Id</th>
				<th>Nombre</th>
				<th>Subtítulo</th>
				
				<th></th>
				<th></th>
				<th></th>
				<th></th>

				<c:forEach var="noticia" items="${miListaNoticias}">
					<tr>
						<td>${noticia.idNoticia}</td>
						<td>${noticia.nombre}</td>
						<td>${noticia.subtitulo}</td>
						
						<c:if test="${categoria == '(todos)' || categoria == 'activos' || categoria.contains('del categoria')}">
							<c:if test="${noticia.destacada == 's'}">
								<td>Sí</td>
							</c:if>
							<c:if test="${noticia.destacada != 's'}">
								<td>No</td>
							</c:if>
						</c:if>

						<td><a href="/gestion/noticias/editar/${noticia.idNoticia}${origen}"
							class="btn btn-info btn-sm">Editar</a></td>
						<c:if test="${noticia.destacada == 's'}">
							<td><a href="/gestion/noticias/destacar/${noticia.idNoticia}${origen}/no"
							class="btn btn-outline-warning btn-sm">Normal</a></td>
							</c:if>
						<c:if test="${noticia.destacada == ''}">
							<td><a href="/gestion/noticias/destacar/${noticia.idNoticia}${origen}/si"
							class="btn btn-outline-success btn-sm disabled">Destacar</a></td>
							</c:if>
						<td><a href="/gestion/noticias/eliminar/${noticia.idNoticia}${origen}"
							class="btn btn-danger btn-sm">Eliminar</a></td>

					</tr>
				</c:forEach>
			</table>
			</c:if>
</div>
<br/>
					<div class="col-md-8 blog-post-wrapper">
						<div class="post-navigation wow fadeInUp">
							<a class="btn prev-post" href="/gestion/noticias/todos">Volver</a>
						</div>
					</div>
	
</main>


	<%@ include file="../common/footer.jsp"%>

</body>

</html>