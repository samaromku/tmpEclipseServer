 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../tmp/header.jsp"></jsp:include>

<h1>Создать пользователя</h1>
<form class="editForm" action="createUser" method="post">
	<p>Логин<input type="text" name="login" required></p>
	<p>Пароль<input type="text" name="pwd" required></p>
	<p>Email<input type="text" name="email" required></p>
	<p>Телефон<input type="text" name="phone" required></p>
	<p>Права
	<select name="role">
		<c:forEach items="${userRoles}" var="userRole">
			<option value="${userRole}">${userRole}</option>
		</c:forEach>
	</select></p>
	<p>ФИО<input type="text" name="fio" required></p>
	<input type="submit" value="Создать пользователя">
</form>	


<jsp:include page="../tmp/footer.jsp"></jsp:include>