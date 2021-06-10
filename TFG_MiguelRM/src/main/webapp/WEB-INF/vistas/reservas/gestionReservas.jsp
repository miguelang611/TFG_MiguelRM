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
<title>${tipo}</title>
</head>

<body>
	<%@ include file="../common/scripts.jsp"%>
	<%@ include file="../common/nav.jsp"%>
	
	
<main class="blog-post-single" style="padding: 50px">
<div class="container">
<div class="page-header wow fadeInUp text-center">
					<h1 class="page-title">${tipo}</h1>
					<p class="result-count">Mostrando ${miListaReservas.size()}
						reservas</p>
			<a href="/eventos" class="btn btn-primary btn-sm">Nueva
				Reserva</a>
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
<c:if test="${miListaReservas.size() != 0}">
			<table class="table table-striped table-sm fadeInUp wow">
				<th>Id Reserva</th>
				<c:if test="${tipo.contains('Mis')}">
				<th>Nombre</th>
				<th>Fecha</th>
				</c:if>
				<c:if test="${tipo.contains('evento') }">
				<th>Cliente</th>
				</c:if>
				<c:if test="${tipo.contains('Todas') }">
				<th>Evento</th>
				<th>Email</th>
				</c:if>
				<th>Cantidad</th>
				<th></th>
				<th></th>

				<c:forEach var="reserva" items="${miListaReservas}">
					<tr>
						<td>${reserva.idReserva}</td>
						<c:if test="${tipo.contains('Mis')}">
						<td>${reserva.evento.nombre}</td>
						<td>${reserva.evento.fechaInicio}</td>
						</c:if>
						<c:if test="${tipo.contains('evento') }">
						<td>${reserva.usuario.email }
						</c:if>
						<c:if test="${tipo.contains('Todas') }">
						<td>${reserva.evento.nombre}</td>
						<td>${reserva.usuario.email}</td>
						</c:if>
						
						<td>${reserva.cantidad}</td>

						


						<td><a href="/eventos/detalle/${reserva.evento.idEvento}"
							class="btn btn-info btn-sm">Detalle Evento</a></td>

						<td><a href="/cliente/reservas/eliminar/${reserva.idReserva}${origen}"
							class="btn btn-danger btn-sm">Cancelar</a></td>

					</tr>
				</c:forEach>
			</table>
			</c:if>
</div>
<br/>
					<div class="col-md-8 blog-post-wrapper">
						<div class="post-navigation wow fadeInUp">
							<a class="btn prev-post" href="/gestion/reservas/todos">Volver</a>
						</div>
					</div>
	
</main>


	<%@ include file="../common/footer.jsp"%>

</body>

</html>