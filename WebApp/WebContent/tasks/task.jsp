 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../tmp/header.jsp"></jsp:include>

<div class="oneTask">
	<c:set value="${task}" var="task"/>
	<p>Id заявки: <span>${task.getId()}</span></p>
   	<p>Дата создания: <span>${task.getCreated()}</span></p>
    <p>Важность: <span>${task.getImportance()}</span></p>
    <p>Тело заявки: <span>${task.getBody()}</span></p>
    <p>Статус: <span>${task.getStatus()}</span></p>
    <p>Тип: <span>${task.getType()}</span></p>
    <p>Крайнее выполнения: <span>${task.getDoneTime()}</span></p>
    <p>Адрес: <span>${task.getAddress()}</span></p>
    <p>Имя оргиназации: <span>${task.getOrgName()}</span></p>
    <p>Исполнитель: <span>${userName}</span></p>
	<a href="/WebApp/tasks/editTask?id=${task.getId()}">Редактировать заявку</a>
	
	<h1>Комментарии</h1>
<table id="com_table" class="comment_table">
	<thead>
	<tr>
		<th>id</th>
		<th>Комментарий</th>
		<th>userId</th>
		<th>Дата</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${comments}" var="comment">
		<tr>
			<td>${comment.getId()}</td>
			<td>${comment.getBody()}</td>
			<td>${comment.getUserId()}</td>
			<td>${comment.getTs()}</td>
		</tr>
	</c:forEach>
</tbody>
</table>

</div>

<div id="contacts">
	
	<c:forEach items="${contacts}" var="contact">
		<div class="one_contact">
			<p><b>Имя:</b> <span id="contact_name">${contact.getName()}</span></p>
			<p><b>Должность:</b><span id="contact_post"> ${contact.getPost()}</span></p>
			<p><b>Email:</b>
			<span id="contact_emails">
				<c:forEach items="${contact.getEmails()}" var="email">
					 ${email},
				 </c:forEach>
			 </span>
			 </p>
			<p><b>Телефон:</b>
			<span id="contact_phones">
				<c:forEach items="${contact.getPhones()}" var="phone">
					${phone},
				</c:forEach>
				</span>
			 </p>
			<p><b>Квартира:</b> <span id="contact_apartments">${contact.getApartments()}</span></p>
		</div>	
	</c:forEach>
	
</div>
<jsp:include page="../tmp/footer.jsp"></jsp:include>