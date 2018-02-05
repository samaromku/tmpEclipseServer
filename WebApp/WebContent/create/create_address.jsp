 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../tmp/header.jsp"></jsp:include>

	<form class="editForm" action="createAddress" method="post">
		<p>Имя организации:<input type="text" name="org_name"></p>
		<p>Адрес:<input type="text" name="address"></p>
		<p><input type="submit"></p>
	</form>


<jsp:include page="../tmp/footer.jsp"></jsp:include>