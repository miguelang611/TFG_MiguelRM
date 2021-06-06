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

	<c:if test="${mensajeError != null}">

		<main class="blog-post-single">
			<div class="container">
				<h1 class=" text-warning text-center page-title">${mensajeError}</h1>
				<div class="row">
					<div class="col-md-8 blog-post-wrapper">
						<div class="post-navigation wow fadeInUp">
							<a class="btn prev-post" href="/noticias/">Volver</a>
						</div>
					</div>
				</div>
			</div>
		</main>
	</c:if>


	<c:if test="${miListaNoticias.size() != 0}">

		<main class="shop-page">
			<div class="container">

				<div class="page-header wow fadeInUp">
					<h1 class="page-title">Noticias ${categoria}</h1>
					<p class="result-count">Mostrando ${miListaNoticias.size()}
						noticias</p>

				</div>
				<c:if test="${categoria == 'activos' }">
					<div class="blog-post-single">

						<a class="btn" href="/noticias/destacadas">Destacadas</a>

						<c:forEach items="${listaCategorias}" var="miCategoria"
							varStatus="varEstado">
							<a class="btn" href="/noticias/categoria/${miCategoria.idCategoria}">${miCategoria.nombre}</a>
						</c:forEach>

					</div>
					<br/>
					<br/>
				</c:if>
				<c:if test="${categoria != 'activos' }">
					<div class="row">
						<div class="col-md-8 blog-post-wrapper">
							<div class="blog-post-single wow fadeInUp">
								<a class="btn prev-post" href="/noticias/">Volver</a>
							</div>
						</div>
					</div>
										<br/>
					<br/>
				</c:if>
				<div class="row">
					<c:forEach var="noticia" items="${miListaNoticias}">
						<c:if test="${noticia.destacada.equals('s')}">
							<article class="col-md-4 product-card wow fadeInUp">
								<a href="/noticias/detalle/${noticia.idNoticia}">
									<div class="product-thumbnail-wrapper">
										<img src="" alt="product"
											class="product-thumbnail">
									</div>
								</a>

								<h5 class="product-title">${noticia.nombre}
								<c:if test="${noticia.destacada.equals('s') && categoria != 'destacadas'}">
								 ¡Destacada!
								</c:if>
								</h5>

								<form class="btn-wrapper">
									<button class="btn btn-add-to-cart" type="submit"
										formaction="/noticias/detalle/${noticia.idNoticia}">
										Detalle</button>
								</form>
							</article>
						</c:if>
					</c:forEach>
					<c:forEach var="noticia" items="${miListaNoticias}">
						<c:if test="${noticia.destacada.equals('')}">
<article class="col-md-4 product-card wow fadeInUp">
								<a href="/noticias/detalle/${noticia.idNoticia}">
									<div class="product-thumbnail-wrapper">
										<img src="" alt="product"
											class="product-thumbnail">
									</div>
								</a>

								<h5 class="product-title">${noticia.nombre}
								<c:if test="${noticia.destacada.equals('s') && categoria != 'destacadas'}">
								 ¡Destacada!
								</c:if>
								</h5>

								<form class="btn-wrapper">
									<button class="btn btn-add-to-cart" type="submit"
										formaction="/noticias/detalle/${noticia.idNoticia}">
										Detalle</button>
								</form>
							</article>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</main>
	</c:if>


	<%@ include file="../common/footer.jsp"%>

</body>

</html>