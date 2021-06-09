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
<title>Eventos ${tipo}</title>
</head>

<body>
	<%@ include file="../common/scripts.jsp"%>
	<%@ include file="../common/nav.jsp"%>
	
	
<main class="blog-post-single" style="padding: 50px">
<div class="container">
<div class="page-header wow fadeInUp text-center">
					<h1 class="page-title">Eventos ${tipo}</h1>
					<p class="result-count">Mostrando ${miListaEventos.size()}
						eventos</p>
			<a href="/gestion/eventos/create${origen}" class="btn btn-primary btn-sm">Nuevo
				Evento</a>
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
<c:if test="${miListaEventos.size() != 0}">
			<table class="table table-striped table-sm fadeInUp wow">
				<th>Id</th>
				<th>Nombre</th>
				<th>Descripción</th>
				<th>Precio</th>
				<c:if test="${tipo == '(todos)' || tipo.contains('del tipo')}">
					<th>Estado</th>
				</c:if>
				<c:if test="${tipo == '(todos)' || tipo == 'activos' || tipo.contains('del tipo')}">
					<th>Destacado</th>
				</c:if>
				<th></th>
				<th></th>
				<th></th>
				<th></th>

				<c:forEach var="evento" items="${miListaEventos}">
					<tr>
						<td>${evento.idEvento}</td>
						<td>${evento.nombre}</td>
						<td>${evento.descripcion}</td>
						<td>${evento.precio}</td>
						<c:if test="${tipo == '(todos)' || tipo.contains('del tipo')}">
							<td>${evento.estado}</td>
						</c:if>
						<c:if test="${tipo == '(todos)' || tipo == 'activos' || tipo.contains('del tipo')}">
							<c:if test="${evento.destacado == 's'}">
								<td>Sí</td>
							</c:if>
							<c:if test="${evento.destacado != 's'}">
								<td>No</td>
							</c:if>
						</c:if>

						<td><a href="/gestion/eventos/editar/${evento.idEvento}${origen}"
							class="btn btn-info btn-sm">Editar</a></td>
						<c:if test="${evento.estado == 'activo'}">
							<td><a href="/gestion/eventos/estado/${evento.idEvento}${origen}/cancelado"
							class="btn btn-warning btn-sm">Cancelar</a></td>
							</c:if>
						<c:if test="${evento.estado == 'cancelado'}">
							<td><a href="/gestion/eventos/estado/${evento.idEvento}${origen}/activo"
							class="btn btn-success btn-sm">Activar</a></td>
							</c:if>
						<c:if test="${evento.destacado == 's'}">
							<td><a href="/gestion/eventos/destacar/${evento.idEvento}${origen}/no"
							class="btn btn-outline-warning btn-sm">Normal</a></td>
							</c:if>
						<c:if test="${evento.destacado == '' && evento.estado == 'cancelado'}">
							<td><a href="/gestion/eventos/destacar/${evento.idEvento}${origen}/si"
							class="btn btn-outline-success btn-sm disabled">Destacar</a></td>
							</c:if>
						<c:if test="${evento.destacado == '' && evento.estado == 'activo'}">
							<td><a href="/gestion/eventos/destacar/${evento.idEvento}${origen}/si"
							class="btn btn-outline-success btn-sm">Destacar</a></td>
							</c:if>
						<td><a href="/gestion/eventos/eliminar/${evento.idEvento}${origen}"
							class="btn btn-danger btn-sm">Eliminar</a></td>

					</tr>
				</c:forEach>
			</table>
			</c:if>
</div>
<br/>
					<div class="col-md-8 blog-post-wrapper">
						<div class="post-navigation wow fadeInUp">
							<a class="btn prev-post" href="/gestion/eventos/todos">Volver</a>
						</div>
					</div>
	
</main>


	<%@ include file="../common/footer.jsp"%>

</body>

</html>