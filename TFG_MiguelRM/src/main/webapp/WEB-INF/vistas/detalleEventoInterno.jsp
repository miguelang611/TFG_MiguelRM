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

<!-- Podemos tener un mensaje por alert
(proveniente de la parte logeada de la aplicaci�n) -->
<c:if test="${requestScope.mensaje!=null}">
<script>
alert("${requestScope.mensaje}")
window.location = "${requestScope.urlDestino}"
</script>
</c:if>	

		
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

</header>

<main>



</main>

<!--

Dentro del main, tenemos el botón de nuevo evento, y la tabla con la correspondiente lista:
activos, destacados o por tipo.

*Si es activo, mostraremos si está destacado o no

*Si hemos filtrado por tipo, mostraremos activos y cancelados, destacados y no destacados,
indicando esa condición

*Si es destacado, no mostraremos ninguna propiedad adicional, ya que será activo obligatoriamente

-->



</body>
</html>