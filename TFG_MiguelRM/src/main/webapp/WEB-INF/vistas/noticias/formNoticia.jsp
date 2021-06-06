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
			<h1>FORMULARIO DE ${accion} DE EVENTOS</h1>
			<h1>${mensaje}</h1>
			<br>
		</header>


<!-- Main con formulario:

Es común para la creación y para la edición de noticias; pero leemos con "accion" el categoria que es
y actuamos en consecuencia.

Traemos la lista de noticias siempre, para que con un desplegable podamos escoger el categoria
por número y nombre

Cuando estamos creando un nuevo noticia, no se muestran opciones de ID, ni de activo,
ya que el ID se asigna vía autoincrement y todos los noticias nuevos son activoss

Por otro lado, se utilizan value leyendo las variables del noticia para su edición,
además de algún if vía JSTL para determinar si es selected cuando son opciones de select
como estado, destacada o categoria de noticia

Para los campos numéricos, salvo el precio, se opta por un slider

Así mismo, todo el formulario se valida vía script con ayuda de Bootstrap
utilizando expresiones regulares acordes a cada campo y con mensajes personalizados

-->
		<main>
			<form class="row g-3 needs-validation justify-content-center"
				novalidate action="${destino}" method="get">


				<c:if test="${accion.equals('EDICIÓN')}">
				
					<article class="col-md-4">
						<label for="idInput" class="form-label">ID Noticia</label> <input
							type="text" name="idNoticia" class="form-control" id="idInput"
							value="${noticia.idNoticia}" required readonly>
					</article>
					
					<article class="col-md-4">
					<label for="estadoInput" class="form-label">¿Estado?</label>
					<select name="estado" class="form-select" id="estadoInput"
					 required>

						<option value="activo"
						<c:if test="${noticia.estado == 'activo'}">selected</c:if>
						>Activo</option>
						<option value="cancelado"
						<c:if test="${noticia.estado == 'cancelado'}">selected</c:if>
						>Cancelado</option>
						
					</select>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Escoge una opción porfa!</div>
				</article>

				</c:if>

				<article class="col-md-4">
					<label for="nombreInput" class="form-label">Nombre</label> <input
						type="text" name="nombre" class="form-control" id="nombreInput"
						pattern="^[a-zA-ZñÑáéíóúÁÉÍÓÚ,.\s]{3,}" value="${noticia.nombre}"
						placeholder="${noticia.nombre}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Este nombre no es válido!</div>
				</article>

				<article class="col-md-8">
					<label for="descripcionInput" class="form-label">Descripción</label>
					<input type="text" name="descripcion" class="form-control"
						id="descripcionInput" value="${noticia.descripcion}"
						placeholder="${noticia.descripcion}"
						pattern="{^[#.0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\s,-]{10,}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la descripción
						sea correcta y detallada!</div>
				</article>

				<article class="col-md-4">
					<label for="fechaInicioInput" class="form-label">Fecha de
						inicio</label> <input type="date" name="fechaInicio" class="form-control"
						id="fechaInicioInput" value="${noticia.fechaInicio}"
						placeholder="${noticia.fechaInicio}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la fecha sea
						correcta!</div>
				</article>
				
				<article class="col-md-4">
					<label for="destacadaInput" class="form-label">¿Destacar?</label>
					<select name="destacada" class="form-select" id="destacadaInput"
					 required>
					 
					 	<option value="s"
						<c:if test="${noticia.destacada == 's'}">selected</c:if>
						>Sí Destacar</option>
						
						<option value=""
						<c:if test="${noticia.destacada == ''}">selected</c:if>
						>No Destacar</option>
						
					</select>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Escoge una opción porfa!</div>
				</article>


				
				<article class="col-md-4">
					<label for="precioInput" class="form-label">Precio</label>
					<input type="text" name="precio" class="form-control"
						id="precioInput" value="${noticia.precio}"
						placeholder="${noticia.precio}"
						pattern="^[0-9.]{1,}"  required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que el precio sea correcto! ¡No uses decimales con ","!</div>
				</article>
			
				
				<article class="col-md-4">
					<label for="categoriaInput" class="form-label">Categoria de noticia</label>
					<select name="Categoria.idCategoria" class="form-select" id="categoriaInput"
						value="${noticia.categoria.idCategoria}"
						placeholder="${noticia.categoria}" required>
						<c:forEach items="${listaCategoriasFull}" var="categoria" varStatus="varEstado">
						<option value="${categoria.idCategoria}"
						<c:if test="${noticia.categoria.idCategoria == categoria.idCategoria}">
						selected
						</c:if>
						>${categoria.idCategoria} - ${categoria.nombre}</option>
						</c:forEach>
						
					</select>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Escoge una opción porfa!</div>
				</article>
				
				<article class="col-md-8">
					<label for="direccionInput" class="form-label">Dirección</label> <input
						type="text" name="direccion" class="form-control"
						id="direccionInput" value="${noticia.direccion}"
						placeholder="${noticia.direccion}"
						pattern="{^[#.0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\s,-]{8,}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la dirección
						tenga todos los datos!</div>
				</article>
				
				<div class="row g-3 justify-content-center">
				
				<article class="col-md-8">
					<label for="duracionInput" class="form-label" id="labDuracion">Duración (horas): ${noticia.duracion}</label>
					<input onInput="$('#labDuracion').html('Duración: '+$(this).val()+' horas')"
						name="duracion" class="form-range" id="duracionInput"
						value="${noticia.duracion}" type="range" class="form-range" min="1"
						max="20" step="1" placeholder="${noticia.duracion}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la duración sea
						un número!</div>
						
				</article>

				<article class="col-md-8 justify-content-center">
					<label for="aforoMaximoInput" class="form-label" id="labAforoMaximo">Aforo Máximo (personas): ${noticia.aforoMaximo}</label>
					<input onInput="$('#labAforoMaximo').html('Aforo Máximo: '+$(this).val()+' personas')"
						name="aforoMaximo" class="form-range" id="aforoMaximoInput"
						value="${noticia.aforoMaximo}" type="range" class="form-range" min="100"
						max="10000" step="100" placeholder="${noticia.aforoMaximo}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la duración sea
						un número!</div>
						
				</article>

				<article class="col-md-8 justify-content-center">
					<label for="minimoAsistenciaInput" class="form-label" id="labMinAsitencia">Mínimo de Asistencia (personas): ${noticia.minimoAsistencia}</label>
					<input onInput="$('#labMinAsitencia').html('Mínimo de Asistencia: '+$(this).val()+' personas')"
						name="minimoAsistencia" class="form-range" id="minimoAsistenciaInput"
						value="${noticia.minimoAsistencia}" type="range" class="form-range" min="20"
						max="1000" step="20" placeholder="${noticia.minimoAsistencia}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la asistencia sea
						un número!</div>
						
				</article>
				
				<article class="col-md-8 justify-content-center">
					<label for="imgInput" class="form-label">URL Imagen</label>
					<input type="text" name="img" class="form-control"
						id="imgInput" value="${noticia.img}"
						placeholder="${noticia.img}"
						required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la URL de ruta sea correcta!</div>
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
	<!-- Función para manejar validación -->
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