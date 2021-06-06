<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<!DOCTYPE html>
<html lang="es">

<head>
<%@ include file="common/head.jsp"%>

<c:if test="${mensaje != null}">
	<title>Error</title>
</c:if>
<title>Eventos ${tipo}</title>
</head>

<body>
	<%@ include file="common/scripts.jsp"%>
	<%@ include file="common/nav.jsp"%>
	
	
<main class="blog-post-single" style="padding: 50px">
<div class="container">
<div class="page-header wow fadeInUp text-center">
					<h1 class="page-title">FORMULARIO DE ${accion} DE EVENTOS</h1>
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

<!-- Main con formulario:

Es común para la creación y para la edición de eventos; pero leemos con "accion" el tipo que es
y actuamos en consecuencia.

Traemos la lista de eventos siempre, para que con un desplegable podamos escoger el tipo
por número y nombre

Cuando estamos creando un nuevo evento, no se muestran opciones de ID, ni de activo,
ya que el ID se asigna vía autoincrement y todos los eventos nuevos son activoss

Por otro lado, se utilizan value leyendo las variables del evento para su edición,
además de algún if vía JSTL para determinar si es selected cuando son opciones de select
como estado, destacado o tipo de evento

Para los campos numéricos, salvo el precio, se opta por un slider

Así mismo, todo el formulario se valida vía script con ayuda de Bootstrap
utilizando expresiones regulares acordes a cada campo y con mensajes personalizados

-->
			<form class="row g-3 needs-validation justify-content-center"
				novalidate action="${destino}" method="get">


				<c:if test="${accion.equals('EDICIÓN')}">
				
					<article class="col-md-4">
						<label for="idInput" class="form-label">ID Evento</label> <input
							type="text" name="idEvento" class="form-control" id="idInput"
							value="${evento.idEvento}" required readonly>
					</article>
					
					<article class="col-md-4">
					<label for="estadoInput" class="form-label">¿Estado?</label>
					<select name="estado" class="form-select" id="estadoInput"
					 required>

						<option value="activo"
						<c:if test="${evento.estado == 'activo'}">selected</c:if>
						>Activo</option>
						<option value="cancelado"
						<c:if test="${evento.estado == 'cancelado'}">selected</c:if>
						>Cancelado</option>
						
					</select>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Escoge una opción porfa!</div>
				</article>

				</c:if>

				<article class="col-md-4">
					<label for="nombreInput" class="form-label">Nombre</label> <input
						type="text" name="nombre" class="form-control" id="nombreInput"
						pattern="^[a-zA-ZñÑáéíóúÁÉÍÓÚ,.\s]{3,}" value="${evento.nombre}"
						placeholder="${evento.nombre}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Este nombre no es válido!</div>
				</article>

				<article class="col-md-8">
					<label for="descripcionInput" class="form-label">Descripción</label>
					<input type="text" name="descripcion" class="form-control"
						id="descripcionInput" value="${evento.descripcion}"
						placeholder="${evento.descripcion}"
						pattern="{^[#.0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\s,-]{10,}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la descripción
						sea correcta y detallada!</div>
				</article>

				<article class="col-md-4">
					<label for="fechaInicioInput" class="form-label">Fecha de
						inicio</label> <input type="date" name="fechaInicio" class="form-control"
						id="fechaInicioInput" value="${evento.fechaInicio}"
						placeholder="${evento.fechaInicio}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la fecha sea
						correcta!</div>
				</article>
				
				<article class="col-md-4">
					<label for="destacadoInput" class="form-label">¿Destacar?</label>
					<select name="destacado" class="form-select" id="destacadoInput"
					 required>
					 
					 	<option value="s"
						<c:if test="${evento.destacado == 's'}">selected</c:if>
						>Sí Destacar</option>
						
						<option value=""
						<c:if test="${evento.destacado == ''}">selected</c:if>
						>No Destacar</option>
						
					</select>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Escoge una opción porfa!</div>
				</article>


				
				<article class="col-md-4">
					<label for="precioInput" class="form-label">Precio</label>
					<input type="text" name="precio" class="form-control"
						id="precioInput" value="${evento.precio}"
						placeholder="${evento.precio}"
						pattern="^[0-9.]{1,}"  required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que el precio sea correcto! ¡No uses decimales con ","!</div>
				</article>
			
				
				<article class="col-md-4">
					<label for="tipoInput" class="form-label">Tipo de evento</label>
					<select name="Tipo.idTipo" class="form-select" id="tipoInput"
						value="${evento.tipo.idTipo}"
						placeholder="${evento.tipo}" required>
						<c:forEach items="${listaTipos}" var="tipo" varStatus="varEstado">
						<option value="${tipo.idTipo}"
						<c:if test="${evento.tipo.idTipo == tipo.idTipo}">
						selected
						</c:if>
						>${tipo.idTipo} - ${tipo.nombre}</option>
						</c:forEach>
						
					</select>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Escoge una opción porfa!</div>
				</article>
				
				<article class="col-md-8">
					<label for="direccionInput" class="form-label">Dirección</label> <input
						type="text" name="direccion" class="form-control"
						id="direccionInput" value="${evento.direccion}"
						placeholder="${evento.direccion}"
						pattern="{^[#.0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\s,-]{8,}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la dirección
						tenga todos los datos!</div>
				</article>
				
				<div class="row g-3 justify-content-center">
				
				<article class="col-md-8">
					<label for="duracionInput" class="form-label" id="labDuracion">Duración (horas): ${evento.duracion}</label>
					<input onInput="$('#labDuracion').html('Duración: '+$(this).val()+' horas')"
						name="duracion" class="form-range" id="duracionInput"
						value="${evento.duracion}" type="range" class="form-range" min="1"
						max="20" step="1" placeholder="${evento.duracion}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la duración sea
						un número!</div>
						
				</article>

				<article class="col-md-8 justify-content-center">
					<label for="aforoMaximoInput" class="form-label" id="labAforoMaximo">Aforo Máximo (personas): ${evento.aforoMaximo}</label>
					<input onInput="$('#labAforoMaximo').html('Aforo Máximo: '+$(this).val()+' personas')"
						name="aforoMaximo" class="form-range" id="aforoMaximoInput"
						value="${evento.aforoMaximo}" type="range" class="form-range" min="100"
						max="10000" step="100" placeholder="${evento.aforoMaximo}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la duración sea
						un número!</div>
						
				</article>

				<article class="col-md-8 justify-content-center">
					<label for="minimoAsistenciaInput" class="form-label" id="labMinAsitencia">Mínimo de Asistencia (personas): ${evento.minimoAsistencia}</label>
					<input onInput="$('#labMinAsitencia').html('Mínimo de Asistencia: '+$(this).val()+' personas')"
						name="minimoAsistencia" class="form-range" id="minimoAsistenciaInput"
						value="${evento.minimoAsistencia}" type="range" class="form-range" min="20"
						max="1000" step="20" placeholder="${evento.minimoAsistencia}" required>
					<div class="valid-feedback">¡Tiene buena pinta!</div>
					<div class="invalid-feedback">¡Comprueba que la asistencia sea
						un número!</div>
						
				</article>
				
				</div>				
				
				<div class="col-12 text-center">
					<br>
					<button class="btn btn-primary center" type="submit">Enviar</button>
				</div>
			</form>
</div>
<br/>
					<div class="col-md-8 blog-post-wrapper">
						<div class="post-navigation wow fadeInUp">
							<a class="btn prev-post" href="/gestion/eventos/todos">Volver</a>
						</div>
					</div>
					

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
	
</main>


	<%@ include file="common/footer.jsp"%>

</body>

</html>