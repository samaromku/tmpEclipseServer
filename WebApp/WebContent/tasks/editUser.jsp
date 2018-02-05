 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../tmp/header.jsp"></jsp:include>

	<form class="editForm" action="editUser" method="post">
	<c:set value="${user}" var = "user"></c:set>
		<p>Id: <input disabled="disabled" value="${user.getId()}"/></p>
		<p>Логин: <input name="login" value="${user.getLogin()}"/></p>
		<p>Пароль: <input name="pwd"/></p>
		<p>Права: переделать в селект <input name="role" value="${user.getRole()}"/></p>
		<p>Телефон: <input name="phone" value="${user.getTelephone()}"/></p>
		<p>E-main<input name="email" value="${user.getEmail()}"/></p>
		<p>Ф.И.О.<textarea class="task_body" name="fio" rows="2" required>${user.getFIO()}</textarea></p>
		<input type="submit">
	</form>

<jsp:include page="../tmp/footer.jsp"></jsp:include>