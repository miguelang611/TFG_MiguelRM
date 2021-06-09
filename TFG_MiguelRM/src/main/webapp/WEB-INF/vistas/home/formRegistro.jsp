<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">

<head>
<%@ include file="../common/head.jsp"%>
<title>Formulario de Registro</title>
</head>

<body>
	<%@ include file="../common/scripts.jsp"%>
	<%@ include file="../common/nav.jsp"%>

	<main class="contact-page">
		<div class="container">
			<h1 class="oleez-page-title text-center wow fadeInUp">Formulario de Registro</h1>
			<c:if test="${mensaje != null }">
				<c:if test="${mensaje.contains('Error') }">
					<h3 class=" text-warning text-center page-title">${mensaje}</h3>
				</c:if>
				<c:if test="${!mensaje.contains('Error') }">
					<h3 class=" text-warning text-center page-title">${mensaje}</h3>
				</c:if>
				<br />
			</c:if>

			<br />
			<div class="text-center">

				<div class="wow fadeInRight">
					<form action="/registro" method="POST"
						class="oleez-contact-form needs-validation" novalidate>
						<div class="form-group">
							<label for="nombre">Nombre</label>
							<input type="text"
								class="oleez-input" id="nombre" name="nombre"
								pattern="^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]{5,}" value="${usuario.nombre}" placeholder="${usuario.nombre}" required>
							<div class="valid-feedback">
								<br />¡Tiene buena pinta!
							</div>
							<div class="invalid-feedback">
								<br />¡Comprueba que la dirección sea correcta!
							</div>

						</div>
						<div class="form-group">
							<label for="email">Email</label> <input type="email"
								class="oleez-input" id="email" name="email" value="${usuario.email}" placeholder="${usuario.email}"  required>
							<div class="valid-feedback">
								<br />¡Tiene buena pinta!
							</div>
							<div class="invalid-feedback">
								<br />¡Comprueba que el email sea correcto!
							</div>

						</div>
						<div class="form-group">
							<label for="password">Contraseña</label> <input type="password"
								class="oleez-input" id="password" name="password" pattern="^[0-9a-zA-Z]{6,}" required>
							<div class="valid-feedback">
								<br />¡Tiene buena pinta!
							</div>
							<div class="invalid-feedback">
								<br />¡Comprueba que la contraseña tenga al menos 6 caracteres!
							</div>

						</div>
						<div class="form-group">
							<label for="direccion">Dirección</label>
							<input type="text"
								class="oleez-input" id="direccion" name="direccion"
								pattern="^[#.0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\s,-]{10,}"
								value="${usuario.direccion}"
								placeholder="${usuario.direccion}" 
								required>
							<div class="valid-feedback">
								<br />¡Tiene buena pinta!
							</div>
							<div class="invalid-feedback">
								<br />¡Comprueba que la dirección sea correcta!
							</div>

						</div>
						<button type="submit" class="btn btn-submit">¡Vamos!</button>
					</form>
				</div>
			</div>
		</div>
	</main>

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

	<%@ include file="../common/footer.jsp"%>

</body>

</html>