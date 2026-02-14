<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Клиенты</title>
</head>
<body>
<%@include file="header.jsp"%>
<h1>Список клиентов:</h1>

<c:if test="${not empty requestScope.customers}">
    <ul>
        <c:forEach var="customer" items="${requestScope.customers}">
            <li>
                <a href="${pageContext.request.contextPath}/motorcycles?customerId=${customer.id}">
                        ${customer.personalInfo.name} (${customer.email})
                </a>
            </li>
        </c:forEach>
    </ul>
</c:if>

</body>
</html>