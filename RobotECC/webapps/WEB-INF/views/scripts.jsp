<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix ="c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix ="spring" uri = "http://www.springframework.org/tags" %>
 <table class="table-dark table-striped tablescripts">
 <thead>
 <tr>
 <th colspan="2">Script</th>
 </tr>
 </thead>
 <tbody>
 
 <c:forEach items="${listFile}" var="map">
 <tr>
 <td>${map}</td>
 <td><button class="btn btn-success buttonExecScript">Ejecutar</button></td>
 </tr>
 </c:forEach>
 </tbody>
</table>

<div id="ejecutando"></div>
