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
<title>Eventos ${tipo}</title>
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
							<a class="btn prev-post" href="/eventos/">Volver</a>
						</div>
					</div>
				</div>
			</div>
		</main>
	</c:if>


	<c:if test="${miListaEventos.size() != 0}">

		<main class="shop-page">
			<div class="container">

				<div class="page-header wow fadeInUp">
					<h1 class="page-title">Eventos ${tipo}</h1>
					<p class="result-count">Mostrando ${miListaEventos.size()}
						eventos</p>

				</div>
				<c:if test="${tipo == 'activos' }">
					<div class="blog-post-single">

						<a class="btn" href="/eventos/destacados">Destacados</a>

						<c:forEach items="${listaTipos}" var="miTipo"
							varStatus="varEstado">
							<a class="btn" href="/eventos/tipo/${miTipo.idTipo}">${miTipo.nombre}</a>
						</c:forEach>

					</div>
					<br/>
					<br/>
				</c:if>
				<c:if test="${tipo != 'activos' }">
					<div class="row">
						<div class="col-md-8 blog-post-wrapper">
							<div class="blog-post-single wow fadeInUp">
								<a class="btn prev-post" href="/eventos/">Volver</a>
							</div>
						</div>
					</div>
										<br/>
					<br/>
				</c:if>
				<div class="row">
					<c:forEach var="evento" items="${miListaEventos}">
						<c:if test="${evento.destacado.equals('s')}">
							<article class="col-md-4 product-card wow fadeInUp">
								<a href="/eventos/detalle/${evento.idEvento}">
									<div class="product-thumbnail-wrapper">
										<img src="${evento.img}" alt="product"
											class="product-thumbnail">
									</div>
								</a>

								<h5 class="product-title">${evento.nombre}
								<c:if test="${evento.destacado.equals('s') && tipo != 'destacados'}">
								 ¡Destacado!
								</c:if>
								</h5>
								<c:if test="${evento.destacado.equals('s')}">
																<p class="product-price">${evento.precio}€<del>${evento.precio +50}€</del>
								</p>
								</c:if>
								<c:if test="${evento.destacado.equals('')}">
																<p class="product-price">${evento.precio}€<del>${evento.precio +20}€</del>
								</p>
								</c:if>

								<form class="btn-wrapper">
									<button class="btn btn-add-to-cart" type="submit"
										formaction="/eventos/detalle/${evento.idEvento}">
										Detalle</button>
								</form>
							</article>
						</c:if>
					</c:forEach>
					<c:forEach var="evento" items="${miListaEventos}">
						<c:if test="${evento.destacado.equals('')}">
<article class="col-md-4 product-card wow fadeInUp">
								<a href="/eventos/detalle/${evento.idEvento}">
									<div class="product-thumbnail-wrapper">
										<img src="${evento.img}" alt="product"
											class="product-thumbnail">
									</div>
								</a>

								<h5 class="product-title">${evento.nombre}
								<c:if test="${evento.destacado.equals('s') && tipo != 'destacados'}">
								 ¡Destacado!
								</c:if>
								</h5>
								<c:if test="${evento.destacado.equals('s')}">
																<p class="product-price">${evento.precio}€<del>${evento.precio +50}€</del>
								</p>
								</c:if>
								<c:if test="${evento.destacado.equals('')}">
																<p class="product-price">${evento.precio}€<del>${evento.precio +20}€</del>
								</p>
								</c:if>

								<form class="btn-wrapper">
									<button class="btn btn-add-to-cart" type="submit"
										formaction="/eventos/detalle/${evento.idEvento}">
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