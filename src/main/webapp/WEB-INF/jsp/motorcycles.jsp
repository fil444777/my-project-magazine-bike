<%--
  Created by IntelliJ IDEA.
  User: artem
  Date: 11.12.2025
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>\

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Мотоциклы</title>
</head>
<body>
<h1> Купленные мотоциклы: </h1>
<c:if test="${not empty requestScope.motorcycles}">
    <c:forEach var="motorcycles" items="${requestScope.motorcycles}">
        <li>${motorcycles.getModel()}</li>
    </c:forEach>
</c:if>
</body>
</html>