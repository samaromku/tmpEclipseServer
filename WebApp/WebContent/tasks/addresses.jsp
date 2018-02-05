 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../tmp/header.jsp"></jsp:include>
	
	<h1>Список всех адресов</h1>
	<form action="addresses" method="post" >
		<input type="submit" value="Получить координаты адресов"/>
	</form>
	<form action="getContacts" method="post" >
		<input type="submit" value="Получить список контактов"/>
	</form>
	<table class="table-bordered">
		<tr>
			<td>Адрес</td>
			<td>Абонент</td>
			<td>Должность</td>
			<td>Имя</td>
			<td>Телефон</td>
			<td>Email</td>
			<td>Квартира</td>
		</tr>
		<c:forEach items="${contacts}" var="contact">
			<tr>
				<td>${contact.getAddress()}</td>
				<td>${contact.getOrgName()}</td>
				<td>${contact.getPost()}</td>
				<td><button class="task_btn btn-modal" value="${contact.getId()}">${contact.getName()}</button></td>
				<td>${contact.getPhone()}</td>
				<td>${contact.getEmail()}</td>
				<td>${contact.getApartments()}</td>
			</tr>
		</c:forEach>
	</table>
	
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <div class="modal-dialog not_big">
     <a title="Редактировать контакт" class="edit_btn" id="edit_contact">
          <span class="glyphicon glyphicon-edit modal_btns"></span>
    </a>
    <a title="Удалить контакт" class="delete_btn" id="delete_contact">
          <span class="glyphicon glyphicon-remove modal_btns"></span>
    </a>
    <a title="Редактировать адрес" id="edit_address">
          <span class="glyphicon glyphicon-edit modal_btns"></span>
    </a>
    <a title="Удалить адрес" id="remove_address">
          <span class="glyphicon glyphicon-remove modal_btns"></span>
    </a>
<div class="modal fade" id="my-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">Удаление контакта</div>
            <div class="modal-body"></div>
            <div class="modal-footer">
                <a id="bt-modal-cancel" href="#" class="btn btn-default" data-dismiss="modal">Отмена</a> 
                <a id="bt-modal-confirm" class="btn btn-danger btn-ok" data-dismiss="modal">Да</a>

			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="distrib" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">Удаление адреса</div>
            <div class="modal-body"></div>
            <div class="modal-footer">
                <a id="bt-modal-cancel1" href="#" class="btn btn-default" data-dismiss="modal">Отмена</a> 
                <a id="bt-modal-confirm1" class="btn btn-danger btn-ok" data-dismiss="modal">Да</a>

			</div>
		</div>
	</div>
</div>

   <div class="modal-content">

    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      <div class="modal-header">
		<div class="oneTask">
			<p>Id контакта: <span id = "contact_id"></span></p>
		   	<p>Адрес: <span id = "address"></span></p>
		   	<p>Абонент: <span id = "org_name"></span></p>
		   	<p>Должность: <span id = "post"></span></p>
		   	<p>ФИО: <span id = "fio"></span></p>
		    <p>Телефон: <span id = "phone"></span></p>
		    <p>Email: <span id = "email"></span></p>
		    <p>Квартира: <span id = "apartments"></span></p>
		</div> 
      </div>
     </div>
 </div>
</div>

<script type="text/javascript">

	//push contacts in javascript
	var contacts = Array();
	<c:forEach items="${contacts}" var="contact" varStatus="loop">
		contacts.push({
			id:'${contact.getId()}',
			address:'${contact.getAddress()}',
			org_name:'${contact.getOrgName()}',
			post:'${contact.getPost()}',
			fio:'${contact.getName()}',
			phone:'${contact.getPhone()}',
			email:'${contact.getEmail()}',
			apartments:'${contact.getApartments()}',
			addressId:'${contact.getAddressId()}'
		});
	</c:forEach>
</script>
	
<script type="text/javascript" src="../js/myjs/modalContact.js"></script>
<jsp:include page="../tmp/footer.jsp"></jsp:include>