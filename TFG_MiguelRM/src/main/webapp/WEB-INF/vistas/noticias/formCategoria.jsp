<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>FORMULARIO DE ${accion} DE TIPOS</title>
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

<!-- Muestra mensaje en H1 si falla algo -->
	<c:if test="${mensaje != null}">
		<br>
		<h1 class="text-primary text-warning text-center">${mensaje}</h1>
	</c:if>
	
<!-- Si todo ok -->
	<c:if test="${mensaje == null}">

<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">

  <div class="container-fluid">
    <a class="navbar-brand" href="#">MRM</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
             <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/noticias/todos">Noticias</a>
        </li>
        
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/noticias/activos">Noticias Activos</a>
        </li>
        

        
        <li class="nav-item">
          <a class="nav-link" href="/noticias/destacadas">Noticias Destacadas</a>
        </li>
        
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Noticias/categoria
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
    		<c:forEach items="${listaCategorias}" var="miCategoria" varStatus="varEstado">
    		    <li><a class="dropdown-item" href="/noticias/${miCategoria.idCategoria}">${miCategoria.idCategoria} - ${miCategoria.nombre}</a></li>
			</c:forEach>
          </ul>
        </li>
        
        <li class="nav-item">
          <a class="nav-link" href="/categorias/todos">Categorias</a>
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


<!-- Header -->
		<header class="text-center">
			<br>
			<h1>FORMULARIO DE ${accion} DE CATEGORÍAS</h1>
			<h1>${mensaje}</h1>
			<br>
		</header>


<!-- Main con formulario:

Se basa en el formulario de noticias y sigue el mismo patrón de funcionamiento, siendo
mucho más simple por la menor cantidad de datos de categoria vs noticia

-->
		<main>
			<form class="row g-3 needs-validation justify-content-center"
				novalidate action="${destino}" method="POST">


				<c:if test="${accion.equals('EDICIÓN')}">
					<article class="col-md-4">
						<label for="idInput" class="form-label">ID Categoria</label> <input
							type="text" name="idCategoria" class="form-control" id="idInput"
							value="${categoria.idCategoria}" required readonly>
					</article>

				</c:if>

				<article class="col-md-4">
					<label for="nombreInput" class="form-label">Nombre</label> <input
						type="text" name="nombre" class="form-control" id="nombreInput"
						pattern="^[a-zA-ZñÑáéíóúÁÉÍÓÚ,.\s]{3,}" value="${categoria.nombre}"
						placeholder="${categoria.nombre}"
						pattern="^[a-zA-ZñÑáéíóúÁÉÍÓÚ,.\s]{3,}"  required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Este nombre no es válido!</div>
				</article>

				<article class="col-md-8">
					<label for="descripcionInput" class="form-label">Descripción</label>
					<input type="text" name="descripcion" class="form-control"
						id="descripcionInput" value="${categoria.descripcion}"
						placeholder="${categoria.descripcion}"
						pattern="{^[#.0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\s,-]{10,}"  required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la descripción
						sea correcta y detallada!</div>
				</article>

				<div class="col-12 text-center">
					<br>
					<button class="btn btn-primary center" type="submit">Enviar</button>
				</div>
			</form>
		</main>

	</c:if>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
		crossorigin="anonymous"></script>
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.6.0.slim.min.js"></script>
	<script>
		//Example starter JavaScript for disabling form submissions if there are invalid fields
		(function() {
			'use strict'

			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.querySelectorAll('.needs-validation')

			// Loop over them and prevent submission
			Array.prototype.slice.call(forms).forEach(function(form) {
				form.addEventListener('submit', function(event) {
					if (!form.checkValidity()) {
						event.preventDefault()
						event.stopPropagation()
					}

					form.classList.add('was-validated')
				}, false)
			})
		})()
	</script>
</body>
</html>