 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../tmp/header.jsp"></jsp:include>

<form class="editForm" action="editAddress" method="post">
	<p>Адрес:<input type="text" name="address" value="${address.getAddress()}"></p>
	<p>Абонент:<input type="text" name="name" value="${address.getName()}"></p>
	<p><input type="submit" value="Изменить"></p>
</form>

<jsp:include page="../tmp/footer.jsp"></jsp:include>