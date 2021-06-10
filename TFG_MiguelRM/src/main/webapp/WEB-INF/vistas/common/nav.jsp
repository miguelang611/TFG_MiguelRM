<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<div class="oleez-loader"></div>
<header class="oleez-header">
	<nav class="navbar navbar-expand-lg navbar-light">
		<a class="navbar-brand" href="/"><img
			src="/resources/media/Logo2.png" alt="MRM Performance"></a>
		<ul class="nav nav-actions d-lg-none ml-auto">
							<sec:authorize access="isAnonymous()">
				<li class="nav-item"><a class="nav-link" href="/login">Login</a>
				</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">

										<li class="nav-item"><a class="nav-link" href="/cliente/reservas/todas">Reservas</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="/logout">Logout</a>
				</li>
												<li class="nav-item"><a class="nav-link" href="#">¡¡Hola ${usuario.nombre}!!</a>
				</li>
				</sec:authorize>
		</ul>
		<button class="navbar-toggler d-lg-none" type="button"
			data-toggle="collapse" data-target="#oleezMainNav"
			aria-controls="oleezMainNav" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="oleezMainNav">
			<ul class="navbar-nav mx-auto mt-2 mt-lg-0">
				<li class="nav-item"><a class="nav-link" href="/">Home</a>
				</li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#!" id="pagesDropdown"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Eventos</a>
					<div class="dropdown-menu" aria-labelledby="pagesDropdown">
							<a class="dropdown-item" href="/eventos/">Todos</a>
							<a class="dropdown-item" href="/eventos/destacados">Destacados</a>

						<c:forEach items="${listaTipos}" var="miTipo" varStatus="varEstado">
							<a class="dropdown-item" href="/eventos/tipo/${miTipo.idTipo}">${miTipo.nombre}</a>
						</c:forEach>
					</div></li>
					<li class="nav-item"><a class="nav-link" href="#">Coches Nuevos</a>
					</li>
					<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#!" id="pagesDropdown"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Noticias</a>
					<div class="dropdown-menu" aria-labelledby="pagesDropdown">
							<a class="dropdown-item" href="/noticias/">Todos</a>
							<a class="dropdown-item" href="/noticias/destacadas">Destacadas</a>

						<c:forEach items="${listaCategorias}" var="miCategoria" varStatus="varEstado">
							<a class="dropdown-item" href="/noticias/categoria/${miCategoria.idCategoria}">${miCategoria.nombre}</a>
						</c:forEach>
					</div></li>
					<li class="nav-item"><a class="nav-link" href="#">Contacto</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#">Conócenos</a>
					</li>
					<!--  
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#!" id="portfolioDropdown"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Portfolio</a>
					<div class="dropdown-menu" aria-labelledby="portfolioDropdown">
						<a class="dropdown-item" href="portfolio-list.html">Portfolio
							list</a> <a class="dropdown-item" href="portfolio.html">Portfolio
							grid</a> <a class="dropdown-item" href="portfolio-masonry.html">Portfolio
							masonry</a>
					</div></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#!" id="blogDropdown"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Blog</a>
					<div class="dropdown-menu" aria-labelledby="blogDropdown">
						<a class="dropdown-item" href="blog-standard.html">Blog
							Standard</a> <a class="dropdown-item" href="blog-grid.html">Blog
							grid</a> <a class="dropdown-item" href="blog-single.html">Blog
							Post</a>
					</div></li>-->
			</ul>
			
			<ul class="navbar-nav d-none d-lg-flex">
				<sec:authorize access="isAnonymous()">
				<li class="nav-item"><a class="nav-link" href="/login">Login</a>
				</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">

										<li class="nav-item"><a class="nav-link" href="/cliente/reservas/todas">Reservas</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="/logout">Logout</a>
				</li>
												<li class="nav-item"><a class="nav-link" href="#">¡¡Hola ${usuario.nombre}!!</a>
				</li>
				</sec:authorize>
			</ul>
		
		</div>
	</nav>
	
		<sec:authorize access="hasAnyAuthority('admin','organizador')">
			<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-light" style="background-color: grey">

		<div class="collapse navbar-collapse">
							<div class="nav-item">
			PANEL DE GESTIÓN
			</div>
			<ul class="navbar-nav mx-auto mt-2 mt-lg-0">
								<li class="nav-item"><a class="nav-link" href="/gestion/eventos/todos">Eventos</a>
				</li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#!" id="pagesDropdown"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Eventos / estado</a>
					<div class="dropdown-menu" aria-labelledby="pagesDropdown">
							<a class="dropdown-item" href="/gestion/eventos/activos">Activos</a>
							<a class="dropdown-item" href="/gestion/eventos/cancelados">Cancelados</a>

					</div></li>
					
					<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#!" id="pagesDropdown"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Eventos / destacado</a>
					<div class="dropdown-menu" aria-labelledby="pagesDropdown">
							<a class="dropdown-item" href="/gestion/eventos/destacados">Destacados</a>
							<a class="dropdown-item" href="/gestion/eventos/nodestacados">No Destacados</a>
					</div></li>
					
					<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#!" id="pagesDropdown"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Eventos / tipo</a>
					<div class="dropdown-menu" aria-labelledby="pagesDropdown">
						<c:forEach items="${listaTiposFull}" var="miTipo" varStatus="varEstado">
							<a class="dropdown-item" href="/gestion/eventos/${miTipo.idTipo}">${miTipo.nombre}</a>
						</c:forEach>
					</div></li>
				<li class="nav-item"><a class="nav-link" href="/gestion/tipos">Tipos</a>
				</li>
			<li class="nav-item"><a class="nav-link" href="/gestion/reservas/todas">Reservas</a>
				</li>
					
			</ul>
					<div class="nav-item">
			ROL: ${usuario.perfil.nombre}
			</div>

		</div>

	</nav>
	
			</sec:authorize>
	
	<sec:authorize access="hasAnyAuthority('admin','redactor')">
			<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-light" style="background-color: cyan;">

		<div class="collapse navbar-collapse">
							<div class="nav-item">
			PANEL DE GESTIÓN
			</div>
			<ul class="navbar-nav mx-auto mt-2 mt-lg-0">
								<li class="nav-item"><a class="nav-link" href="/gestion/noticias/todas">Noticias</a>
				</li>
					
					<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#!" id="pagesDropdown"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Noticias / destacada</a>
					<div class="dropdown-menu" aria-labelledby="pagesDropdown">
							<a class="dropdown-item" href="/gestion/noticias/destacadas">Destacadas</a>
							<a class="dropdown-item" href="/gestion/noticias/nodestacadas">No Destacadas</a>
					</div></li>
					
					<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#!" id="pagesDropdown"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Noticias / categoria</a>
					<div class="dropdown-menu" aria-labelledby="pagesDropdown">
						<c:forEach items="${listaCategorias}" var="miCategoria" varStatus="varEstado">
							<a class="dropdown-item" href="/gestion/noticias/${miCategoria.idCategoria}">${miCategoria.nombre}</a>
						</c:forEach>
					</div></li>
				<li class="nav-item"><a class="nav-link" href="/gestion/categorias">Categorias</a>
				</li>
					
			</ul>
					<div class="nav-item">
			
			ROL: ${usuario.perfil.nombre}

			</div>

		</div>

	</nav>
	
	
	
		
		</sec:authorize>
		

</header>