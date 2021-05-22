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
<title>Lista de tipos</title>
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

	<!-- Podemos tener un mensaje por alert
(proveniente de la parte de nuevo y edición de la aplicación),
además de cambiar la location para llevar a una url principal -->
	<c:if test="${mensaje!=null}">
		<script>
			alert("${mensaje}")
			window.location = "${urlDestino}"
		</script>
	</c:if>
	
	<!-- Si la lista de eventos es nula, no tenemos conexión a BD,
	mostraremos mensaje rojo de error -->
	<c:if test="${requestScope.listaTipos == null}">
		<br><h1 class="text-primary text-danger text-center">${mensajeError}</h1>
	</c:if>
	
		<!-- Si la lista de tipos tiene tamaño 0, hay conexión a BD, pero no
		hay tipos en la tabla, mostraremos mensaje amarillo de warning -->
	<c:if test="${requestScope.listaTipos.size() == 0}">
		<br><h1 class="text-primary text-warning text-center">${mensajeError}</h1>
	</c:if>
	
	<!-- En caso contrario, todo es correcto -->
	<c:if test="${requestScope.listaTipos != null && requestScope.listaTipos.size() != 0}">
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
	
	<h1 class="text-primary text-center"">Lista de tipos ${requestScope.tipo }</h1>

</header>

<!-- El main contiene la propia lista, que se muestra en forma de tabla, junto con el botón
de nuevo tipo -->
<main>
	<a href="/tipos/create" class="btn btn-primary btn-sm" >Nuevo Tipo</a></td>
	<table class="table table-striped table-sm" >
	<th>Id</th>
	<th>Nombre</th>
	<th>Descripción</th>
	<th></th>
	<th></th>

	
	<c:forEach var="tipo" items="${listaTipos}" >
		<tr>
			<td>${tipo.idTipo}</td>
			<td>${tipo.nombre}</td>
			<td>${tipo.descripcion}</td>
			<td><a href="/tipos/editar/${tipo.idTipo}" class="btn btn-success btn-sm" >Editar</a></td> 
 			<td><a href="/tipos/eliminar/${tipo.idTipo}" class="btn btn-danger btn-sm">Eliminar</a></td>
			 
		</tr>
	</c:forEach>
	</table>
	</main>
	</c:if>
	</body>
</html>