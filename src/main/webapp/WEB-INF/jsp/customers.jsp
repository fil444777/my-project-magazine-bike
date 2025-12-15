><%--
  Created by IntelliJ IDEA.
  User: artem
  Date: 11.12.2025
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Покупатели</title>
</head>
<body>
<h1> Список покупателей: </h1>
<c:if test="${not empty requestScope.customers}">
    <c:forEach var="customers" items="${requestScope.customers}">
        <li>
            <a href="${pageContext.request.contextPath}/motorcycles?customersId=${customers.getId()}">${customers.toString()}</a>
        </li>
    </c:forEach>
</c:if>
</body>
</html>