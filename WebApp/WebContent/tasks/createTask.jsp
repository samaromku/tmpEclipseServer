 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../tmp/header.jsp"></jsp:include>

	<form class="editForm" action="createTask" method="post">
	<p>Абонент:
		<c:forEach items="${addresses}" var = "address">
				<c:choose>
					<c:when test="${address.getId()==addressId}">
						<span id="org_name">${address.getName()}</span>
					</c:when>
				</c:choose>
		</c:forEach>
		<c:choose>
			<c:when test="${addressId==null}">
				<span id="org_name">${addresses.get(0).getName()}</span>
			</c:when>
		</c:choose>
	</p>
	
	<p>Адрес:
		<select id="address" name="addressId">
    	<c:forEach items="${addresses}" var = "address">
    		<option value="${address.getId()}" ${addressId == address.getId()? 'selected' : ''}>${address.getAddress()}</option>
    	</c:forEach>
    </select>
	</p>
	
 	<p>Тип: 
		<select name = "type">
			<c:forEach items="${types}" var = "type">
    		<option value="${type}">${type}</option>
    	</c:forEach>
		</select>
	</p>
	
	 <p>
	 	Что сделать: <textarea class="task_body" name="body" rows="2" required/></textarea>
	 </p>
	 
	 <p>
	 	Выполнить до: <input type="datetime-local" min="${currentDate}" max="2020-11-16T21:25:33" name="doneTime" required/>
	 </p> 
	 
    <p>Важность: 
    
    <select name="importance">
    	<c:forEach items="${importances}" var = "importance">
    		<option value="${importance}">${importance}</option>
    	</c:forEach>
    </select>
    </p>
   
    <p>Статус: 
    
    <select id="status" name="status">
    	<c:forEach items="${statuses}" var = "status">
    		<option value="${status}">${status}</option>
    	</c:forEach>
    </select>
    </p>
 
    <p>Исполнитель: 
    <select id="executor" name="userName">
    	<c:forEach items="${userNames}" var = "userName">
    		<option value="${userName}">${userName}</option>
    	</c:forEach>
    </select>
</p>
	<input type="submit" value="Создать заявку">	
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