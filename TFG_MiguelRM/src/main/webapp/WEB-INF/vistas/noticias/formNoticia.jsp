<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>FORMULARIO DE ${accion} DE EVENTOS</title>
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
          <a class="nav-link" aria-current="page" href="/gestion/noticias/todos">Noticias</a>
        </li>
        
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/gestion/noticias/activos">Noticias Activos</a>
        </li>
        

        
        <li class="nav-item">
          <a class="nav-link" href="/gestion/noticias/destacadas">Noticias Destacadas</a>
        </li>
        
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Noticias/categoria
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
    		<c:forEach items="${listaCategoriasFull}" var="miCategoria" varStatus="varEstado">
    		    <li><a class="dropdown-item" href="/gestion/noticias/${miCategoria.idCategoria}">${miCategoria.idCategoria} - ${miCategoria.nombre}</a></li>
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
			<h1>FORMULARIO DE ${accion} DE NOTICIAS</h1>
			<h1>${mensaje}</h1>
			<br>
		</header>


<!-- Main con formulario:

Es com??n para la creaci??n y para la edici??n de noticias; pero leemos con "accion" el categoria que es
y actuamos en consecuencia.

Traemos la lista de noticias siempre, para que con un desplegable podamos escoger el categoria
por n??mero y nombre

Cuando estamos creando un nuevo noticia, no se muestran opciones de ID, ni de activo,
ya que el ID se asigna v??a autoincrement y todos los noticias nuevos son activoss

Por otro lado, se utilizan value leyendo las variables del noticia para su edici??n,
adem??s de alg??n if v??a JSTL para determinar si es selected cuando son opciones de select
como estado, destacada o categoria de noticia

Para los campos num??ricos, salvo el precio, se opta por un slider

As?? mismo, todo el formulario se valida v??a script con ayuda de Bootstrap
utilizando expresiones regulares acordes a cada campo y con mensajes personalizados

-->
		<main>
			<form class="row g-3 needs-validation justify-content-center"
				novalidate action="${destino}" method="get">


				<c:if test="${accion.equals('EDICI??N')}">
				
					<article class="col-md-4">
						<label for="idInput" class="form-label">ID Noticia</label> <input
							type="text" name="idNoticia" class="form-control" id="idInput"
							value="${noticia.idNoticia}" required readonly>
					</article>
					
				</article>

				</c:if>

				<article class="col-md-4">
					<label for="nombreInput" class="form-label">Nombre</label> <input
						type="text" name="nombre" class="form-control" id="nombreInput"
						pattern="^[a-zA-Z????????????????????????,.\s]{3,}" value="${noticia.nombre}"
						placeholder="${noticia.nombre}" required>
					<div class="valid-feedback">??Tiene buena pinta!</div>
					<div class="invalid-feedback">??Este nombre no es v??lido!</div>
				</article>

				<article class="col-md-8">
					<label for="descripcionInput" class="form-label">Subt??tulo</label>
					<input type="text" name="descripcion" class="form-control"
						id="descripcionInput" value="${noticia.subtitulo}"
						placeholder="${noticia.subtitulo}"
						pattern="{^[#.0-9a-zA-Z????????????????????????\s,-]{10,}" required>
					<div class="valid-feedback">??Tiene buena pinta!</div>
					<div class="invalid-feedback">??Comprueba que la descripci??n
						sea correcta y detallada!</div>
				</article>
			

				<article class="col-md-4">
					<label for="fechaInput" class="form-label">Fecha de
						inicio</label> <input type="date" name="fecha" class="form-control"
						id="fechaInput" value="${noticia.fecha}"
						placeholder="${noticia.fecha}" required>
					<div class="valid-feedback">??Tiene buena pinta!</div>
					<div class="invalid-feedback">??Comprueba que la fecha sea
						correcta!</div>
				</article>
				
				<article class="col-md-4">
					<label for="destacadaInput" class="form-label">??Destacar?</label>
					<select name="destacada" class="form-select" id="destacadaInput"
					 required>
					 
					 	<option value="s"
						<c:if test="${noticia.destacada == 's'}">selected</c:if>
						>S?? Destacar</option>
						
						<option value=""
						<c:if test="${noticia.destacada == ''}">selected</c:if>
						>No Destacar</option>
						
					</select>
					<div class="valid-feedback">??Tiene buena pinta!</div>
					<div class="invalid-feedback">??Escoge una opci??n porfa!</div>
				</article>


			
				
				<article class="col-md-4">
					<label for="categoriaInput" class="form-label">Categor??a de noticia</label>
					<select name="Categoria.idCategoria" class="form-select" id="categoriaInput"
						value="${noticia.categoria.idCategoria}"
						placeholder="${noticia.categoria}" required>
						<c:forEach items="${listaCategorias}" var="categoria" varStatus="varEstado">
						<option value="${categoria.idCategoria}"
						<c:if test="${noticia.categoria.idCategoria == categoria.idCategoria}">
						selected
						</c:if>
						>${categoria.idCategoria} - ${categoria.nombre}</option>
						</c:forEach>
						
					</select>
					<div class="valid-feedback">??Tiene buena pinta!</div>
					<div class="invalid-feedback">??Escoge una opci??n porfa!</div>
				</article>
				
				
				<div class="row g-3 justify-content-center">
				

				<article class="col-md-8 justify-content-center">
					<label for="imgInput" class="form-label">URL Imagen</label>
					<input type="text" name="img" class="form-control"
						id="imgInput" value="${noticia.img}"
						placeholder="${noticia.img}"
						required>
					<div class="valid-feedback">??Tiene buena pinta!</div>
					<div class="invalid-feedback">??Comprueba que la URL de ruta sea correcta!</div>
				</article>
				
				<article class="col-md-12 justify-content-center">
					<label for="detalleInput" class="form-label">Cuerpo</label>
					<textarea name="detalle" class="form-control"
						id="detalleInput" value="${noticia.detalle}"
						placeholder="${noticia.detalle}"
						pattern="{^[#.0-9a-zA-Z????????????????????????\s,-]{50,}" required></textarea>
					<div class="valid-feedback">??Tiene buena pinta!</div>
					<div class="invalid-feedback">??Comprueba que el cuerpo
						sea correcto y detallado!</div>
				</article>
				
				</div>				
				
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
	<!-- Funci??n para manejar validaci??n -->
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