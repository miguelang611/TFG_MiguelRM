<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">

<head>
<%@ include file="../common/head.jsp"%>
<title>${evento.nombre}</title>
</head>

<body>
	<%@ include file="../common/scripts.jsp"%>
	<%@ include file="../common/nav.jsp"%>

	<main class="blog-post-single">
		<div class="container">
			<c:if test="${mensajeError != null}">
				<h1 class=" text-warning text-center page-title">${mensajeError}</h1>
				<div class="row">
					<div class="col-md-8 blog-post-wrapper">
						<div class="post-navigation wow fadeInUp">
							<a class="btn prev-post" href="/eventos/">Volver</a>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${mensaje != null && !mensaje.contains('Error')}">
				<h4 class=" text-success text-center">${mensaje}</h4>
			</c:if>
			<c:if test="${evento != null}">

				<h1 class="post-title wow fadeInUp">Evento ${evento.nombre}</h1>

				<div class="row">
					<div class="col-md-8 blog-post-wrapper">
						<div class="post-header wow fadeInUp">
							<img src="${evento.img}" alt="Imagen Evento ${evento.nombre}"
								class="post-featured-image">
						</div>
						<div class="post-content wow fadeInUp">
							<c:if test="${evento.destacado == 's'}">
								<blockquote class="blockquote wow fadeInUp">
									<h4>¡EVENTO DESTACADO!</h4>
									<h5>¡Alto número de reservas en las pasadas 24 horas!</h5>
								</blockquote>
							</c:if>
							<h4>${evento.descripcion}</h4>
							<p>Localización: ${evento.direccion}</p>
							<p class="post-date">Fecha: ${evento.fechaInicio}</p>

							<p>Duración: ${evento.duracion} horas</p>

							<p>Aforo máximo: ${evento.aforoMaximo } personas</p>

							<p>
								Precio:
								<del>${evento.precio +20}€</del>
								${evento.precio}€
							</p>

							<div class="shop-page" style="padding-bottom: 0px">

								<article class="product-card wow fadeInUp">


									<form class="btn-wrapper">
										<button class="btn btn-add-to-cart" type="submit" disabled
											formaction="/eventos/comprar/${evento.idEvento}${origen}">
											Comprar</button>
									</form>
								</article>

							</div>

							<div class="post-navigation wow fadeInUp">
								<a class="btn prev-post" href="/eventos/">Volver</a>
							</div>

						</div>

					</div>
					<aside class="col-md-4">
						<div class="sidebar-widget wow fadeInUp">
							<h5 class="widget-title">Etiquetas</h5>
							<div class="widget-content">
								<a href="/eventos/tipo/${evento.tipo.idTipo}" class="post-tag">${evento.tipo.nombre}</a>
								<c:if test="${evento.destacado == 's' }">
									<a href="/eventos/destacados" class="post-tag">Destacado</a>
								</c:if>
								<a href="/eventos/" class="post-tag">2021</a>
							</div>
						</div>
						<div class="sidebar-widget wow fadeInUp">
							<h5 class="widget-title">Otros Eventos</h5>
							<div class="widget-content">
								<c:forEach items="${listaEventos}" var="miEv"
									varStatus="varEstado">
									<div class="gallery">
										<a href="/eventos/detalle/${miEv.idEvento}"
											class="gallery-grid-item" data-fancybox="widget-gallery">
											<img src="${miEv.img}" alt="Imagen ${miEv.nombre}">
										</a>

									</div>
									<br />
								</c:forEach>

							</div>
						</div>
						<div class="sidebar-widget wow fadeInUp">
							<h5 class="widget-title">Categorías</h5>
							<div class="widget-content">
								<ul class="category-list">
									<c:forEach items="${listaTipos}" var="miTipo"
										varStatus="varEstado">
										<li><a href="/eventos/tipo/${miTipo.idTipo}">${miTipo.nombre}</a></li>
									</c:forEach>

								</ul>
							</div>
						</div>
					</aside>
				</div>
			</c:if>

		</div>
	</main>

	<%@ include file="../common/footer.jsp"%>

</body>

</html>