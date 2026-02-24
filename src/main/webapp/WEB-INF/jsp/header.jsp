<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div>
  <c:if test="${not empty sessionScope.user}">
    <form action="${pageContext.request.contextPath}/logout" method="post">
      <button type="submit">Logout</button>
    </form>
  </c:if>
</div>