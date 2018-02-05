 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../tmp/header.jsp"></jsp:include>

<c:set value="${task}" var="task"/>
	<form class="editForm" action="editTask" method="post">
	<p>Id заявки: <input disabled="disabled" value="${task.getId()}"></p>
   	<p>Дата создания: <input disabled="disabled" name="created" value="${task.getCreated()}"/></p>
    
    <p>Абонент:
		<c:forEach items="${addresses}" var = "address">
				<c:choose>
					<c:when test="${address.getId()==addressId}">
						<span id="org_name">${address.getName()}</span>
					</c:when>
					<c:when test="${address.getId()==task.getAddressId()}">
						<span id="org_name">${address.getName()}</span>
					</c:when>
				</c:choose>
		</c:forEach>
	</p>
    
    <p>Адрес: 
    <select id="address" name = "address">
			<c:forEach items="${addresses}" var = "address">
    		<option value="${address.getId()}" ${task.getAddressId() == address.getId()? 'selected' : ''}>${address.getAddress()}</option>
    	</c:forEach>
	</select>
    </p>
    
    <p>Тип: 
		<select name = "type">
			<c:forEach items="${types}" var = "type">
    		<option value="${type}" ${task.getType() == type? 'selected' : ''}>${type}</option>
    	</c:forEach>
		</select>
	</p>
    
    <p>Что сделать: <textarea class="task_body" name="body" rows="2" required>${task.getBody()}</textarea></p>
    
    <p>Выполнить до: <input type="datetime-local"   name="doneTime" value="${doneTime}" min="${currentDate}" max="2020-11-16T21:25:33" required/></p>
    
    <p>Важность: 
    <select name="importance">
    	<c:forEach items="${importances}" var = "importance">
    		<option value="${importance}" ${task.getImportance() == importance? 'selected' : ''}>${importance}</option>
    	</c:forEach>
    </select>
    </p>
    
    <p>Статус: 
    
    <select id="status" name="status">
    	<c:forEach items="${statuses}" var = "status">
    		<option value="${status}" ${task.getStatus() == status? 'selected' : ''}>${status}</option>
    	</c:forEach>
    </select>
    </p>

    <p>Исполнитель: 
    <select id="executor" name="userName">
    	<c:forEach items="${users}" var = "user">
    		<option value="${user.getLogin()}" ${task.getUserId() == user.getId()? 'selected' : ''}>${user.getLogin()}</option>
    	</c:forEach>
    </select>
	</p>
	<input type="submit" value="Сохранить">	
	</form>
	
<script type="text/javascript">
var addresses = Array();
<c:forEach items="${addresses}" var = "address">
	addresses.push({
		id:'${address.getId()}',
		address:'${address.getAddress()}',
		name:'${address.getName()}'
	});
</c:forEach>

$("#address").on("change",
        function(){
            var a = $(this).find("option:selected").val();
            for(var i = 0; i<addresses.length; i++){
            	if(addresses[i].id==a){
            		$("#org_name").text(addresses[i].name);		
            	}
            }
});
</script>
<script src="../js/myjs/change_executor.js" type="text/javascript"></script>
<jsp:include page="../tmp/footer.jsp"></jsp:include>