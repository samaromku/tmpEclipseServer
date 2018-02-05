 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	var error = '${error}';
	if(0 != error.length){
		alert('Ошибка: '+ error);	
	}
</script>

<jsp:include page="../tmp/header.jsp"></jsp:include>
	<h1>Список пользователей</h1>
<table class="table-bordered task_table">
	<thead>
  <tr>
    <th>Логин</th>
    <th>E-mail</th>
    <th>Телефон</th>
    <th>Права</th>
    <th>ФИО</th>
  </tr>
  </thead>
 <tbody>
  	<c:forEach items="${users}" var = "user">
		<tr>
    		<td><button value = "${user.getId()}"  class="task_btn btn-modal">${user.getLogin()}</button></td>
    		<td>${user.getEmail()}</td>
    		<td>${user.getTelephone()}</td>
    		<td>${user.getRole()}</td>
    		<td>${user.getFIO()}</td>
   		
  		</tr>
  	</c:forEach>
 
 </tbody>
</table>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <div class="modal-dialog not_big">
      <a title="Редактировать пользователя" class="edit_btn" id="edit_user">
          <span class="glyphicon glyphicon-edit modal_btns"></span>
    </a>
    <a title="Удалить пользователя" class="delete_btn" id="delete_user">
          <span class="glyphicon glyphicon-remove modal_btns"></span>
    </a>
 
 <div class="modal fade" id="my-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">Удаление заявки</div>
            <div class="modal-body"></div>
            <div class="modal-footer">
                <a id="bt-modal-cancel" href="#" class="btn btn-default" data-dismiss="modal">Отмена</a> 
                <a id="bt-modal-confirm" class="btn btn-danger btn-ok" data-dismiss="modal">Да</a>

</div>
</div>
</div>
</div>
<script type="text/javascript" src="../js/myjs/confirm.js"></script>
    <div class="modal-content">

    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      <div class="modal-header">
		<div class="oneTask">
	<p>Id: <span id = "user_id"></span></p>
   	<p>Логин: <span id = "login"></span></p>
   	<p>E-main: <span id = "email"></span></p>
   	<p>Телефон: <span id = "phone"></span></p>
   	<p>Права: <span id = "role"></span></p>
    <p>ФИО: <span id = "fio"></span></p>
    

		</div>      		
      </div>
     </div>
 </div>
</div>

<script type="text/javascript">
//usersArray
var users = Array();
<c:forEach items="${users}" var="user">
	users.push({
		userId:'${user.getId()}',
		login:'${user.getLogin()}',
		email:'${user.getEmail()}',
		phone:'${user.getTelephone()}',
		role:'${user.getRole()}',
		fio:'${user.getFIO()}'
	});
</c:forEach>


//modal
$(document).ready(function () {
    $('#myModal').bind('show', function () {
        //do stuf on show
    });
    
    $('.btn-modal').click(function() {
    	var userId = $(this).val();
    	$('#myModal').modal('show');
    	
    	for(var i = 0; i<users.length; i++){
    		if(users[i].userId==userId){
    			$('#user_id').text(users[i].userId);
    			$('#login').text(users[i].login);
    			$('#email').text(users[i].email);
    			$('#phone').text(users[i].phone);
    			$('#role').text(users[i].role);
    			$('#fio').text(users[i].fio);
    			
    			var edit_user = document.getElementById('edit_user');
    			var delete_user = document.getElementById('delete_user');

    			//var edit_url = document.createElement('a');
    			edit_user.setAttribute('href', "/WebApp/tasks/editUser?id="+users[i].userId);
    			//edit_user.textContent = 'Редактировать пользователя';

    			delete_user.setAttribute('href', "/WebApp/tasks/deleteUser?id="+users[i].userId);
    			//delete_user.textContent = 'Удалить пользователя';
    		}
    	}
    });
});


</script>

<jsp:include page="../tmp/footer.jsp"></jsp:include>