<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">

<head>
<%@ include file="../common/head.jsp"%>
<c:if test="${mensaje != null}">
	<title>Error</title>
</c:if>
<title>Noticias ${categoria}</title>
</head>

<body>
	<%@ include file="../common/scripts.jsp"%>
	<%@ include file="../common/nav.jsp"%>

	<c:if test="${miListaNoticias == null || miListaNoticias.size() == 0}">

		<main class="blog-post-single">
			<div class="container">
				<h1 class=" text-warning text-center page-title">${mensajeError}</h1>
				<div class="row">
					<div class="col-md-8 blog-post-wrapper">
						<div class="post-navigation wow fadeInUp">
							<a class="btn prev-post" href="/noticias">Volver</a>
						</div>
					</div>
				</div>
			</div>
		</main>
	</c:if>


	<c:if test="${miListaNoticias.size() != 0}">
		<main class="portfolio-list">
			<div class="container">
				<h1 class="oleez-page-title wow fadeInUp ">${categoria}</h1>

				<c:if test="${categoria == 'Noticias' }">

				<div class="row g-3 wow fadeInUp">
					<form method="GET" action="/noticias/contiene"
						class="col-md-4 ">
						<input type="text" id="palabra" name="palabra" required
							placeholder="¡Buscar por nombre!">
						<button type="submit" class="btn btn-primary btn-sm">Buscar</button>

					</form>
				</div>
				</c:if>
				
				<br/>
				<c:forEach var="noticia" items="${miListaNoticias}">


					<c:if test="${noticia.destacada == 's' }">

						<article class="project">
							<div class="row">
								<div
									class="col-md-4 mb-5 md-mb-0 project-content wow fadeInLeft">
									<h2 class="project-title link">${noticia.nombre }</h2>

									<h4 class="project-subtitle">${noticia.subtitulo }</h4>
									<br /> <a href="/noticias/detalle/${noticia.idNoticia}"
										class="project-link">¡Cuéntame más!</a>
								</div>
								<div class="col-md-7 mb-5">
									<div class="project-thumbnail-wrapper wow fadeInRight">
										<a href="/noticias/detalle/${noticia.idNoticia}"><img
											src="${noticia.img}" alt="Imagen de ${noticia.nombre }"></a>
									</div>
								</div>
							</div>
						</article>

					</c:if>

				</c:forEach>

				<c:forEach var="noticia" items="${miListaNoticias}">


					<c:if test="${noticia.destacada == '' }">

						<article class="project">
							<div class="row">
								<div
									class="col-md-4 mb-5 md-mb-0 project-content wow fadeInLeft">
									<h2 class="project-title link">${noticia.nombre }</h2>

									<h4 class="project-subtitle">${noticia.subtitulo }</h4>
									<br /> <a href="/noticias/detalle/${noticia.idNoticia}"
										class="project-link">¡Cuéntame más!</a>
								</div>
								<div class="col-md-7 mb-5">
									<div class="project-thumbnail-wrapper wow fadeInRight">
										<a href="/noticias/detalle/${noticia.idNoticia}"><img
											src="${noticia.img}" alt="Imagen de ${noticia.nombre }"></a>
									</div>
								</div>
							</div>
						</article>

					</c:if>

				</c:forEach>
				<c:if test="${categoria == 'Noticias' }">

					<nav class="oleez-pagination wow fadeInUp">
						<a href="#!" class="active">01</a> <a href="#!">02</a> <a
							href="#!">03</a> <a href="#!" class="next">&rarr;</a>
					</nav>
				</c:if>
				<br /> <br />

			</div>


		</main>

	</c:if>

	<%@ include file="../common/footer.jsp"%>

</body>

</html>