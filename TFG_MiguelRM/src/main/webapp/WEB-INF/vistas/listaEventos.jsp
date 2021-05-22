<!-- Todos los jsp están realizados usando JSTL, Bootstrap (con jQuery incluido)

Cuentan con un nav común que sólo se despliega cuando no ha habido errores.

(Si hubiera un error de conexión a BBDD, el eventos/tipo no se mostraría correctamente)

 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Eventos ${tipo}</title>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
		crossorigin="anonymous"></script>
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.6.0.slim.min.js"></script>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0"
	crossorigin="anonymous">
</head>
<body class="px-5 py-1">

		
		<!-- Tenemos una navbar que cuenta con dropdown, y con algunos elementos todavía desactivados,
		COMÚN a todas las páginas -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">

  <div class="container-fluid">
    <a class="navbar-brand" href="#">MRM</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
             <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/eventos/todos">Eventos</a>
        </li>
        
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/eventos/activos">Eventos Activos</a>
        </li>
        

        
        <li class="nav-item">
          <a class="nav-link" href="/eventos/destacados">Eventos Destacados</a>
        </li>
        
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Eventos/tipo
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
    		<c:forEach items="${listaTipos}" var="miTipo" varStatus="varEstado">
    		    <li><a class="dropdown-item" href="/eventos/${miTipo.idTipo}">${miTipo.idTipo} - ${miTipo.nombre}</a></li>
			</c:forEach>
          </ul>
        </li>
        
        <li class="nav-item">
          <a class="nav-link" href="/tipos/todos">Tipos</a>
        </li>
        
       <li class="nav-item">
          <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Usuarios</a>
        </li>
        
       <li class="nav-item">
          <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Login</a>
        </li>
        
       <li class="nav-item">
          <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Registro</a>
        </li>
        
       <li class="nav-item">
          <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Salir</a>
        </li>
      </ul>

    </div>
  </div>
</nav>


<!-- Posteriormente, tenemos el encabezado de la página -->
<header class="px-5 py-4">
			<h1 class="text-primary text-center">Lista de eventos
				${tipo}</h1>
			<c:if test="${mensaje != null && mensaje.contains('Error')}">
					<h4 class=" text-warning text-center">${requestScope.mensaje}</h4>
			</c:if>
			<c:if test="${mensaje != null && !mensaje.contains('Error')}">
					<h4 class=" text-success text-center">${requestScope.mensaje}</h4>
			</c:if>

</header>

<!--

Dentro del main, tenemos el botón de nuevo evento, y la tabla con la correspondiente lista:
activos, destacados o por tipo.

*Si es activo, mostraremos si está destacado o no

*Si hemos filtrado por tipo, mostraremos activos y cancelados, destacados y no destacados,
indicando esa condición

*Si es destacado, no mostraremos ninguna propiedad adicional, ya que será activo obligatoriamente

-->
<main>
			<a href="/eventos/create${origen}" class="btn btn-primary btn-sm">Nuevo
				Evento</a>
		<c:if test="${mensajeError != null}">
				<br>
			<h2 class="text-primary text-warning text-center">${mensajeError}</h2>
		</c:if>
		<c:if test="${miListaEventos.size() != 0}">
			<table class="table table-striped table-sm">
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

						<td><a href="/eventos/editar/${evento.idEvento}${origen}"
							class="btn btn-info btn-sm">Editar</a></td>
						<c:if test="${evento.estado == 'activo'}">
							<td><a href="/eventos/estado/${evento.idEvento}${origen}/cancelado"
							class="btn btn-warning btn-sm">Cancelar</a></td>
							</c:if>
						<c:if test="${evento.estado == 'cancelado'}">
							<td><a href="/eventos/estado/${evento.idEvento}${origen}/activo"
							class="btn btn-success btn-sm">Activar</a></td>
							</c:if>
						<c:if test="${evento.destacado == 's'}">
							<td><a href="/eventos/destacar/${evento.idEvento}${origen}/no"
							class="btn btn-outline-warning btn-sm">Normal</a></td>
							</c:if>
						<c:if test="${evento.destacado == '' && evento.estado == 'cancelado'}">
							<td><a href="/eventos/destacar/${evento.idEvento}${origen}/si"
							class="btn btn-outline-success btn-sm disabled">Destacar</a></td>
							</c:if>
						<c:if test="${evento.destacado == '' && evento.estado == 'activo'}">
							<td><a href="/eventos/destacar/${evento.idEvento}${origen}/si"
							class="btn btn-outline-success btn-sm">Destacar</a></td>
							</c:if>
						<td><a href="/eventos/eliminar/${evento.idEvento}${origen}"
							class="btn btn-danger btn-sm">Eliminar</a></td>

					</tr>
				</c:forEach>
			</table>
			</c:if>
</main>
</body>
</html>