<%-- 
    Document   : CargaDatos
    Created on : 31-ago-2021, 23:47:33
    Author     : emili
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page import="InicioJava.VerificarDatos"%>
<%@page import="InicioJava.CargaArchivo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cargar Datos</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">


    </head>
    <body>
        <% if (request.getAttribute("Sucess") == null) {%>

        <%--Se verifican si ya hay un llenado en la base de datos, si hay, redirigir a login --%>
        <%
            VerificarDatos verificar = new VerificarDatos();
            int resultAux = verificar.comprobarDatos();
            if (resultAux == 1) {
                response.sendRedirect(request.getContextPath() + "/Inicio/Login.jsp");
            }
        %>


        <div class="container">
            <div class="row" style="text-align: center">
                <div class="col-12">
                    <header class="mt-5 mb-5">
                        <h1>Carga de Archivo</h1>
                    </header>
                    <div class="container">
                        <FORM class="col-12 caja2" METHOD="POST" ACTION="../CargaArchivo" enctype="multipart/form-data">  
                            <div class="col-12">
                                <input class="form-control" type="file"  name="ARCHIVO" id="ARCHIVO" placeholder="Elija el Archivo" required>
                            </div>
                            <div class="mt-5">
                                <input class="btn btn-primary" type="submit" value="Cargar">
                                <input class="btn btn-secundary" type="reset" value="Eliminar Datos">
                            </div>
                        </FORM>
                    </div>
                </div>
            </div>
        </div>

        <% } else {%>

        <div class="container" style="padding-top: 150px" >
            <h1 class="mb-5" style="text-align: center">
                Carga de Archivo
            </h1>
            <div class="fila-respuesta row justify-content-around">
                <div class="col col-5 align-self-center">
                    <% if ((boolean) request.getAttribute("Sucess")) {%>
                    <div class="alert alert-success" style="text-align: center">
                        Carga de Archivo Realizada con Exito
                    </div>
                    <div class="row justify-content-center">
                        <a href="${pageContext.request.contextPath}/Inicio/Login.jsp" class="btn btn-primary">
                            Iniciar Sesion
                        </a>
                    </div>

                    <% } else {%>
                    <div class="alert alert-danger " style="text-align: center">
                        Fallo en la Carga de Archivo
                    </div>
                    <div class="row justify-content-center">
                        <a href="index.jsp" class="btn btn-primary">
                            Volver a Intentar
                        </a>
                    </div>

                    <% }%>
                </div>
                <div class="col errores col-5" style="background: #f3f3f3;height: 600px">
                    <div class="row justify-content-first mt-3">
                        <div class="col-5 text-center">
                            <label for="inputFiltro">Busqueda por Filtro:</label>
                        </div>
                        <div class="col-5">
                            <input class="form-control" id="inputFiltro" type="text" placeholder="Filtro">
                        </div>
                    </div>
                    <div style="height: 600px; width: 100%; overflow-y: scroll;">
                        <table class="table table-striped table-bordered mt-5" style="text-align: center">
                            <thead class="table-dark">
                                <tr>
                                    <th class="th-sm">Linea de Error</th>
                                    <th class="th-sm">Tipo de Error</th>
                                    <th class="th-sm">Mensaje de Error</th>
                                </tr>
                            </thead>
                            <tbody id="tablaFiltro">
                                <c:forEach items="${errores}" var="error">
                                    <tr>
                                        <td>${error.numeroLineaError}</td>
                                        <td>${error.tipoError}</td>
                                        <td>${error.mensajeError}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th>Linea de Error</th>
                                    <th>Tipo de Error</th>
                                    <th>Mensaje de Error</th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <% }%> 

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    </body>
</html>
