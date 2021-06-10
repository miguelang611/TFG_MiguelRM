<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">

<head>
<%@ include file="../common/head.jsp"%>
<title>${noticia.nombre}</title>
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
							<a class="btn prev-post" href="/noticias/">Volver</a>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${mensaje != null && !mensaje.contains('Error')}">
				<h4 class=" text-success text-center">${mensaje}</h4>
			</c:if>
			<c:if test="${noticia != null}">

				<h1 class="post-title wow fadeInUp">${noticia.nombre}</h1>

				<div class="row">
					<div class="col-md-8 blog-post-wrapper">
						<div class="post-header wow fadeInUp">
							<img src="${noticia.img}" alt="Imagen Noticia ${noticia.nombre}"
								class="post-featured-image">
						</div>
						<div class="post-content wow fadeInUp">
						
							<c:if test="${noticia.destacada == 's'}">
								<blockquote class="blockquote wow fadeInUp">
									<h4 style="margin-bottom: 0px;">${noticia.subtitulo}</h4>
								</blockquote>
							</c:if>

							<p class="post-date">Fecha: ${noticia.fecha} | by ${noticia.usuario.nombre }</p>
						

							<h5>${noticia.detalle }</h5>
							<br/>
							<div class="post-navigation wow fadeInUp">
								<a class="btn prev-post" href="/noticias/">Volver</a>
							</div>
<br/>
						</div>

					</div>
					<aside class="col-md-4">
						<div class="sidebar-widget wow fadeInUp">
							<h5 class="widget-title">Etiquetas</h5>
							<div class="widget-content">
								<a href="/noticias/categoria/${noticia.categoria.idCategoria}" class="post-tag">${noticia.categoria.nombre}</a>
								<c:if test="${noticia.destacada == 's' }">
									<a href="/noticias/destacadas" class="post-tag">Destacada</a>
								</c:if>
								<a href="/noticias/" class="post-tag">2021</a>
							</div>
						</div>
						<div class="sidebar-widget wow fadeInUp">
							<h5 class="widget-title">Otros Noticias</h5>
							<div class="widget-content">
								<c:forEach items="${listaNoticias}" var="miEv"
									varStatus="varEstado">
									<div class="gallery">
										<a href="/noticias/detalle/${miEv.idNoticia}"
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
									<c:forEach items="${listaCategorias}" var="miCategoria"
										varStatus="varEstado">
										<li><a href="/noticias/categoria/${miCategoria.idCategoria}">${miCategoria.nombre}</a></li>
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