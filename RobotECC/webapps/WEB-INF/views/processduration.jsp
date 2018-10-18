<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix ="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:if test="${error}">
<h2>Se ha producido un error!</h2>
<p>${errortxt}</p>
</c:if>
<c:if test="${not error}">

<table class="table-dark table-striped tablescripts">
 <thead>
 <tr>
 <th >Test</th>
 <th >Duracion(ms)</th>
 </tr>
 </thead>
 <tbody>
 
 <c:forEach items="${listDur}" var="results">
 <tr>
 <td>${results[0]}</td>
 <td>${results[1]}</td>
 </tr>

 </c:forEach>
 </tbody>
</table>


</c:if>

</body>
</html>