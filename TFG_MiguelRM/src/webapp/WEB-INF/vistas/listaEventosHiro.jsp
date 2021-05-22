<!-- Todos los jsp están realizados usando JSTL, Bootstrap (con jQuery incluido)

Cuentan con un nav común que sólo se despliega cuando no ha habido errores.

(Si hubiera un error de conexión a BBDD, el eventos/tipo no se mostraría correctamente)

 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Hiro Agency Landing Page</title>
    <link rel="stylesheet" href="hiro/hiro/assets/vendors/animate.css/animate.min.css">
    <link rel="stylesheet" href="hiro/assets/css/style.css">
    <script src="hiro/assets/vendors/jquery/jquery.min.js"></script>
    <script src="hiro/assets/js/loader.js"></script>
</head>

<body>
    <div class="hiro-loader"></div>
    <header class="hiro-header">
        <nav>
            <div class="container">
                <div class="hiro-nav">
                    <a href="index.html" class="nav-brand">
                        <img src="hiro/assets/images/logo.svg" alt="Hiro" class="logo">
                        <img src="hiro/assets/images/logo_white.svg" alt="Hiro" class="logo-white">
                    </a>
                    <button class="hiro-nav-popup-toggle">
                        <svg width="20px" height="18px" viewBox="0 0 20 18" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                            <g id="Hiro" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                <g id="hiro-agency" transform="translate(-964.000000, -37.000000)" fill="#FFFFFF">
                                    <g id="Menu" transform="translate(964.000000, 37.000000)">
                                        <g id="menu">
                                            <rect id="Rectangle" fill-rule="nonzero" x="-8.05281767e-14" y="7.50795322" width="19.9609942" height="2.4951462"></rect>
                                            <rect id="Rectangle" fill-rule="nonzero" x="-8.05281767e-14" y="14.9964327" width="19.9609942" height="2.4951462"></rect>
                                            <rect id="Rectangle" fill-rule="nonzero" x="-8.05281767e-14" y="0.0194736842" width="19.9609942" height="2.4951462"></rect>
                                        </g>
                                    </g>
                                </g>
                            </g>
                        </svg>
                    </button>
                </div>
            </div>
        </nav>
    </header>
    <div class="hiro-nav-popup-modal">
        <div class="hiro-nav-popup-modal-content">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 menu-wrapper">
                        <ul class="nav hiro-main-nav">
                            <li class="nav-item">
                                <a href="index.html" class="nav-link">Home</a>
                            </li>
                            <li class="nav-item">
                                <a href="#about" class="nav-link">About Us</a>
                            </li>
                            <li class="nav-item">
                                <a href="#services" class="nav-link">Services</a>
                            </li>
                            <li class="nav-item">
                                <a href="#works" class="nav-link">Works</a>
                            </li>
                            <li class="nav-item">
                                <a href="contact.html" class="nav-link">Contact</a>
                            </li>
                        </ul>
                    </div>
                    <div class="col-md-6 blog-posts text-white">
                        <h5 class="popup-blog-headng">Blog</h5>
                        <div class="media mb-4">
                            <img src="hiro/assets/images/Blog_small2.jpg" alt="blog" width="86px" class="img-fluid mr-4">
                            <div class="media-body align-self-end">
                                <h5 class="popup-blog-post-title">Ambitious <br> designs</h5>
                            </div>
                        </div>
                        <div class="media mb-4">
                            <img src="hiro/assets/images/Blog_small1.jpg" alt="blog" width="86px" class="img-fluid mr-4">
                            <div class="media-body align-self-end">
                                <h5 class="popup-blog-post-title">The Collection <br> Cover Archive</h5>
                            </div>
                        </div>
                        <p><a href="blog.html" class="text-white link-hover-fx text-decoration-none" class="font-weight-medium">View more</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <main class="blog-page-wrapper">
<div>
			<h1 class="text-primary text-center">Lista de eventos
				${tipo}</h1>
			<c:if test="${mensaje != null && mensaje.contains('Error')}">
					<h4 class=" text-warning text-center">${requestScope.mensaje}</h4>
			</c:if>
			<c:if test="${mensaje != null && !mensaje.contains('Error')}">
					<h4 class=" text-success text-center">${requestScope.mensaje}</h4>
			</c:if>
</div>
		<c:if test="${miListaEventos.size() != 0}">

        <div class="container">
        
            <div class="row">
                <div class="col-lg-8 blog-post-archive">
                <c:forEach var="evento" items="${miListaEventos}">
                    <article class="media blog-post wow fadeInUp">
                        <img src="hiro/assets/images/blog_1.jpg" alt="blog post" class="blog-post-thumbnail">
                        <div class="media-body">
                            <h5 class="blog-post-title">${evento.nombre}</h5>
                            <p class="blog-post-excerpt">${evento.descripcion}</p>
                            <a href="/eventos/editar/${evento.idEvento}${origen}" class="blog-post-link">Read more</a>
                        </div>
                    </article>
                </c:forEach>
                </div>
                
            </div>
        </div>
        </c:if>
    </main>
    
    
    <!-- NAV INFERIOR -->
    <footer class="hiro-footer wow fadeInUp">
        <div class="container d-flex flex-wrap justify-content-lg-between align-items-center">
            <p class="mb-0 footer-text">© <a href="https://www.bootstrapdash.com" target="_blank" rel="noopener noreferrer" class="text-reset">BootstrapDash</a> 2020. All rights reserved.</p>
            <nav class="footer-nav nav">
                <a href="#!" class="nav-link">Terms and Conditions</a>
                <a href="#!" class="nav-link">Privacy Policy</a>
                <a href="#!" class="nav-link">Cookie Policy</a>
            </nav>
        </div>
    </footer>

    <script src="hiro/assets/vendors/wowjs/wow.min.js"></script>
    <script src="hiro/assets/vendors/popper.js/popper.min.js"></script>
    <script src="hiro/assets/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="hiro/assets/js/main.js"></script>
    <script>
        new WOW().init();
    </script>
</body>

</html>