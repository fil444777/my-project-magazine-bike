<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Клиенты</title>
</head>
<body>
<%@include file="header.jsp"%>
<h1>Список клиентов:</h1>

<c:if test="${not empty requestScope.users}">
    <ul>
        <c:forEach var="user" items="${requestScope.users}">
            <li>
                <a href="${pageContext.request.contextPath}/motorcycles?userId=${user.id}">
                        ${user.personalInfo.name} (${user.email})
                </a>
            </li>
        </c:forEach>
    </ul>
</c:if>

</body>
</html>