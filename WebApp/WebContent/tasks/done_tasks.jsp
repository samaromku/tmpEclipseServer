 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
 
<jsp:include page="../tmp/header.jsp"></jsp:include>

 <link rel="stylesheet" href="../js/tablesorter-master/css/theme.blue.css"/>
<script type="text/javascript" src = "../js/tablesorter-master/js/jquery.tablesorter.js"></script>
<script type="text/javascript" src = "../js/tablesorter-master/js/jquery.tablesorter.widgets.js"></script>

<p class="tasks_count">Количество выполненных заявок:${tasks.size()}</p>
	
	<h1 class="title">Выполненные заявки</h1>
	<input type="button" onclick="tableToExcel('testTable', 'W3C Example Table')" value="Export to Excel">

	
	<script type="text/javascript">
var tableToExcel = (function() {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
  return function(table, name) {
    if (!table.nodeType) table = document.getElementById('sort_table')
    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
    window.location.href = uri + base64(format(template, ctx))
  }
})()
</script>

<script type="text/javascript">
	var doneComments = Array();
	<c:forEach items="${doneComments}" var="doneComment">
		doneComments.push({
			id:'${doneComment.getId()}'
		});
	</c:forEach>
	console.log(doneComments.length);
</script>
	
 <table class="task_table table-bordered" id="sort_table">
<thead>
	<tr>
		<th class="col-ms-2">Адрес</th>
		<th class="col-ms-1">Важн</th>
		<th class="col-ms-5">Что сделать</th>
		<th style="min-width:90px;" data-date-format="ddmmyyyy">Выполнить до</th>
		<th class="col-ms-1">Тип</th>
		<th class="col-ms-1">Исполнитель</th>
		<th class="col-ms-1">Статус</th>
		<th class="col-ms-3">Абонент</th>
		<th style="min-width:90px;" data-date-format="ddmmyyyy">Дата создания</th>
		<th style="min-width:90px;" data-date-format="ddmmyyyy">Дата выполнения</th>
		
	</tr>
</thead>
	
<tbody>
	<c:forEach items="${tasks}" var="task" varStatus="loop">
        <tr data-value="${task.getStatus()}"  data-type="${task.getAddress()}" class="${task.getStatus()} ${task.getImportance()}">
        	<td>${task.getAddress()}</td>
        	<td>${fn:substring(task.getImportance(), 0, 4)}</td>
        	<td><button value = "${task.getId()}"  class="task_btn btn-modal">${task.getBody()}</button>	</td>
        	<td class="date_format_year">${task.getDoneTime()}</td>
        	<td>${task.getType()}</td>
        	<td>
        		<c:forEach items="${users}" var = "user">
        			<c:if test="${task.getUserId()==user.getId()}">${user.getLogin()}</c:if>
        		</c:forEach>
        	</td>
        	<td>${task.getStatus()}</td>
        	<td>${task.getOrgName()}</td>
        	<td class="date_format_year">${task.getCreated()}</td>
        	<td class="date_format_year">
        		<c:forEach items="${doneComments}" var = "doneComment">
        			<c:if test="${task.getId()==doneComment.getTaskId()}">${doneComment.getTs()}</c:if>
        		</c:forEach>
        	</td>
        </tr>
    </c:forEach>
</tbody>
</table>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <div class="modal-dialog not_big">
     <a title="Редактировать задание" class="edit_btn" id="edit_task">
          <span class="glyphicon glyphicon-edit modal_btns"></span>
    </a>
    <a title="Удалить задание" class="delete_btn" id="delete_task">
          <span class="glyphicon glyphicon-remove modal_btns"></span>
    </a>
    <a title="В выполненные" id="make_done_task">
          <span class="glyphicon glyphicon-ok modal_btns"></span>
    </a>
    <a title="Смена исполнителя" id="change_user">
          <span class="glyphicon glyphicon-user modal_btns"></span>
    </a>
     <form id="select_user" action="">
		<select id="users_distr"  class="form-control">
			<c:forEach items="${users}" var = "user">
				<option value="/WebApp/tasks/distribTask?status=распределено&userId=${user.getId()}" data-dismiss="modal" >${user.getLogin()}</option>
       		</c:forEach>
		</select>
	</form>
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

<div class="modal fade" id="distrib" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">Назначить исполнителя</div>
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
	<c:set value="${task}" var="task"/>
	<p>Id заявки: <span id = "task_id"></span></p>
   	<p>Дата создания: <span id = "created"></span></p>
   	<p>Абонент: <span id = "org_name"></span></p>
   	<p>Адрес: <span id = "address"></span></p>
   	<p>Тип: <span id = "type"></span></p>
    <p>Что сделать: <span id = "task_body"></span></p>
    <p>Выполнить до: <span id = "done_time"></span></p>
    <p>Статус: <span id = "status"></span></p>
    <p>Важность: <span id = "importance"></span></p>
    <p>Исполнитель: <span id = "user"></span></p>
	
	<h1>Комментарии</h1>
		<table id="com_table" class="comment_table table-striped">
		<thead>
			<tr>
				<th>Комментарий</th>
				<th>Автор</th>
				<th>Дата</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
		</table>

</div> 

<div class="contacts_modal">
	<h2>Контакты</h2>
</div>     		
      </div>
     </div>
 </div>
</div>



<script type="text/javascript">

//taskId
var taskIdFromMap = ${taskId};

//usersArray
var users = Array();
<c:forEach items="${users}" var="user">
	users.push({
		userId:'${user.getId()}',
		userLogin:'${user.getLogin()}'
	});
</c:forEach>

//пихаем заявки в массив на джаваскрипт
	var tasks = Array();
	<c:forEach items="${tasks}" var="task" varStatus="loop">
		tasks.push({
			taskId : '${task.getId()}',
			created : '${task.getCreated()}',
			importance : '${task.getImportance()}',
			body : '${task.getBody()}',
			status : '${task.getStatus()}',
			type : '${task.getType()}',
			doneTime : '${task.getDoneTime()}',
			address:'${task.getAddress()}',
			orgName:'${task.getOrgName()}',
			<c:forEach items="${users}" var = "user">
				<c:if test="${task.getUserId()==user.getId()}">user: '${user.getLogin()}'</c:if>
			</c:forEach>	
	});
	</c:forEach>
	

</script>
<script type="text/javascript" src="../js/myjs/changeDateFormat.js"></script>
<script type="text/javascript" src="../js/myjs/sortFilters.js"></script>
<script type="text/javascript" src="../js/myjs/statusSelect.js"></script>
<script type="text/javascript" src="../js/myjs/confirm.js"></script>
<script type="text/javascript" src="../js/myjs/tableSorter.js"></script>
<script type="text/javascript" src="../js/myjs/modalTask.js"></script>
<jsp:include page="../tmp/footer.jsp"></jsp:include>