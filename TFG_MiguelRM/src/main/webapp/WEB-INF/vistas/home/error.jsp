<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">

<head>
<%@ include file="../common/head.jsp"%>
<title>Error ${codigo}</title>
</head>

<body>
	<%@ include file="../common/scripts.jsp"%>
	<%@ include file="../common/nav.jsp"%>

	   <main class="error-page">
        <div class="container">
            <h1 class="error-code wow fadeInUp">${codigo}</h1>
            <p class="error-description wow fadeInUp">${mensaje}</p>
        </div>
    </main>


	<%@ include file="../common/footer.jsp"%>

</body>

</html>