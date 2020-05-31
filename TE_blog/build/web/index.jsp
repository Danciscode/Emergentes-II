<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.emergentes.modelo.Blog"%>
<%
    if (session.getAttribute("logueado") != "ok") {
        response.sendRedirect("login.jsp");
    }
    List<Blog> lista = (List<Blog>) request.getAttribute("lista");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><center>BLOG DE MUSICA</center></h1>
        <p>
            <a href="LoginController?action=logout">Salir</a>
        <p></p>
            <a href="MainController?op=nuevo">Nueva Entrada</a>
        </p>

    <c:forEach var="item" items="${lista}">
        <table border="0" width="100%">   
            <tr><td colspan="2">${item.fecha}</td></tr>
            <tr><td colspan="2"><h2>${item.titulo}</h2></td></tr>
            <tr><td colspan="2"><font size="5">${item.contenido}</font></td></tr>
            <tr><td></td><td width="150"><a href="MainController?op=editar&id=${item.id}">Editar Entrada</a></td></tr>
            <tr><td>Autor: ${item.usuario}</td><td width="150"><a href="MainController?op=eliminar&id=${item.id}" onclick="return(confirm('Esta seguro?'))">Eliminar Entrada</a></td></tr>
        </table><p></p>

    </c:forEach>


</body>
</html>
