<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">

<head>
<%@ include file="../common/head.jsp"%>
<title>Login</title>
</head>

<body>
	<%@ include file="../common/scripts.jsp"%>
	<%@ include file="../common/nav.jsp"%>

    <main class="contact-page">
        <div class="container">
            <h1 class="oleez-page-title text-center wow fadeInUp">Login</h1>
            <c:if test="${mensaje != null }">
            <c:if test="${mensaje.contains('Error') }">
            <h3 class=" text-warning text-center page-title">${mensaje}</h3>
            </c:if>
            <c:if test="${!mensaje.contains('Error') }">
            <h3 class=" text-success text-center page-title">${mensaje}</h3>
            </c:if>
			<br/>
            </c:if>

            <br/>
            <div class="row">
                <div class="col-md-6 mb-5 mb-md-0 pr-lg-5 wow fadeInLeft">
                    <div class="embed-responsive embed-responsive-1by1">
                            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d10759.495900110083!2d-122.34410726082857!3d47.609140133437705!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x54906ab679235d7d%3A0xae763a82ab1fed6c!2sCentral%20Business%20District%2C%20Seattle%2C%20WA%2C%20USA!5e0!3m2!1sen!2sin!4v1587035152547!5m2!1sen!2sin" width="600" height="450" frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
                    </div>
                </div>
                <div class="col-md-6 pl-lg-5 wow fadeInRight">
                    <form action="/login" method="POST" class="oleez-contact-form">
                        <div class="form-group">
                            <input type="email" class="oleez-input" id="username" name="username" required>
                            <label for="username">Usuario</label>
                        </div>
                        <div class="form-group">
                            <input type="password" class="oleez-input" id="password" name="password" required>
                            <label for="email">Contraseña</label>
                        </div>
                        <button type="submit" class="btn btn-submit">Entrar</button>
                        <div class="form-group">
                        <br/>
                        <p>¿Aún no registrado? ¡Sin problema!</p>
                        <a href="/registro"><input type="button" class="btn btn-submit" value="Regístrate"></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>


	<%@ include file="../common/footer.jsp"%>

</body>

</html>